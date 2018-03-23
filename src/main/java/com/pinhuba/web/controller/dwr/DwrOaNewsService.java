package com.pinhuba.web.controller.dwr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.iservice.IOaNewsService;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaAdversaria;
import com.pinhuba.core.pojo.OaAnnouncement;
import com.pinhuba.core.pojo.OaNotice;
import com.pinhuba.core.pojo.SysLibraryInfo;

/**********************************************
Class name: 信息发布
Description:对DWR服务进行描述
Others:         // 
History:        
liurunkai    2010.4.28     v3.0
**********************************************/
public class DwrOaNewsService {
	
	private final static Logger logger = Logger.getLogger(DwrOaNewsService.class);
	@Resource
	private IOaNewsService oaNewsService;
	@Resource
	private IHrmEmployeeService employeeinfoService;

    /**s
	 * 显示公告
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
    public ResultBean  listAnnouncement(ServletContext context, HttpServletRequest request,OaAnnouncement announcement,Pager pager){
    	List<OaAnnouncement> list = null;
		pager = PagerHelper.getPager(pager,oaNewsService.listAnnouncementCount(announcement,UtilTool.getCompanyId(request)));
		list = oaNewsService.getAllAnnouncement(announcement, UtilTool.getCompanyId(request), pager);
		for (OaAnnouncement oaAnnouncement : list) {
			if(oaAnnouncement.getOaAnnoEmp() != null){
				oaAnnouncement.setEmployee(employeeinfoService.getEmployeeByPK(oaAnnouncement.getOaAnnoEmp()));
			}else{
				oaAnnouncement.setEmployee(new HrmEmployee());
			}
			if(oaAnnouncement.getOaAnnoType() != null){
				oaAnnouncement.setOaAnnoLib(oaNewsService.getLibraryInfoByPK(oaAnnouncement.getOaAnnoType()));
			}else{
				oaAnnouncement.setOaAnnoLib(new SysLibraryInfo());
			}
		}
		logger.info("显示所有公告...");
    	
    	return WebUtilWork.WebResultPack(list, pager);
    }
    
    /**s
	 * 显示公告(查看页面)
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
    public ResultBean  listAnnouncementView(ServletContext context, HttpServletRequest request,OaAnnouncement announcement,Pager pager){
    	List<OaAnnouncement> list = null;
		pager = PagerHelper.getPager(pager,oaNewsService.listAnnouncementCount(announcement,UtilTool.getCompanyId(request)));
		list = oaNewsService.getAllAnnouncement(announcement, UtilTool.getCompanyId(request), pager);
		for (OaAnnouncement oaAnnouncement : list) {
			if(oaAnnouncement.getOaAnnoType() != null){
				oaAnnouncement.setOaAnnoLib(oaNewsService.getLibraryInfoByPK(oaAnnouncement.getOaAnnoType()));
			}else{
				oaAnnouncement.setOaAnnoLib(new SysLibraryInfo());
			}
			if(oaAnnouncement.getOaAnnoEmp() != null && oaAnnouncement.getOaAnnoEmp().length() > 0){
				oaAnnouncement.setEmployee(employeeinfoService.getEmployeeByPK(oaAnnouncement.getOaAnnoEmp()));
			}
		}
		logger.info("显示查看所有公告...");
    	
    	return WebUtilWork.WebResultPack(list, pager);
    }
    
    /**s
	 * 删除公告
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
    public ResultBean deleteAnnouncementById(ServletContext context,HttpServletRequest request,long[] ids){
		try {
			for (long l : ids) {
				OaAnnouncement announcement = oaNewsService.getAnnouncementByPK(l);
				if(announcement.getOaAnnoAcce() != null && announcement.getOaAnnoAcce().length()>0){
					 UtilTool.deleteAttachmentsAndFile(context, request, announcement.getOaAnnoAcce());
				}
			}
			oaNewsService.deleteAnnouncementByPk(ids);
			logger.info("删除公告信息...");
		} catch (Exception e) {
			logger.error("删除公告出错..."+e.getMessage());
			return new ResultBean(false, e.getMessage());
		}
		return WebUtilWork.WebResultPack(null);
	}
    
    /**s
	 * 根据ID获取公告信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
     public ResultBean getAnnouncementByPk(ServletContext context,HttpServletRequest request,long id){
	    OaAnnouncement announcement =  new OaAnnouncement();
    	try{
    		announcement = oaNewsService.getAnnouncementByPK(id);
    		if(announcement.getOaAnnoType() != null){
    			announcement.setOaAnnoLib(oaNewsService.getLibraryInfoByPK(announcement.getOaAnnoType()));
    		}
    		if(announcement.getOaAnnoEmp() != null){
    			announcement.setEmployee(employeeinfoService.getEmployeeByPK(announcement.getOaAnnoEmp()));
    		}
    		logger.info("根据id获取公告信息...");
    	}catch(Exception e){
    		logger.error("根据id获取公告信息出错..."+e.getMessage());
			return new ResultBean(false, e.getMessage());
    	}
    	List<OaAnnouncement> list = new ArrayList<OaAnnouncement>();
		list.add(announcement);
    	return WebUtilWork.WebResultPack(list);
     }
     
     /**s
 	 * 新增公告
 	 * 
 	 * @param context
 	 * @param request
 	 * @return
 	 */
     public ResultBean  saveAnnouncement(ServletContext context, HttpServletRequest request,OaAnnouncement announcement,String acceStr){
     	SessionUser user = (SessionUser)LoginContext.getSessionValueByLogin(request);
 	
 		String ids = UtilTool.saveAttachments(context, request, acceStr);
		announcement.setOaAnnoAcce(ids);

		announcement.setCompanyId(UtilTool.getCompanyId(request));
		announcement.setOaAnnoEmp(user.getEmployeeInfo().getPrimaryKey());
		announcement.setOaAnnoStatus(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(new Date());
		announcement.setOaAnnoTime(time);

		announcement.setLastmodiDate(UtilWork.getNowTime());
		announcement.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());
		announcement.setRecordId(user.getEmployeeInfo().getPrimaryKey());
		announcement.setRecordDate(UtilWork.getNowTime());

		oaNewsService.saveAnnouncement(announcement);
		logger.info("新增公告信息...");
     	
     	return WebUtilWork.WebResultPack(null);
     }
     
