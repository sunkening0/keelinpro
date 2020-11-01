package com.keelin.safeend;

/**
 * @description: 安全终端线程
 * @author: skn
 * @create: 2020-11-01 13:14
 */
public class EndRunnable {
    private static class UserRunnable implements Runnable{

        @Override
        public void run(){
            String threadName = Thread.currentThread().getName();//当前线程名
            while(!Thread.currentThread().isInterrupted()){  //如果此处不判断是否中断 那这个线程永远并不会中断
                System.out.println(threadName + " is running!");
            }
            System.out.println(threadName + " interruot flag is " + Thread.currentThread().isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UserRunnable runnable = new UserRunnable();
        Thread end = new Thread(runnable,"endThread");
        end.start();
        Thread.sleep(20);  //该方法运行在哪个线程   哪个线程就睡眠
        end.interrupt();
    }
}