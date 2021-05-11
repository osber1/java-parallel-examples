package com.osvaldas.learning.parallel.completable.future;

import com.osvaldas.learning.parallel.service.HelloWorldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

import static com.osvaldas.learning.parallel.util.CommonUtil.*;

@Slf4j
@RequiredArgsConstructor
public class CompletableFutureHelloWorld {
    private final HelloWorldService hws;

    public static void main(String[] args) {
    }

    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.supplyAsync(hws::helloWorld)
                .thenApply(String::toUpperCase);
    }

    public String helloWorldSeparateWords() {
        String hello = hws.hello();
        String world = hws.world();
        return hello + world;
    }

    public String helloWorldAsync() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
        String result = hello.thenCombine(world, String::concat)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return result;
    }

    public String helloWorldMultipleAsync() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });
        String result = hello.thenCombine(world, String::concat)
                .thenCombine(hiCompletableFuture, String::concat)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return result;
    }

    public CompletableFuture<String> helloWorldCompose() {
        return CompletableFuture.supplyAsync(hws::hello)
                .thenCompose(hws::worldFuture)
                .thenApply(String::toUpperCase);
    }
}
