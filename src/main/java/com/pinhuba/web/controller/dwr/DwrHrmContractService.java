package com.pinhuba.web.controller.dwr;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.UtilPrimaryKey;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.core.iservice.IHrmContractService;
import com.pinhuba.core.pojo.HrmContract;
import com.pinhuba.core.pojo.HrmContractType;

/**********************************************
 * Class name:
 * Description:
 * Others:
 * History:
 **********************************************/
public class DwrHrmContractService {

    private final static Logger logger = LoggerFactory.getLogger(DwrHrmContractService.class);

    @Resource
    private IHrmContractService hrmContractService;

    /**
     * 查询 HrmContract 分页列表
     * @param context
     * @param request
     * @param hrmContract
     * @param pager
     */
    public ResultBean listHrmContract(ServletContext context, HttpServletRequest request, HrmContract hrmContract, Pager pager){
    	hrmContract.setCompanyId(UtilTool.getCompanyId(request));
    	List<HrmContract> list = null;
        pager = PagerHelper.getPager(pager,hrmContractService.listHrmContractCount(hrmContract));
        list = hrmContractService.listHrmContract(hrmContract, pager);
        logger.info("查询 HrmContract 分页列表...");
        return WebUtilWork.WebResultPack(list, pager);
    }

    /**
     * 保存 HrmContract
     * @param context
     * @param request
     * @param hrmContract
     */
    public ResultBean saveHrmContract(ServletContext context, HttpServletRequest request, HrmContract hrmContract ,String attach){
    	// 保存附件
		String ids = UtilTool.saveAttachments(context, request, attach);
		hrmContract.setContractFile(ids);
    	
    	String empid = UtilTool.getEmployeeId(request);
        hrmContract.initSave(empid);
        hrmContract.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
        hrmContract.setCompanyId(UtilTool.getCompanyId(request));
        hrmContractService.saveHrmContract(hrmContract);
        logger.info("保存 HrmContract...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 更新 HrmContract
     * @param context
     * @param request
     * @param hrmContract
     */
    public ResultBean updateHrmContract(ServletContext context, HttpServletRequest request, HrmContract hrmContract ,String attach){
        HrmContract tmp = hrmContractService.getHrmContractByPk(hrmContract.getPrimaryKey());
        // 删除原附件
     	UtilTool.deleteAttachmentsNoFile(context, request, tmp.getContractFile());
     	// 保存附件
 		String ids = UtilTool.saveAttachments(context, request, attach);
 		hrmContract.setContractFile(ids);
        String empid = UtilTool.getEmployeeId(request);
        hrmContract.initUpdate(empid);
        hrmContract.setCompanyId(UtilTool.getCompanyId(request));
        hrmContractService.saveHrmContract(hrmContract);
        logger.info("更新 HrmContract...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 根据ID获得 HrmContract
     * @param context
     * @param request
     * @param pk
     */
    public ResultBean getHrmContractByPk(ServletContext context, HttpServletRequest request, String pk){
        HrmContract hrmContract = hrmContractService.getHrmContractByPk(pk);
        logger.info("根据ID获得 HrmContract...{}", hrmContract.getPrimaryKey());
        return WebUtilWork.WebObjectPack(hrmContract);
    }

    /**
     * 删除 HrmContract
     * @param context
     * @param request
     * @param pks
     */
    public ResultBean deleteHrmContractByPks(ServletContext context, HttpServletRequest request, String[] pks){
        hrmContractService.deleteHrmContractByPks(pks);
        for (String pk : pks) {
            logger.info("删除 HrmContract...{}", pk);
        }
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 查询 HrmContractType 分页列表
     * @param context
     * @param request
     * @param hrmContractType
     * @param pager
     */
    public ResultBean listHrmContractType(ServletContext context, HttpServletRequest request, HrmContractType hrmContractType, Pager pager){
    	hrmContractType.setCompanyId(UtilTool.getCompanyId(request));
    	List<HrmContractType> list = null;
        pager = PagerHelper.getPager(pager,hrmContractService.listHrmContractTypeCount(hrmContractType));
        list = hrmContractService.listHrmContractType(hrmContractType, pager);
        logger.info("查询 HrmContractType 分页列表...");
        return WebUtilWork.WebResultPack(list, pager);
    }
    
    /**
     * 查询 HrmContractType 所有
     * @param context
     * @param request
     */
    public ResultBean listHrmContractTypeAll(ServletContext context, HttpServletRequest request){
        HrmContractType hrmContractType = new HrmContractType();
        hrmContractType.setCompanyId(UtilTool.getCompanyId(request));
        List<HrmContractType> list = hrmContractService.listHrmContractType(hrmContractType);
        logger.info("查询所有 HrmContractType 列表...");
        return WebUtilWork.WebResultPack(list);
    }


    /**
     * 保存 HrmContractType
     * @param context
     * @param request
     * @param hrmContractType
     */
    public ResultBean saveHrmContractType(ServletContext context, HttpServletRequest request, HrmContractType hrmContractType, String attach){
    	// 保存附件
		String ids = UtilTool.saveAttachments(context, request, attach);
		hrmContractType.setTypeFile(ids);
    	
    	String empid = UtilTool.getEmployeeId(request);
        hrmContractType.initSave(empid);
        hrmContractType.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
        hrmContractType.setCompanyId(UtilTool.getCompanyId(request));
        hrmContractService.saveHrmContractType(hrmContractType);
        logger.info("保存 HrmContractType...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 更新 HrmContractType
     * @param context
     * @param request
     * @param hrmContractType
     */
    public ResultBean updateHrmContractType(ServletContext context, HttpServletRequest request, HrmContractType hrmContractType, String attach){
        HrmContractType tmp = hrmContractService.getHrmContractTypeByPk(hrmContractType.getPrimaryKey());
        
        // 删除原附件
     	UtilTool.deleteAttachmentsNoFile(context, request, tmp.getTypeFile());
     	// 保存附件
 		String ids = UtilTool.saveAttachments(context, request, attach);
 		hrmContractType.setTypeFile(ids);
        
        String empid = UtilTool.getEmployeeId(request);
        hrmContractType.initUpdate(empid);
        hrmContractType.setCompanyId(UtilTool.getCompanyId(request));
        hrmContractService.saveHrmContractType(hrmContractType);
        logger.info("更新 HrmContractType...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 根据ID获得 HrmContractType
     * @param context
     * @param request
     * @param pk
     */
    public ResultBean getHrmContractTypeByPk(ServletContext context, HttpServletRequest request, String pk){
        HrmContractType hrmContractType = hrmContractService.getHrmContractTypeByPk(pk);
        logger.info("根据ID获得 HrmContractType...{}", hrmContractType.getPrimaryKey());
        return WebUtilWork.WebObjectPack(hrmContractType);
    }

    /**
     * 删除 HrmContractType
     * @param context
     * @param request
     * @param pks
     */
    public ResultBean deleteHrmContractTypeByPks(ServletContext context, HttpServletRequest request, String[] pks){
        for (String pk : pks) {
        	if(hrmContractService.listHrmContractCountByType(pk) > 0){
        		return new ResultBean(false,"该类型已经被使用，不能删除！");
        	}
        	
            logger.info("删除 HrmContractType...{}", pk);
        }
        hrmContractService.deleteHrmContractTypeByPks(pks);
        return WebUtilWork.WebResultPack(null);
    }

/**********************************************
 * 以上代码由BAMS代码生成工具自动生成，一般情况下无需修改。
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