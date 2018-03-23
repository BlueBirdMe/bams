package com.pinhuba.web.controller.dwr;

import com.pinhuba.core.pojo.DxEducate;
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
import com.pinhuba.core.iservice.IDxEducateService;

/**********************************************
 * Class name:
 * Description:
 * Others:
 * History:
 **********************************************/
public class DwrDxEducateService {

    private final static Logger logger = LoggerFactory.getLogger(DwrDxEducateService.class);

    @Resource
    private IDxEducateService dxEducateService;

    /**
     * 查询 DxEducate 分页列表
     * @param context
     * @param request
     * @param dxEducate
     * @param pager
     */
    public ResultBean listDxEducate(ServletContext context, HttpServletRequest request, DxEducate dxEducate, Pager pager){
        List<DxEducate> list = null;
        pager = PagerHelper.getPager(pager,dxEducateService.listDxEducateCount(dxEducate));
        list = dxEducateService.listDxEducate(dxEducate, pager);
        logger.info("查询 DxEducate 分页列表...");
        return WebUtilWork.WebResultPack(list, pager);
    }

    /**
     * 查询所有 DxEducate 列表
     * @param context
     * @param request
     * @param dxEducate
     * @param pager
     */
    public ResultBean listDxEducateAll(ServletContext context, HttpServletRequest request){
        DxEducate dxEducate = new DxEducate();
        List<DxEducate> list = dxEducateService.listDxEducate(dxEducate);
        logger.info("查询所有 DxEducate 列表...");
        return WebUtilWork.WebResultPack(list);
    }

    /**
     * 保存 DxEducate
     * @param context
     * @param request
     * @param dxEducate
     */
    public ResultBean saveDxEducate(ServletContext context, HttpServletRequest request, DxEducate dxEducate){
        String empid = UtilTool.getEmployeeId(request);
        dxEducate.initSave(empid);
        dxEducate.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
        dxEducateService.saveDxEducate(dxEducate);
        logger.info("保存 DxEducate...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 更新 DxEducate
     * @param context
     * @param request
     * @param dxEducate
     */
    public ResultBean updateDxEducate(ServletContext context, HttpServletRequest request, DxEducate dxEducate){
        DxEducate tmp = dxEducateService.getDxEducateByPk(dxEducate.getPrimaryKey());
        String empid = UtilTool.getEmployeeId(request);
        dxEducate.initUpdate(empid);
        dxEducateService.saveDxEducate(dxEducate);
        logger.info("更新 DxEducate...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 根据ID获得 DxEducate
     * @param context
     * @param request
     * @param pk
     */
    public ResultBean getDxEducateByPk(ServletContext context, HttpServletRequest request, String pk){
        DxEducate dxEducate = dxEducateService.getDxEducateByPk(pk);
        if(dxEducate==null){
            logger.info("根据ID获得 dxEducate...{}", "无信息");
        } else{
            logger.info("根据ID获得 dxEducate...{}", dxEducate.getPrimaryKey());
        }
        return WebUtilWork.WebObjectPack(dxEducate);
    }

    public ResultBean getModDxEducateByPk(ServletContext context, HttpServletRequest request, String pk){
        DxEducate dxEducate = dxEducateService.getModDxEducateByPk(pk);
        if(dxEducate==null){
            logger.info("根据ID获得 dxEducate...{}", "无信息");
        } else{
            logger.info("根据ID获得 dxEducate...{}", dxEducate.getPrimaryKey());
        }
        return WebUtilWork.WebObjectPack(dxEducate);
    }



    /**
     * 删除 DxEducate
     * @param context
     * @param request
     * @param pks
     */
    public ResultBean deleteDxEducateByPks(ServletContext context, HttpServletRequest request, String[] pks){
        dxEducateService.deleteDxEducateByPks(pks);
        for (String pk : pks) {
            logger.info("删除 DxEducate...{}", pk);
        }
        return WebUtilWork.WebResultPack(null);
    }

/**********************************************
 * 以上代码由BAMS代码生成工具自动生成，请根据具体需求进行修改。
 * 开发人员在此注释以下编写业务逻辑代码，并将自己写的代码框起来，便于后期代码合并，例如：
 **********************************************/

/**********************JC-begin**********************/
    public void method(){
        System.out.println("JC's code here");
    }
/**********************JC-end**********************/

/**********************Jacy-begin**********************/
    public void method2(){
        System.out.println("Jacy's code here");
    }
/**********************Jacy-end**********************/

}