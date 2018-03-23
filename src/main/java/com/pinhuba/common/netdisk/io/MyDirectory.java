package com.pinhuba.common.netdisk.io;

import java.lang.reflect.Field;

public class MyDirectory {
	private String name;
	private String path;
	private boolean hasChildren;

	public void setName(String inName) {
		this.name = inName;
	}

	public String getName() {
		return this.name;
	}

	public void setPath(String inPath) {
		this.path = inPath;
	}

	public String getPath() {
		return this.path;
	}

	public void setHasChildren(boolean inHasChildren) {
		this.hasChildren = inHasChildren;
	}

	public boolean getHasChildren() {
		return this.hasChildren;
	}

	public String toString() {
		String str = null;
		StringBuffer sb = new StringBuffer(1000);
		sb.append("[").append(super.toString()).append("]={");
		boolean firstPropertyDisplayed = false;
		try {
			Field[] fields = getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (firstPropertyDisplayed)
					sb.append(", ");
				else {
					firstPropertyDisplayed = true;
				}
				sb.append(fields[i].getName()).append("=")
						.append(fields[i].get(this));
			}
			sb.append("}");
			str = sb.toString().trim();
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		return str;
	}
}