package com.osvaldas.learning.parallel.completable.future;

import com.osvaldas.learning.parallel.service.HelloWorldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public String helloWorldMultipleAsyncLogs() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });
        String result = hello.thenCombine(world, (h, w) -> {
            log.info("thenCombine h/w");
            return h + w;
        })
                .thenCombine(hiCompletableFuture, (a, b) -> {
                    log.info("thenCombine a/b");
                    return a + b;
                })
                .thenApply(s -> {
                    log.info("thenApply");
                    return s.toUpperCase();
                })
                .join();
        timeTaken();
        return result;
    }

    public String helloWorldMultipleAsyncCustomThreadPool() {
        startTimer();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world, executorService);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        }, executorService);
        String result = hello.thenCombine(world, (h, w) -> {
            log.info("thenCombine h/w");
            return h + w;
        })
                .thenCombine(hiCompletableFuture, (a, b) -> {
                    log.info("thenCombine a/b");
                    return a + b;
                })
                .thenApply(s -> {
                    log.info("thenApply");
                    return s.toUpperCase();
                })
                .join();
        timeTaken();
        return result;
    }

    public String helloWorldMultipleAsyncLogsAsync() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });
        String result = hello.thenCombineAsync(world, (h, w) -> {
            log.info("thenCombine h/w");
            return h + w;
        })
                .thenCombineAsync(hiCompletableFuture, (a, b) -> {
                    log.info("thenCombine a/b");
                    return a + b;
                })
                .thenApplyAsync(s -> {
                    log.info("thenApply");
                    return s.toUpperCase();
                })
                .join();
        timeTaken();
        return result;
    }

    public String helloWorldMultipleAsyncCustomThreadPoolAsync() {
        startTimer();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world, executorService);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        }, executorService);
        String result = hello.thenCombineAsync(world, (h, w) -> {
            log.info("thenCombine h/w");
            return h + w;
        }, executorService)
                .thenCombineAsync(hiCompletableFuture, (a, b) -> {
                    log.info("thenCombine a/b");
                    return a + b;
                }, executorService)
                .thenApplyAsync(s -> {
                    log.info("thenApply");
                    return s.toUpperCase();
                }, executorService)
                .join();
        timeTaken();
        return result;
    }

    public CompletableFuture<String> helloWorldCompose() {
        return CompletableFuture.supplyAsync(hws::hello)
                .thenCompose(hws::worldFuture)
                .thenApply(String::toUpperCase);
    }

    public String anyOf() {
        // DB
        CompletableFuture<String> db = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            log.info("Response from DB.");
            return "hello world";
        });

        // REST
        CompletableFuture<String> rest = CompletableFuture.supplyAsync(() -> {
            delay(2000);
            log.info("Response from REST.");
            return "hello world";
        });

        // SOAP
        CompletableFuture<String> soap = CompletableFuture.supplyAsync(() -> {
            delay(3000);
            log.info("Response from SOAP.");
            return "hello world";
        });

        List<CompletableFuture<String>> cfList = List.of(db, rest, soap, soap);

        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(cfList.toArray(new CompletableFuture[0]));
        return (String) anyOf.thenApply(v -> {
            if (v instanceof String) {
                return v;
            }
            return null;
        })
                .join();
    }
}
