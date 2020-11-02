package keelin.forkjoin;

/**
 * 单线程测试一个数组的和
* 类名称：SumArray   
* 类描述：   
* 创建人：skn   
* 创建时间：2019年10月1日 下午9:20:06   
* @version
 */
public class SumArray {
	
	public static void main(String[] args) {
		long t1=System.currentTimeMillis();
		int sum = 0;
		int[] x=MakeArray.makeArray();
		for(int i=0;i<x.length;i++){
			sum = sum+x[i];
		}
		System.out.println("数组和为："+sum+";  计算耗时="+(System.currentTimeMillis()-t1)+"ms");
	}
	
}
