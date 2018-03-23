package com.pinhuba.common.code.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveFile {
	
	private final static Logger logger = LoggerFactory.getLogger(SaveFile.class);
	
	public static void writeFile(String path,String context) throws Exception{
		File f =new File(path); 
		OutputStreamWriter write = null;
		if (!f.isDirectory()) {
			f.mkdirs();
		}
		if (f.exists()) {
			f.delete();
		}
		f.createNewFile();
		write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
	   
		try {
			write.write(context);
			write.flush();
			logger.info("{} 写入成功！",path);
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		}finally{
			if (write!=null) {
				write.close();
			}
		}
	}
	
	
	/**
	 * 追加文件：使用FileWriter
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void addToFile(String fileName, String content) {
		FileWriter writer = null;
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(fileName, true);
			writer.write(content);
			logger.info("配置文件已复制到 {} 文件",fileName);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
