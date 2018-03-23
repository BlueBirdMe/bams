<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.net.*,java.text.*,java.util.zip.*,java.io.*"%>
<%!
private static String tempdir = ".";//改变上传文件时的缓冲目录(一般不需要修改)

public class FileInfo {
	public String name = null, clientFileName = null, fileContentType = null;
	private byte[] fileContents = null;
	public File file = null;
	public StringBuffer sb = new StringBuffer(100);

	public void setFileContents(byte[] aByteArray) {
		fileContents = new byte[aByteArray.length];
		System.arraycopy(aByteArray, 0, fileContents, 0, aByteArray.length);
	}
}

public class HttpMultiPartParser {
	private final String lineSeparator = System.getProperty("line.separator", "\n");
	private final int ONE_MB = 1024 * 1024 * 1;

	public Hashtable processData(ServletInputStream is, String boundary, String saveInDir) throws IllegalArgumentException, IOException {
		if (is == null)
			throw new IllegalArgumentException("InputStream");
		if (boundary == null || boundary.trim().length() < 1)
			throw new IllegalArgumentException("boundary");
		boundary = "--" + boundary;
		StringTokenizer stLine = null, stFields = null;
		FileInfo fileInfo = null;
		Hashtable dataTable = new Hashtable(5);
		String line = null, field = null, paramName = null;
		boolean saveFiles = (saveInDir != null && saveInDir.trim().length() > 0), isFile = false;
		if (saveFiles) {
			File f = new File(saveInDir);
			f.mkdirs();
		}
		line = getLine(is);
		if (line == null || !line.startsWith(boundary))
			throw new IOException("未发现;" + " boundary = " + boundary + ", line = " + line);
		while (line != null) {
			if (line == null || !line.startsWith(boundary))
				return dataTable;
			line = getLine(is);
			if (line == null)
				return dataTable;
			stLine = new StringTokenizer(line, ";\r\n");
			if (stLine.countTokens() < 2)
				throw new IllegalArgumentException("出现错误!");
			line = stLine.nextToken().toLowerCase();
			if (line.indexOf("form-data") < 0)
				throw new IllegalArgumentException("出现错误!");
			stFields = new StringTokenizer(stLine.nextToken(), "=\"");
			if (stFields.countTokens() < 2)
				throw new IllegalArgumentException("出现错误!");
			fileInfo = new FileInfo();
			stFields.nextToken();
			paramName = stFields.nextToken();
			isFile = false;
			if (stLine.hasMoreTokens()) {
				field = stLine.nextToken();
				stFields = new StringTokenizer(field, "=\"");
				if (stFields.countTokens() > 1) {
					if (stFields.nextToken().trim().equalsIgnoreCase("filename")) {
						fileInfo.name = paramName;
						String value = stFields.nextToken();
						if (value != null && value.trim().length() > 0) {
							fileInfo.clientFileName = value;
							isFile = true;
						} else {
							line = getLine(is); // 去掉"Content-Type:"行
							line = getLine(is); // 去掉空白行
							line = getLine(is); // 去掉空白行
							line = getLine(is); // 定位
							continue;
						}
					}
				} else if (field.toLowerCase().indexOf("filename") >= 0) {
					line = getLine(is); // 去掉"Content-Type:"行
					line = getLine(is); // 去掉空白行
					line = getLine(is); // 去掉空白行
					line = getLine(is); // 定位
					continue;
				}
			}
			boolean skipBlankLine = true;
			if (isFile) {
				line = getLine(is);
				if (line == null)
					return dataTable;
				if (line.trim().length() < 1)
					skipBlankLine = false;
				else {
					stLine = new StringTokenizer(line, ": ");
					if (stLine.countTokens() < 2)
						throw new IllegalArgumentException("出现错误!");
					stLine.nextToken();
					fileInfo.fileContentType = stLine.nextToken();
				}
			}
			if (skipBlankLine) {
				line = getLine(is);
				if (line == null)
					return dataTable;
			}
			if (!isFile) {
				line = getLine(is);
				if (line == null)
					return dataTable;
				dataTable.put(paramName, line);
				//判断是否为目录
				if (paramName.equals("dir")) {
					saveInDir = line;
					System.out.println(line);
				}
				line = getLine(is);
				continue;
			}
			try {
				OutputStream os = null;
				String path = null;
				if (saveFiles)
					os = new FileOutputStream(path = getFileName(saveInDir, fileInfo.clientFileName));
				else
					os = new ByteArrayOutputStream(ONE_MB);
				boolean readingContent = true;
				byte previousLine[] = new byte[2 * ONE_MB];
				byte temp[] = null;
				byte currentLine[] = new byte[2 * ONE_MB];
				int read, read3;
				if ((read = is.readLine(previousLine, 0, previousLine.length)) == -1) {
					line = null;
					break;
				}
				while (readingContent) {
					if ((read3 = is.readLine(currentLine, 0, currentLine.length)) == -1) {
						line = null;
						break;
					}
					if (compareBoundary(boundary, currentLine)) {
						os.write(previousLine, 0, read);
						os.flush();
						line = new String(currentLine, 0, read3);
						break;
					} else {
						os.write(previousLine, 0, read);
						os.flush();
						temp = currentLine;
						currentLine = previousLine;
						previousLine = temp;
						read = read3;
					}
				}
				os.close();
				temp = null;
				previousLine = null;
				currentLine = null;
				if (!saveFiles) {
					ByteArrayOutputStream baos = (ByteArrayOutputStream) os;
					fileInfo.setFileContents(baos.toByteArray());
				} else {
					fileInfo.file = new File(path);
					os = null;
				}
				dataTable.put(paramName, fileInfo);
			} catch (IOException e) {
				throw e;
			}
		}
		return dataTable;
	}

