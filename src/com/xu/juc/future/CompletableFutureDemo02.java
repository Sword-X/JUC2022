package com.xu.juc.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, Exception {
        /**
         * get方法会阻塞任务（放在最后）；isDone判断是否完成，轮询会占用CPU资源
         */
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AAA");
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println(Thread.currentThread().getName());
        while (!futureTask.isDone()){
            System.out.println("正在执行中。。。");
            TimeUnit.MILLISECONDS.sleep(1000);
        }
        System.out.println(futureTask.get());
    }
}
