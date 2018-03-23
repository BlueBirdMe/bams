package com.pinhuba.web.controller.dwr;

import com.pinhuba.core.pojo.DxArchivementTemp;
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
import com.pinhuba.core.iservice.IDxArchivementTempService;

/**********************************************
 * Class name:
 * Description:
 * Others:
 * History:
 **********************************************/
public class DwrDxArchivementTempService {

    private final static Logger logger = LoggerFactory.getLogger(DwrDxArchivementTempService.class);

    @Resource
    private IDxArchivementTempService dxArchivementTempService;

    /**
     * 查询 DxArchivementTemp 分页列表
     * @param context
     * @param request
     * @param dxArchivementTemp
     * @param pager
     */
    public ResultBean listDxArchivementTemp(ServletContext context, HttpServletRequest request, DxArchivementTemp dxArchivementTemp, Pager pager){
        List<DxArchivementTemp> list = null;
        pager = PagerHelper.getPager(pager,dxArchivementTempService.listDxArchivementTempCount(dxArchivementTemp));
        list = dxArchivementTempService.listDxArchivementTemp(dxArchivementTemp, pager);
        logger.info("查询 DxArchivementTemp 分页列表...");
        return WebUtilWork.WebResultPack(list, pager);
    }

    /**
     * 查询所有 DxArchivementTemp 列表
     * @param context
     * @param request
     * @param
     * @param
     */
    public ResultBean listDxArchivementTempAll(ServletContext context, HttpServletRequest request){
        DxArchivementTemp dxArchivementTemp = new DxArchivementTemp();
        List<DxArchivementTemp> list = dxArchivementTempService.listDxArchivementTemp(dxArchivementTemp);
        logger.info("查询所有 DxArchivementTemp 列表...");
        return WebUtilWork.WebResultPack(list);
    }

    /**
     * 保存 DxArchivementTemp
     * @param context
     * @param request
     * @param dxArchivementTemp
     */
    public ResultBean saveDxArchivementTemp(ServletContext context, HttpServletRequest request, DxArchivementTemp dxArchivementTemp){
        String empid = UtilTool.getEmployeeId(request);
        dxArchivementTempService.deleteDxArchivementTempByPks(new String[]{empid});
        dxArchivementTemp.initSave(empid);
        dxArchivementTemp.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
        dxArchivementTempService.saveDxArchivementTemp(dxArchivementTemp);
        logger.info("保存 DxArchivementTemp...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 更新 DxArchivementTemp
     * @param context
     * @param request
     * @param dxArchivementTemp
     */
    public ResultBean updateDxArchivementTemp(ServletContext context, HttpServletRequest request, DxArchivementTemp dxArchivementTemp){
        DxArchivementTemp tmp = dxArchivementTempService.getDxArchivementTempByPk(dxArchivementTemp.getPrimaryKey());
        String empid = UtilTool.getEmployeeId(request);
        dxArchivementTemp.initUpdate(empid);
        dxArchivementTempService.saveDxArchivementTemp(dxArchivementTemp);
        logger.info("更新 DxArchivementTemp...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 根据ID获得 DxArchivementTemp
     * @param context
     * @param request
     * @param pk
     */
    public ResultBean getDxArchivementTempByPk(ServletContext context, HttpServletRequest request, String pk){
        DxArchivementTemp dxArchivementTemp = dxArchivementTempService.getDxArchivementTempByPk(pk);
        logger.info("根据ID获得 DxArchivementTemp...{}", dxArchivementTemp.getPrimaryKey());
        return WebUtilWork.WebObjectPack(dxArchivementTemp);
    }

    /**
     * 删除 DxArchivementTemp
     * @param context
     * @param request
     * @param pks
     */
    public ResultBean deleteDxArchivementTempByPks(ServletContext context, HttpServletRequest request, String[] pks){
        dxArchivementTempService.deleteDxArchivementTempByPks(pks);
        for (String pk : pks) {
            logger.info("删除 DxArchivementTemp...{}", pk);
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