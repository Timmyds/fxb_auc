package com.fxb.work.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;


public class PropertiesUtil {

	private static Properties properties = null;


	static {
		
		//使用文件的另一种输入流获取方式，修改配置之后，可以得到最新的信息
		String path = PropertiesUtil.class.getClassLoader().getResource("config.properties").getPath();  
		InputStream is;
		properties = new Properties(); 
		try {
			is = new FileInputStream(path);
			properties.load(is);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		
		//-------这种方式在应用启动之后，信息放在内存中缓存，修改需要重启服务
		/*InputStream in = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("properties/config.properties");
		properties = new Properties();
		try {
			properties.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	// 根据key读取value
	public static String get(String key) {
		String msg = properties.getProperty(key);
		try {
			return new String(msg.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	// 根据key设置value
	public static void set(String key, String content) {
		String filePath = PropertiesUtil.class.getResource(
				"config.properties").toString();
		// 截掉路径的”file:/“前缀
		filePath = filePath.substring(5);

		try {

			OutputStream fos = new FileOutputStream(filePath);
			properties.setProperty(key, content);
			// 保存，并加入注释
			properties.store(fos, new Date() + " Update '" + key + "' value");
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读TXT文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readTxtFile(File fileName) throws Exception {
		String result = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			String read = null;
			while ((read = bufferedReader.readLine()) != null) {
				result = result + read;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		System.out.println("读取出来的文件内容是：" + "\r\n" + result);
		return result;
	}

	public static boolean writeTxtFile(String content, File fileName)
			throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("UTF-8"));
			o.close();
			// mm=new RandomAccessFile(fileName,"rw");
			// mm.writeBytes(content);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}
	
	
}
