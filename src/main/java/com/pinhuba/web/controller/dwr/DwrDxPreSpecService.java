package com.pinhuba.web.controller.dwr;

import com.pinhuba.core.pojo.DxPreSpec;
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
import com.pinhuba.core.iservice.IDxPreSpecService;

/**********************************************
 * Class name:
 * Description:
 * Others:
 * History:
 **********************************************/
public class DwrDxPreSpecService {

    private final static Logger logger = LoggerFactory.getLogger(DwrDxPreSpecService.class);

    @Resource
    private IDxPreSpecService dxPreSpecService;

    /**
     * 查询 DxPreSpec 分页列表
     * @param context
     * @param request
     * @param dxPreSpec
     * @param pager
     */
    public ResultBean listDxPreSpec(ServletContext context, HttpServletRequest request, DxPreSpec dxPreSpec, Pager pager){
        List<DxPreSpec> list = null;
        pager = PagerHelper.getPager(pager,dxPreSpecService.listDxPreSpecCount(dxPreSpec));
        list = dxPreSpecService.listDxPreSpec(dxPreSpec, pager);
        logger.info("查询 DxPreSpec 分页列表...");
        return WebUtilWork.WebResultPack(list, pager);
    }

    /**
     * 查询所有 DxPreSpec 列表
     * @param context
     * @param request
     * @param dxPreSpec
     * @param pager
     */
    public ResultBean listDxPreSpecAll(ServletContext context, HttpServletRequest request){
        DxPreSpec dxPreSpec = new DxPreSpec();
        List<DxPreSpec> list = dxPreSpecService.listDxPreSpec(dxPreSpec);
        logger.info("查询所有 DxPreSpec 列表...");
        return WebUtilWork.WebResultPack(list);
    }

    /**
     * 保存 DxPreSpec
     * @param context
     * @param request
     * @param dxPreSpec
     */
    public ResultBean saveDxPreSpec(ServletContext context, HttpServletRequest request, DxPreSpec dxPreSpec){
        String empid = UtilTool.getEmployeeId(request);
        dxPreSpec.initSave(empid);
        dxPreSpec.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
        dxPreSpecService.saveDxPreSpec(dxPreSpec);
        logger.info("保存 DxPreSpec...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 更新 DxPreSpec
     * @param context
     * @param request
     * @param dxPreSpec
     */
    public ResultBean updateDxPreSpec(ServletContext context, HttpServletRequest request, DxPreSpec dxPreSpec){
        DxPreSpec tmp = dxPreSpecService.getDxPreSpecByPk(dxPreSpec.getPrimaryKey());
        String empid = UtilTool.getEmployeeId(request);
        dxPreSpec.initUpdate(empid);
        dxPreSpecService.saveDxPreSpec(dxPreSpec);
        logger.info("更新 DxPreSpec...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 根据ID获得 DxPreSpec
     * @param context
     * @param request
     * @param pk
     */
    public ResultBean getDxPreSpecByPk(ServletContext context, HttpServletRequest request, String pk){
        DxPreSpec dxPreSpec = dxPreSpecService.getDxPreSpecByPk(pk);
        logger.info("根据ID获得 DxPreSpec...{}", dxPreSpec.getPrimaryKey());
        return WebUtilWork.WebObjectPack(dxPreSpec);
    }

    /**
     * 删除 DxPreSpec
     * @param context
     * @param request
     * @param pks
     */
    public ResultBean deleteDxPreSpecByPks(ServletContext context, HttpServletRequest request, String[] pks){
        dxPreSpecService.deleteDxPreSpecByPks(pks);
        for (String pk : pks) {
            logger.info("删除 DxPreSpec...{}", pk);
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