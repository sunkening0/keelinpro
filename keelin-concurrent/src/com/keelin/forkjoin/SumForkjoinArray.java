package com.keelin.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * fork/join 分而治之  将一个大任务拆分成几个小任务（拆分原则是根据自定义的阀值（阈值），
 * （阀值和阈值这两个词是同一个意思，都指的是临界值，只是两种装逼的说法））  
 * 多线程运算   最后再将几个小任务的结果合并
 * 
 * 
 * 需注意：fork/join多线程运算不一定比单线程快的原因是  CPU时间片切换耗时
 * 
 * 继承RecursiveTask会有返回值
* 类名称：SumForkjoinArray   
* 类描述：统计整型数组中所有元素的和
* 创建人：skn   
* 创建时间：2019年10月1日 下午9:30:18   
* @version
 */
public class SumForkjoinArray {
	private static class SumTask extends RecursiveTask<Integer>{
		//阀值  例如：数组长度为10000，那么要按每组1000拆分数组  
		private final static int THREAD_HOLD=MakeArray.ARRAY_LENGTH/10;
		private int[] src;//实际要统计的数组   即长度为10000的大数组
		private int begin;//数组开始下标
		private int end;//数组结束下标
		
		public SumTask(int[] src,int begin,int end){
			this.src=src;
			this.begin=begin;
			this.end=end;
		}
		
		@Override
		protected Integer compute() {
			if(end-begin<=THREAD_HOLD){
				int sum = 0;
				for(int x=0;x<end;x++){
					sum = sum+src[x];
				}
				return sum;
			}else{//拆分子任务
				int mid = (begin+end)/2;
				SumTask left = new SumTask(src, begin, mid);
				SumTask right = new SumTask(src, mid+1, end);
				invokeAll(left,right);
				return left.join()+right.join();
			}
		}
		
	}
	
	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		int[] src=MakeArray.makeArray();
		SumTask task= new SumTask(src, 0, src.length-1);
		long t1=System.currentTimeMillis();
		pool.invoke(task);//invoke :同步调用方法  execute:异步调用
		System.out.println("数组和为："+task.join()+";  计算耗时="+(System.currentTimeMillis()-t1)+"ms");
	}
}
