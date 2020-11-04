package keelin.juc;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @description: 213
 * @author: skn
 * @create: 2020-11-04 20:56
 */
public class UserCyclicBarrier {
    private static CyclicBarrier barrier = new CyclicBarrier(5,new CollectorThread());

    private static ConcurrentHashMap<String,Long> resultMap = new ConcurrentHashMap<>();  //存放子线程工作结果的容器

    public static void main(String[] args) {
        for(int i=0;i<5;i++){
            Thread thread = new Thread(new SubThread());
            thread.start();
        }
    }

    //设置屏障开放后的工作线程
    public static class CollectorThread implements Runnable{

        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String,Long> workresult:resultMap.entrySet()){
                sb.append("["+workresult.getValue()+"]");
            }
            System.out.println("the result = "+sb.toString());
            System.out.println("do other business");
        }
    }

    //工作线程
    public static class SubThread implements Runnable{

        @Override
        public void run() {
            long id = Thread.currentThread().getId();

            resultMap.put(id+"",id);
            Random r = new Random();//随机决定工作线程是否睡眠

            try{
                if(r.nextBoolean()){
                    Thread.sleep(2000+id);
                    System.out.println("Thread_"+id+".....do something");
                }
                System.out.println(id+"....is await");
                barrier.await();
                Thread.sleep(1000+id);
                System.out.println("Thread_"+id+".....do its business");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}