/*******************************************************************************
 * 
 *  * Copyright (c) 2008 Koal Weixin Corporation.
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Koal Weixin Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.koal.com
 *  * Contributors:
 *  *     Koal Weixin Corporation - initial API and implementation
 *  * Creating time: 2008
 *  * Author: 
 *******************************************************************************/

package com.pinhuba.common.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/*******************************************************************************
 * 
 * @author Administrator
 * 
 */
public class FileUploadUtil {
	// /////////////////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 1L;
	/***************************************************************************
	 * 
	 */
	private final static String CHARACTER = "iso-ir-58";
	/***************************************************************************
	 * LlL 1/4 
	 */
	private String TARGET_FOLDER = null;
	/***************************************************************************
	 * Θl,"."
	 */
	private String ALLOW_SUFFIX = null;
	/***************************************************************************
	 * С
	 */
	private long MAX_FILE_SIZE = Long.MAX_VALUE;

	/***************************************************************************
	 * 
	 * !
	 */
	private String message = null;
	// l
	private String[] sourceFile = new String[255];
	// l
	private String[] suffix = new String[255];
	/**
	 * 
	 */
	private Map<Object, Object> fieldValues = new HashMap<Object, Object>();

	/***************************************************************************
	 * lΘ
	 */
	private FileUploadUtil() {

	}

	public static FileUploadUtil newInstance() {
		return new FileUploadUtil();
	}

	private String[] description = new String[255];

	/***************************************************************************
	 * 
	 * 
	 * lL
	 */
	private int count = 0;

	/***************************************************************************
	 * Θλ
	 */
	private boolean successful = true;

	/***************************************************************************
	 * жΘl
	 * 
	 * @param i
	 * @return
	 */
	private boolean canUpload(final int i) {
		if (ALLOW_SUFFIX == null || ALLOW_SUFFIX.trim().length() == 0) {
			return true;
		}
		suffix[i] = suffix[i].toLowerCase();
		// 
		if (sourceFile[i].equals("")
				|| (!(ALLOW_SUFFIX.indexOf("." + suffix[i]) >= 0))) {
			description[i] = ": lΘll.";
			return false;
		} else {
			return true;
		}
	}

