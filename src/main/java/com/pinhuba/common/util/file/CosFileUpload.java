package com.pinhuba.common.util.file;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * 使用O'Reilly的cos实现多文件上传
 * 
 * @author peng.ning
 * 
 */
public class CosFileUpload {

	private HttpServletRequest request;

	private String CharSet = "utf-8";

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setCharSet(String charSet) {
		CharSet = charSet;
	}

	public CosFileUpload(HttpServletRequest request, String charSet) {
		super();
		this.request = request;
		CharSet = charSet;
	}

	public CosFileUpload(HttpServletRequest request) {
		super();
		this.request = request;
	}

	/**
	 * 文件上传，返回上传成功后文件目录列表（不包含路径）
	 * 
	 * @param savepath
	 * @param fileCount
	 * @param maxFileSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> filesUpload(String savepath, int fileCount, int maxFileSize) throws Exception {
		List<String> fileList = new ArrayList<String>();
		try {
			File file = new File(savepath);
			if (!file.exists()) {
				file.mkdirs();
			}
			int maxPostSize = fileCount * maxFileSize * 1024 * 1024;

			FileRenamePolicy rename = new MyFileRenamePolicy();

			MultipartRequest multi = new MultipartRequest(this.request, savepath, maxPostSize, this.CharSet, rename);
			Enumeration files = multi.getFileNames();
			while (files.hasMoreElements()) {
				String name = (String) files.nextElement();
				File f = multi.getFile(name);
				if (f != null) {
					String fileName = multi.getFilesystemName(name);
					fileList.add(fileName);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return fileList;
	}

	// 命名规则，防止重名
	class MyFileRenamePolicy implements FileRenamePolicy {
		public File rename(File file) {
			String newName = addInNameExt(file.getName(), getDateStr());
			File result = new File(file.getParentFile(), newName);

			return result;
		}

		// 返回一个当前时间的字符串表示
		private String getDateStr() {
			String pattern = "yyyyMMddHHmmssSSS";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			String dateStr = sdf.format(new java.util.Date());

			return dateStr;
		}

		// 分离完整文件名的文件名和后缀,并在中间加入字符串后返回
		private String addInNameExt(String fullName, String add) {
			String name = "";// 文件名
			String ext = ""; // 后缀
			char point = '.';
			int index = fullName.lastIndexOf(point);

			if (index != -1) {// 如果有后缀
				name = fullName.substring(0, index);
				ext = fullName.substring(index + 1);
			} else {
				name = fullName;
			}

			return name + "_" + add + point + ext;
		}
	}
}
