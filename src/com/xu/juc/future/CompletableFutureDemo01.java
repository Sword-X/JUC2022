package com.xu.juc.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CompletableFutureDemo01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * Future接口：并行、异步
         * FutureTask实现类：异步、多线程、有返回值，实现Future和Runable接口，构造注入Callable接口
         */
        FutureTask<String> futureTask = new FutureTask<>(new MyThread());
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println(futureTask.get());
    }
}

class MyThread implements Callable<String>{
    @Override
    public String call() throws Exception {
        System.out.println(" ------");
        return "hello WorLd!!!";
    }
}
