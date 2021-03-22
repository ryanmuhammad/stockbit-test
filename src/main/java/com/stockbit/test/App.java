package com.stockbit.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stockbit.test.consumer.StockSubscriber;
import com.stockbit.test.service.ReadFileService;

public class App {

    public static void main(String[] args) throws JsonProcessingException {
        ReadFileService.readStock();
        StockSubscriber.process();
    }
}
