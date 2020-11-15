package com.keelin.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


/**
 * 查找
 * 
 * 继承RecursiveAction   没有返回值
* 类名称：FindFile   
* 类描述：遍历指定目录（子目录） 寻找指定类型的文件
* 创建人：skn   
* 创建时间：2019年10月1日 下午10:09:16   
* @version
 */
public class FindFile extends RecursiveAction{
	private File path;  //当前任务
	
	public FindFile(File path){
		this.path = path;
	}
	
	@Override
	protected void compute() {
		List<FindFile> subTasks=new ArrayList<FindFile>();
		File[] files=path.listFiles();
		if(files!=null){
			for(File file:files){
				if(file.isDirectory()){
					subTasks.add(new FindFile(file));
				}else{
					//遇到文件
					if(file.getAbsolutePath().endsWith("txt")){
						System.out.println("文件："+file.getAbsolutePath());
					}
				}
			}
			if(!subTasks.isEmpty()){
				for(FindFile subtask:invokeAll(subTasks)){
					subtask.join();//等待子任务执行完成
				}
			}
			
		}
	}
	
	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		FindFile task=new FindFile(new File("D:/"));
		pool.execute(task);//异步方法
		//pool.invoke(task);
		task.join();//阻塞主线程   以防主线程执行很快完  但是异步方法execute还没有执行完   使用同步方法invoke不用写join
		
	}
}
