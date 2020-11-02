package keelin.pool;

import javafx.concurrent.Worker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 连接池测试
 * @author: skn
 * @create: 2020-11-02 22:54
 */
public class DBPoolTest {
    static DBPool pool = new DBPool(10);
    static CountDownLatch end; //控制器：用来控制主线程  等待 所有worker结束之后才继续执行

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 50;//线程数量
        end = new CountDownLatch(threadCount);
        int count = 20;//每个线程的操作次数

        AtomicInteger got = new AtomicInteger();//计数器：统计可以拿到连接的线程
        AtomicInteger notgot = new AtomicInteger();//计数器：统计不能拿到连接的线程

        for(int x=0;x<threadCount;x++){
            Thread thread = new Thread(new Worker(count,got,notgot));
            thread.start();
        }
        end.await();//主线程等待
        System.out.println("总共尝试了："+(threadCount*count));
        System.out.println("成功次数："+got);
        System.out.println("失败次数："+notgot);

    }

    static class Worker implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger notgot;

        public Worker(int count,AtomicInteger got,AtomicInteger notgot){
            this.count = count;
            this.got = got;
            this.notgot = notgot;
        }

        public void run() {
            while(count > 0){
                try {
                    Connection conn = pool.fetchConn(1000);
                    if(conn != null){
                        try {
                            conn.createStatement();
                            conn.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }finally{
                            pool.releaseConn(conn);
                            got.incrementAndGet();
                        }
                    }else{
                        notgot.incrementAndGet();
                        System.out.println(Thread.currentThread().getName() + "等待超时！");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    count--;
                }
            }
            end.countDown();
        }
    }
}