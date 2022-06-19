package com.xu.juc.future;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 线程池get方法：2177毫秒
 */
public class CompletableFutureDemo03 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, Exception {
        long time = new Date().getTime();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
//                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AAA");
        executorService.submit(futureTask);
        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
//                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BBB");
        executorService.submit(futureTask2);
        System.out.println(futureTask.get());
        System.out.println(futureTask2.get());
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println(Thread.currentThread().getName()+"\tend");
        System.out.println(new Date().getTime()-time+"毫秒");
        executorService.shutdown();
    }
}
