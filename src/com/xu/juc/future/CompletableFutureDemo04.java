package com.xu.juc.future;

import java.util.Date;
import java.util.concurrent.*;

/**
 * CompletableFutable是Future的功能增强版,默认线程池ForkJoinPool为守护线程
 *      减少阻塞和轮询
 *      可以传入回调对象，当异步任务完成或失败时，自动调用回调对象的回调方法
 */
public class CompletableFutureDemo04 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, Exception {
        future4();
    }

    // 自定义线程池，需手动关闭
    private static void future4() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        try {
            CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName()+"--- start");
                int result = ThreadLocalRandom.current().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1s 出结果：" + result);
                if (result > 2){
                    int i = 1/0;
                }
                return result;
            },executorService).whenComplete((r,e) -> {
                if (e == null){
                    System.out.println("程序运行正常:"+r);
                }
            }).exceptionally(e -> {
                e.printStackTrace();
                System.out.println(e.getCause()+"\t"+e.getMessage());
                return null;
            });
            System.out.println(Thread.currentThread().getName()+"执行其他任务了。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    // supply返回值方法，不带线程池，默认守护线程
    private static void future3() {
        try {
            CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName()+"--- start");
                int result = ThreadLocalRandom.current().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1s 出结果：" + result);
                return result;
            }).whenComplete((r,e) -> {
                if (e == null){
                    System.out.println("程序运行正常:"+r);
                }
            }).exceptionally(e -> {
                System.out.println(e.getCause()+"\t"+e.getMessage());
                return null;
            });
            System.out.println(Thread.currentThread().getName()+"执行其他任务了。。。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 使用自定义线程池
    private static void future2() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
        },executorService);
        System.out.println(voidCompletableFuture.get());
        executorService.shutdown();
    }

    // 传递一个runable参数，默认使用ForkJoinPool
    private static void future1() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        System.out.println(voidCompletableFuture.get());
    }
}
