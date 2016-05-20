package cn.dsx.db;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author 杜世翔
 */
public class DomainGenerate {

	//这里用队列来存放组合好的域名
	private static ArrayBlockingQueue<String> taskQueue = new ArrayBlockingQueue<String>(163620); 	
	//域名后缀
	private static String DOMAINNAMESUFFIX = ".cn";	
	private static final String[] Letter = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
            "x", "y", "z", };

	static {
		try {
			int length = Letter.length;
			//组合域名
			for(int i=0; i<length; i++){
				for(int j=0; j<length; j++){
					for(int k=0; k<length; k++){
						String domain = Letter[i]+Letter[j]+Letter[k]+DOMAINNAMESUFFIX;
						taskQueue.add(domain);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayBlockingQueue<String> getTaskQueue(){
		return taskQueue;
	}
}
