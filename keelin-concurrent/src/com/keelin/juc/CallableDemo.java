package keelin.juc;

import java.util.concurrent.*;

/**
 * @description: Callable
 * @author: skn
 * @create: 2020-11-05 20:36
 */
public class CallableDemo {

    private static class Task implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            Thread.sleep(3000);
            int sum = 0;
            for(int i=0;i<100;i++)
                sum += i;
            return sum;
        }

    }

    public static void main(String[] args) {
        //创建线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        //创建Callable对象任务
        Task task = new Task();
        //提交任务并获取执行结果
        Future<Integer> result = executor.submit(task);
        //关闭线程池
        executor.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        System.out.println("主线程在执行任务");

        try {
            if(result.get()!=null){
                System.out.println("task运行结果"+result.get());
            }else{
                System.out.println("未获取到结果");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }  catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }



}