	// 比较数据
	private boolean compareBoundary(String boundary, byte ba[]) {
		byte b;
		if (boundary == null || ba == null)
			return false;
		for (int i = 0; i < boundary.length(); i++)
			if ((byte) boundary.charAt(i) != ba[i])
				return false;
		return true;
	}

	private synchronized String getLine(ServletInputStream sis) throws IOException {
		byte b[] = new byte[1024];
		int read = sis.readLine(b, 0, b.length), index;
		String line = null;
		if (read != -1) {
			line = new String(b, 0, read);
			if ((index = line.indexOf('\n')) >= 0)
				line = line.substring(0, index - 1);
		}
		b = null;
		return line;
	}

	public String getFileName(String dir, String fileName) throws IllegalArgumentException {
		String path = null;
		if (dir == null || fileName == null)
			throw new IllegalArgumentException("目录或者文件不存在!");
		int index = fileName.lastIndexOf('/');
		String name = null;
		if (index >= 0)
			name = fileName.substring(index + 1);
		else
			name = fileName;
		index = name.lastIndexOf('\\');
		if (index >= 0)
			fileName = name.substring(index + 1);
		path = dir + File.separator + fileName;
		if (File.separatorChar == '/')
			return path.replace('\\', File.separatorChar);
		else
			return path.replace('/', File.separatorChar);
	}
}

/**
 * 下面这个类是为文件和目录排序
 * @author bagheera
 * @version 1.001
 */
class FileComp implements Comparator {
	int mode = 1;

	/**
	 * @排序方法 1=文件名, 2=大小, 3=日期
	 */
	FileComp(int mode) {
		this.mode = mode;
	}

	public int compare(Object o1, Object o2) {
		File f1 = (File) o1;
		File f2 = (File) o2;
		if (f1.isDirectory()) {
			if (f2.isDirectory()) {
				switch (mode) {
				case 1:
					return f1.getAbsolutePath().toUpperCase().compareTo(f2.getAbsolutePath().toUpperCase());
				case 2:
					return new Long(f1.length()).compareTo(new Long(f2.length()));
				case 3:
					return new Long(f1.lastModified()).compareTo(new Long(f2.lastModified()));
				default:
					return 1;
				}
			} else
				return -1;
		} else if (f2.isDirectory())
			return 1;
		else {
			switch (mode) {
			case 1:
				return f1.getAbsolutePath().toUpperCase().compareTo(f2.getAbsolutePath().toUpperCase());
			case 2:
				return new Long(f1.length()).compareTo(new Long(f2.length()));
			case 3:
				return new Long(f1.lastModified()).compareTo(new Long(f2.lastModified()));
			default:
				return 1;
			}
		}
	}
}

