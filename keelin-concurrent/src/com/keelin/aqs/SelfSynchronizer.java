package com.keelin.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 实现一个自定义的 队列同步器  (独占型)
 */
public class SelfSynchronizer {
    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    public boolean unLock() {
        return sync.release(1);
    }

    static class Sync extends AbstractQueuedSynchronizer {
        //是否处于占用状态
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 获取同步资源
         * @param acquires
         * @return
         */
        @Override
        public boolean tryAcquire(int acquires) {
            if(compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            //这里没有考虑可重入锁
            /*else if (Thread.currentThread() == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }*/
            return false;
        }

        /**
         * 释放同步资源
         * @param releases
         * @return
         */
        @Override
        protected boolean tryRelease(int releases) {
            int c = getState() - releases;
            boolean free = false;
            if (c == 0) {
                free = true;
            }
            setState(c);
            return free;
        }
    }
}


