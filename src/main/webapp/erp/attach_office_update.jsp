<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.io.*,java.util.*" %>
<%@ page language="java" import="org.apache.commons.fileupload.*,org.apache.commons.fileupload.disk.*,org.apache.commons.fileupload.servlet.*"%>
<%@page import="java.net.*" %>
<%
String filePath = URLDecoder.decode(request.getParameter("filePath"),"UTF-8");
FileItem officeFileItem = null;
File officeFileUpload = null;

DiskFileItemFactory factory = new DiskFileItemFactory();
factory.setSizeThreshold(4096);//设置最多只允许在内存中存储的数据,单位:字节
ServletFileUpload upload = new ServletFileUpload(factory);
upload.setSizeMax(1024*1024*4);//设置允许用户上传文件大小,单位:字节
List fileItems = null;
try{
	fileItems = upload.parseRequest(request);
}catch(FileUploadException e){
	out.println("the max upload size is 4m,cheeck upload file size!");
	out.println(e.getMessage());
	e.printStackTrace();
	return;
}
Iterator iter = fileItems.iterator();
while (iter.hasNext()) {
	FileItem item = (FileItem) iter.next();
	//打印提交的文本域和文件域名称
	//out.println(item.getFieldName());

	if(item.getFieldName().equalsIgnoreCase("upLoadFile")){
		officeFileItem = item;
	}
}

try{
	System.out.println("officefilepath:"+filePath);
	if(filePath != null){
		officeFileUpload = new File(filePath);
		officeFileItem.write(officeFileUpload);
		out.print("保存成功");
	}
}catch(FileNotFoundException e){
	e.printStackTrace();
}catch(Exception e){
	System.out.println("error saveFileToDisk:"+e.getMessage());
	e.printStackTrace();
}
%>