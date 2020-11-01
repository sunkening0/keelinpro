package com.keelin.vola;

/**
 * @description: 测试volatile不安全
 * @author: skn
 * @create: 2020-11-01 15:21
 */
public class VolatileUnsafe {
    private static class VolatileVar implements Runnable{
        private volatile int a = 0;

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            a = a + 1;
            System.out.println(threadName + ":=====" + a);
            /*a = a + 1;
            System.out.println(threadName + ":=====" + a);*/
        }
    }

    public static void main(String[] args) {
        VolatileVar volatileVar = new VolatileVar();
        Thread t1 = new Thread(volatileVar);
        Thread t2 = new Thread(volatileVar);
        Thread t3 = new Thread(volatileVar);
        Thread t4 = new Thread(volatileVar);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}