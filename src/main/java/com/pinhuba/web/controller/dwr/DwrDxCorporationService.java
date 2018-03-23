package com.pinhuba.web.controller.dwr;

import com.pinhuba.core.pojo.DxCorporation;
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
import com.pinhuba.core.iservice.IDxCorporationService;

/**********************************************
 * Class name:
 * Description:
 * Others:
 * History:
 **********************************************/
public class DwrDxCorporationService {

    private final static Logger logger = LoggerFactory.getLogger(DwrDxCorporationService.class);

    @Resource
    private IDxCorporationService dxCorporationService;

    /**
     * 查询 DxCorporation 分页列表
     * @param context
     * @param request
     * @param dxCorporation
     * @param pager
     */
    public ResultBean listDxCorporation(ServletContext context, HttpServletRequest request, DxCorporation dxCorporation, Pager pager){
        List<DxCorporation> list = null;
        pager = PagerHelper.getPager(pager,dxCorporationService.listDxCorporationCount(dxCorporation));
        list = dxCorporationService.listDxCorporation(dxCorporation, pager);
        logger.info("查询 DxCorporation 分页列表...");
        return WebUtilWork.WebResultPack(list, pager);
    }

    /**
     * 查询所有 DxCorporation 列表
     * @param context
     * @param request
     * @param dxCorporation
     * @param pager
     */
    public ResultBean listDxCorporationAll(ServletContext context, HttpServletRequest request){
        DxCorporation dxCorporation = new DxCorporation();
        List<DxCorporation> list = dxCorporationService.listDxCorporation(dxCorporation);
        logger.info("查询所有 DxCorporation 列表...");
        return WebUtilWork.WebResultPack(list);
    }

    /**
     * 保存 DxCorporation
     * @param context
     * @param request
     * @param dxCorporation
     */
    public ResultBean saveDxCorporation(ServletContext context, HttpServletRequest request, DxCorporation dxCorporation){
        String empid = UtilTool.getEmployeeId(request);
        dxCorporation.initSave(empid);
        dxCorporation.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
        dxCorporationService.saveDxCorporation(dxCorporation);
        logger.info("保存 DxCorporation...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 更新 DxCorporation
     * @param context
     * @param request
     * @param dxCorporation
     */
    public ResultBean updateDxCorporation(ServletContext context, HttpServletRequest request, DxCorporation dxCorporation){
        DxCorporation tmp = dxCorporationService.getDxCorporationByPk(dxCorporation.getPrimaryKey());
        String empid = UtilTool.getEmployeeId(request);
        dxCorporation.initUpdate(empid);
        dxCorporationService.saveDxCorporation(dxCorporation);
        logger.info("更新 DxCorporation...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 根据ID获得 DxCorporation
     * @param context
     * @param request
     * @param pk
     */
    public ResultBean getDxCorporationByPk(ServletContext context, HttpServletRequest request, String pk){
        DxCorporation dxCorporation = dxCorporationService.getDxCorporationByPk(pk);
        logger.info("根据ID获得 DxCorporation...{}", dxCorporation.getPrimaryKey());
        return WebUtilWork.WebObjectPack(dxCorporation);
    }

    /**
     * 删除 DxCorporation
     * @param context
     * @param request
     * @param pks
     */
    public ResultBean deleteDxCorporationByPks(ServletContext context, HttpServletRequest request, String[] pks){
        dxCorporationService.deleteDxCorporationByPks(pks);
        for (String pk : pks) {
            logger.info("删除 DxCorporation...{}", pk);
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