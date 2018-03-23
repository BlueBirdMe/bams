/**
 * @author Ф
 * @version 1.0 2006/11/29
 * 
 * Copyright (C) 2006, , KOAL, Inc.
 */

package com.pinhuba.common.util.file.properties;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyTool {

	private Properties property = new Properties();

	public static PropertyTool getInstance(String fileName) {
		PropertyTool temp = new PropertyTool();
		if (!temp.load(new File(fileName)))
			return null;
		return temp;
	}

	private boolean load(File file) {
		if (file == null)
			return false;

		if (!file.exists())
			return false;

		FileInputStream fin;
		try {
			fin = new FileInputStream(file);
			property.load(fin);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String getProperty(String key) {
		if (key == null)
			return null;

		return property.getProperty(key);
	}
}
