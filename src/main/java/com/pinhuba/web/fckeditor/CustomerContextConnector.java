package com.pinhuba.web.fckeditor;

import java.io.InputStream;

import com.pinhuba.common.util.ConvertPinyin;


import net.fckeditor.connector.exception.InvalidCurrentFolderException;
import net.fckeditor.connector.exception.WriteException;
import net.fckeditor.connector.impl.ContextConnector;
import net.fckeditor.handlers.ResourceType;

/**
 * 上传及文件重命名
 * 
 * @author peng.ning
 * 
 */
public class CustomerContextConnector extends ContextConnector {
	@Override
	public String fileUpload(ResourceType type, String currentFolder, String fileName, InputStream inputStream) throws InvalidCurrentFolderException, WriteException {
		String tmp = fileName;
		try {
			tmp = ConvertPinyin.getPinyin(fileName);// 重命名操作在这里进行
		} catch (Exception e) {
			tmp = fileName;
		}
		return super.fileUpload(type, currentFolder, tmp, inputStream);
	}
}
