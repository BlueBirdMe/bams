package com.pinhuba.common.util.file.office;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.DocumentHandler;
import com.pinhuba.common.util.file.properties.SystemConfig;
import com.pinhuba.web.springmvc.RealPathResolver;
import freemarker.template.TemplateException;
import sun.misc.BASE64Encoder;

@Component
@Scope("prototype")
public class WordExport {
	
	/**
	 * 导出word
	 * @param map 数据map
	 * @param templateName 模板名称
	 * @param fileName 生成的临时文件名称
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public String export(Map<String, Object> map, String templateName, String fileName) throws IOException, TemplateException {
		String filePath = getDownloadFilePath() + fileName;
		new DocumentHandler(getPathPrefix()).handler(filePath, map, templateName);
		return filePath;
	}
	
//==========================================================================//
	
	@Resource
	private RealPathResolver realPathResolver;
	
	private String fontFamily = "宋体";
	private String fontSize = "18";
	private String spacingLine = "360";  //行间距
	
	private static String br = "#br#";
	private static String[] htmlTag = {"<div>","</div>","<p>","</p>","<br />"};
	
	public String getDownloadFilePath() {
		return realPathResolver.get("") + ConstWords.DOWNLOADFILE_PATH;
	}

	public String getPathPrefix() throws IOException {
		return SystemConfig.getParam("erp.word.template.path");
	}
	
	public String getFckImgPath() throws IOException {
		return realPathResolver.get("") + ConstWords.USERTEMPFILE_PATH;//FCK上传的图片路径
	}

	public void setFontFamily(String afontFamily) {
		this.fontFamily = afontFamily;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontSize(String afontSize) {
		this.fontSize = afontSize;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setSpacingLine(String aspacingLine) {
		this.spacingLine = aspacingLine;
	}

	public String getSpacingLine() {
		return spacingLine;
	}

	public String changeToWordXml(String str) throws IOException {
		Document doc = Jsoup.parse(replaceHtmlTag(str));//使用jsoup清楚html格式
		return changeToWordTag(doc.text());
	}
	
	/**
	 * 转换成word格式
	 * @param str
	 * @return
	 * @throws IOException 
	 */
	private String changeToWordTag(String str) throws IOException {
		// 替换为word格式，xml中以下符号要特殊处理
		str = str.replaceAll("&", "&#38;");// 转换成Unicode
		str = str.replaceAll(">", "&#62;");
		str = str.replaceAll("<", "&#60;");

		str = str.replaceAll(br, getWordXmlBr());

		// 转换图片成为word格式图片
		Pattern p1 = Pattern.compile("#img(.*?)/#");
		Matcher m1 = p1.matcher(str);
		while (m1.find()) {
			String imgHtml = "#img" + m1.group(1) + "/#";
			String[] imgArray = getImgAttribute(imgHtml);
			String imgxml = getWordXmlImg(imgArray);
			str = str.replace(imgHtml, imgxml);
		}
		return str;
	}
	
	private String getWordXmlImg(String[] imgArray) throws IOException{
		String str =	"</w:t>" +
					"</w:r>" +
					"<w:r>" +
						"<w:pict>" +
							"<w:binData w:name='wordml://" + imgArray[3] + "' xml:space='preserve'>" + 
								GetImageStr(getFckImgPath() + imgArray[0]) +
							"</w:binData>" +
							"<v:shape id='图片 1' o:spid='_x0000_i1025' type='#_x0000_t75' style='width:" + imgArray[1] + "px;height:" + imgArray[2] + "px;visibility:visible;mso-wrap-style:square'>" +
								"<v:imagedata src='wordml://" + imgArray[3] + "' o:title=''/>" +
							"</v:shape>" +
						"</w:pict>" +
					"</w:r>" +
					"<w:r>" +
						"<w:t>";
		return str;
	}
	

	private String getWordXmlBr(){
		String str =		"</w:t>" +
						"</w:r>" +
					"</w:p>" +
					"<w:p>" +
						"<w:pPr>" +
							"<w:spacing w:line=\"" + getSpacingLine() + "\" w:line-rule=\"auto\"/>" +
							"<w:rPr>" +
								"<w:sz w:val=\"" + getFontSize() + "\"/>" +	
								"<w:sz-cs w:val=\"" + getFontSize() + "\"/>" +
								"<w:rFonts w:ascii=\"" + getFontFamily() + "\" w:fareast=\"" + getFontFamily() + "\" w:h-ansi=\"" + getFontFamily() + "\" w:hint=\"fareast\"/>" +
								"<wx:font wx:val=\"" + getFontFamily() + "\"/>" +
							"</w:rPr>" +
						"</w:pPr>" +
						"<w:r>" +
							"<w:rPr>" +
								"<w:sz w:val=\"" + getFontSize() + "\"/>" +
								"<w:sz-cs w:val=\"" + getFontSize() + "\"/>" +
								"<w:rFonts w:ascii=\"" + getFontFamily() + "\" w:fareast=\"" + getFontFamily() + "\" w:h-ansi=\"" + getFontFamily() + "\" w:hint=\"fareast\"/>" +
								"<wx:font wx:val=\"" + getFontFamily() + "\"/>" +
							"</w:rPr>" +
							"<w:t>";
		return str;
	}
	
	
	/**
	 * 常用html标签，替换成#，防止被jsoup清除
	 * 
	 * @param str
	 * @return
	 */
	private static String replaceHtmlTag(String str) {

		for (int i = 0; i < htmlTag.length; i++) {
			str = str.replaceAll(htmlTag[i], br);
		}

		// 特殊符号
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&amp;", "&");

		// 替换图片标签，防止被jsoup清楚
		Pattern p = Pattern.compile("<img(.*?)/>");
		Matcher m = p.matcher(str);
		while (m.find()) {
			String imgHtml = "<img" + m.group(1) + "/>";
			String imgxml = "#img" + m.group(1) + "/#";
			str = str.replace(imgHtml, imgxml);
		}

		return str;
	}

	private static String[] getImgAttribute(String imgHtml) {
		String[] imgArray = new String[4];

		Pattern p1 = Pattern.compile(ConstWords.USERTEMPFILE + "/(.*?)\"");
		Matcher m1 = p1.matcher(imgHtml);

		Pattern p2 = Pattern.compile("width=\"(.*?)\"");
		Matcher m2 = p2.matcher(imgHtml);

		Pattern p3 = Pattern.compile("height=\"(.*?)\"");
		Matcher m3 = p3.matcher(imgHtml);

		Pattern p4 = Pattern.compile("image/(.*?)\"");
		Matcher m4 = p4.matcher(imgHtml);

		while (m1.find()) {
			imgArray[0] = m1.group(1);
		}
		while (m2.find()) {
			imgArray[1] = m2.group(1);
		}
		while (m3.find()) {
			imgArray[2] = m3.group(1);
		}
		while (m4.find()) {
			imgArray[3] = m4.group(1);
		}
		return imgArray;
	}

	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String GetImageStr(String path) throws IOException {
		String imgFile = path;// 待处理的图片
		InputStream in = null;
		byte[] data = null;

		in = new FileInputStream(imgFile);// 读取图片字节数组
		data = new byte[in.available()];
		in.read(data);
		in.close();

		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
	
}
