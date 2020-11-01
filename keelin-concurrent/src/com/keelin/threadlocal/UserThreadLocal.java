package com.keelin.threadlocal;

/**
 * @description: 测试ThreadLocal
 * @author: skn
 * @create: 2020-11-01 15:28
 */
public class UserThreadLocal {
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        /*protected T initialValue() {
            return null;
        }*/
        @Override
        protected Integer initialValue() {
            return 1; //设置初始值
        }
    };

    public static class TestThreadLocal extends Thread{
        int id;
        public TestThreadLocal(int id){
            this.id = id;
        }

        public void run(){
            System.out.println();
            Integer x = threadLocal.get();
            x = x + 1;
            threadLocal.set(x);

            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());

            //threadLocal.remove();  //显式调用remove并不是必须的  只是为了加速垃圾回收
        }
    }

    public void startThread(){
        Thread[] threads = new Thread[3];
        for(int x = 0; x < threads.length; x++){
            threads[x] = new Thread(new TestThreadLocal(x));
        }
        for(int x = 0; x < threads.length; x++){
            threads[x].start();
        }
    }

    public static void main(String[] args) {
        UserThreadLocal userThreadLocal = new UserThreadLocal();
        userThreadLocal.startThread();
    }
}