	/***************************************************************************
	 * 
	 * @param dirPath
	 */
	private boolean mkDirs(final String dirPath) {
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

	/***************************************************************************
	 * 
	 * @param absoluteFileName
	 * @return
	 */
	public boolean isFileExist(final String absoluteFileName) {
		if (absoluteFileName != null && absoluteFileName.trim().length() > 0) {
			File file = new File(absoluteFileName.trim());
			if (file.exists()) {
				return true;
			}
		}
		return false;
	}

	/***************************************************************************
	 * *
	 * lΘmaxFileSizelong'xL0L,С0′~olΘС100 *
	 * 1024 * 1024100MB
	 * allowSuffixΘl'".xxx.yyyy.mmm.nnnn",xxxlβ,
	 * 趨".jpeg.gif.PNG.bMp"lβСд,lΘNULL
	 * overWrite趨ltargetFolderΘL 1/4 L 1/4 趨ΘL 1/4 L 1/4 L 1/4 L 1/4 l·
	 * lMap
	 * 
	 * @param request
	 * @param overWrite
	 * @param targetFolder
	 * @param allowSuffix
	 * @param maxSize
	 * @return
	 * @throws Exception
	 */
	public void upload(final HttpServletRequest request,
			final boolean overWrite, final String targetFolder,
			final String allowSuffix, final long maxSize) throws Exception {
		if (targetFolder == null || targetFolder.trim().length() == 0) {
			throw new Exception("ΘLL 1/4 ,!");
		}
		TARGET_FOLDER = targetFolder;
		if (TARGET_FOLDER == null) {
			throw new Exception("δ趨LL 1/4 !");
		}
		if (!mkDirs(TARGET_FOLDER)) {
			throw new Exception("L 1/4 Θ·");
		}
		ALLOW_SUFFIX = allowSuffix;
		if (maxSize > 0) {
			MAX_FILE_SIZE = maxSize;
		}
		uploadFile(request, overWrite);
	}

	/***************************************************************************
	 * l
	 * 
	 * @param i
	 * @param inputstream
	 * @param buffer
	 * @param overWrite
	 */
	private void tansfer(int i, ServletInputStream inputstream, byte[] buffer,
			boolean overWrite) {
		String fileName = sourceFile[i];
		if (fileName == null || fileName.trim().length() == 0) {
			return;
		}
		// /////////////////////////////////////////////////////////////////
		fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		// /////////////////////////////////////////////////////////////////
		String aboluteFilePath = TARGET_FOLDER + File.separator + fileName;
		try {
			File targetFile = new File(aboluteFilePath);
			// /////////////////////////////////////////////////////////////////
			// >  l IJ
			if (!overWrite) {
				if (targetFile.exists()) {
					description[count] = description[count] + sourceFile[i]
							+ "l";
					return;
				}
			}
			// ////////////////////////////////////////////////////////////////
			FileOutputStream out = new FileOutputStream(targetFile);
			int a = 0;
			int k = 0;
			// /////////////////////////////////////////////////////////////////
			// 
			long hastransfered = 0;
			String s = "";
			while ((a = inputstream.readLine(buffer, 0, buffer.length)) != -1) {
				s = new String(buffer, 0, a);
				k = s.indexOf("Content-Type:");
				if (k != -1) {
					break;
				}
			}
			// /////////////////////////////////////////////////////////////////
			inputstream.readLine(buffer, 0, buffer.length);
			while ((a = inputstream.readLine(buffer, 0, buffer.length)) != -1) {
				s = new String(buffer, 0, a);
				if ((buffer[0] == 45) && (buffer[1] == 45) && (buffer[2] == 45) && (buffer[3] == 45) && (buffer[4] == 45)) {
					break;
				}
				out.write(buffer, 0, a);
				hastransfered += a;
				if (hastransfered >= MAX_FILE_SIZE) {
					description[count] = ": l[ " + sourceFile[count]
							+ "]^JСΘlkΘж.";
					successful = false;
					break;
				}
			}
			// /////////////////////////////////////////////////////////////////
			if (successful) {
				description[count] = ":  " + sourceFile[count] + " Θ";
			}
			// /////////////////////////////////////////////////////////////////
			out.close();
			// /////////////////////////////////////////////////////////////////
			if (!successful) {
				inputstream.close();
				File tmp = new File(TARGET_FOLDER + File.separator
						+ sourceFile[count]);
				tmp.delete();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			description[i] = ioe.toString();
		}
	}

	/***************************************************************************
	 * 
	 * Θ
	 * 
	 * @param request
	 * @throws IOException
	 */
	private void uploadFile(HttpServletRequest request, boolean overWrite)
			throws IOException {
		/*  */
		byte[] buffer = new byte[4096];
		/*  */
		ServletInputStream inputstream = request.getInputStream();
		int a = 0;
		int k = 0;
		String s = "";
		while ((a = inputstream.readLine(buffer, 0, buffer.length)) != -1) {
			s = new String(buffer, 0, a);
			if ((k = s.indexOf("filename=\"")) != -1) {
				// l
				s = s.substring(k + 10);
				k = s.indexOf("\"");
				s = s.substring(0, k);
				if (s.length() > 0) {
					sourceFile[count] = new String(s.getBytes(), "GBK");
				} else {
					sourceFile[count] = s;
				}
				System.out.println(sourceFile[count]);
				k = s.lastIndexOf(".");
				suffix[count] = s.substring(k + 1);
				if (canUpload(count)) {
					tansfer(count, inputstream, buffer, overWrite);
				}
				++count;
			} else if ((k = s.indexOf("name=\"")) != -1) {
				// ?
				String fieldName = s.substring(k + 6, s.length() - 3);
				inputstream.readLine(buffer, 0, buffer.length);
				String fieldValue = new String();
				while ((a = inputstream.readLine(buffer, 0, buffer.length)) != -1) {
					s = new String(buffer, 0, a - 2);
					if ((buffer[0] == 45) && (buffer[1] == 45)
							&& (buffer[2] == 45) && (buffer[3] == 45)
							&& (buffer[4] == 45) && (buffer[5] == 45)
							&& (buffer[6] == 45) && (buffer[7] == 45)
							&& (buffer[8] == 45) && (buffer[9] == 45)
							&& (buffer[10] == 45) && (buffer[11] == 45)
							&& (buffer[12] == 45) && (buffer[13] == 45)
							&& (buffer[14] == 45) && (buffer[15] == 45)
							&& (buffer[16] == 45)) {
						break;
					} else {
						fieldValue += s;
					}
				}
				// /////////////////////////////////////////////////////////
				fieldValues.put(fieldName, fieldValue);
			}
			if (!successful) {
				break;
			}
		}
	}

	/***************************************************************************
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String native2Unicode(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		char c;
		// 趨^wbyte
		byte[] buffer = new byte[s.length() * 2];
		// ij
		int j = 0;
		try {
			// 
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) >= 0x100) {
					// 16λchar
					c = s.charAt(i);
					// charbyte
					byte[] buf = ("" + c).getBytes(CHARACTER);
					buffer[j] = buf[0];
					j++;
					buffer[j] = buf[1];
					j++;
				} else {
					// 255ascii,
					buffer[j] = (byte) s.charAt(i);
					j++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(buffer, 0, j);
	}

	/***************************************************************************
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/***************************************************************************
	 * l
	 * 
	 * @return
	 */
	public String[] getDescription() {
		return description;
	}

	/***************************************************************************
	 * 
	 * @return
	 */
	public int getCount() {
		return count;
	}

	/***************************************************************************
	 * 
	 * @return
	 */
	public long getMAX_FILE_SIZE() {
		return MAX_FILE_SIZE;
	}

	/***************************************************************************
	 * 
	 * @param size
	 */
	public void setMAX_FILE_SIZE(long max_file_size) {
		MAX_FILE_SIZE = max_file_size;
	}

	/***************************************************************************
	 * l
	 * 
	 * @return
	 */
	public final Map<Object, Object> getFieldValues() {
		return fieldValues;
	}

	/***************************************************************************
	 * 
	 * @return
	 */
	/*
	 * public static String getMAX_FILE_SIZE_IDENTIFIER() { return
	 * MAX_FILE_SIZE_IDENTIFIER; }
	 */
}
