package cn.dsx.main;

import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpHost;

import cn.dsx.db.DomainGenerate;
import cn.dsx.thread.CheckDomainRunnable;
import cn.dsx.util.GetProxy;

public class DomainScan {

	public static void main(String[] args) throws Exception {
		ArrayBlockingQueue<String> taskQueue = DomainGenerate.getTaskQueue();
		System.out.println("已经生成"+taskQueue.size()+"个domain");
		
		Vector<HttpHost> usableProxys = GetProxy.getProxy(1);
		System.out.println("获取到"+usableProxys.size()+"个代理ip");
		
		CheckDomainRunnable runnable = new CheckDomainRunnable(taskQueue,usableProxys);
		int threadNum = 5;
		ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);
		for(int i=1; i<=threadNum; i++){
			System.err.println("线程"+i+"开启。");
			threadPool.execute(runnable);
		}
	}

}
