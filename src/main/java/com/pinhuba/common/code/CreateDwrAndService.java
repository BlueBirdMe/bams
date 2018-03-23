package com.pinhuba.common.code;

import com.pinhuba.common.code.bean.CodeConfig;
import com.pinhuba.common.code.util.SaveFile;
import com.pinhuba.common.code.util.Util;

/**
 * 适用于BAMS项目创建Dwr类及Service类
 * @author JC
 */
public class CreateDwrAndService {

	String srcPath = CodeConfig.getInstance().getSrcPath();
	String dwrClass = null;
	String serviceClass = null;
	String[] daoArray = null;
	String[] pojoArray = null;
	String hqlOrSqlPackName = null;
	String moduleName = null;
	Boolean istree = false;

	public CreateDwrAndService() {
		super();
	}

	public CreateDwrAndService(String moduleName, String pojoClass, Boolean istree) {
		
		moduleName = Util.upperCaseFirstLetter(moduleName);
		
		this.dwrClass = "Dwr" + moduleName + "Service";
		this.serviceClass = "I" + moduleName + "Service";
		this.daoArray = getdaoArray(pojoClass);
		this.pojoArray = getPojoArray(pojoClass);
		this.hqlOrSqlPackName = moduleName + "Pack";
		this.moduleName = moduleName;
		this.istree = istree;
	}

	private String[] getdaoArray(String pojoClass) {
		String[] temp = getPojoArray(pojoClass);
		String[] daoArray = new String[temp.length];
		for (int i = 0; i < temp.length; i++) {
			String tempStr = temp[i];
			daoArray[i] = "I" + tempStr + "Dao";
		}
		return daoArray;
	}

	private String[] getPojoArray(String pojoClass) {
		return pojoClass.split(",");
	}

	public void getDwr() throws Exception {
		String reString = this.getDwrString();
		// 写入文件
		SaveFile.writeFile(srcPath + "com/pinhuba/web/controller/dwr/" + dwrClass + ".java", reString);
	}

