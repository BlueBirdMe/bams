package com.pinhuba.common.netdisk.io;

import java.lang.reflect.Field;
import java.util.Date;

public class MyFile {
	private String name;
	private long size;
	private String type;
	private Date modified;

	public void setName(String inName) {
		this.name = inName;
	}

	public String getName() {
		return this.name;
	}

	public void setSize(long inSize) {
		this.size = inSize;
	}

	public long getSize() {
		return this.size;
	}

	public void setType(String inType) {
		if ((inType == null) && (this.name != null)) {
			String fileExtension = "";
			int dotLocation = this.name.lastIndexOf(".");
			if (dotLocation != -1) {
				fileExtension = this.name.substring(dotLocation + 1);
			}
			this.type = "Unknown File";
			if (fileExtension.equalsIgnoreCase("txt"))
				this.type = "Text Document";
			else if (fileExtension.equalsIgnoreCase("zip"))
				this.type = "Zip Archive";
			else if (fileExtension.equalsIgnoreCase("pdf"))
				this.type = "Adobe Acrobat Document";
			else if (fileExtension.equalsIgnoreCase("doc"))
				this.type = "Microsoft Word Document";
			else if (fileExtension.equalsIgnoreCase("xls"))
				this.type = "Microsoft Excel Spreadsheet";
			else if (fileExtension.equalsIgnoreCase("ppt"))
				this.type = "Microsoft PowerPoint Presentation";
			else if (fileExtension.equalsIgnoreCase("avi"))
				this.type = "Video Clip";
			else if (fileExtension.equalsIgnoreCase("bmp"))
				this.type = "Bitmap Image File";
			else if (fileExtension.equalsIgnoreCase("gif"))
				this.type = "GIF Image File";
			else if (fileExtension.equalsIgnoreCase("jpg"))
				this.type = "JPEG Image File";
			else if (fileExtension.equalsIgnoreCase("wav"))
				this.type = "WAV Audio File";
			else if (fileExtension.equalsIgnoreCase("mp3"))
				this.type = "MP3 Audio File";
			else if (fileExtension.equalsIgnoreCase("wma"))
				this.type = "Windows Media Audio File";
			else if (fileExtension.equalsIgnoreCase("wmv"))
				this.type = "Windows Media Video File";
			else if (fileExtension.equalsIgnoreCase("exe"))
				this.type = "Application";
			else if (fileExtension.equalsIgnoreCase("com"))
				this.type = "Application";
			else if (fileExtension.equalsIgnoreCase("bat"))
				this.type = "DOS Batch File";
			else if (fileExtension.equalsIgnoreCase("java"))
				this.type = "Java Source File";
			else if (fileExtension.equalsIgnoreCase("class"))
				this.type = "Java Class File";
			else if (fileExtension.equalsIgnoreCase("rar"))
				this.type = "RAR Archive";
			else if (fileExtension.equalsIgnoreCase("arj"))
				this.type = "ARJ Archive";
			else if (fileExtension.equalsIgnoreCase("scr"))
				this.type = "Windows Screensaver";
			else if (fileExtension.equalsIgnoreCase("ini"))
				this.type = "Configuration Settings";
			else if (fileExtension.equalsIgnoreCase("dll"))
				this.type = "Dynamic Link Library";
			else if (fileExtension.equalsIgnoreCase("log"))
				this.type = "Log File";
			else if (fileExtension.equalsIgnoreCase("rtf"))
				this.type = "Rich Text Format";
			else if (fileExtension.equalsIgnoreCase("cpp"))
				this.type = "C++ Source File";
			else if (fileExtension.equalsIgnoreCase("h"))
				this.type = "C Header File";
			else if (fileExtension.equalsIgnoreCase("iso"))
				this.type = "Disc Image";
			else if (fileExtension.equalsIgnoreCase("htm"))
				this.type = "HTML Document";
			else if (fileExtension.equalsIgnoreCase("html"))
				this.type = "HTML Document";
			else if (fileExtension.equalsIgnoreCase("js"))
				this.type = "Javascript Source File";
			else if (fileExtension.equalsIgnoreCase("jsp"))
				this.type = "JavaServer Page";
			else if (fileExtension.equalsIgnoreCase("asp"))
				this.type = "Active Server Page";
			else if (fileExtension.equalsIgnoreCase("php"))
				this.type = "PHP Page";
			else if (fileExtension.equalsIgnoreCase("py"))
				this.type = "Python Script";
			else if (fileExtension.equalsIgnoreCase("vbs"))
				this.type = "Visual Basic Script File";
			else if (fileExtension.equalsIgnoreCase("ico"))
				this.type = "Icon";
			else if (fileExtension.equalsIgnoreCase("ogg"))
				this.type = "Ogg Vorbis File";
			else if (fileExtension.equalsIgnoreCase("tif"))
				this.type = "TIFF Image File";
			else if (fileExtension.equalsIgnoreCase("tiff"))
				this.type = "TIFF Image File";
			else if (fileExtension.equalsIgnoreCase("xml"))
				this.type = "XML Document";
			else if (fileExtension.equalsIgnoreCase("cfg"))
				this.type = "Configuration File";
			else if (fileExtension.equalsIgnoreCase("nfo"))
				this.type = "MSInfo File";
			else if (fileExtension.equalsIgnoreCase("jar"))
				this.type = "Java Archive";
			else if (fileExtension.equalsIgnoreCase("css"))
				this.type = "Cascading Stylesheet";
			else if (fileExtension.equalsIgnoreCase("sh"))
				this.type = "Shell Script";
			else if (fileExtension.equalsIgnoreCase("csv"))
				this.type = "Comma-Separated Values File";
		} else {
			this.type = inType;
		}
	}

	public String getType() {
		return this.type;
	}

	public void setModified(Date inModified) {
		this.modified = inModified;
	}

	public Date getModified() {
		return this.modified;
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