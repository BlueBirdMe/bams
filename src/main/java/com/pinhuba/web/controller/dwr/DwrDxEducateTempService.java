package com.pinhuba.web.controller.dwr;

import com.pinhuba.core.pojo.DxEducateTemp;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilPrimaryKey;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.core.iservice.IDxEducateTempService;

/**********************************************
 * Class name:
 * Description:
 * Others:
 * History:
 **********************************************/
public class DwrDxEducateTempService {

    private final static Logger logger = LoggerFactory.getLogger(DwrDxEducateTempService.class);

    @Resource
    private IDxEducateTempService dxEducateTempService;

    /**
     * 查询 DxEducateTemp 分页列表
     *
     * @param context
     * @param request
     * @param dxEducateTemp
     * @param pager
     */
    public ResultBean listDxEducateTemp(ServletContext context, HttpServletRequest request, DxEducateTemp dxEducateTemp, Pager pager) {
        List<DxEducateTemp> list = null;
        pager = PagerHelper.getPager(pager, dxEducateTempService.listDxEducateTempCount(dxEducateTemp));
        list = dxEducateTempService.listDxEducateTemp(dxEducateTemp, pager);
        logger.info("查询 DxEducateTemp 分页列表...");
        return WebUtilWork.WebResultPack(list, pager);
    }

    /**
     * 查询所有 DxEducateTemp 列表
     *
     * @param context
     * @param request
     * @param
     * @param
     */
    public ResultBean listDxEducateTempAll(ServletContext context, HttpServletRequest request) {
        DxEducateTemp dxEducateTemp = new DxEducateTemp();
        List<DxEducateTemp> list = dxEducateTempService.listDxEducateTemp(dxEducateTemp);
        logger.info("查询所有 DxEducateTemp 列表...");
        return WebUtilWork.WebResultPack(list);
    }

    /**
     * 保存 DxEducateTemp
     *
     * @param context
     * @param request
     * @param dxEducateTemp
     */
    public ResultBean saveDxEducateTemp(ServletContext context, HttpServletRequest request, DxEducateTemp dxEducateTemp) {
        String empid = UtilTool.getEmployeeId(request);
        dxEducateTempService.deleteDxEducateTempByPks(new String[]{empid});
        dxEducateTemp.initSave(empid);
        dxEducateTemp.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
        dxEducateTempService.saveDxEducateTemp(dxEducateTemp);
        logger.info("保存 DxEducateTemp...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 更新 DxEducateTemp
     *
     * @param context
     * @param request
     * @param dxEducateTemp
     */
    public ResultBean updateDxEducateTemp(ServletContext context, HttpServletRequest request, DxEducateTemp dxEducateTemp) {
        DxEducateTemp tmp = dxEducateTempService.getDxEducateTempByPk(dxEducateTemp.getPrimaryKey());
        String empid = UtilTool.getEmployeeId(request);
        dxEducateTemp.initUpdate(empid);
        dxEducateTempService.saveDxEducateTemp(dxEducateTemp);
        logger.info("更新 DxEducateTemp...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 根据ID获得 DxEducateTemp
     *
     * @param context
     * @param request
     * @param pk
     */
    public ResultBean getDxEducateTempByPk(ServletContext context, HttpServletRequest request, String pk) {
        DxEducateTemp dxEducateTemp = dxEducateTempService.getDxEducateTempByPk(pk);
        logger.info("根据ID获得 DxEducateTemp...{}", dxEducateTemp.getPrimaryKey());
        return WebUtilWork.WebObjectPack(dxEducateTemp);
    }

    /**
     * 删除 DxEducateTemp
     *
     * @param context
     * @param request
     * @param pks
     */
    public ResultBean deleteDxEducateTempByPks(ServletContext context, HttpServletRequest request, String[] pks) {
        dxEducateTempService.deleteDxEducateTempByPks(pks);
        for (String pk : pks) {
            logger.info("删除 DxEducateTemp...{}", pk);
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
/**********************Jacy-end**********************/

}