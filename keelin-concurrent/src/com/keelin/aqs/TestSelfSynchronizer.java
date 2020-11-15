package com.keelin.aqs;

import java.util.concurrent.*;

public class TestSelfSynchronizer {
    private static int a = 0;
    private static int b = 0;
    private static SelfSynchronizer selfSynchronizer = new SelfSynchronizer();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 50, 1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    private static ExecutorService ec = Executors.newFixedThreadPool(20);
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 20 ; i++) {
            executor.submit(new Task());
        }

        for (int j = 0; j < 20 ; j++) {
            ec.submit(new TaskSync());
        }
        Thread.sleep(10000);
        System.out.println("a的值：" + a);
        System.out.println("b的值：" + b);
        executor.shutdown();
        ec.shutdown();
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            for(int i=0;i<10000;i++) {
                a++;
            }
        }
    }

    static class TaskSync implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                //使用同步器加锁
                selfSynchronizer.lock();
                b++;
                selfSynchronizer.unLock();
            }
        }
    }
}