class Writer2Stream extends OutputStream {
	Writer out;

	Writer2Stream(Writer w) {
		super();
		out = w;
	}

	public void write(int i) throws IOException {
		out.write(i);
	}

	public void write(byte[] b) throws IOException {
		for (int i = 0; i < b.length; i++) {
			int n = b[i];
			//Convert byte to ubyte
			n = ((n >>> 4) & 0xF) * 16 + (n & 0xF);
			out.write(n);
		}
	}

	public void write(byte[] b, int off, int len) throws IOException {
		for (int i = off; i < off + len; i++) {
			int n = b[i];
			n = ((n >>> 4) & 0xF) * 16 + (n & 0xF);
			out.write(n);
		}
	}
}

static Vector expandFileList(String[] files, boolean inclDirs) {
	Vector v = new Vector();
	if (files == null)
		return v;
	for (int i = 0; i < files.length; i++)
		v.add(new File(URLDecoder.decode(files[i])));
	for (int i = 0; i < v.size(); i++) {
		File f = (File) v.get(i);
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			for (int n = 0; n < fs.length; n++)
				v.add(fs[n]);
			if (!inclDirs) {
				v.remove(i);
				i--;
			}
		}
	}
	return v;
}

static String substr(String s, String search, String replace) {
	StringBuffer s2 = new StringBuffer();
	int i = 0, j = 0;
	int len = search.length();
	while (j > -1) {
		j = s.indexOf(search, i);
		if (j > -1) {
			s2.append(s.substring(i, j));
			s2.append(replace);
			i = j + len;
		}
	}
	s2.append(s.substring(i, s.length()));
	return s2.toString();
}

static String getDir(String dir, String name) {
	if (!dir.endsWith(File.separator))
		dir = dir + File.separator;
	File mv = new File(name);
	String new_dir = null;
	if (!mv.isAbsolute()) {
		new_dir = dir + name;
	} else
		new_dir = name;
	return new_dir;
}
%>

<%
request.setAttribute("dir", request.getParameter("dir"));
String browser_name = request.getRequestURI();

//查看文件
if (request.getParameter("file") != null) {
	File f = new File(request.getParameter("file"));
	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
	BufferedReader reader = new BufferedReader(new InputStreamReader(bis, "UTF-8"));

	int l = f.getName().lastIndexOf(".");
	//判断文件后缀
	if (l >= 0) {
		String ext = f.getName().substring(l).toLowerCase();
		if (ext.equals(".jpg") || ext.equals(".jpeg") || ext.equals(".jpe"))
			response.setContentType("image/jpeg");
		else if (ext.equals(".gif"))
			response.setContentType("image/gif");
		else if (ext.equals(".pdf"))
			response.setContentType("application/pdf");
		else if (ext.equals(".htm") || ext.equals(".html") || ext.equals(".shtml"))
			response.setContentType("text/html");
		else if (ext.equals(".avi"))
			response.setContentType("video/x-msvideo");
		else if (ext.equals(".mov") || ext.equals(".qt"))
			response.setContentType("video/quicktime");
		else if (ext.equals(".mpg") || ext.equals(".mpeg") || ext.equals(".mpe"))
			response.setContentType("video/mpeg");
		else if (ext.equals(".zip"))
			response.setContentType("application/zip");
		else if (ext.equals(".tiff") || ext.equals(".tif"))
			response.setContentType("image/tiff");
		else if (ext.equals(".rtf"))
			response.setContentType("application/rtf");
		else if (ext.equals(".mid") || ext.equals(".midi"))
			response.setContentType("audio/x-midi");
		else if (ext.equals(".xl") || ext.equals(".xls") || ext.equals(".xlv") || ext.equals(".xla") || ext.equals(".xlb") || ext.equals(".xlt") || ext.equals(".xlm") || ext.equals(".xlk"))
			response.setContentType("application/excel");
		else if (ext.equals(".doc") || ext.equals(".dot"))
			response.setContentType("application/msword");
		else if (ext.equals(".png"))
			response.setContentType("image/png");
		else if (ext.equals(".xml"))
			response.setContentType("text/xml");
		else if (ext.equals(".svg"))
			response.setContentType("image/svg+xml");
		else
			response.setContentType("text/plain");
	} else
		response.setContentType("text/plain");
	response.setContentLength((int) f.length());
	out.clearBuffer();
	int i;
	while ((i = reader.read()) != -1)
		out.write(i);
	reader.close();
	out.flush();
}

