package com.pinhuba.web.controller.dwr;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.core.iservice.INetdiskService;
import com.pinhuba.core.pojo.OaNetdiskConfig;
import com.pinhuba.core.pojo.OaNetdiskShare;

/**
 * 网络磁盘
 * @author Administrator
 *
 */
public class DwrNetdiskService {
    private Logger log = Logger.getLogger(this.getClass());
	
    @Resource
	private INetdiskService netdiskService;

	/**
	 * 保存共享记录
	 * @param context
	 * @param request
	 * @param oaNetdiskShare
	 * @return
	 */
	public ResultBean saveNetdiskShare(ServletContext context, HttpServletRequest request, OaNetdiskShare oaNetdiskShare) {
			String empid = UtilTool.getEmployeeId(request);
			String nowtime = UtilWork.getNowTime();
			OaNetdiskShare tmp = netdiskService.getShareByHrmEmpIDandPath(oaNetdiskShare);
			if (tmp != null) {
				tmp.setNetdiskDeps(oaNetdiskShare.getNetdiskDeps());
				tmp.setNetdiskEmps(oaNetdiskShare.getNetdiskEmps());
				tmp.setShareDesc(oaNetdiskShare.getShareDesc());
				oaNetdiskShare.setLastmodiId(empid);
				oaNetdiskShare.setLastmodiDate(nowtime);
				netdiskService.saveOaNetdiskShare(tmp);
			} else {
				//新纪录
				oaNetdiskShare.setRecordId(empid);
				oaNetdiskShare.setRecordDate(nowtime);
				oaNetdiskShare.setHrmEmployeeId(empid);
				oaNetdiskShare.setCompanyId(UtilTool.getCompanyId(request));
				oaNetdiskShare.setLastmodiId(empid);
				oaNetdiskShare.setLastmodiDate(nowtime);
				netdiskService.saveOaNetdiskShare(oaNetdiskShare);
			}
			
			log.info("保存共享记录...");
			return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 查询共享记录
	 * @param context
	 * 
	 * @param request
	 * @param oaNetdiskShare
	 * @return
	 */
	public ResultBean getShareByHrmEmpIDandPath(ServletContext context, HttpServletRequest request, OaNetdiskShare oaNetdiskShare) {
		OaNetdiskShare tmp = netdiskService.getShareByHrmEmpIDandPath(oaNetdiskShare);
		log.info("查询共享记录...");
		return WebUtilWork.WebObjectPack(tmp);
}
	
	/**
	 * 磁盘管理
	 * netdiskService.getHrmEmployee() 检查是否有新员工 没有在磁盘表 如果没有 就添加进去
	 * netdiskService.listOaNetdisk(） 查询 所有磁盘设置表的数据
	 * netdiskService.gethrmEmployeebyid()把人放进 磁盘表 前台读取名字
	 * netdiskService.getHrmDeparTmentBy()把部门放进人表 前台读取 部门名称
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listOaNetdisk(ServletContext context, HttpServletRequest request, OaNetdiskConfig oaNetdisk, Pager paer) throws Exception {
		List<OaNetdiskConfig> OaNetdisks = null;

		oaNetdisk.setCompanyId(UtilTool.getCompanyId(request));
		netdiskService.getHrmEmployee(oaNetdisk, request);
		paer = PagerHelper.getPager(paer, netdiskService.listOaNetdiskCount(oaNetdisk));
		OaNetdisks = netdiskService.listOaNetdisk(oaNetdisk, paer);
		for (OaNetdiskConfig OaNetdis : OaNetdisks) {
			OaNetdis.setHrmEmployee(netdiskService.gethrmEmployeebyid(OaNetdis.getHrmEmployeeId()));
			OaNetdis.getHrmEmployee().setHrmDepartment(netdiskService.getHrmDeparTmentBy(OaNetdis.getHrmEmployee().getHrmEmployeeDepid()));
		}
		log.info("磁盘管理显示取值");
		return WebUtilWork.WebResultPack(OaNetdisks, paer);
	}

	/**
	 * 设置磁盘管理大小
	 *  oaNetdisk.getHrmEmployeeId() 做临时 存储 前台传过来的 主键字符串 因为下面要 要分解出单个的主键
	 *  保存	netdiskService.SeveOaNetdisk(oaNetdisk); 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean addOaNetdisk(ServletContext context, HttpServletRequest request, OaNetdiskConfig oaNetdisk)throws Exception {
        int totalSpace=oaNetdisk.getTotalSpace();
		String time = UtilWork.getNowTime();
			String hrmEmployee = oaNetdisk.getHrmEmployeeId()+",";
			int j = 0;
			for (int i = 0; i < hrmEmployee.length(); i++) {
				//System.out.print(hrmEmployee.length());
				i = hrmEmployee.indexOf(",", i);
				oaNetdisk=netdiskService.getOaNetdisk(Long.parseLong(hrmEmployee.substring(j, i)));
				oaNetdisk.setTotalSpace(totalSpace);
				oaNetdisk.setLastmodiId(UtilTool.getEmployeeId(request));
			
			    oaNetdisk.setLastmodiDate(time);
				oaNetdisk.setRecordId(oaNetdisk.getRecordId());
				oaNetdisk.setRecordDate(oaNetdisk.getRecordDate());
				oaNetdisk.setCompanyId(UtilTool.getCompanyId(request));
				if(totalSpace<=oaNetdisk.getUsedSpace()){
					return new ResultBean(false, "您设置的大小,小于已经使用的空间了，不能修改");
				}else{
					netdiskService.SeveOaNetdisk(oaNetdisk);
				}
				i++;
				j = i;
			    
			}
			log.info("设置磁盘管大小示取值");
		
		return WebUtilWork.WebResultPack(null);
	}
	/**
	 * 根据ID去 修改大小磁盘的
	 * @return
	 */
	public ResultBean getOaNetdisksize(ServletContext context, HttpServletRequest request, int id)throws Exception {
		OaNetdiskConfig	oaNetdisk=netdiskService.getOaNetdisk(id);
		
		return WebUtilWork.WebObjectPack(oaNetdisk);
	}
}
