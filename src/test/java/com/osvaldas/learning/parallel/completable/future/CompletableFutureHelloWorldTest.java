package com.osvaldas.learning.parallel.completable.future;

import com.osvaldas.learning.parallel.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.osvaldas.learning.parallel.util.CommonUtil.startTimer;
import static com.osvaldas.learning.parallel.util.CommonUtil.timeTaken;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CompletableFutureHelloWorldTest {

    HelloWorldService helloWorldService = new HelloWorldService();
    CompletableFutureHelloWorld helloWorldCompletableFuture = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void testHelloWorld() {
        //when
        CompletableFuture<String> completableFuture = helloWorldCompletableFuture.helloWorld();

        //then
        completableFuture.thenAccept(s -> assertThat(s).isEqualTo("HELLO WORLD")).join();
    }

    @Test
    void testHelloWorldAsync() {
        //when
        String helloWorld = helloWorldCompletableFuture.helloWorldAsync();

        //then
        assertThat(helloWorld).isEqualTo("HELLO WORLD!");
    }

    @Test
    void testHelloWorldMultipleAsync() {
        //when
        String helloWorld = helloWorldCompletableFuture.helloWorldMultipleAsync();

        //then
        assertThat(helloWorld).isEqualTo("HELLO WORLD! HI COMPLETABLEFUTURE!");
    }

    @Test
    void testHelloWorldCompose() {
        startTimer();
        //when
        CompletableFuture<String> completableFuture = helloWorldCompletableFuture.helloWorldCompose();

        //then
        completableFuture.thenAccept(s -> assertThat(s).isEqualTo("HELLO WORLD!")).join();
        timeTaken();
    }

    @Test
    void testHelloWorldMultipleAsyncLogs() {
        //when
        String helloWorld = helloWorldCompletableFuture.helloWorldMultipleAsyncLogs();

        //then
        assertThat(helloWorld).isEqualTo("HELLO WORLD! HI COMPLETABLEFUTURE!");
    }

    @Test
    void testHelloWorldMultipleAsyncCustomThreadPool() {
        //when
        String helloWorld = helloWorldCompletableFuture.helloWorldMultipleAsyncCustomThreadPool();

        //then
        assertThat(helloWorld).isEqualTo("HELLO WORLD! HI COMPLETABLEFUTURE!");
    }

    @Test
    void testHelloWorldMultipleAsyncLogsAsync() {
        //when
        String helloWorld = helloWorldCompletableFuture.helloWorldMultipleAsyncLogsAsync();

        //then
        assertThat(helloWorld).isEqualTo("HELLO WORLD! HI COMPLETABLEFUTURE!");
    }

    @Test
    void testHelloWorldMultipleAsyncCustomThreadPoolAsync() {
        //when
        String helloWorld = helloWorldCompletableFuture.helloWorldMultipleAsyncCustomThreadPoolAsync();

        //then
        assertThat(helloWorld).isEqualTo("HELLO WORLD! HI COMPLETABLEFUTURE!");
    }

    @Test
    void testAnyOf() {
        //when
        String helloWorld = helloWorldCompletableFuture.anyOf();

        //then
        assertThat(helloWorld).isEqualTo("hello world");
    }
}