     /**s
 	 * 修改公告
 	 * 
 	 * @param context
 	 * @param request
 	 * @return
 	 */
     public ResultBean updateAnnouncement(ServletContext context, HttpServletRequest request, OaAnnouncement announcement,String acceStr) {
 		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
 		
		OaAnnouncement oaAnnouncement = oaNewsService.getAnnouncementByPK(announcement.getPrimaryKey());
		//更新附件
		if(announcement.getPrimaryKey() > 0){
			 UtilTool.deleteAttachmentsNoFile(context, request, oaAnnouncement.getOaAnnoAcce());
		}
		String ids = UtilTool.saveAttachments(context, request, acceStr);
		announcement.setOaAnnoAcce(ids);
		
		announcement.setOaAnnoEmp(oaAnnouncement.getOaAnnoEmp());
		announcement.setOaAnnoStatus(oaAnnouncement.getOaAnnoStatus());
		announcement.setCompanyId(oaAnnouncement.getCompanyId());
		announcement.setRecordId(oaAnnouncement.getRecordId());
		announcement.setRecordDate(oaAnnouncement.getRecordDate());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(new Date());
		announcement.setOaAnnoTime(time);

		announcement.setLastmodiDate(UtilWork.getNowTime());
		announcement.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());

		oaNewsService.saveAnnouncement(announcement);
		logger.info("修改新闻信息...");
 		