//保存所选中文件为zip文件
if ((request.getParameter("Submit") != null) && (request.getParameter("Submit").equals("保存为ZIP"))) {
	Vector v = expandFileList(request.getParameterValues("selfile"), false);
	File dir_file = new File("" + request.getAttribute("dir"));
	int dir_l = dir_file.getAbsolutePath().length();
	response.setContentType("application/zip");
	response.setHeader("Content-Disposition", "attachment;filename=\"file.zip\"");
	out.clearBuffer();
	ZipOutputStream zipout = new ZipOutputStream(new Writer2Stream(out));
	zipout.setComment("Created by BAMS");
	for (int i = 0; i < v.size(); i++) {
		File f = (File) v.get(i);
		if (f.canRead()) {
			zipout.putNextEntry(new ZipEntry(f.getAbsolutePath().substring(dir_l + 1)));
			BufferedInputStream fr = new BufferedInputStream(new FileInputStream(f));
			int b;
			while ((b = fr.read()) != -1)
				zipout.write(b);
			fr.close();
			zipout.closeEntry();
		}
	}
	zipout.finish();
	out.flush();
}

//下载文件
if (request.getParameter("downfile") != null) {
	String filePath = request.getParameter("downfile");
	File f = new File(filePath);
	if (f.exists() && f.canRead()) {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=\"" + f.getName() + "\"");
		response.setContentLength((int) f.length());
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
		BufferedReader reader = new BufferedReader(new InputStreamReader(bis, "UTF-8"));
		int i;
		out.clearBuffer();
		while ((i = reader.read()) != -1)
			out.write(i);
		reader.close();
		out.flush();
	} else {
		out.println("<html><body><h1>文件" + f.getAbsolutePath() + "不存在或者无读权限</h1></body></html>");
	}
}

//上传文件
if ((request.getContentType() != null) && (request.getContentType().toLowerCase().startsWith("multipart"))) {
	response.setContentType("text/html");
	HttpMultiPartParser parser = new HttpMultiPartParser();
	boolean error = false;
	try {
		Hashtable ht = parser.processData(request.getInputStream(), "-", tempdir);
		if (ht.get("myFile") != null) {
			FileInfo fi = (FileInfo) ht.get("myFile");
			File f = fi.file;
			//把文件从缓冲目录里复制出来
			String path = (String) ht.get("dir");
			if (!path.endsWith(File.separator))
				path = path + File.separator;
			if (!f.renameTo(new File(path + f.getName()))) {
				request.setAttribute("message", "无法上传文件.");
				error = true;
				f.delete();
			}
		} else {
			request.setAttribute("message", "请选中上传文件!");
			error = true;
		}
		request.setAttribute("dir", (String) ht.get("dir"));
	} catch (Exception e) {
		request.setAttribute("message", "发生如下错误:" + e + ". 上传失败!");
		error = true;
	}
	if (!error)
		request.setAttribute("message", "文件上传成功.");
}

//保存文件
if (request.getParameter("nfile") != null) {
	File f = new File(request.getParameter("nfile"));
	File new_f = new File(getDir(f.getParent(), request.getParameter("new_name")));
	if (request.getParameter("Submit").equals("保存")) {
		if (new_f.exists() && request.getParameter("Backup") != null) {
			File bak = new File(new_f.getAbsolutePath() + ".bak");
			bak.delete();
			new_f.renameTo(bak);
		}
		
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new_f), "UTF-8"));
		writer.write(request.getParameter("text"));
		writer.flush();
		writer.close();
	}
	request.setAttribute("dir", f.getParent());
}

