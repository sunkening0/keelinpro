package com.keelin.safeend;

/**
 * @description: 安全的中断线程
 * @author: skn
 * @create: 2020-11-01 13:01
 */
public class EndThread {
    private static class UserThread extends Thread{
        public UserThread(String name){
            super(name);
        }

        @Override
        public void run(){
            String threadName = Thread.currentThread().getName();//当前线程名
            while(!isInterrupted()){  //如果此处不判断是否中断 那这个线程永远并不会中断
                System.out.println(threadName + " is running!");
            }
            System.out.println(threadName + " interruot flag is " + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread end = new UserThread("endThread");
        end.start();
        Thread.sleep(20);  //该方法运行在哪个线程   哪个线程就睡眠
        end.interrupt();
    }
}