	private String getDwrString() {

		String servicevar = Util.lowerCaseFirstLetter(removeFirstLetter(serviceClass));// 接口变量

		StringBuffer sb = new StringBuffer();
		sb.append("package com.pinhuba.web.controller.dwr;\n\n");

		for (int i = 0; i < pojoArray.length; i++) {
			String pojo = pojoArray[i];
			sb.append("import com.pinhuba.core.pojo." + pojo + ";\n");
		}
		sb.append("import javax.annotation.Resource;\n");
		sb.append("import org.apache.commons.lang.StringUtils;\n");
		sb.append("import org.slf4j.Logger;\n");
		sb.append("import org.slf4j.LoggerFactory;\n");
		sb.append("import java.util.List;\n");
		sb.append("import javax.servlet.ServletContext;\n");
		sb.append("import javax.servlet.http.HttpServletRequest;\n");
		sb.append("import com.pinhuba.common.module.ResultBean;\n");
		sb.append("import com.pinhuba.common.pages.Pager;\n");
		sb.append("import com.pinhuba.common.pages.PagerHelper;\n");
		sb.append("import com.pinhuba.common.util.UtilTool;\n");
		sb.append("import com.pinhuba.common.util.UtilPrimaryKey;\n");
		sb.append("import com.pinhuba.common.util.WebUtilWork;\n");
		sb.append("import com.pinhuba.core.iservice." + serviceClass + ";\n\n");

		sb.append("/**********************************************\n");
		sb.append(" * Class name:\n");
		sb.append(" * Description:\n");
		sb.append(" * Others:\n");
		sb.append(" * History:\n");
		sb.append(" **********************************************/\n");
		
		
		sb.append("public class " + dwrClass + " {\n\n");
		sb.append("    private final static Logger logger = LoggerFactory.getLogger(" + dwrClass + ".class);\n\n");
		sb.append("    @Resource\n");
		sb.append("    private " + serviceClass + " " + servicevar + ";\n\n");

		for (int i = 0; i < pojoArray.length; i++) {
			String pojo = pojoArray[i];
			String lowerPojo = Util.lowerCaseFirstLetter(pojo);

			sb.append("    /**\n");
			sb.append("     * 查询 " + pojo + " 分页列表\n");
			sb.append("     * @param context\n");
			sb.append("     * @param request\n");
			sb.append("     * @param " + lowerPojo + "\n");
			sb.append("     * @param pager\n");
			sb.append("     */\n");
			sb.append("    public ResultBean list" + pojo + "(ServletContext context, HttpServletRequest request, " + pojo + " " + lowerPojo + ", Pager pager){\n");
			sb.append("        List<" + pojo + "> list = null;\n");
			sb.append("        pager = PagerHelper.getPager(pager," + servicevar + ".list" + pojo + "Count(" + lowerPojo + "));\n");
			sb.append("        list = " + servicevar + ".list" + pojo + "(" + lowerPojo + ", pager);\n");
			sb.append("        logger.info(\"查询 " + pojo + " 分页列表...\");\n");
			sb.append("        return WebUtilWork.WebResultPack(list, pager);\n");
			sb.append("    }\n\n");

			sb.append("    /**\n");
			sb.append("     * 查询所有 " + pojo + " 列表\n");
			sb.append("     * @param context\n");
			sb.append("     * @param request\n");
			sb.append("     * @param " + lowerPojo + "\n");
			sb.append("     * @param pager\n");
			sb.append("     */\n");
			sb.append("    public ResultBean list" + pojo + "All(ServletContext context, HttpServletRequest request){\n");
			sb.append("        " + pojo + " " + lowerPojo + " = new " + pojo + "();\n");
			sb.append("        List<" + pojo + "> list = " + servicevar + ".list" + pojo + "(" + lowerPojo + ");\n");
			sb.append("        logger.info(\"查询所有 " + pojo + " 列表...\");\n");
			sb.append("        return WebUtilWork.WebResultPack(list);\n");
			sb.append("    }\n\n");

			sb.append("    /**\n");
			sb.append("     * 保存 " + pojo + "\n");
			sb.append("     * @param context\n");
			sb.append("     * @param request\n");
			sb.append("     * @param " + lowerPojo + "\n");
			sb.append("     */\n");
			sb.append("    public ResultBean save" + pojo + "(ServletContext context, HttpServletRequest request, " + pojo + " " + lowerPojo + "){\n");
			sb.append("        String empid = UtilTool.getEmployeeId(request);\n");
			sb.append("        " + lowerPojo + ".initSave(empid);\n");
			sb.append("        " + lowerPojo + ".setPrimaryKey(UtilPrimaryKey.getPrimaryKey());\n");
			sb.append("        " + servicevar + ".save" + pojo + "(" + lowerPojo + ");\n");
			sb.append("        logger.info(\"保存 " + pojo + "...\");\n");
			sb.append("        return WebUtilWork.WebResultPack(null);\n");
			sb.append("    }\n\n");

			sb.append("    /**\n");
			sb.append("     * 更新 " + pojo + "\n");
			sb.append("     * @param context\n");
			sb.append("     * @param request\n");
			sb.append("     * @param " + lowerPojo + "\n");
			sb.append("     */\n");
			sb.append("    public ResultBean update" + pojo + "(ServletContext context, HttpServletRequest request, " + pojo + " " + lowerPojo + "){\n");
			sb.append("        " + pojo + " tmp = " + servicevar + ".get" + pojo + "ByPk(" + lowerPojo + ".getPrimaryKey());\n");
			sb.append("        String empid = UtilTool.getEmployeeId(request);\n");
			sb.append("        " + lowerPojo + ".initUpdate(empid);\n");
			sb.append("        " + servicevar + ".save" + pojo + "(" + lowerPojo + ");\n");
			sb.append("        logger.info(\"更新 " + pojo + "...\");\n");
			sb.append("        return WebUtilWork.WebResultPack(null);\n");
			sb.append("    }\n\n");

			sb.append("    /**\n");
			sb.append("     * 根据ID获得 " + pojo + "\n");
			sb.append("     * @param context\n");
			sb.append("     * @param request\n");
			sb.append("     * @param pk\n");
			sb.append("     */\n");
			sb.append("    public ResultBean get" + pojo + "ByPk(ServletContext context, HttpServletRequest request, String pk){\n");
			sb.append("        " + pojo + " " + lowerPojo + " = " + servicevar + ".get" + pojo + "ByPk(pk);\n");
			sb.append("        logger.info(\"根据ID获得 " + pojo + "...{}\", " + lowerPojo + ".getPrimaryKey());\n");
			sb.append("        return WebUtilWork.WebObjectPack(" + lowerPojo + ");\n");
			sb.append("    }\n\n");

			sb.append("    /**\n");
			sb.append("     * 删除 " + pojo + "\n");
			sb.append("     * @param context\n");
			sb.append("     * @param request\n");
			sb.append("     * @param pks\n");
			sb.append("     */\n");
			sb.append("    public ResultBean delete" + pojo + "ByPks(ServletContext context, HttpServletRequest request, String[] pks){\n");
			sb.append("        " + servicevar + ".delete" + pojo + "ByPks(pks);\n");
			sb.append("        for (String pk : pks) {\n");
			sb.append("            logger.info(\"删除 " + pojo + "...{}\", pk);\n");
			sb.append("        }\n");
			sb.append("        return WebUtilWork.WebResultPack(null);\n");
			sb.append("    }\n\n");

			sb = CreateTree.createDwr(istree, sb, pojo, servicevar);
		}

		sb.append("/**********************************************\n");
		sb.append(" * 以上代码由BAMS代码生成工具自动生成，请根据具体需求进行修改。\n");
		sb.append(" * 开发人员在此注释以下编写业务逻辑代码，并将自己写的代码框起来，便于后期代码合并，例如：\n");
		sb.append(" **********************************************/\n\n");

		sb.append("/**********************JC-begin**********************/\n");
		sb.append("    public void method(){\n");
		sb.append("        System.out.println(\"JC's code here\");\n");
		sb.append("    }\n");
		sb.append("/**********************JC-end**********************/\n\n");

		sb.append("/**********************Jacy-begin**********************/\n");
		sb.append("    public void method2(){\n");
		sb.append("        System.out.println(\"Jacy's code here\");\n");
		sb.append("    }\n");
		sb.append("/**********************Jacy-end**********************/\n\n");

		sb.append("}");

		return sb.toString();
	}

