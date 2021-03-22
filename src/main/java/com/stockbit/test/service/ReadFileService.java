package com.stockbit.test.service;

import com.stockbit.test.App;
import com.stockbit.test.model.Stock;
import com.stockbit.test.producer.Publisher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReadFileService {

    public static void readStock() {
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream("test.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            br.lines().skip(1).forEach(line -> {
                String[] data = line.split("\\|");
                LocalDateTime transactionTime = formatStringToLocalDateTime(data[0], "yyyy-MMM-dd HH:mm:ss");
                Stock stock = new Stock();
                stock.setTransactionTime(transactionTime);
                stock.setStockSymbol(data[1]);
                stock.setTradePrice(Integer.valueOf(data[2]));

                Publisher.publishMessage(stock, stock.getStockSymbol());
            });
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
    }

    private static LocalDateTime formatStringToLocalDateTime(String datetime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(datetime, formatter).withSecond(0);
    }
}
