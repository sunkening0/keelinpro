package com.keelin.safeend;

/**
 * @description: 测试sleep的时间会不会叠加  （经验证  会叠加）
 * @author: skn
 * @create: 2020-11-01 13:37
 */
public class TestSleep {
    public static void main(String[] args) throws InterruptedException {
        Long t1 = System.currentTimeMillis();
        System.out.println(t1);
        Thread.sleep(100);
        Long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);

        Long t3 = System.currentTimeMillis();
        for(int x=0;x<5;x++){
            Thread.sleep(100);
        }
        Long t4 = System.currentTimeMillis();
        System.out.println(t4-t3);
    }
}