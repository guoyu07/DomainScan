package cn.dsx.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MyLog {
	private static BufferedWriter writer;
	
	static {
		try {
			String path = System.getProperty("user.dir");
			writer = new BufferedWriter(new FileWriter(path+"\\log.txt",true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void log(String message){
		try {
			writer.write(message);
			writer.newLine();   
	    	writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