	// 去除第一个字母
	private String removeFirstLetter(String str) {
		return str.substring(1, str.length());
	}

	public void getService() throws Exception {
		String reString = this.getServiceString();
		String reString2 = this.getServiceImplString();
		// 写入文件
		SaveFile.writeFile(srcPath + "com/pinhuba/core/iservice/" + serviceClass + ".java", reString);
		SaveFile.writeFile(srcPath + "com/pinhuba/core/service/" + removeFirstLetter(serviceClass) + ".java", reString2);
	}

	private String getServiceString() {
		StringBuffer sb = new StringBuffer();
		sb.append("package com.pinhuba.core.iservice;\n\n");

		for (int i = 0; i < pojoArray.length; i++) {
			String pojo = pojoArray[i];
			sb.append("import com.pinhuba.core.pojo." + pojo + ";\n");
		}
		sb.append("import java.util.List;\n");
		sb.append("import com.pinhuba.common.pages.Pager;\n\n");

		sb.append("public interface " + serviceClass + "{\n\n");

		for (int i = 0; i < pojoArray.length; i++) {
			String pojo = pojoArray[i];
			String lowerPojo = Util.lowerCaseFirstLetter(pojo);
			sb.append("    public int list" + pojo + "Count(" + pojo + " " + lowerPojo + ");\n");
			sb.append("    public List<" + pojo + "> list" + pojo + "(" + pojo + " " + lowerPojo + ", Pager pager);\n");
			sb.append("    public List<" + pojo + "> list" + pojo + "(" + pojo + " " + lowerPojo + ");\n");
			sb.append("    public " + pojo + " save" + pojo + "(" + pojo + " " + lowerPojo + ");\n");
			sb.append("    public " + pojo + " get" + pojo + "ByPk(String pk);\n");
			sb.append("    public void delete" + pojo + "ByPks(String[] pks);\n");

			sb = CreateTree.createService(istree, sb, pojo);
		}

		sb.append("}");
		return sb.toString();
	}

