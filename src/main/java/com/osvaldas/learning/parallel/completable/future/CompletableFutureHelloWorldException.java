package com.osvaldas.learning.parallel.completable.future;

import com.osvaldas.learning.parallel.service.HelloWorldService;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

import static com.osvaldas.learning.parallel.util.CommonUtil.*;

@RequiredArgsConstructor
public class CompletableFutureHelloWorldException {
    private final HelloWorldService hws;

    public String helloWorldMultipleAsyncHandle() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String result = hello
                .handle()
                .thenCombine(world, String::concat)
                .thenCombine(hiCompletableFuture, String::concat)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return result;
    }
}