//删除文件
if ((request.getParameter("Submit") != null) && (request.getParameter("Submit").equals("删除文件"))) {
	Vector v = expandFileList(request.getParameterValues("selfile"), true);
	boolean error = false;
	for (int i = v.size() - 1; i >= 0; i--) {
		File f = (File) v.get(i);
		if (!f.canWrite() || !f.delete()) {
			request.setAttribute("message", "无法删除文件" + f.getAbsolutePath() + ". 删除失败");
			error = true;
			break;
		}
	}
	if ((!error) && (v.size() > 1))
		request.setAttribute("message", "多个文件已经被删除！");
	else if ((!error) && (v.size() > 0))
		request.setAttribute("message", "文件已经被删除！");
	else if (!error)
		request.setAttribute("message", "请选择要删除的文件!");
}

//建新目录
if ((request.getParameter("Submit") != null) && (request.getParameter("Submit").equals("创建目录"))) {
	String dir = "" + request.getAttribute("dir");
	String dir_name = request.getParameter("cr_dir");
	String new_dir = getDir(dir, dir_name);
	if (new File(new_dir).mkdirs()) {
		request.setAttribute("message", "目录创建完成");
	} else
		request.setAttribute("message", "创建新目录" + new_dir + "失败");
}

//创建文件
if ((request.getParameter("Submit") != null) && (request.getParameter("Submit").equals("创建文件"))) {
	String dir = "" + request.getAttribute("dir");
	String file_name = request.getParameter("cr_dir");
	String new_file = getDir(dir, file_name);
	//Test, if file_name is empty
	if ((file_name.trim() != "") && !file_name.endsWith(File.separator)) {
		if (new File(new_file).createNewFile())
			request.setAttribute("message", "文件成功创建");
		else
			request.setAttribute("message", "创建文件" + new_file + "失败");
	} else
		request.setAttribute("message", "错误: " + file_name + "文件不存在");
}

//转移文件
if ((request.getParameter("Submit") != null) && (request.getParameter("Submit").equals("移动文件"))) {
	Vector v = expandFileList(request.getParameterValues("selfile"), true);
	String dir = "" + request.getAttribute("dir");
	String dir_name = request.getParameter("cr_dir");
	String new_dir = getDir(dir, dir_name);
	boolean error = false;
	if (!new_dir.endsWith(File.separator))
		new_dir += File.separator;
	for (int i = v.size() - 1; i >= 0; i--) {
		File f = (File) v.get(i);
		if (!f.canWrite() || !f.renameTo(new File(new_dir + f.getAbsolutePath().substring(dir.length())))) {
			request.setAttribute("message", "不能转移" + f.getAbsolutePath() + ".转移失败");
			error = true;
			break;
		}
	}
	if ((!error) && (v.size() > 1))
		request.setAttribute("message", "全部文件转移成功");
	else if ((!error) && (v.size() > 0))
		request.setAttribute("message", "文件转移成功");
	else if (!error)
		request.setAttribute("message", "请选择文件");
}

