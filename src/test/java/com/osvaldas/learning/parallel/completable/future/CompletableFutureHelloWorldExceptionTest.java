package com.osvaldas.learning.parallel.completable.future;

import com.osvaldas.learning.parallel.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

    @Mock
    HelloWorldService helloWorldService;

    @InjectMocks
    CompletableFutureHelloWorldException completableFutureHelloWorldException;

    @Test
    void testHelloWorldMultipleAsyncHandle_whenThrowHello() {
        //given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = completableFutureHelloWorldException.helloWorldMultipleAsyncHandle();

        //then
        assertThat(result).isEqualTo(" WORLD! HI COMPLETABLEFUTURE!");
    }

    @Test
    void testHelloWorldMultipleAsyncHandle_whenThrowBoth() {
        //given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        //when
        String result = completableFutureHelloWorldException.helloWorldMultipleAsyncHandle();

        //then
        assertThat(result).isEqualTo(" HI COMPLETABLEFUTURE!");
    }

    @Test
    void testHelloWorldMultipleAsyncHandle_whenNotThrow() {
        //given
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = completableFutureHelloWorldException.helloWorldMultipleAsyncHandle();

        //then
        assertThat(result).isEqualTo("HELLO WORLD! HI COMPLETABLEFUTURE!");
    }

    @Test
    void testHelloWorldMultipleAsyncExceptionally_whenNotThrow() {
        //given
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = completableFutureHelloWorldException.helloWorldMultipleAsyncExceptionally();

        //then
        assertThat(result).isEqualTo("HELLO WORLD! HI COMPLETABLEFUTURE!");
    }

    @Test
    void testHelloWorldMultipleAsyncExceptionally_whenThrowBoth() {
        //given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        //when
        String result = completableFutureHelloWorldException.helloWorldMultipleAsyncExceptionally();

        //then
        assertThat(result).isEqualTo(" HI COMPLETABLEFUTURE!");
    }

    @Test
    void testHelloWorldMultipleAsyncWhenHandle_whenNotThrow() {
        //given
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = completableFutureHelloWorldException.helloWorldMultipleAsyncWhenHandle();

        //then
        assertThat(result).isEqualTo("HELLO WORLD! HI COMPLETABLEFUTURE!");
    }

    @Test
    void testHelloWorldMultipleAsyncWhenHandle_whenThrow() {
        //given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        //when
        String result = completableFutureHelloWorldException.helloWorldMultipleAsyncWhenHandle();

        //then
        assertThat(result).isEqualTo(" HI COMPLETABLEFUTURE!");
    }
}