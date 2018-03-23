/**
 * @author 
 * @version 1.0 
 * 
 */
package com.pinhuba.common.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;
//import org.apache.tools.zip.ZipOutputStream;

/**
 * lLLlIJ.
 * 
 * <p>
 * <b>Company</b>ShangHai KOAL SoftWare CO.,LTD.
 * </p>
 * <p>
 * <b>Author</b>
 * </p>
 * <p>
 * <b>Date</b>2006.06.26
 * </p>
 * 
 * @see FileToolTest
 * @see FileToolTests
 */

public class FileTool {
	private static Logger log = Logger.getLogger("FileTool");

	/**
	 * i'ilδ'′Iρ'
	 * 
	 * @param filePathAndName
	 *            ·l
	 * @param encoding
	 *            ili'
	 * @return ilnull
	 */
	public static String readTxt(String filePathAndName, String encoding) {
		log.info("...");
		if (filePathAndName == null) {
			return null;
		}

		// ^YL
		StringBuffer strBuff = new StringBuffer();

		log.info("l...");
		File f = new File(filePathAndName);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);

			InputStreamReader isr = null;
			if (encoding == null) {
				log.info("′Iρ'");
				isr = new InputStreamReader(fis);
			} else {
				log.info("′" + encoding + "'");
				isr = new InputStreamReader(fis, encoding);
			}
			BufferedReader br = new BufferedReader(isr);

