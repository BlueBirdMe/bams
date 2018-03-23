package com.pinhuba.web.controller.dwr;

import com.pinhuba.core.pojo.DxArchivement;
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
import com.pinhuba.core.iservice.IDxArchivementService;

/**********************************************
 * Class name:
 * Description:
 * Others:
 * History:
 **********************************************/
public class DwrDxArchivementService {

    private final static Logger logger = LoggerFactory.getLogger(DwrDxArchivementService.class);

    @Resource
    private IDxArchivementService dxArchivementService;

    /**
     * 查询 DxArchivement 分页列表
     * @param context
     * @param request
     * @param dxArchivement
     * @param pager
     */
    public ResultBean listDxArchivement(ServletContext context, HttpServletRequest request, DxArchivement dxArchivement, Pager pager){
        List<DxArchivement> list = null;
        pager = PagerHelper.getPager(pager,dxArchivementService.listDxArchivementCount(dxArchivement));
        list = dxArchivementService.listDxArchivement(dxArchivement, pager);
        logger.info("查询 DxArchivement 分页列表...");
        return WebUtilWork.WebResultPack(list, pager);
    }

    /**
     * 查询所有 DxArchivement 列表
     * @param context
     * @param request
     */
    public ResultBean listDxArchivementAll(ServletContext context, HttpServletRequest request){
        DxArchivement dxArchivement = new DxArchivement();
        List<DxArchivement> list = dxArchivementService.listDxArchivement(dxArchivement);
        logger.info("查询所有 DxArchivement 列表...");
        return WebUtilWork.WebResultPack(list);
    }

    /**
     * 保存 DxArchivement
     * @param context
     * @param request
     * @param dxArchivement
     */
    public ResultBean saveDxArchivement(ServletContext context, HttpServletRequest request, DxArchivement dxArchivement){
        String empid = UtilTool.getEmployeeId(request);
        dxArchivement.initSave(empid);
        dxArchivement.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
        dxArchivementService.saveDxArchivement(dxArchivement);
        logger.info("保存 DxArchivement...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 更新 DxArchivement
     * @param context
     * @param request
     * @param dxArchivement
     */
    public ResultBean updateDxArchivement(ServletContext context, HttpServletRequest request, DxArchivement dxArchivement){
        DxArchivement tmp = dxArchivementService.getDxArchivementByPk(dxArchivement.getPrimaryKey());
        String empid = UtilTool.getEmployeeId(request);
        dxArchivement.initUpdate(empid);
        dxArchivementService.saveDxArchivement(dxArchivement);
        logger.info("更新 DxArchivement...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 根据ID获得 DxArchivement
     * @param context
     * @param request
     * @param pk
     */
    public ResultBean getDxArchivementByPk(ServletContext context, HttpServletRequest request, String pk){
        DxArchivement dxArchivement = dxArchivementService.getDxArchivementByPk(pk);
        if(dxArchivement==null){
            logger.info("根据ID获得 DxArchivement...{}", "无信息");
        } else{
            logger.info("根据ID获得 DxArchivement...{}", dxArchivement.getPrimaryKey());
        }
        return WebUtilWork.WebObjectPack(dxArchivement);
    }

    public ResultBean getModDxArchivementByPk(ServletContext context, HttpServletRequest request, String pk){
        DxArchivement dxArchivement = dxArchivementService.getModDxArchivementByPk(pk);
        if(dxArchivement==null){
            logger.info("根据ID获得 DxArchivement...{}", "无信息");
        } else{
            logger.info("根据ID获得 DxArchivement...{}", dxArchivement.getPrimaryKey());
        }
        return WebUtilWork.WebObjectPack(dxArchivement);
    }

    /**
     * 删除 DxArchivement
     * @param context
     * @param request
     * @param pks
     */
    public ResultBean deleteDxArchivementByPks(ServletContext context, HttpServletRequest request, String[] pks){
        dxArchivementService.deleteDxArchivementByPks(pks);
        for (String pk : pks) {
            logger.info("删除 DxArchivement...{}", pk);
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