//复制文件
if ((request.getParameter("Submit") != null) && (request.getParameter("Submit").equals("复制文件"))) {
	Vector v = expandFileList(request.getParameterValues("selfile"), true);
	String dir = (String) request.getAttribute("dir");
	if (!dir.endsWith(File.separator))
		dir += File.separator;
	String dir_name = request.getParameter("cr_dir");
	String new_dir = getDir(dir, dir_name);
	boolean error = false;
	if (!new_dir.endsWith(File.separator))
		new_dir += File.separator;
	byte buffer[] = new byte[0xffff];
	try {
		for (int i = 0; i < v.size(); i++) {
			File f_old = (File) v.get(i);
			File f_new = new File(new_dir + f_old.getAbsolutePath().substring(dir.length()));
			if (f_old.isDirectory())
				f_new.mkdirs();
			else if (!f_new.exists()) {
				InputStream fis = new FileInputStream(f_old);
				OutputStream fos = new FileOutputStream(f_new);
				int b;
				while ((b = fis.read(buffer)) != -1)
					fos.write(buffer, 0, b);
				fis.close();
				fos.close();
			} else {
				//文件存在
				request.setAttribute("message", "无法复制" + f_old.getAbsolutePath() + ",文件已经存在,复制失败");
				error = true;
				break;
			}
		}
	} catch (IOException e) {
		request.setAttribute("message", "错误" + e + ".复制取消");
		error = true;
	}
	if ((!error) && (v.size() > 1))
		request.setAttribute("message", "全部文件复制成功");
	else if ((!error) && (v.size() > 0))
			request.setAttribute("message", "文件复制成功");
		else if (!error)
			request.setAttribute("message", "请选择文件");
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
body{font-size:14px;font-family:微软雅黑;}
a{text-decoration: none; }
</style>

<%
//编辑文件
if (request.getParameter("editfile") != null) {
%>
	<title>编辑文件</title>
	</head>
	<body>
	<div style="margin-bottom:10px;">当前文件：<%=request.getParameter("editfile")%></div>
	<%
	request.setAttribute("dir", null);
	File ef = new File(request.getParameter("editfile"));
	InputStreamReader isr = new InputStreamReader(new FileInputStream(ef), "UTF-8");
	BufferedReader reader = new BufferedReader(isr);
	String disable = "";
	if (!ef.canWrite())
		disable = "无法打开文件";
	out.print("<form action=\"" + browser_name + "\" method=\"Post\">\n" + "<textarea name='text' wrap='off' style='width:100%;height:460px;'>" + disable);
	
	String c;
	while ((c = reader.readLine()) != null) {
		c = substr(c, "&", "&amp;");
		c = substr(c, "<", "&lt;");
		c = substr(c, ">", "&gt;");
		c = substr(c, "\"", "&quot;");
		out.print(c + "\n");
	}
	reader.close();
	%>
	</textarea>
	<input type="hidden" name="nfile" value="<%= request.getParameter("editfile")%>">
	<table>
		<tr>
			<td title="输入新的文件名">
				<input type="text" name="new_name" size="30" value="<%=ef.getName()%>">
				<input type="checkbox" name="Backup" checked>备份
				<input type="Submit" name="Submit" value="保存">
				<input type="button" onclick="javascpipt:history.go(-1);" value="返回">
			</td>
		</tr>
	</table>
	</form>
	</body>
	</html>
<%
}
%>


<%
if (request.getAttribute("dir") == null) {
	request.setAttribute("dir", application.getRealPath(""));//默认打开当前项目目录
}

//目录浏览
if ((request.getAttribute("dir") != null) && (request.getParameter("editfile") == null) && (request.getParameter("downfile") == null)) {
%>
	<title>目录浏览</title>
	
	<%if (request.getAttribute("message") != null) {%>
	<script type='text/javascript'>
		alert('<%=request.getAttribute("message")%>');
	</script>
	<%}%>
	</head>
	<body>
	<div style="margin-bottom:10px;">当前目录：<%=request.getAttribute("dir")%></div>
	<form action="<%= browser_name %>" method="Post">
	<table border="1" cellpadding="5" cellspacing="0" width="100%" style="border-collapse:collapse;">
	<%
	//String dir = URLEncoder.encode("" + request.getAttribute("dir"));
	String dir = request.getAttribute("dir").toString();
	String cmd = browser_name + "?dir=" + dir;
	out.println("<tr bgcolor='#eeeeee'><th width='20'></th><th title='按文件名称排序'><a href='" + cmd + "&sort=1'>文件名</a></th>" + "<th width='100' title='按大小称排序'><a href='" + cmd + "&sort=2'>大小</th>" + "<th width='150' title='按日期称排序'><a href='" + cmd + "&sort=3'>日期</th>" + "<th width='100'>操作</th></tr>");
	out.println("<tr><td colspan='5'>※切换到相应盘符：");
	File f = new File("" + request.getAttribute("dir"));
	File[] entry = File.listRoots();//根或者分区
	
	for (int i = 0; i < entry.length; i++) {
		//String name = URLEncoder.encode(entry[i].getAbsolutePath());
		String name = entry[i].getAbsolutePath();
		String buf = entry[i].getAbsolutePath();
		out.println("<a href=\"" + browser_name + "?dir=" + name + "\">[" + buf + "]</a>");
	}
	//返回上级目录
	if (f.getParent() != null) {
		//out.println("<a href=\"" + browser_name + "?dir=" + URLEncoder.encode(f.getParent()) + "\">[返回上级目录]</a>");
		out.println("<a href=\"" + browser_name + "?dir=" + f.getParent() + "\">[返回上级目录]</a>");
	}
	out.println("</td></tr>");
	
	//文件和目录
	entry = f.listFiles();
	if (entry != null && entry.length > 0) {
		int mode = 1;
		if (request.getParameter("sort") != null)
			mode = Integer.parseInt(request.getParameter("sort"));
		Arrays.sort(entry, new FileComp(mode));
		String ahref = "<a href=\"";
		for (int i = 0; i < entry.length; i++) {
			//String name = URLEncoder.encode(entry[i].getAbsolutePath());
			String name = entry[i].getAbsolutePath();
			String link;
			String dlink = "&nbsp;";
			String elink = "&nbsp;";
			String buf = entry[i].getName();
			if (entry[i].isDirectory()) {
				if (entry[i].canRead())
					link = ahref + browser_name + "?dir=" + name + "\">[" + buf + "]</a>";
				else
					link = "[" + buf + "]";
			} else {
				if (entry[i].canRead()) {
					if (entry[i].canWrite()) {
						link = ahref + browser_name + "?file=" + name + "\">" + buf + "</a>";
						dlink = ahref + browser_name + "?downfile=" + name + "\">下载</a>";
						elink = ahref + browser_name + "?editfile=" + name + "\">编辑</a>";
					} else {
						link = ahref + browser_name + "?file=" + name + "\"><i>" + buf + "</i></a>";
						dlink = ahref + browser_name + "?downfile=" + name + "\">下载</a>";
						elink = ahref + browser_name + "?editfile=" + name + "\">查看</a>";
					}
				} else {
					link = buf;
				}
			}
			String date = DateFormat.getDateTimeInstance().format(new Date(entry[i].lastModified()));
			out.println("<tr>");
			out.println("<td align='center'><input type=\"checkbox\" name=\"selfile\" value=\"" + name + "\"></td>");
			out.println("<td align='left'>" + link + "</td>");
			out.println("<td align='right'>" + entry[i].length() + " bytes</td>");
			out.println("<td align='center'>" + date + "</td>");
			out.println("<td align='center'>" + dlink + "&nbsp;&nbsp;" + elink + "</td>");
			out.println("</tr>");
		}
	}
	%>
	</table>
	<div style="margin-top:10px;margin-bottom:10px;">
		<input type="hidden" name="dir" value="<%=request.getAttribute("dir")%>">
		<input title="把所选文件打包下载" class="button" type="Submit" name="Submit" value="保存为ZIP">
		<input title="删除所选文件和文件夹" class="button" type="Submit" name="Submit" value="删除文件">
		<input type="text" name="cr_dir"></td>
		<input class="button" type="Submit" name="Submit" value="创建目录"></td>
		<input class="button" type="Submit" name="Submit" value="创建文件"></td>
		<input class="button" type="Submit" name="Submit" value="移动文件"></td>
		<input class="button" type="Submit" name="Submit" value="复制文件"></td>
	</div>
	</form>
	
	<form action="<%= browser_name %>" enctype="multipart/form-data" method="POST">
		<input type="hidden" name="dir" value="<%=request.getAttribute("dir")%>">
		<input type="file" name="myFile">
		<input type="Submit" class="button" name="Submit" value="上传文件">
	</form>
	</body>
	</html>
<%
}
%>
