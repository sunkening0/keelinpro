package com.keelin;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @description: 123
 * @author: skn
 * @create: 2020-11-01 10:41
 */
public class OnlyMain {
    public static void main(String[] args) {
        //虚拟机线程管理的接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //获取所有线程
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false,false);
        for(ThreadInfo threadInfo:threadInfos){  //其实这几个线程都是守护线程】
            System.out.println("["+threadInfo.getThreadId()+"] "+threadInfo.getThreadName());
        }
    }
}