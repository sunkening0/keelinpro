package keelin.utils;

/**
 * @description: 线程休眠工具类
 * @author: skn
 * @create: 2020-11-02 22:55
 */
public class SleepTools {
    public static void ms(long mils){
        try {
            Thread.sleep(mils);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}