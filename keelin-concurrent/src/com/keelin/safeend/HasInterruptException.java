package com.keelin.safeend;

/**
 * @description: 抛出异常
 * @author: skn
 * @create: 2020-11-01 13:19
 */
public class HasInterruptException {
    private static class UserThread extends Thread{
        public UserThread(String name){
            super(name);
        }

        @Override
        public void run(){
            String threadName = Thread.currentThread().getName();//当前线程名

            while(!isInterrupted()){  //如果此处不判断是否中断 那这个线程永远并不会中断
                try {
                    Thread.sleep(100);  //线程sleep的时间会叠加
                } catch (InterruptedException e) {
                    System.out.println(threadName + " interruot flag is " + isInterrupted()); //抛出interruptException之后 标志位会直接置为false
                    interrupt();  //这里手动调用该方法 才能使线程中断
                    e.printStackTrace();
                }
                System.out.println(threadName);
            }
            System.out.println(threadName + " interruot flag is " + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread end = new HasInterruptException.UserThread("endThread");
        end.start();
        Thread.sleep(20);  //该方法运行在哪个线程   哪个线程就睡眠
        end.interrupt();
    }
}