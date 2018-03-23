package com.pinhuba.web.controller.dwr;

import com.pinhuba.core.pojo.DxUserinfo;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilPrimaryKey;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.core.iservice.IDxUserinfoService;

/**********************************************
 * Class name:
 * Description:
 * Others:
 * History:
 **********************************************/
public class DwrDxUserinfoService {

    private final static Logger logger = LoggerFactory.getLogger(DwrDxUserinfoService.class);

    @Resource
    private IDxUserinfoService dxUserinfoService;

    /**
     * 查询 DxUserinfo 分页列表
     *
     * @param context
     * @param request
     * @param dxUserinfo
     * @param pager
     */
    public ResultBean listDxUserinfo(ServletContext context, HttpServletRequest request, DxUserinfo dxUserinfo, Pager pager) {
        List<DxUserinfo> list = null;
        pager = PagerHelper.getPager(pager, dxUserinfoService.listDxUserinfoCount(dxUserinfo));
        list = dxUserinfoService.listDxUserinfo(dxUserinfo, pager);
        logger.info("查询 DxUserinfo 分页列表...");
        return WebUtilWork.WebResultPack(list, pager);
    }

    /**
     * 查询所有 DxUserinfo 列表
     *
     * @param context
     * @param request
     * @param dxUserinfo
     * @param pager
     */
    public ResultBean listDxUserinfoAll(ServletContext context, HttpServletRequest request) {
        DxUserinfo dxUserinfo = new DxUserinfo();
        List<DxUserinfo> list = dxUserinfoService.listDxUserinfo(dxUserinfo);
        logger.info("查询所有 DxUserinfo 列表...");
        return WebUtilWork.WebResultPack(list);
    }

    /**
     * 保存 DxUserinfo
     *
     * @param context
     * @param request
     * @param dxUserinfo
     */
    public ResultBean saveDxUserinfo(ServletContext context, HttpServletRequest request, DxUserinfo dxUserinfo) {
        String empid = UtilTool.getEmployeeId(request);
        dxUserinfo.initSave(empid);
        dxUserinfo.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
        dxUserinfoService.saveDxUserinfo(dxUserinfo);
        logger.info("保存 DxUserinfo...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 更新 DxUserinfo
     *
     * @param context
     * @param request
     * @param dxUserinfo
     */
    public ResultBean updateDxUserinfo(ServletContext context, HttpServletRequest request, DxUserinfo dxUserinfo) {
        DxUserinfo tmp = dxUserinfoService.getDxUserinfoByPk(dxUserinfo.getPrimaryKey());
        String empid = UtilTool.getEmployeeId(request);
        dxUserinfo.initUpdate(empid);
        dxUserinfoService.saveDxUserinfo(dxUserinfo);
        logger.info("更新 DxUserinfo...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 根据ID获得 DxUserinfo
     *
     * @param context
     * @param request
     * @param pk
     */
    public ResultBean getDxUserinfoByPk(ServletContext context, HttpServletRequest request, String pk) {
        DxUserinfo dxUserinfo = dxUserinfoService.getDxUserinfoByPk(pk);
        logger.info("根据ID获得 DxUserinfo...{}", dxUserinfo.getPrimaryKey());
        return WebUtilWork.WebObjectPack(dxUserinfo);
    }

    /**
     * 删除 DxUserinfo
     *
     * @param context
     * @param request
     * @param pks
     */
    public ResultBean deleteDxUserinfoByPks(ServletContext context, HttpServletRequest request, String[] pks) {
        dxUserinfoService.deleteDxUserinfoByPks(pks);
        for (String pk : pks) {
            logger.info("删除 DxUserinfo...{}", pk);
        }
        return WebUtilWork.WebResultPack(null);
    }

/**********************************************
 * 以上代码由BAMS代码生成工具自动生成，请根据具体需求进行修改。
 * 开发人员在此注释以下编写业务逻辑代码，并将自己写的代码框起来，便于后期代码合并，例如：
 **********************************************/

    /**********************
     * JC-begin
     **********************/
    public void method() {
        System.out.println("JC's code here");
    }
/**********************JC-end**********************/

    /**********************
     * Jacy-begin
     **********************/
    public void method2() {
        System.out.println("Jacy's code here");
    }

    /**********************
     * Jacy-end
     **********************/

    /**
     * 查询所有 DxUserinfo 列表
     *
     * @param context
     * @param request
     */
    public ResultBean listDxUserByDept_Age(ServletContext context, HttpServletRequest request, String s_age, String e_age, String deptName) {
        DxUserinfo dxUserinfo = new DxUserinfo();
        List<DxUserinfo> list = dxUserinfoService.listDxUserinfo(dxUserinfo);
        logger.info("按条件查询 DxUserinfo 列表...");
        return WebUtilWork.WebResultPack(list);
    }

    public ResultBean countUserByDept_Age(ServletContext context, HttpServletRequest request, String s_age, String e_age, String deptName) {
        List<Object[]> list = dxUserinfoService.countUserByDept_Age(s_age, e_age, deptName);
        logger.info("按条件查询 DxUserinfo 列表...");
        return WebUtilWork.WebResultPack(list);
    }

}