	private String getServiceImplString() {
		StringBuffer sb = new StringBuffer();
		sb.append("package com.pinhuba.core.service;\n\n");

		for (int i = 0; i < pojoArray.length; i++) {
			String pojo = pojoArray[i];
			sb.append("import com.pinhuba.core.pojo." + pojo + ";\n");
		}

		for (int i = 0; i < daoArray.length; i++) {
			String dao = daoArray[i];
			sb.append("import com.pinhuba.core.dao." + dao + ";\n");
		}
		sb.append("import com.pinhuba.core.iservice." + serviceClass + ";\n");
		sb.append("import com.pinhuba.common.pages.Pager;\n");
		sb.append("import com.pinhuba.common.pack." + moduleName + "Pack;\n");
		sb.append("import java.util.List;\n");
		sb.append("import javax.annotation.Resource;\n");
		sb.append("import org.springframework.stereotype.Service;\n\n");
		sb.append("import org.springframework.transaction.annotation.Transactional;\n");
		
		sb.append("@Service\n");
		sb.append("@Transactional\n");
		sb.append("public class " + removeFirstLetter(serviceClass) + " implements " + serviceClass + "{\n\n");

		for (int i = 0; i < daoArray.length; i++) {
			String dao = daoArray[i];
			String daovar = Util.lowerCaseFirstLetter(removeFirstLetter(dao));
			sb.append("    @Resource\n");
			sb.append("    private " + dao + " " + daovar + ";\n\n");
		}

		for (int i = 0; i < pojoArray.length; i++) {
			String pojo = pojoArray[i];
			String lowerPojo = Util.lowerCaseFirstLetter(pojo);
			String pojoDao = lowerPojo + "Dao";

			sb.append("    public int list" + pojo + "Count(" + pojo + " " + lowerPojo + "){\n");
			sb.append("        int count = " + pojoDao + ".findByHqlWhereCount(" + moduleName + "Pack.pack" + pojo + "Query(" + lowerPojo + "));\n");
			sb.append("        return count;\n");
			sb.append("    }\n\n");

			sb.append("    public List<" + pojo + "> list" + pojo + "(" + pojo + " " + lowerPojo + ", Pager pager){\n");
			sb.append("        List<" + pojo + "> list = " + pojoDao + ".findByHqlWherePage(" + moduleName + "Pack.pack" + pojo + "Query(" + lowerPojo + "), pager);\n");
			sb.append("        return list;\n");
			sb.append("    }\n\n");

			sb.append("    public List<" + pojo + "> list" + pojo + "(" + pojo + " " + lowerPojo + "){\n");
			sb.append("        List<" + pojo + "> list = " + pojoDao + ".findByHqlWhere(" + moduleName + "Pack.pack" + pojo + "Query(" + lowerPojo + "));\n");
			sb.append("        return list;\n");
			sb.append("    }\n\n");

			sb.append("    public " + pojo + " save" + pojo + "(" + pojo + " " + lowerPojo + "){\n");
			sb.append("        " + pojo + " temp = (" + pojo + ")" + pojoDao + ".save(" + lowerPojo + ");\n");
			sb.append("        return temp;\n");
			sb.append("    }\n\n");

			sb.append("    public " + pojo + " get" + pojo + "ByPk(String pk){\n");
			sb.append("        " + pojo + " " + lowerPojo + " = (" + pojo + ")" + pojoDao + ".getByPK(pk);\n");
			sb.append("        return " + lowerPojo + ";\n");
			sb.append("    }\n\n");

			sb.append("    public void delete" + pojo + "ByPks(String[] pks){\n");
			sb.append("        for (String pk : pks) {\n");
			sb.append("            " + pojo + " " + lowerPojo + " = " + pojoDao + ".getByPK(pk);\n");
			sb.append("            " + pojoDao + ".remove(" + lowerPojo + ");\n");
			sb.append("        }\n");
			sb.append("    }\n");

			sb = CreateTree.createServiceImpl(istree, sb, pojo);
		}

		sb.append("}");
		return sb.toString();
	}

	public void getPack() throws Exception {
		String reString = this.getPackString();
		SaveFile.writeFile(srcPath + "com/pinhuba/common/pack/" + hqlOrSqlPackName + ".java", reString);
	}

	private String getPackString() {
		StringBuffer sb = new StringBuffer();
		sb.append("package com.pinhuba.common.pack;\n\n");

		for (int i = 0; i < pojoArray.length; i++) {
			String pojo = pojoArray[i];
			sb.append("import com.pinhuba.core.pojo." + pojo + ";\n");
		}

		sb.append("import com.pinhuba.common.util.UtilWork;\n\n");

		sb.append("public class " + moduleName + "Pack{\n\n");

		for (int i = 0; i < pojoArray.length; i++) {
			String pojo = pojoArray[i];
			sb.append("    public static String pack" + pojo + "Query(" + pojo + " " + Util.lowerCaseFirstLetter(pojo) + "){\n");
			sb.append("        StringBuffer result = new StringBuffer();\n");
			sb.append("        //result.append(\" order by model.recordDate desc\");\n");
			sb.append("        return result.toString();\n");
			sb.append("    }\n\n");
		}

		sb.append("}");
		return sb.toString();
	}

	public void getConfig() throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("//复制到spring-service.xml文件 Dwr Serivce配置区\n");
		sb.append("<bean id=\"dwr" + moduleName + "Service\" class=\"com.pinhuba.web.controller.dwr." + dwrClass + "\">\n");
		sb.append("    <dwr:remote javascript=\"dwr" + moduleName + "Service\"/>\n");
		sb.append("</bean>\n\n");

		SaveFile.writeFile(srcPath + "dwrConfig.txt", sb.toString());
	}
}
