package com.xu.juc.thread;

/**
 * 守护线程setDaemon，随着被守护线程结束而结束
 */
public class ThreadOne {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            for (int i = 0; i < 1000; i++) {
                System.out.println(i);
            }
        },"t1");
        t1.setDaemon(true);
        t1.start();
        System.out.println(Thread.currentThread().getName());
    }
}
