package com.stockbit.test.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stockbit.test.config.CommonUtils;
import com.stockbit.test.model.Stock;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.javatuples.Quartet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

public class StockSubscriber {

    private static Map<String, Quartet<String, String, Integer, Integer>> dataSummaries = new LinkedHashMap<>();

    public static void process() throws JsonProcessingException {
        Consumer<String, String> consumer = ConsumerCreator.createConsumer();
        final int giveUp = 1;
        int noRecordsCount = 0;
        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : consumerRecords) {
                Stock stock = CommonUtils.mapper().readValue(record.value(), Stock.class);
                updateStocks(stock);
                consumer.commitAsync();
            }
            if (consumerRecords.isEmpty()) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) {
                    System.out.println(dataSummaries.size());
                    writeSummarize();
                    break;
                } else continue;
            }
        }
    }

    static synchronized void updateStocks(Stock stock) {
        String time = String.join("", stock.getTransactionTime().toLocalTime().toString(), ":00");
        String key = String.join("-", stock.getStockSymbol(), time);
        int highestPrice = stock.getTradePrice(), lowestPrice = stock.getTradePrice();

        if (dataSummaries.get(key) != null) {
            highestPrice = Math.max(dataSummaries.get(key).getValue2(), stock.getTradePrice());
            lowestPrice = Math.min(dataSummaries.get(key).getValue3(), stock.getTradePrice());
        }

        Quartet<String, String, Integer, Integer> summaryStocks = Quartet.with(time, stock.getStockSymbol(), highestPrice, lowestPrice);
        dataSummaries.put(key, summaryStocks);
    }

    static void writeSummarize() {
        File csvOutputFile = new File("src/main/resources/summarize.txt");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataSummaries.entrySet().stream()
                    .map(data -> convertToCSV(data.getValue()))
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            System.out.println(e.getCause());
        }
    }

    private static String convertToCSV(Quartet<String, String, Integer, Integer> data) {
        return String.join("|", data.getValue0(), data.getValue1(), "high;"+data.getValue2(), "low;"+data.getValue3());
    }
}
