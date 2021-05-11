package com.osvaldas.learning.parallel.service;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

import static com.osvaldas.learning.parallel.util.CommonUtil.delay;

@Slf4j
public class HelloWorldService {

    public  String helloWorld() {
        delay(1000);
        log.info("inside helloWorld");
        return "hello world";
    }

    public  String hello() {
        delay(1000);
        log.info("inside hello");
        return "hello";
    }

    public  String world() {
        delay(1000);
        log.info("inside world");
        return " world!";
    }

    public CompletableFuture<String> worldFuture(String input) {
        return CompletableFuture.supplyAsync(()->{
            delay(1000);
            return input+" world!";
        });
    }

}
