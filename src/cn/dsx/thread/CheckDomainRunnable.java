package cn.dsx.thread;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.http.HttpHost;

import cn.dsx.util.GetProxy;
import cn.dsx.util.MyHttpClient;
import cn.dsx.util.MyLog;

public class CheckDomainRunnable implements Runnable {

	private ArrayBlockingQueue<String> taskQueue;
	private String domain,url,result;
	private Vector<HttpHost> usableProxys;
	private Vector<HttpHost> unUsableProxys = new Vector<>();
	private HttpHost proxy;
	private Integer index = 0;
	
	public CheckDomainRunnable(ArrayBlockingQueue<String> taskQueue,Vector<HttpHost> usableProxys) {
		this.taskQueue = taskQueue;
		this.usableProxys = usableProxys;
	}
	
	@Override
	public void run() {
		while(( domain=taskQueue.poll() ) != null){
			url = "http://panda.www.net.cn/cgi-bin/check.cgi?area_domain="+domain;
			proxy = usableProxys.get(index);
			
			try {
				MyHttpClient client = new MyHttpClient();
				client.setProxy(proxy);
				client.addHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
				result = client.get(url,"gb2312");
				if(result.contains("<original>210")){ 
					System.out.println(Thread.currentThread().getName()+"--Î´×¢²á-->"+domain);
					MyLog.log(domain);
				} else if(result.contains("<original>211")   || result.contains("<original>212")  || result.contains("<original>214")){  
			    	System.err.println(Thread.currentThread().getName()+"--ÒÑ×¢²á-->"+domain);
			    } else {  
			    	usableProxys.remove(proxy);
			    	unUsableProxys.addElement(proxy);
			        System.err.println(Thread.currentThread().getName()+"-->IP±»·â-->"+proxy.toString()); 
			    }
			}  catch (Exception e){
				System.err.println(Thread.currentThread().getName()+"-->Òì³£-->"+proxy.toString()); 
				usableProxys.remove(proxy);
		    	unUsableProxys.addElement(proxy);
		    	taskQueue.add(domain);
			}
			
			index++;
			if(index>=usableProxys.size()){
				index = 0;
			}
			if(usableProxys.size()<30){
				try {
					HashSet<HttpHost> hosts = new HashSet<>(GetProxy.getProxy(1));
					Iterator<HttpHost> iterator = hosts.iterator();
					while (iterator.hasNext()) {
						HttpHost next = iterator.next();
						if(!unUsableProxys.contains(next) && !usableProxys.contains(next)){
							usableProxys.add(proxy);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
