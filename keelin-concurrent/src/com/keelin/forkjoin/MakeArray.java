package com.keelin.forkjoin;

import java.util.Random;

/**
 * 生成随机数组 类名称：MakeArray 类描述： 创建人：skn 创建时间：2019年10月1日 下午9:21:37
 * 
 * @version
 */
public class MakeArray {
	// 数组长度
	public static final int ARRAY_LENGTH = 10000000;

	public static int[] makeArray() {
		Random random = new Random();
		int[] x=new int[ARRAY_LENGTH];
		for(int i=0;i<ARRAY_LENGTH;i++){
			x[i]=random.nextInt(ARRAY_LENGTH)*3;
		}
		return x;
	}

}
