package keelin.juc;

import keelin.utils.SleepTools;

import java.sql.Connection;
import java.util.Random;

/**
 * @description: 测试
 * @author: skn
 * @create: 2020-11-04 22:02
 */
public class TestDBPoolSemaphore {
    private static DBPoolSemaphore dbpool = new DBPoolSemaphore();

    //业务线程
    private static class BusiThread implements Runnable{

        @Override
        public void run() {
            Random r = new Random();//让每个线程持有连接的时间不一样
            long start = System.currentTimeMillis();
            try{
                Connection conn = dbpool.takeConnection();
                System.out.println("Thread_"+Thread.currentThread().getId()+"_获取连接耗时【"+(System.currentTimeMillis()-start)+"】ms.");
                SleepTools.ms(100+r.nextInt(100));//模拟业务操作  业务执行时间
                dbpool.returnConnect(conn);
            }catch (Exception e){

            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<50;i++){
            Thread thread = new Thread(new BusiThread());
            thread.start();
        }
    }
}