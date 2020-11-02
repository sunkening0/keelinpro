package keelin.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @description: 模拟数据库连接池
 * @author: skn
 * @create: 2020-11-02 22:53
 */
public class DBPool {
    private static LinkedList<Connection> pool = new LinkedList<Connection>();

    public DBPool(int initalSize){//连接池链接数量初始值
        if(initalSize>0){
            for(int i=0;i<initalSize;i++){
                pool.addLast(SqlConnectImpl.fetchConnection());
            }
        }
    }

    /*
     *@Description:
     *@Param: [mills]  超时时间
     *@return: java.sql.Connection
     *@Author: skn
     *@date: 2020/11/2
     */
    public Connection fetchConn(long mills) throws InterruptedException {
        synchronized (pool){
            if(mills<0){  //没有设置超时时间的时候 一直等待
                while(pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                long overtime = System.currentTimeMillis() + mills;
                long remain = mills;
                while(pool.isEmpty()&&remain>0){
                    pool.wait(remain);
                    remain = overtime - System.currentTimeMillis();
                }
                Connection result = null;
                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }

        }
    }

    /*
     *@Description: 释放连接
     *@Param: [conn]
     *@return: void
     *@Author: skn
     *@date: 2020/11/2
     */
    public void releaseConn(Connection conn){
        if(conn != null){
            synchronized (pool){
                pool.addLast(conn);
                pool.notifyAll();
            }
        }
    }
}