package com.keelin.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用显式锁的范式
 */
public class LockDemo {
    private Lock lock = new ReentrantLock();
    private int count;

    public void increment(){
        lock.lock();
        try{
            count++;
        }finally{
            lock.unlock();//一定要在finally中释放锁
        }
    }

    public synchronized void increment2(){
        count++;
    }
}
