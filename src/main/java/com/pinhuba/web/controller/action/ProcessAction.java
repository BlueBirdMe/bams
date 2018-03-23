package com.pinhuba.web.controller.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipInputStream;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pinhuba.common.activiti.HistoryProcessInstanceDiagramCmd;
import com.pinhuba.common.activiti.WorkflowUtils;
import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.file.properties.SystemConfig;

@Controller
public class ProcessAction {
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private ManagementService managementService;

	/**
	 * 流程部署
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/processDeploy.do", method = RequestMethod.POST)
	public String processDeploy(HttpServletRequest request, ModelMap model) throws IOException {
		String fileName = null;
		String exportDir = SystemConfig.getParam("erp.workflow.path");
		InputStream fileInputStream = null;

		// 文件上传处理工厂
		FileItemFactory factory = new DiskFileItemFactory();

		// 创建文件上传处理器
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 开始解析请求信息
		List items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		// 对所有请求信息进行判断
		Iterator iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			// 信息为普通的格式
			if (!item.isFormField()) {
				fileName = item.getName();
				int index = fileName.lastIndexOf("\\");
				fileName = fileName.substring(index + 1);
				fileInputStream = item.getInputStream();
			}
		}

		try {
			Deployment deployment = null;

			String extension = FilenameUtils.getExtension(fileName);
			if (extension.equals("zip") || extension.equals("bar")) {
				ZipInputStream zip = new ZipInputStream(fileInputStream);
				deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
			} else {
				deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
			}

			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();

			for (ProcessDefinition processDefinition : list) {
				WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ConstWords.TempStringMsg, "部署流程失败！请检查流程定义文件！");
			return "/erp/system_set/process_add.jsp";
		}
		return "/erp/system_set/process.jsp";
	}

	/**
	 * 资源查看
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/processResource.do", method = RequestMethod.GET)
	public void processResource(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		String resourceType = request.getParameter("type");
		String processDefinitionId = request.getParameter("pid");

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		String resourceName = "";
		if (resourceType.equals("image")) {
			resourceName = processDefinition.getDiagramResourceName();
		} else if (resourceType.equals("xml")) {
			resourceName = processDefinition.getResourceName();
		}
		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 流程追踪
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/processTrace.do", method = RequestMethod.GET)
	public void processTrace(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		String processInstanceId = request.getParameter("id");
		Command<InputStream> cmd = new HistoryProcessInstanceDiagramCmd(processInstanceId);
		InputStream is = managementService.executeCommand(cmd);
		response.setContentType("image/png");

		int len = 0;
		byte[] b = new byte[1024];

		while ((len = is.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

}
