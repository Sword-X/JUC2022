package com.xu.juc.thread;

public class ThreadTwo implements Runnable{
    @Override
    public void run() {
        System.out.println("111");
    }

    public static void main(String[] args) {
        ThreadTwo threadTwo = new ThreadTwo();
        new Thread(threadTwo).start();
    }
}
