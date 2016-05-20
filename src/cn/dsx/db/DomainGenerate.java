package cn.dsx.db;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author ������
 */
public class DomainGenerate {

	//�����ö����������Ϻõ�����
	private static ArrayBlockingQueue<String> taskQueue = new ArrayBlockingQueue<String>(163620); 	
	//������׺
	private static String DOMAINNAMESUFFIX = ".cn";	
	private static final String[] Letter = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
            "x", "y", "z", };

	static {
		try {
			int length = Letter.length;
			//�������
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
