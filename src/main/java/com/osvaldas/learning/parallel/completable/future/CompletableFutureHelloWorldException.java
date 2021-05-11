package com.osvaldas.learning.parallel.completable.future;

import com.osvaldas.learning.parallel.service.HelloWorldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

import static com.osvaldas.learning.parallel.util.CommonUtil.*;

@Slf4j
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
                .handle((res, e) -> {
                    log.info("Res is: {}", res);
                    if (e != null) {
                        log.info("Exception after hello: {}", e.getMessage());
                        return "";
                    } else {
                        return res;
                    }
                })
                .thenCombine(world, String::concat)
                .handle((res, e) -> {
                    log.info("Res is: {}", res);
                    if (e != null) {
                        log.info("Exception after world: {}", e.getMessage());
                        return "";
                    } else {
                        return res;
                    }
                })
                .thenCombine(hiCompletableFuture, String::concat)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return result;
    }

    public String helloWorldMultipleAsyncExceptionally() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String result = hello
                .exceptionally((e) -> {
                    log.info("Exception after hello: {}", e.getMessage());
                    return "";
                })
                .thenCombine(world, String::concat)
                .exceptionally((e) -> {
                    log.info("Exception after world: {}", e.getMessage());
                    return "";
                })
                .thenCombine(hiCompletableFuture, String::concat)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return result;
    }

    public String helloWorldMultipleAsyncWhenHandle() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String result = hello
                .whenComplete((res, e) -> {
                    log.info("Res is: {}", res);
                    if (e != null) {
                        log.info("Exception after hello: {}", e.getMessage());
                    }
                })
                .thenCombine(world, String::concat)
                .whenComplete((res, e) -> {
                    log.info("Res is: {}", res);
                    if (e != null) {
                        log.info("Exception after world: {}", e.getMessage());
                    }
                })
                .exceptionally((e) -> {
                    log.info("Exception after thenCombine: {}", e.getMessage());
                    return "";
                })
                .thenCombine(hiCompletableFuture, String::concat)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return result;
    }
}
