package com.anso.mall;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-03 9:53
 */
public class demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println(Thread.currentThread().getName() + "\t completableFuture");
                //int i = 10 / 0;
                return 1024;
            }
        }).thenApply(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer o) {
                System.out.println("thenApply方法，上次返回结果：" + o);
                return o * 2;
            }
        }).whenComplete(new BiConsumer<Integer, Throwable>() {
            @Override
            public void accept(Integer o, Throwable throwable) {
                System.out.println("-------o=" + o);
                System.out.println("-------throwable=" + throwable);
            }
        }).exceptionally(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) {
                System.out.println("throwable=" + throwable);
                return 6666;
            }
        });
        System.out.println(future.get());
    }
}