 		return WebUtilWork.WebResultPack(null);
 	}
     
     /**s
 	 * 显示通知
 	 * 
 	 * @param context
 	 * @param request
 	 * @return
 	 */
     public ResultBean  listNotice(ServletContext context, HttpServletRequest request,OaNotice notice,Pager pager){
     	List<OaNotice> list = null;
 		pager = PagerHelper.getPager(pager,oaNewsService.listNoticeCount(notice,UtilTool.getCompanyId(request)));
 		list = oaNewsService.getAllNotice(notice, UtilTool.getCompanyId(request), pager);
 		for (OaNotice oaNotice : list) {
 			if(oaNotice.getOaNotiEmp() != null){
 				oaNotice.setEmployee(employeeinfoService.getEmployeeByPK(oaNotice.getOaNotiEmp()));
 			}else{
 				oaNotice.setEmployee(new HrmEmployee());
 			}
 		}
 		logger.info("显示所有通知...");
     	
     	return WebUtilWork.WebResultPack(list, pager);
     }
     
     /**s
  	 * 显示通知(查看页面使用)
  	 * 
  	 * @param context
  	 * @param request
  	 * @return
  	 */
     public ResultBean  listNoticeView(ServletContext context, HttpServletRequest request,OaNotice notice,Pager pager){
    	String empId = UtilTool.getEmployeeId(request)+",";
 		Long depId = UtilTool.getDeptId(request);
 		String dep = depId.toString()+",";
 		notice.setOaObjDep(dep);
 		notice.setOaObjEmp(empId);
 		notice.setOaNotiStatus(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value);
 		
    	List<OaNotice> list = null;
  		pager = PagerHelper.getPager(pager,oaNewsService.listNoticeCount(notice,UtilTool.getCompanyId(request)));
  		list = oaNewsService.getAllNotice(notice, UtilTool.getCompanyId(request), pager);
  		for (OaNotice oaNotice : list) {
  			if(oaNotice.getOaNotiEmp() != null){
  				oaNotice.setEmployee(employeeinfoService.getEmployeeByPK(oaNotice.getOaNotiEmp()));
  			}else{
  				oaNotice.setEmployee(new HrmEmployee());
  			}
  		}
  		logger.info("显示查看所有通知...");
      	
      	return WebUtilWork.WebResultPack(list, pager);
     }
     
     /**s
 	 * 删除通知
 	 * 
 	 * @param context
 	 * @param request
 	 * @return
 	 */
     public ResultBean deleteNoticeById(ServletContext context,HttpServletRequest request,long[] ids){
        for (long l : ids) {
        	OaNotice notice = oaNewsService.getNoticeByPK(l);
        	if(notice.getOaNotiAcce() != null && notice.getOaNotiAcce().length() >0){
        		UtilTool.deleteAttachmentsAndFile(context, request, notice.getOaNotiAcce());
        	}
		}
		oaNewsService.deleteNoticeByPk(ids);
 		
 		return WebUtilWork.WebResultPack(null);
 	}
     
     /**
	 * s 根据ID获取通知信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getNoticeByPk(ServletContext context, HttpServletRequest request, long id) {
		OaNotice notice = new OaNotice();
		try {
			notice = oaNewsService.getNoticeByPK(id);
            //加载通知类型
			if(notice.getOaNotiType() != null){
            	notice.setOaNoticeLib(oaNewsService.getLibraryInfoByPK(notice.getOaNotiType()));
            }
			//加载通知范围（部门）
			if (notice.getOaObjDep() != null && notice.getOaObjDep().length() > 0) {
				String depids[] = notice.getOaObjDep().substring(0, notice.getOaObjDep().length() - 1).split(",");
				String depnames = "";
				for (int i = 0; i < depids.length; i++) {
					HrmDepartment department = employeeinfoService.getDepartmentByPK(Integer.parseInt(depids[i]));
					if (department != null) {
						depnames += department.getHrmDepName() + ",";
					}
				}
				notice.setDepList(depnames);
			}
            //加载通知范围（人员）
			if (notice.getOaObjEmp() != null && notice.getOaObjEmp().length() > 0) {
				String empids[] = notice.getOaObjEmp().substring(0, notice.getOaObjEmp().length() - 1).split(",");
				String empnames = "";
				for (int i = 0; i < empids.length; i++) {
					HrmEmployee employee = employeeinfoService.getEmployeeByPK(empids[i]);
					if (employee != null) {
						empnames += employee.getHrmEmployeeName() + ",";
					}
				}
				notice.setEmpLIst(empnames);
			}

			//加载发布人员
			if(notice.getOaNotiEmp() != null && notice.getOaNotiEmp().length() >0){
			    HrmEmployee employee = employeeinfoService.getEmployeeByPK(notice.getOaNotiEmp());
			    notice.setEmployee(employee);
			}
			logger.info("根据id获取通知信息...");
		} catch (Exception e) {
			logger.error("根据id获取通知信息出错..." + e.getMessage());
			return new ResultBean(false, e.getMessage());
		}
		List<OaNotice> list = new ArrayList<OaNotice>();
		list.add(notice);
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * s 新增通知
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveNotice(ServletContext context, HttpServletRequest request, OaNotice notice, String acceStr) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);

		String ids = UtilTool.saveAttachments(context, request, acceStr);
		notice.setOaNotiAcce(ids);

		notice.setCompanyId(UtilTool.getCompanyId(request));
		notice.setOaNotiEmp(user.getEmployeeInfo().getPrimaryKey());
		notice.setOaNotiStatus(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(new Date());

		notice.setOaNotiTime(time);
		notice.setLastmodiDate(UtilWork.getNowTime());
		notice.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());
		notice.setRecordId(user.getEmployeeInfo().getPrimaryKey());
		notice.setRecordDate(UtilWork.getNowTime());

		oaNewsService.saveNotice(notice);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 修改通知
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updateNotice(ServletContext context, HttpServletRequest request, OaNotice notice,String acceStr) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);

		OaNotice oaNotice = oaNewsService.getNoticeByPK(notice.getPrimaryKey());
        //更新附件
		if (oaNotice.getOaNotiAcce() != null && oaNotice.getOaNotiAcce().length() > 0) {
			UtilTool.deleteAttachmentsNoFile(context, request, oaNotice.getOaNotiAcce());
		}
		String ids = UtilTool.saveAttachments(context, request, acceStr);
		notice.setOaNotiAcce(ids);

		notice.setOaNotiEmp(oaNotice.getOaNotiEmp());
		notice.setOaNotiStatus(oaNotice.getOaNotiStatus());
		notice.setCompanyId(oaNotice.getCompanyId());
		notice.setRecordId(oaNotice.getRecordId());
		notice.setRecordDate(oaNotice.getRecordDate());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(new Date());
		notice.setOaNotiTime(time);
		
		notice.setLastmodiDate(UtilWork.getNowTime());
		notice.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());

		oaNewsService.saveNotice(notice);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 显示公司记事
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listAdversaria(ServletContext context, HttpServletRequest request, OaAdversaria adversaria, Pager pager) {
		List<OaAdversaria> list = null;
		pager = PagerHelper.getPager(pager, oaNewsService.listAdversariaCount(adversaria, UtilTool.getCompanyId(request)));
		list = oaNewsService.getAllAdversaria(adversaria, UtilTool.getCompanyId(request), pager);
		//加载记事人信息
		for (OaAdversaria oaAdversaria : list) {
			if (oaAdversaria.getOaAdverEmp() != null) {
				oaAdversaria.setEmployee(employeeinfoService.getEmployeeByPK(oaAdversaria.getOaAdverEmp()));
			} else {
				oaAdversaria.setEmployee(new HrmEmployee());
			}
		}
		logger.info("显示所有公司记事...");

		return WebUtilWork.WebResultPack(list, pager);
	}

	/**s
	 * 删除公司记事
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deleteAdversariaById(ServletContext context, HttpServletRequest request, long[] ids) {
        for (long l : ids) {
        	OaAdversaria adversaria = oaNewsService.getAdversariaByPK(l);
        	if(adversaria.getOaAdverAcce() != null && adversaria.getOaAdverAcce().length() > 0){
        		UtilTool.deleteAttachmentsAndFile(context, request, adversaria.getOaAdverAcce());
        	}
		}
		oaNewsService.deleteAdversariaByPk(ids);
		logger.info("删除公司记事信息...");

		return WebUtilWork.WebResultPack(null);
	}

	/**s
	 * 根据ID获取公司记事信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getAdversariaByPk(ServletContext context, HttpServletRequest request, long id) {
		OaAdversaria adversaria = new OaAdversaria();
		try {
			adversaria = oaNewsService.getAdversariaByPK(id);
			if(adversaria.getOaAdverEmp() != null && adversaria.getOaAdverEmp().length()>0){
				adversaria.setEmployee(employeeinfoService.getEmployeeByPK(adversaria.getOaAdverEmp()));
			}
			logger.info("根据id获取公司记事信息...");
		} catch (Exception e) {
			logger.error("根据id获取公司记事信息出错..." + e.getMessage());
			return new ResultBean(false, e.getMessage());
		}
		List<OaAdversaria> list = new ArrayList<OaAdversaria>();
		list.add(adversaria);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * s 新增公司记事
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveAdversaria(ServletContext context, HttpServletRequest request, OaAdversaria adversaria,String acceFile) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		
		//保存附件
		String acceId = UtilTool.saveAttachments(context, request, acceFile);
		adversaria.setOaAdverAcce(acceId);
	
		
		adversaria.setCompanyId(UtilTool.getCompanyId(request));
		adversaria.setOaAdverEmp(user.getEmployeeInfo().getPrimaryKey());
		adversaria.setOaAdverStatus(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(new Date());
		adversaria.setOaAdverTime(time);

		adversaria.setLastmodiDate(UtilWork.getNowTime());
		adversaria.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());
		adversaria.setRecordId(user.getEmployeeInfo().getPrimaryKey());
		adversaria.setRecordDate(UtilWork.getNowTime());

		oaNewsService.saveAdversaria(adversaria);
		logger.info("新增公司记事信息...");
		
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * s 修改公司记事
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updateAdversaria(ServletContext context, HttpServletRequest request, OaAdversaria adversaria,String acceFile) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		OaAdversaria oaAdversaria = oaNewsService.getAdversariaByPK(adversaria.getPrimaryKey());
		
		// 删除原有附件记录
		if(oaAdversaria.getOaAdverAcce() != null && oaAdversaria.getOaAdverAcce().length()>0){
		     UtilTool.deleteAttachmentsNoFile(context, request, oaAdversaria.getOaAdverAcce());
		}
		// 保存新附件记录
		String acceId = UtilTool.saveAttachments(context, request, acceFile);
		adversaria.setOaAdverAcce(acceId);
		
		adversaria.setOaAdverEmp(oaAdversaria.getOaAdverEmp());
		adversaria.setOaAdverStatus(oaAdversaria.getOaAdverStatus());
		adversaria.setCompanyId(oaAdversaria.getCompanyId());
		adversaria.setRecordId(oaAdversaria.getRecordId());
		adversaria.setRecordDate(oaAdversaria.getRecordDate());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(new Date());
		adversaria.setOaAdverTime(time);

		adversaria.setLastmodiDate(UtilWork.getNowTime());
		adversaria.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());

		oaNewsService.saveAdversaria(adversaria);
		logger.info("修改公司记事信息...");
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * s 生效/失效(新闻，公告，通知，公司记事)信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean setIssueInfo(ServletContext context, HttpServletRequest request, long[] ids,String name) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		try {
			if (name != null) {
				if (name.equals("notice")) {
					for (long m : ids) {
						OaNotice notice = oaNewsService.getNoticeByPK(m);
						if (notice.getOaNotiStatus() == EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value) {
							notice.setOaNotiStatus(EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value);
						} else {
							notice.setOaNotiStatus(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value);
						}
						SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
						String time = sdf.format(new Date());
						notice.setLastmodiDate(time);
						notice.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());

						oaNewsService.saveNotice(notice);
					}
					
				} else if (name.equals("adversaria")) {
					for (long n : ids) {
						OaAdversaria adversaria = oaNewsService.getAdversariaByPK(n);
						if (adversaria.getOaAdverStatus() == EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value) {
							adversaria.setOaAdverStatus(EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value);
						} else {
							adversaria.setOaAdverStatus(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value);
						}

						SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
						String time = sdf.format(new Date());
						adversaria.setLastmodiDate(time);
						adversaria.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());

						oaNewsService.saveAdversaria(adversaria);
					}
				} else {
					for (long o : ids) {
						OaAnnouncement announcement = oaNewsService.getAnnouncementByPK(o);
						if (announcement.getOaAnnoStatus() == EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value) {
							announcement.setOaAnnoStatus(EnumUtil.OA_ISSUEINFO_STATUS.FAILURE.value);
						} else {
							announcement.setOaAnnoStatus(EnumUtil.OA_ISSUEINFO_STATUS.EFFECT.value);
						}
						
						SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
						String time = sdf.format(new Date());
						announcement.setLastmodiDate(time);
						announcement.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());
						
						oaNewsService.saveAnnouncement(announcement);
					}
				}
			}
			logger.info("设置信息状态...");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("设置信息状态出错...");
		}
		return WebUtilWork.WebResultPack(null);
	}
}
