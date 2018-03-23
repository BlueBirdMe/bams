package com.pinhuba.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DocumentHandler {

	private Configuration configuration = null;

	public DocumentHandler(String pathPrefix) throws IOException {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		// 设置模版装载方法和路径，FreeMarker支持多种模板装载方法。可以重servlet、classpath、数据库装载
		configuration.setClassicCompatible(true);
		configuration.setClassForTemplateLoading(this.getClass(), pathPrefix);
	}

	/**
	 * 
	 * @param fullPath 生成文件的全路径
	 * @param map 数据
	 * @param templet 要装载的模板
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void handler(String fullPath, Map<String, Object> map, String template) throws IOException, TemplateException {
		Template t = configuration.getTemplate(template);
		File outFile = new File(fullPath);//输出文档路径及名称
		Writer out = null;
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
		t.process(map, out);
		out.close();
	}

}