			log.info("’l...");
			String data = br.readLine();
			while (data != null) {
				strBuff.append(data);
				data = br.readLine();
				if (data != null) {
					strBuff.append("\n");
				}
			}
			log.info("l");
		} catch (Exception e) {
			return null;
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				// 
			}
		}

		return strBuff.toString();
	}

	/**
	 *  1/2 L 1/4 
	 * 
	 * @param folderPath
	 *            L 1/4 
	 * @return FilelжnullfolderPath·
	 */
	public static File createFolder(String folderPath) {
		if (folderPath == null) {
			return null;
		}

		File f = null;
		try {
			f = new File(folderPath);
			if (!f.exists()) {
				f.mkdirs();
			}
		} catch (Exception e) {
			return null;
		}

		if (f.exists()) {
			return f;
		} else {
			return null;
		}
	}

	// llеl
	private static FileFilter fileFilter = new FileFilter() {
		public boolean accept(File f) {
			return f.isDirectory();
		}
	};

	/**
	 * 
	 * @param path
	 * @param extend
	 * @return
	 */
	public static List getSpecialFileList(String path, String extend) {
		// Ч
		if (path == null) {
			return null;
		}
		if (extend == null) {
			extend = ".xml";
		}
		log.info("Ч");

		File f = new File(path);

		return getSpecialFileList(f, extend);
	}

	/**
	 * 
	 * @param pathname
	 * @param extend
	 * @return
	 */
	public static List getSpecialFileList(File f, String extend) {
		// Ч
		if (f == null) {
			return null;
		}
		if (extend == null) {
			extend = ".xml";
		}
		log.info("Ч");

		if (!f.isDirectory()) {
			return null;
		}
		// 
		Vector list = new Vector(20, 5);
		list.add(f.getAbsolutePath() + extend);

		// l
		File[] fList = f.listFiles(fileFilter);
		if (fList == null) {
			return null;
		}
		for (int i = 0; i < fList.length; i++) {
			List tempList = getSpecialFileList(fList[i], extend);
			if (tempList != null) {
				list.addAll(tempList);
			}
		}

		return list;
	}

	/**
	 * l
	 * 
	 * @param src
	 * @param dst
	 */
	public static void copyFile(String src, String dst) {
		File srcFile = new File(src);
		if (!srcFile.exists()) {
			throw new RuntimeException("l" + srcFile);
		}

		try {
			FileInputStream inputStream = new FileInputStream(srcFile);
			FileOutputStream outputStream = new FileOutputStream(new File(dst));
			byte[] readBytes = new byte[1024];
			int readbyte = inputStream.read(readBytes);
			while (readbyte != -1) {
				// l
				outputStream.write(readBytes, 0, readbyte);
				outputStream.flush();
				readbyte = inputStream.read(readBytes);
			}
			// 
			inputStream.close();
			outputStream.close();
			srcFile.delete();
		} catch (Exception e1) {
			srcFile.delete();
			e1.printStackTrace();
		}
	}

	/**
	 * l
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFilePostfix(String fileName) {
		if (fileName == null) {
			throw new RuntimeException("l");
		}

		int pos = fileName.lastIndexOf(".");
		if (pos == -1) {
			throw new RuntimeException("l");
		}

		return fileName.substring(pos);
	}

	/**
	 * l
	 * 
	 * @param dirPath
	 * @return mkDirs FileTool
	 */
	public static boolean mkDirs(final String dirPath) {
		File dir = new File(dirPath);
		try {
			if (!dir.exists()) {
				return dir.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * l
	 * 
	 * @param fileList
	 *            lб
	 * @param fileZipName
	 *            l
	 * @return
	 */
	public static String zip(File[] fileList, String fileZipName) {
//		ZipOutputStream out = null;
//		FileInputStream in = null;
//		try {
//			out = new ZipOutputStream(new FileOutputStream(fileZipName));
//
//			for (int i = 0; i < fileList.length; i++) {
//				if (fileList[i].isDirectory()) {
//					continue;
//				}
//				out.putNextEntry(new org.apache.tools.zip.ZipEntry(fileList[i]
//						.getName()));
//				in = new FileInputStream(fileList[i]);
//				int b;
//				while ((b = in.read()) != -1) {
//					out.write(b);
//				}
//				in.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			if (out != null) {
//				try {
//					out.flush();
//					out.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//
//			}
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}

		return fileZipName;
	}

	/**
	 * l
	 * 
	 * @param fileName
	 *            l·
	 * @param content
	 *            l
	 * @return l
	 */
	public static boolean generateMakeFile(String fileName, String content) {
		boolean mark = true;
		File file = new File(fileName);

		BufferedReader reader = null;
		PrintWriter pw = null;
		String temp = null;
		try {
			pw = new PrintWriter(new FileOutputStream(file));
			reader = new BufferedReader(new StringReader(content));

			while ((temp = reader.readLine()) != null) {
				pw.println(temp);
			}
		} catch (FileNotFoundException e) {
			mark = false;
			throw new RuntimeException("l·Ч:" + e.getMessage());
		} catch (IOException e) {
			mark = false;
			throw new RuntimeException("дlЧ:" + e.getMessage());
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
		return mark;
	}

	/**
	 * 删除文件，和文件下的所有子文件，如果deleteFloder为True，则判断上级文件夹下是否还有其他文件，如果没有，则删除上级文件夹
	 * @param path
	 * @param deleteFloder
	 * @return
	 */
	public static boolean deleteFiles(String path, boolean deleteFloder) {
		boolean bool = false;
		try {
			File f = new File(path);
			if (f.exists() && f.isDirectory()) {
				if (f.listFiles().length == 0)
					f.delete();
				else {
					File[] flist = f.listFiles();
					for (int i = 0; i < flist.length; i++) {
						if (flist[i].isDirectory()) {
							deleteFiles(flist[i].getAbsolutePath(),
									deleteFloder);
						}
						flist[i].delete();
					}
				}
			}
			File parentFile = f.getParentFile();
			f.delete();
			
			//如果上级文件夹下没有任何文件，则删除
			if(deleteFloder && parentFile.listFiles() !=null && parentFile.listFiles().length == 0 && parentFile.exists()){
				parentFile.delete();
			}
			
			bool = true;
			log.info("删除文件成功!");
		} catch (Exception ex) {
			log.error("删除文件错误:"+ex.getMessage());
			bool = false;
		}
		return bool;
	}
	
	/**
	 * 
	 * @param deleteFiles l
	 * @return 
	 */
	public static boolean deleteFiles(String[] deleteFiles) {
		boolean flag = true;
		try {
			
			for (int i = 0; i < deleteFiles.length; i++) {
				File file = new File(deleteFiles[i]);
				if(file.isFile() && file.exists()){
					file.delete();
				}
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	

	/**
	 * 将路径下的文件以及子文件全部删除
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFiles(String filePath) {
		boolean flag = true;
		try {
			File file = new File(filePath);
			File[] afiles = file.listFiles();
			
			for (int i = 0; i < afiles.length; i++) {
				File afile = afiles[i];
				if (afile.isFile()) {
					afile.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	/**
	 * 判断文件是否存在
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static boolean getIsFile(String filepath) throws IOException {
		File file = new File(filepath);
		boolean bl = false;
		if (file.exists() && file.isFile()) {
			bl = true;
		}
		return bl;
	}
	/**
	 * 创建目录
	 * @param dir
	 */
	public static boolean checkDirAndCreate(String dir) {
		boolean bl = false;  
		File file = new File(dir);
		if (!file.exists()){
			file.mkdirs();
			bl = true;
		}
		return bl;
	}
	/**
	 * 文件拷贝（不删除原文件）
	 * @param F1
	 * @param F2
	 * @throws IOException
	 */
	public static  void saveFileToFile(String F1, String F2) throws IOException {// 实现文件对拷,从F1拷贝到F2,若F2存在则会被覆盖;适用于任何文件
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(new File(F1)); // 建立文件输入流

			File file = new File(F2);
			fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (fis != null)
				fis.close(); // 一定要进行文件的关闭,否则在新文件会是空的!
			if (fos != null)
				fos.close();
		}
	}
	
	public static String getFileSize(String filePath){
		String temp ="不存在";
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			DecimalFormat df =new DecimalFormat("#.#");
			float se=file.length()/new Float(1024.0);
			temp =df.format(se)+"KB";
		}
		return temp;
	}
	
	public static String getRepFileName(String tempstr,String repstr){
		if(tempstr.length()>0){
			char point = '.';
			int index = tempstr.lastIndexOf(point);
			String name = tempstr.substring(0, index);
			String ext = tempstr.substring(index + 1);
			return  name +repstr+point+ext;
		}else{
			return tempstr+repstr;
		}
	}
}
