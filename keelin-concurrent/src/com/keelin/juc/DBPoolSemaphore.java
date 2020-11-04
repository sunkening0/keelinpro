package keelin.juc;

import keelin.pool.SqlConnectImpl;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * @description: 信号量测试
 * @author: skn
 * @create: 2020-11-04 21:53
 */
public class DBPoolSemaphore {
    private static final int pool_size = 10;
    private static LinkedList<Connection> pool = new LinkedList<>();
    private final Semaphore useful,useless;//useful：可用数据库连接  useless：医用数据库连接

    public DBPoolSemaphore(){
        this.useful = new Semaphore(pool_size);
        this.useless = new Semaphore(0);
    }

    //初始化连接池
    static{
        for(int i=0;i<10;i++){
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    //归还连接
    public void returnConnect(Connection connection) throws InterruptedException {
        if(connection!=null){
            useless.acquire();
            synchronized (pool){
                pool.addLast(connection);
            }
            useful.release();
        }
    }

    //获取连接
    public Connection takeConnection() throws InterruptedException {
        useful.acquire();//在提供一个许可前线程会阻塞在这里
        Connection conn;
        synchronized(pool){
            conn = pool.removeFirst();
        }
        useless.release();
        return conn;
    }

}