package com.pinhuba.common.util.imgProcess;

import java.io.File;
import java.text.SimpleDateFormat;

import com.oreilly.servlet.multipart.FileRenamePolicy;
//文件重命名策略类:当前文件名后加上时间和日期,防止重名
public class MyFileRenamePolicy implements FileRenamePolicy {

	public File rename(File file) {
		
		String newName = addInNameExt(file.getName().replaceAll(",", ""), getDateStr());
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

		return name.trim() + "_" + add + point + ext;
	}
}
