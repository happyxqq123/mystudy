package com.apigateway.core;

import org.junit.Test;

import java.util.concurrent.*;

public class CompleableFutureTest {
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            50,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Test
    public void test1() throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> future2 =  CompletableFuture.supplyAsync(()->{
            System.out.println("task 2 "+Thread.currentThread().getName());

            return 10;
        },executor).whenCompleteAsync((res,throwable)->{
            System.out.println("res:"+res+","+Thread.currentThread().getName());
            System.out.println("throwable:"+throwable);
        },executor).exceptionally((e)->{
            System.out.println("e2="+e);
            return 100;
        });
        System.out.println(future2.get());
    }

    @Test
    public void test2() throws InterruptedException {
        CompletableFuture completableFuture = CompletableFuture.runAsync(()->{
            System.out.println("task1 thread:"+Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },executor).thenRunAsync(()->{
            System.out.println("task2 thread:"+Thread.currentThread().getName());
        },executor);

        CompletableFuture completableFuture2 = CompletableFuture.supplyAsync(()->{
            System.out.println("task11 thread:"+Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 100;
        },executor).thenAcceptAsync((res)->{
            System.out.println(res+",task22 thread:"+Thread.currentThread().getName());
        },executor);

        Thread.currentThread().join();
    }

}
