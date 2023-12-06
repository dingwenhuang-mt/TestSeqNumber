package com.example.springbootmall0726.controller;

import com.example.springbootmall0726.service.SeqNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class SeqNumberController {

    @Autowired
    private SeqNumberService seqNumberService;

    private static final Logger log = LoggerFactory.getLogger(SeqNumberController.class);

    @GetMapping("/testForSeqNumber")
    public void testForSeqNumber() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    seqNumberService.doTestSeqNumber(100);
                } catch (Exception e) {
                    log.warn("Error is : " + e);
                }
            }
        };

        for (int i = 0; i < 100; i++) {
            executorService.execute(runnable);
        }

        log.info("測試結束");
        executorService.shutdown();
    }

    @GetMapping("/test")
    public String test() {
        return "You are in test";
    }
}
