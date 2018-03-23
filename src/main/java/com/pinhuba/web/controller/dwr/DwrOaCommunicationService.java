package com.pinhuba.web.controller.dwr;

import java.text.ParseException;
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
import com.pinhuba.core.iservice.IOaChartterService;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaChatGroups;
import com.pinhuba.core.pojo.OaChatters;
import com.pinhuba.core.pojo.OaForums;
import com.pinhuba.core.pojo.OaPosts;
import com.pinhuba.core.pojo.OaVote;
import com.pinhuba.core.pojo.OaVoteOption;
import com.pinhuba.core.pojo.OaVoteStatus;
import com.pinhuba.core.pojo.SysImageInfo;

/*******************************************************************************
 * Class name: 信息交流 Description:对DWR服务进行描述 Others: // History: tang.liang
 * 2010.4.28 v3.0
 ******************************************************************************/
public class DwrOaCommunicationService {
	private final static Logger logger = Logger.getLogger(DwrOaCommunicationService.class);
	@Resource
	private IOaChartterService oaChartterService;
	@Resource
	private IHrmEmployeeService employeeinfoService;



	/**
	 * s 显示所有分组
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listChatGroup(ServletContext context, HttpServletRequest request, OaChatGroups group, Pager pager) {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());
		List<OaChatGroups> list = null;
		// 初始化分组
		OaChatGroups gr = new OaChatGroups();
		int groupCount = oaChartterService.listChatGroupCount(gr, UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request));
		if (groupCount == 0) {
			OaChatGroups chatGroup = new OaChatGroups();
			// 放入默认分组信息
			chatGroup.setCompanyId(UtilTool.getCompanyId(request));
			chatGroup.setLastmodiDate(time);
			chatGroup.setLastmodiId(UtilTool.getEmployeeId(request));
			chatGroup.setRecordDate(time);
			chatGroup.setRecordId(UtilTool.getEmployeeId(request));

			chatGroup.setOaChatgpName("我的好友");
			chatGroup.setOaChatgpDetail("默认分组");

			oaChartterService.saveChatGroup(chatGroup);
			logger.info("初次加载，添加默认分组...");
		}

		pager = PagerHelper.getPager(pager, oaChartterService.listChatGroupCount(group, UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request)));
		list = oaChartterService.getAllChatGroup(group, UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request), pager);
		for (OaChatGroups oaGroups : list) {
			int communCount = oaChartterService.getCommunicationCount(oaGroups.getPrimaryKey(), UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request));
			oaGroups.setCommunicationCount(communCount);
		}

		logger.info("显示所有通讯分组...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * s 根据主键获取分组信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getGroupByid(ServletContext context, HttpServletRequest request, long id) {
		List<OaChatGroups> list = new ArrayList<OaChatGroups>();

		OaChatGroups group = oaChartterService.getChatGroupByid(id);
		list.add(group);

		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 新增分组
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveChatGroup(ServletContext context, HttpServletRequest request, OaChatGroups group) {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());
		String empid = UtilTool.getEmployeeId(request);
		int com = UtilTool.getCompanyId(request);
		
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		if(group.getPrimaryKey()>0){//编辑
			int count = oaChartterService.getChatGroupsCountByname(group.getOaChatgpName(),empid,group.getPrimaryKey(),com);
			if(count>0){
				return new ResultBean(false,"通讯手册分组不能重名！");
			}
			OaChatGroups gro = oaChartterService.getChatGroupByid(group.getPrimaryKey());
			group.setCompanyId(gro.getCompanyId());
			group.setRecordId(gro.getRecordId());
			group.setRecordDate(gro.getRecordDate());
		}else{//新增
			int count = oaChartterService.getChatGroupsCountByname(group.getOaChatgpName(),empid,0,com);
			if(count>0){
				return new ResultBean(false,"通讯手册分组不能重名！");
			}
			group.setCompanyId(com);
			group.setRecordId(user.getEmployeeInfo().getPrimaryKey());
			group.setRecordDate(time);
		}

		group.setLastmodiDate(time);
		group.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());

		oaChartterService.saveChatGroup(group);
		logger.info("新增/编辑分组信息...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 添加默认分组
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public OaChatGroups newChatGroup(ServletContext context, HttpServletRequest request, OaChatGroups group) {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());
		String empid = UtilTool.getEmployeeId(request);
		int com = UtilTool.getCompanyId(request);

		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		group.setCompanyId(com);
		group.setRecordId(user.getEmployeeInfo().getPrimaryKey());
		group.setRecordDate(time);
		group.setLastmodiDate(time);
		group.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());

		OaChatGroups backGroups = oaChartterService.saveChatGroup(group);
		logger.info("添加默认分组...");
		return backGroups;
	}
	
	/**
	 * 删除分组信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deleteGroupById(ServletContext context, HttpServletRequest request, long[] ids) {
		for (long l : ids) {
			OaChatGroups group = oaChartterService.getChatGroupByid(l);

			// 如果分组中还有通讯手册记录，则不能删除！
			OaChatters chatter = new OaChatters();
			chatter.setOaChatGroup((int) l);
			int count = oaChartterService.listCommunicationCount(chatter, UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request));

			if (count > 0) {
				return new ResultBean(false, group.getOaChatgpName() + " 分组中有通讯记录 " + count + " 个,不能删除！");
			}
		}
		oaChartterService.deleteGroupByid(ids);
		logger.info("删除分组信息...");

		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 获取分组信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getAllChatGroup(ServletContext context, HttpServletRequest request, String emp, long com) {
		List<OaChatGroups> list = null;
		list = oaChartterService.getAllChatGroupByEmp(emp, com);

		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * s 显示个人通讯手册信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listCommunications(ServletContext context, HttpServletRequest request, OaChatters chatter, Pager pager) {
		List<OaChatters> list = null;

		pager = PagerHelper.getPager(pager, oaChartterService.listCommunicationCount(chatter, UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request)));
		list = oaChartterService.getAllCommunication(chatter, UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request), pager);

		for (OaChatters oaChatters : list) {
			OaChatGroups ch = oaChartterService.getChatGroupByid(oaChatters.getOaChatGroup());
			oaChatters.setOaChatGroups(ch);
		}
		logger.info("显示所有通讯手册信息...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * s 删除通讯手册记录
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deleteCommunicationById(ServletContext context, HttpServletRequest request, long[] ids) {
		for (long l : ids) {
			OaChatters chatter = oaChartterService.getCommunicationByid(l);
			if (chatter.getOaChatPhotos() != null) {
				UtilTool.deleteImagesAndFile(context, request, chatter.getOaChatPhotos());
			}
		}
		oaChartterService.deleteCOmmunicationByid(ids);
		logger.info("删除通讯手册信息...");

		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 根据id获取通讯手册记录
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getCommunicationById(ServletContext context, HttpServletRequest request, long id) {
		OaChatters chatter = oaChartterService.getCommunicationByid(id);
		String str = "";

		// 如果是共享的通讯记录
		if (chatter.getOaIsShare() != null && chatter.getOaIsShare() == EnumUtil.OA_COMMUNICATION_IS_SHARE.SHARE.value) {
			// 截取共享人员的id，获取人员姓名!
			if (chatter.getOaShareEmp() != null && chatter.getOaShareEmp().length() > 0) {
				String[] empids = chatter.getOaShareEmp().substring(0, chatter.getOaShareEmp().length() - 1).split(",");
				for (int i = 0; i < empids.length; i++) {
					HrmEmployee employee = employeeinfoService.getEmployeeByPK(empids[i]);
					if (employee != null) {
						str += employee.getHrmEmployeeName() + ",";
					}
				}
			}
		}
		// 加载共享人员姓名
		chatter.setShareEmpName(str);

		// 加载分组信息
		if (chatter.getOaChatGroup() != null) {
			OaChatGroups group = oaChartterService.getChatGroupByid(chatter.getOaChatGroup());
			chatter.setOaChatGroups(group);
		}
		List<OaChatters> list = new ArrayList<OaChatters>();
		list.add(chatter);
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * s 新增通讯手册记录
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveCommunication(ServletContext context, HttpServletRequest request, OaChatters chatter) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);

		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());

		try {
			// 保存照片信息
			if (chatter.getOaChatPhotos() != null && chatter.getOaChatPhotos().length() > 0) {
				SysImageInfo image = new SysImageInfo();
				String[] imageInfo = chatter.getOaChatPhotos().split("\\|");
				image.setImageInfoName(imageInfo[0]);
				image.setImageInfoFilename(imageInfo[1]);

				image.setCompanyId(UtilTool.getCompanyId(request));
				image.setLastmodiDate(time);
				image.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());
				image.setRecordId(user.getEmployeeInfo().getPrimaryKey());
				image.setRecordDate(time);

				Long id = oaChartterService.saveImageInfo(image).getPrimaryKey();
				chatter.setOaChatPhotos(id.toString());

			} else {
				chatter.setOaChatPhotos("");
			}

			chatter.setCompanyId(UtilTool.getCompanyId(request));
			chatter.setLastmodiDate(time);
			chatter.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());
			chatter.setRecordId(user.getEmployeeInfo().getPrimaryKey());
			chatter.setRecordDate(time);

			// 保存通讯手册
			oaChartterService.saveCommunication(chatter);

			logger.info("新增通讯手册...");
		} catch (Exception e) {
			logger.error("新增通讯手册出错...");
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 修改通讯手册记录
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updateCommunication(ServletContext context, HttpServletRequest request, OaChatters chatter) {
		SessionUser user = (SessionUser) LoginContext.getSessionValueByLogin(request);
		OaChatters oaChatter = oaChartterService.getCommunicationByid(chatter.getPrimaryKey());
		// 如果是签入记录，则不允许修改为共享记录
		if (chatter.getOaIsShare() == EnumUtil.OA_COMMUNICATION_IS_SHARE.SHARE.value && oaChatter.getOaCheckId() != null && oaChatter.getOaCheckId().longValue() > 0) {
			return new ResultBean(false, " 此通讯手册属于签入记录,不能修改为共享记录！");
		}

		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());
		try {
			// 如果设置为不共享，则删除共享记录
			if (chatter.getOaIsShare() == EnumUtil.OA_COMMUNICATION_IS_SHARE.UNSHARE.value) {
				chatter.setOaShareEmp(null);
			}

			// 如果相片改了，则修改照片记录
			if (chatter.getOaChatPhotos() != null && chatter.getOaChatPhotos().length() > 0) {
				// 删除以前记录
				UtilTool.deleteImagesNoFile(context, request, oaChatter.getOaChatPhotos());

				SysImageInfo image = new SysImageInfo();
				// 修改照片信息
				String[] imageInfo = chatter.getOaChatPhotos().split("\\|");
				image.setImageInfoName(imageInfo[0]);
				image.setImageInfoFilename(imageInfo[1]);

				image.setLastmodiDate(time);
				image.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());

				Long imageId = oaChartterService.saveImageInfo(image).getPrimaryKey();
				String id = imageId.toString();
				chatter.setOaChatPhotos(id);
			} else {
				chatter.setOaChatPhotos("");
			}
			chatter.setOaCheckId(oaChatter.getOaCheckId());
			chatter.setCompanyId(oaChatter.getCompanyId());
			chatter.setRecordId(oaChatter.getRecordId());
			chatter.setRecordDate(oaChatter.getRecordDate());
			chatter.setLastmodiDate(time);
			chatter.setLastmodiId(user.getEmployeeInfo().getPrimaryKey());

			oaChartterService.saveCommunication(chatter);
			logger.info("修改通讯手册...");
		} catch (Exception e) {
			logger.error("修改通讯手册出错...");
		}

		return WebUtilWork.WebResultPack(null);

	}

	/**
	 * s 获取通讯手册共享记录
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listShareCommunications(ServletContext context, HttpServletRequest request, OaChatters chatter, Pager pager) {
		List<OaChatters> communList = new ArrayList<OaChatters>();

		pager = PagerHelper.getPager(pager, oaChartterService.listCommunicationCount(UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request)));
		communList = oaChartterService.getShareCommunication(UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request), pager);

		for (OaChatters oaChatters : communList) {
			// 判断当前人员是否已签入该记录。加上状态
			List<OaChatters> chatterList = oaChartterService.getSharedCommunication(UtilTool.getEmployeeId(request), oaChatters.getPrimaryKey());
			if (chatterList.isEmpty() == false) {
				oaChatters.setOaIsChecked(EnumUtil.SYS_ISACTION.No_Vaild.value);
			} else {
				oaChatters.setOaIsChecked(EnumUtil.SYS_ISACTION.Vaild.value);
			}

			// 加载共享人信息
			if (oaChatters.getRecordId() != null && oaChatters.getRecordId().length() > 0) {
				HrmEmployee employee = employeeinfoService.getEmployeeByPK(oaChatters.getRecordId());
				if (employee != null) {
					oaChatters.setRecordId(employee.getHrmEmployeeName());
				} else {
					oaChatters.setRecordId(null);
				}
			}
			// 加载分组信息
			OaChatGroups group = oaChartterService.getChatGroupByid(oaChatters.getOaChatGroup());
			oaChatters.setOaChatGroups(group);
		}

		logger.info("获取通讯手册共享记录...");
		return WebUtilWork.WebResultPack(communList, pager);
	}

	/**
	 * s 签入通讯手册共享记录
	 * 
	 * 把在共享下的共享记录签到自己的通讯手册
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean copyCommunicationByid(ServletContext context, HttpServletRequest request, long[] id) {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());
		for (long m : id) {
			List<OaChatters> checkedList = oaChartterService.getSharedCommunication(UtilTool.getEmployeeId(request), m);
			if (checkedList.isEmpty() == false) {
				return new ResultBean(false, " 操作记录中存在已签入记录,不能再次签入！");
			}
		}

		for (long l : id) {
			OaChatters oaChatter = oaChartterService.getCommunicationByid(l);
			OaChatters chatter = new OaChatters();

			chatter.setOaChatEmp(oaChatter.getOaChatEmp());
			chatter.setOaChatCom(oaChatter.getOaChatCom());
			chatter.setOaChatAddress(oaChatter.getOaChatAddress());
			chatter.setOaChatMobile(oaChatter.getOaChatMobile());
			chatter.setOaChatPhotos("");
			chatter.setOaChatMsn(oaChatter.getOaChatMsn());
			chatter.setOaChatQq(oaChatter.getOaChatQq());
			chatter.setOaChatSex(oaChatter.getOaChatSex());
			chatter.setOaWorkTel(oaChatter.getOaWorkTel());
			chatter.setOaChatEmail(oaChatter.getOaChatEmail());
			chatter.setOaHomeTel(oaChatter.getOaHomeTel());

			chatter.setCompanyId(UtilTool.getCompanyId(request));
			chatter.setRecordId(UtilTool.getEmployeeId(request));
			chatter.setRecordDate(time);
			chatter.setLastmodiDate(time);
			chatter.setLastmodiId(UtilTool.getEmployeeId(request));

			chatter.setOaIsShare(EnumUtil.OA_COMMUNICATION_IS_SHARE.UNSHARE.value);
			chatter.setOaShareEmp(null);
			chatter.setOaCheckId((int) oaChatter.getPrimaryKey());

			List<OaChatGroups> list = oaChartterService.getAllChatGroupByEmp(UtilTool.getEmployeeId(request), UtilTool.getCompanyId(request));
			OaChatGroups group = list.get(0);
			Long groupid = group.getPrimaryKey();
			chatter.setOaChatGroup(Integer.parseInt(groupid.toString()));

			oaChartterService.saveCommunication(chatter);
		}
		logger.info("签入共享记录...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 把自己所共享的通讯手册记录设置私有！判断是否为空
	 * 
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean setChatterOwned(ServletContext context, HttpServletRequest request, long[] ids) {
		if("noChatter".equals(oaChartterService.setChattersOwnedByids(ids))){
			return new ResultBean(false, "该条通讯录已经被删除！请刷新后重新操作！");
		}
		oaChartterService.setChattersOwnedByids(ids);
		logger.info("通讯手册设置私有...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 批量设置通讯手册共享
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 *            通讯记录主键
	 * @param shareEmp
	 *            共享人员
	 * @return
	 */
	public ResultBean shareSetBakch(ServletContext context, HttpServletRequest request, long[] ids, String shareEmp) {
		for (long l : ids) {
			OaChatters chatter = oaChartterService.getCommunicationByid(l);
			if (chatter.getOaCheckId() != null && chatter.getOaCheckId().longValue() > 0) {
				return new ResultBean(false, chatter.getOaChatEmp() + " 属于签入记录,不能共享！");
			}
		}
		oaChartterService.shareSetChatter(ids, shareEmp, UtilTool.getEmployeeId(request));
		logger.info("共享通讯手册...");
		return WebUtilWork.WebResultPack(null);
	}

	/** ************************************************************************************************************************************** */
	/**
	 * s 获取所有论坛版块信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listForums(ServletContext context, HttpServletRequest request, OaForums forums) {
		List<OaForums> list = new ArrayList<OaForums>();
		try {
			list = oaChartterService.getAllForums(forums, UtilTool.getCompanyId(request));

			// 加载创建人信息和此版块中帖子的个数
			for (OaForums oaForums : list) {
				oaForums.setEmployee(employeeinfoService.getEmployeeByPK(oaForums.getOaForumEmp()));
				oaForums.setOaPostsCount(oaChartterService.getPostsCount(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request)));
				if (oaForums.getOaForumAdmin() != null && oaForums.getOaForumAdmin().length() > 0) {
					HrmEmployee emp = employeeinfoService.getEmployeeByPK(oaForums.getOaForumAdmin());
					if (emp != null) {
						oaForums.setOaForumAdmin(emp.getHrmEmployeeName());
					} else {
						oaForums.setOaForumAdmin(null);
					}
				}
				oaForums.setArticleCount(oaChartterService.getArticleCount(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request)));
				oaForums.setTodayPostCount(oaChartterService.getTodayPostCount(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request)));
				oaForums.setTopicCount(oaChartterService.getTopicCount(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request)));
				// 设置最后回复信息
				List<OaPosts> list2 = oaChartterService.getLastReplyPost(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request));
				if (list2 != null && list2.size() > 0) {
					OaPosts temp = list2.get(0);
					oaForums.setLastReplyTitle(temp.getOaPostName());
					oaForums.setLastReplyAuthor(employeeinfoService.getEmployeeByPK(temp.getOaPostEmp()).getHrmEmployeeName());
					oaForums.setLastReplyDate(temp.getOaPostLastregter());
					oaForums.setLastReplyID(temp.getPrimaryKey());
					oaForums.setListReplyForumID(temp.getOaPostForum());
				}
			}

			logger.info("显示所有版块信息...");
		} catch (Exception e) {
			logger.error("显示所有版块信息出错...");
		}
		return WebUtilWork.WebResultPack(list);
	}
	
	/** ************************************************************************************************************************************** */
	/**
	 * s 获取所有论坛版块信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listForumsByPager(ServletContext context, HttpServletRequest request, OaForums forums, Pager pager) {
		List<OaForums> list = new ArrayList<OaForums>();
		try {
			pager = PagerHelper.getPager(pager, oaChartterService.listForumsCount(forums, UtilTool.getCompanyId(request)));
			list = oaChartterService.getAllForumsByPager(forums, UtilTool.getCompanyId(request), pager);

			// 加载创建人信息和此版块中帖子的个数
			for (OaForums oaForums : list) {
				oaForums.setEmployee(employeeinfoService.getEmployeeByPK(oaForums.getOaForumEmp()));
				oaForums.setOaPostsCount(oaChartterService.getPostsCount(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request)));
				if (oaForums.getOaForumAdmin() != null && oaForums.getOaForumAdmin().length() > 0) {
					HrmEmployee emp = employeeinfoService.getEmployeeByPK(oaForums.getOaForumAdmin());
					if (emp != null) {
						oaForums.setOaForumAdmin(emp.getHrmEmployeeName());
					} else {
						oaForums.setOaForumAdmin(null);
					}
				}
				oaForums.setArticleCount(oaChartterService.getArticleCount(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request)));
				oaForums.setTodayPostCount(oaChartterService.getTodayPostCount(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request)));
				oaForums.setTopicCount(oaChartterService.getTopicCount(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request)));
				// 设置最后回复信息
				List<OaPosts> list2 = oaChartterService.getLastReplyPost(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request));
				if (list2 != null && list2.size() > 0) {
					OaPosts temp = list2.get(0);
					oaForums.setLastReplyTitle(temp.getOaPostName());
					oaForums.setLastReplyAuthor(employeeinfoService.getEmployeeByPK(temp.getOaPostEmp()).getHrmEmployeeName());
					oaForums.setLastReplyDate(temp.getOaPostLastregter());
					oaForums.setLastReplyID(temp.getPrimaryKey());
					oaForums.setListReplyForumID(temp.getOaPostForum());
				}
			}

			logger.info("分页显示所有版块信息...");
		} catch (Exception e) {
			logger.error("显示所有版块信息出错...");
		}
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * s 根据主键获取论坛版块信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getForumByid(ServletContext context, HttpServletRequest request, long id) {
		List<OaForums> list = new ArrayList<OaForums>();

		OaForums forum = oaChartterService.getForumsByid(id);

		// 加载创建人信息
		if (forum.getOaForumEmp() != null && forum.getOaForumEmp().length() > 0) {
			HrmEmployee employee = employeeinfoService.getEmployeeByPK(forum.getOaForumEmp());
			if (employee != null) {
				forum.setEmployee(employee);
			}
		}

		// 加载版主名字
		if (forum.getOaForumAdmin() != null && forum.getOaForumAdmin().length() > 0) {
			HrmEmployee emp = employeeinfoService.getEmployeeByPK(forum.getOaForumAdmin());
			if (emp != null) {
				forum.setForumAdminName(emp.getHrmEmployeeName());
			}
		}

		list.add(forum);

		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * s 删除论坛版块信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deleteForumsById(ServletContext context, HttpServletRequest request, long[] ids) {

		for (long l : ids) {
			OaForums forums = oaChartterService.getForumsByid(l);
			OaPosts posts = new OaPosts();
			posts.setOaPostForum((int) l);
			int count = oaChartterService.listPostSCount(posts, UtilTool.getCompanyId(request));

			if (count > 0) {
				return new ResultBean(false, forums.getOaForumName() + " 版块中有帖子信息 " + count + " 个,不能删除！");
			}
		}

		// 删除版块图片文件和图片记录
		for (long m : ids) {
			OaForums forums = oaChartterService.getForumsByid(m);
			if (forums.getOaForumImage() != null && forums.getOaForumImage().length() > 0) {
				UtilTool.deleteImagesAndFile(context, request, forums.getOaForumImage());
			}
		}

		oaChartterService.deleteForumsByid(ids);
		logger.info("删除论坛版块信息...");

		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 新增论坛版块信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveForums(ServletContext context, HttpServletRequest request, OaForums forums) {
		try {
			String time = UtilWork.getNowTime();
			forums.setCompanyId(UtilTool.getCompanyId(request));
			forums.setRecordId(UtilTool.getEmployeeId(request));
			forums.setLastmodiDate(time);
			forums.setLastmodiId(UtilTool.getEmployeeId(request));
			forums.setRecordDate(time);

			forums.setOaForumTime(UtilWork.getToday());
			forums.setOaForumEmp(UtilTool.getEmployeeId(request));

			// save the image of forum
			if (forums.getOaForumImage() != null && forums.getOaForumImage().length() > 0) {
				SysImageInfo image = new SysImageInfo();
				String[] imageInfo = forums.getOaForumImage().split("\\|");
				image.setImageInfoName(imageInfo[0]);
				image.setImageInfoFilename(imageInfo[1]);

				image.setCompanyId(UtilTool.getCompanyId(request));
				image.setLastmodiDate(time);
				image.setLastmodiId(UtilTool.getEmployeeId(request));
				image.setRecordId(UtilTool.getEmployeeId(request));
				image.setRecordDate(time);

				Long id = oaChartterService.saveImageInfo(image).getPrimaryKey();
				forums.setOaForumImage(id.toString());
			} else {
				forums.setOaForumImage("");
			}

			oaChartterService.saveForums(forums);
			logger.info("新增论坛版块...");
		} catch (Exception e) {
			logger.error("新增论坛版块出错...");
			return new ResultBean(false, e.getMessage());
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 修改论坛版块信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean updateForums(ServletContext context, HttpServletRequest request, OaForums forums) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());

		OaForums oaForum = oaChartterService.getForumsByid(forums.getPrimaryKey());

		forums.setCompanyId(oaForum.getCompanyId());
		forums.setRecordId(oaForum.getRecordId());
		forums.setLastmodiDate(time);
		forums.setLastmodiId(UtilTool.getEmployeeId(request));
		forums.setRecordDate(oaForum.getRecordDate());
		forums.setOaForumTime(oaForum.getOaForumTime());
		forums.setOaForumEmp(oaForum.getOaForumEmp());

		// update the image of forum
		if (oaForum.getOaForumImage() != null && oaForum.getOaForumImage().length() > 0) { // delete
																							// the
																							// original
																							// record
			UtilTool.deleteImagesNoFile(context, request, oaForum.getOaForumImage());

			// save the new image record
			SysImageInfo image = new SysImageInfo();
			String[] imageInfo = forums.getOaForumImage().split("\\|");
			image.setImageInfoName(imageInfo[0]);
			image.setImageInfoFilename(imageInfo[1]);

			image.setCompanyId(UtilTool.getCompanyId(request));
			image.setLastmodiDate(time);
			image.setLastmodiId(UtilTool.getEmployeeId(request));
			image.setRecordId(UtilTool.getEmployeeId(request));
			image.setRecordDate(time);

			Long id = oaChartterService.saveImageInfo(image).getPrimaryKey();
			forums.setOaForumImage(id.toString());
		} else {
			String imgId = null;
			// save the new image record
			if (forums.getOaForumImage() != null && forums.getOaForumImage().length() > 0) {
				SysImageInfo image = new SysImageInfo();
				String[] imageInfo = forums.getOaForumImage().split("\\|");
				image.setImageInfoName(imageInfo[0]);
				image.setImageInfoFilename(imageInfo[1]);

				image.setCompanyId(UtilTool.getCompanyId(request));
				image.setLastmodiDate(time);
				image.setLastmodiId(UtilTool.getEmployeeId(request));
				image.setRecordId(UtilTool.getEmployeeId(request));
				image.setRecordDate(time);
				Long id = oaChartterService.saveImageInfo(image).getPrimaryKey();
				imgId = id.toString();
			}
			forums.setOaForumImage(imgId);
		}

		oaChartterService.saveForums(forums);
		logger.info("修改论坛版块...");

		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 获取帖子信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listPosts(ServletContext context, HttpServletRequest request, OaPosts posts, Pager pager) {
		List<OaPosts> list = new ArrayList<OaPosts>();

		pager = PagerHelper.getPager(pager, oaChartterService.listPostSCount(posts, UtilTool.getCompanyId(request)));
		list = oaChartterService.getAllPosts(posts, UtilTool.getCompanyId(request), pager);

		// 加载人员和版块信息
		for (OaPosts oaPosts : list) {
			oaPosts.setEmployee(employeeinfoService.getEmployeeByPK(oaPosts.getOaPostEmp()));
			oaPosts.setForums(oaChartterService.getForumsByid((oaPosts.getOaPostForum())));

			if (oaPosts.getOaLastRegEmp() != null && oaPosts.getOaLastRegEmp().length() > 0) {
				HrmEmployee emp = employeeinfoService.getEmployeeByPK(oaPosts.getOaLastRegEmp());
				if (emp != null) {
					oaPosts.setOaLastRegEmp(emp.getHrmEmployeeName());
				} else {
					oaPosts.setOaLastRegEmp(null);
				}
			}
		}
		logger.info("获取帖子信息...");

		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * s 获取版块信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getAllForums(ServletContext context, HttpServletRequest request, long com) {
		List<OaForums> list = new ArrayList<OaForums>();
		list = oaChartterService.getForums(com);

		logger.info("获取版块信息...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * s 新增帖子信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean savePosts(ServletContext context, HttpServletRequest request, OaPosts posts) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());

			posts.setOaPostEmp(UtilTool.getEmployeeId(request));
			posts.setOaPostLastregter(time);
			posts.setOaPostTime(time);
			posts.setOaReadCount(0);
			posts.setOaIsBoutique(EnumUtil.OA_POSTS_IS_BOUTIQUE.UNBOUTIQUE.value);
			posts.setOaIsPost(EnumUtil.OA_IS_POSTS.POSTS.value);
			posts.setOaLastRegEmp(UtilTool.getEmployeeId(request));

			posts.setCompanyId(UtilTool.getCompanyId(request));
			posts.setRecordId(UtilTool.getEmployeeId(request));
			posts.setLastModiDate(time);
			posts.setLastModiId(UtilTool.getEmployeeId(request));
			posts.setRecordDate(time);

			Long id = oaChartterService.savePosts(posts).getPrimaryKey();
			OaPosts oaPosts = oaChartterService.getPostsByid(id);
			String postsPk = id.toString();
			oaPosts.setOaPostReg(Integer.parseInt(postsPk));

			oaChartterService.savePosts(oaPosts);
			logger.info("新增帖子...");
		} catch (Exception e) {
			logger.error("新增帖子出错...");
			return new ResultBean(false, e.getMessage());
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 删除帖子信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deletePostsById(ServletContext context, HttpServletRequest request, long[] ids) {
		int companyId = UtilTool.getCompanyId(request);
		String empId = UtilTool.getEmployeeId(request);

		boolean bl = oaChartterService.deletePostsByid(ids, companyId, empId);
		if (bl) {
			return new ResultBean(false, "你删除的帖子中包含其他用户发布的信息，不能删除！");
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 根据主键获取帖子信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getPostsByid(ServletContext context, HttpServletRequest request, long id) {
		List<OaPosts> list = new ArrayList<OaPosts>();
		OaPosts posts = oaChartterService.getPostsByid(id);

		// 增加点击数
		int readCount = posts.getOaReadCount();
		readCount += 1;
		posts.setOaReadCount(readCount);
		oaChartterService.savePosts(posts);

		if (posts.getOaPostEmp() != null) {
			posts.setEmployee(employeeinfoService.getEmployeeByPK(posts.getOaPostEmp()));
		}

		list.add(posts);
		logger.info("根据id获取帖子信息...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * s 根据帖子id获取其回复信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getRegByPostid(ServletContext context, HttpServletRequest request, long id, Pager pager) {
		List<OaPosts> list = new ArrayList<OaPosts>();

		pager = PagerHelper.getPager(pager, oaChartterService.listRegByPostCount(id, UtilTool.getCompanyId(request)));
		list = oaChartterService.getAllRegByPost(id, UtilTool.getCompanyId(request), pager);
		for (OaPosts oaPosts : list) {
			if (oaPosts.getOaPostEmp() != null && oaPosts.getOaPostEmp().length() > 0) {
				HrmEmployee employee = employeeinfoService.getEmployeeByPK(oaPosts.getOaPostEmp());
				oaPosts.setEmployee(employee);
			}
			
		 }
		 if (list.size()>0) {
			 OaPosts pos = list.get(0);
			 OaPosts tmp = oaChartterService.getPostsByid(id);
			 pos.setForums(oaChartterService.getForumsByid(tmp.getOaPostForum().longValue()));
		}
		
     	 return WebUtilWork.WebResultPack(list,pager);
     }
     
     /**s
    	 * 新增帖子回复信息
    	 * 
    	 * @param context
    	 * @param request
    	 * @return
    	 */
      public ResultBean savePostsRegInfo(ServletContext context,HttpServletRequest request,OaPosts posts){
     	 try{
     		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     		 String time = sdf.format(new Date());
     		 
     		 posts.setCompanyId(UtilTool.getCompanyId(request));
     		 posts.setRecordId(UtilTool.getEmployeeId(request));
     		 posts.setLastModiDate(time);
     		 posts.setLastModiId(UtilTool.getEmployeeId(request));
     		 posts.setRecordDate(time);
			 
     		 posts.setOaPostEmp(UtilTool.getEmployeeId(request));
     		 posts.setOaPostTime(time);
     		 posts.setOaIsPost(EnumUtil.OA_IS_POSTS.REG.value);
             
     		 //修改帖子信息
			 OaPosts oaPosts = oaChartterService.getPostsByid(posts.getOaPostReg());
     		 oaPosts.setOaPostLastregter(time);
     		 oaPosts.setOaLastRegEmp(UtilTool.getEmployeeId(request));
     		 
     		 oaChartterService.savePosts(oaPosts);
			 
     		 oaChartterService.savePosts(posts);
     		 logger.info("新增帖子回复...");
     	 }catch(Exception e){
     		 logger.error("新增帖子回复出错...");
     		 return new ResultBean(false, "新增帖子出错...");
     	 }
     	 return WebUtilWork.WebResultPack(null);
      }
      
      /**
       * 删除帖子回复信息
       * 
       * @param context
       * @param request
       * @return
       */
      public ResultBean deleteRegById(ServletContext context, HttpServletRequest request, long id) {
    	  
		OaPosts posts = oaChartterService.getPostsByid(id);

		if (posts.getOaIsPost() == EnumUtil.OA_IS_POSTS.POSTS.value) {
			return new ResultBean(false, "该帖不能在这里删除!");
		} else {
			OaPosts ps = oaChartterService.getPostsByid(posts.getOaPostReg());
			OaForums fs = oaChartterService.getForumsByid(ps.getOaPostForum());
			if (ps.getOaPostEmp().equals(UtilTool.getEmployeeId(request)) || fs.getOaForumAdmin().equals(UtilTool.getEmployeeId(request))) {
				// 是版主或发帖人，才可删除回复信息
				oaChartterService.deleteRegByid(id);
			} else {
				return new ResultBean(false, "你无权删除此回复信息...");
			}
		}
		logger.info("删除帖子回复...");

		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据id获取帖子回复
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getRegPostsByid(ServletContext context, HttpServletRequest request, long id) {
		List<OaPosts> list = new ArrayList<OaPosts>();
		OaPosts posts = oaChartterService.getPostsByid(id);

		if (posts.getOaPostEmp() != null && posts.getOaPostEmp().length() > 0) {
			posts.setEmployee(employeeinfoService.getEmployeeByPK(posts.getOaPostEmp()));
		}

		list.add(posts);
		logger.info("根据id获取回复信息...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 加精/取消加精帖子
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean setBoutiquePosts(ServletContext context, HttpServletRequest request, long[] ids) {

		for (long l : ids) {
			OaPosts posts = oaChartterService.getPostsByid(l);
			OaForums forum = oaChartterService.getForumsByid(posts.getOaPostForum());

			if (forum.getOaForumAdmin() != null && forum.getOaForumAdmin().length() > 0 && forum.getOaForumAdmin().equals(UtilTool.getEmployeeId(request))) {
				if (posts.getOaIsBoutique() == EnumUtil.OA_POSTS_IS_BOUTIQUE.BOUTIQUE.value) {
					posts.setOaIsBoutique(EnumUtil.OA_POSTS_IS_BOUTIQUE.UNBOUTIQUE.value);
				} else {
					posts.setOaIsBoutique(EnumUtil.OA_POSTS_IS_BOUTIQUE.BOUTIQUE.value);
				}

			} else {
				return new ResultBean(false, "您没有设置此帖子的权限！");
			}
			oaChartterService.savePosts(posts);
		}
		logger.info("加精/取消加精帖子...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据当前会话人员主键获取版块信息 判断当前人员是不是版主，如果是获取版块
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getForumByAdmin(ServletContext context, HttpServletRequest request) {
		OaForums fs = new OaForums();
		fs.setOaForumAdmin(UtilTool.getEmployeeId(request));
		List<OaForums> list = oaChartterService.getForumsByAdmin(fs, UtilTool.getCompanyId(request));
		for (OaForums oaForums : list) {
			if (oaForums.getOaForumAdmin() != null && oaForums.getOaForumAdmin().length() > 0) {
				HrmEmployee emp = employeeinfoService.getEmployeeByPK(oaForums.getOaForumAdmin());
				if (emp != null) {
					oaForums.setOaForumAdmin(emp.getHrmEmployeeName());
				} else {
					oaForums.setOaForumAdmin(null);
				}
			}
			oaForums.setArticleCount(oaChartterService.getArticleCount(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request)));
			oaForums.setTodayPostCount(oaChartterService.getTodayPostCount(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request)));
			oaForums.setTopicCount(oaChartterService.getTopicCount(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request)));
			// 设置最后回复信息
			List<OaPosts> list2 = oaChartterService.getLastReplyPost(oaForums.getPrimaryKey(), UtilTool.getCompanyId(request));
			if (list2 != null && list2.size() > 0) {
				OaPosts temp = list2.get(0);
				oaForums.setLastReplyTitle(temp.getOaPostName());
				oaForums.setLastReplyAuthor(employeeinfoService.getEmployeeByPK(temp.getOaPostEmp()).getHrmEmployeeName());
				oaForums.setLastReplyDate(temp.getOaPostLastregter());
				oaForums.setLastReplyID(temp.getPrimaryKey());
				oaForums.setListReplyForumID(temp.getOaPostForum());
			}
		}
		return WebUtilWork.WebResultPack(list);
	}

	/** ************************************************************************************************************************* */
	/**
	 * 根据主键获取投票记录(加载投票范围与查看范围名称)
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getVoteByid(ServletContext context, HttpServletRequest request, long id) {
		List<OaVote> list = new ArrayList<OaVote>();
		OaVote vote = oaChartterService.getVoteByid(id, UtilTool.getCompanyId(request));

		// 加载投票部门名称
		if (vote.getOaRangeDep() != null && vote.getOaRangeDep().length() > 0) {
			String depids[] = vote.getOaRangeDep().substring(0, vote.getOaRangeDep().length() - 1).split(",");
			String depnames = "";
			for (int i = 0; i < depids.length; i++) {
				HrmDepartment department = employeeinfoService.getDepartmentByPK(Integer.parseInt(depids[i]));
				if (department != null) {
					depnames += department.getHrmDepName() + ",";
				}
			}
			vote.setRangDepNames(depnames);
		}
		// 加载投票人员姓名
		if (vote.getOaRangeEmp() != null && vote.getOaRangeEmp().length() > 0) {
			String[] empids = vote.getOaRangeEmp().substring(0, vote.getOaRangeEmp().length() - 1).split(",");
			String empnames = "";
			for (int i = 0; i < empids.length; i++) {
				HrmEmployee employee = employeeinfoService.getEmployeeByPK(empids[i]);
				if (employee != null) {
					empnames += employee.getHrmEmployeeName() + ",";
				}
			}
			vote.setRangEmpNames(empnames);
		}

		// 加载发起人信息
		if (vote.getOaVoteEmp() != null && vote.getOaVoteEmp().length() > 0) {
			HrmEmployee emp = employeeinfoService.getEmployeeByPK(vote.getOaVoteEmp());
			vote.setEmployee(emp);
		}
		list.add(vote);
		logger.info("根据id获取投票记录...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 根据主键获取投票记录(不加载投票范围与查看范围名称,仅加载投票信息)
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getOnlyVoteByid(ServletContext context, HttpServletRequest request, long id) {
		List<OaVote> list = new ArrayList<OaVote>();
		OaVote vote = oaChartterService.getVoteByid(id, UtilTool.getCompanyId(request));

		// 加载发起人信息
		if (vote.getOaVoteEmp() != null && vote.getOaVoteEmp().length() > 0) {
			HrmEmployee emp = employeeinfoService.getEmployeeByPK(vote.getOaVoteEmp());
			vote.setEmployee(emp);
		}
		list.add(vote);
		logger.info("根据id获取投票记录...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 新增投票记录
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean saveVote(ServletContext context, HttpServletRequest request, OaVote vote, String[] options) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());

		vote.setCompanyId(UtilTool.getCompanyId(request));
		vote.setOaVoteEmp(UtilTool.getEmployeeId(request));
		vote.setLastmodiDate(time);
		vote.setLastmodiId(UtilTool.getEmployeeId(request));
		vote.setRecordDate(time);
		vote.setRecordId(UtilTool.getEmployeeId(request));

		vote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.NOSTART.value);

		List<OaVoteOption> list = new ArrayList<OaVoteOption>();
		for (String op : options) {
			OaVoteOption option = new OaVoteOption();
			option.setOaOptionName(op);
			option.setOaOptionCount(0);
			option.setCompanyId(UtilTool.getCompanyId(request));
			option.setRecordId(UtilTool.getEmployeeId(request));
			option.setRecordDate(time);
			option.setLastmodiId(UtilTool.getEmployeeId(request));
			option.setLastmodiDate(time);

			list.add(option);
		}

		oaChartterService.saveVote(vote, list);
		logger.info("新增投票记录...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * s 投票管理页面获取投票信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listVote(ServletContext context, HttpServletRequest request, OaVote vote, Pager pager) {
		List<OaVote> list = new ArrayList<OaVote>();
		try {
			pager = PagerHelper.getPager(pager, oaChartterService.listVoteCountManager(vote, UtilTool.getCompanyId(request)));
			list = oaChartterService.getAllVoteManager(vote, UtilTool.getCompanyId(request), pager);

			// 加载人员和版块信息
			for (OaVote vo : list) {
				HrmEmployee employee = employeeinfoService.getEmployeeByPK(vo.getOaVoteEmp());
				if (employee != null) {
					vo.setEmployee(employee);
				}
				vo.setVotetypeLib(oaChartterService.getVoteTypeByid(vo.getOaVoteType()));
			}

			logger.info("获取投票信息...");
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("获取投票信息出错...");
		}
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * s 投票界面获取投票信息
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean listVoteView(ServletContext context, HttpServletRequest request, OaVote vote, Pager pager) throws Exception {
		List<OaVote> list = new ArrayList<OaVote>();
		String useId = UtilTool.getEmployeeId(request) + ",";
		Long dep = UtilTool.getDeptId(request);
		String depId = dep.toString() + ",";
		pager = PagerHelper.getPager(pager, oaChartterService.listVoteCount(vote, UtilTool.getCompanyId(request), useId, depId));
		list = oaChartterService.getAllVote(vote, UtilTool.getCompanyId(request), useId, depId, pager);
		// 加载当前人员投票状态
		for (OaVote oaVote : list) {
			List<OaVoteStatus> statusList = oaChartterService.getStatusByEmpAndVoteId(UtilTool.getEmployeeId(request), oaVote.getPrimaryKey(), UtilTool.getCompanyId(request));
			if (statusList.isEmpty() == true) {
				oaVote.setVoteCount(0);
			} else {
				oaVote.setVoteCount(1111);
			}
		}
		// 加载人员和版块信息
		for (OaVote vo : list) {
			HrmEmployee employee = employeeinfoService.getEmployeeByPK(vo.getOaVoteEmp());
			if (employee != null) {
				vo.setEmployee(employee);
			}
			vo.setVotetypeLib(oaChartterService.getVoteTypeByid(vo.getOaVoteType()));
		}
		logger.info("获取投票信息...");
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 删除投票记录
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean deleteVote(ServletContext context, HttpServletRequest request, long[] ids) {
		for (long l : ids) {
			OaVote vote = oaChartterService.getVoteByid(l, UtilTool.getCompanyId(request));
			if (vote.getOaVoteStatus() != null) {
				if (vote.getOaVoteStatus() == EnumUtil.OA_VOTE_STATUS.VOTING.value) {
					return new ResultBean(false, vote.getOaVoteName() + " 正在投票中,不能删除！");
				}
			}
		}
		oaChartterService.deleteVoteByids(ids, UtilTool.getCompanyId(request));
		logger.info("删除投票信息...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 手动设置投票状态，立即结束，开始投票，重新启动
	 * 
	 * @param context
	 * @param request
	 * @param id
	 *            投票记录主键
	 * @param str
	 *            页面所传操作参数
	 * @return
	 */
	public ResultBean setVoteStatus(ServletContext context, HttpServletRequest request, long id, String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		OaVote vote = oaChartterService.getVoteByid(id, UtilTool.getCompanyId(request));
		if (str.equals("end")) { // 立即结束
			vote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.END.value);
		} else if (str.equals("start")) { // 未开始的手动开始
			vote.setOaVoteStart(time);
			vote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.VOTING.value);
		} else {
			// 重新开始
			if (vote.getOaVoteEnd() != null && vote.getOaVoteEnd().length() > 0) {
				// 结束时间不为空
				try {
					Date endTime = sdf.parse(vote.getOaVoteEnd());// 结束时间
					Date onTime = sdf.parse(time);// 当前时间
					if (onTime.before(endTime)) {
						// 判断当前时间是否小于结束时间，小于结束时间，则重新开启投票
						vote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.VOTING.value);
					} else {
						// 否则不允许重新开启投票
						return new ResultBean(false, vote.getOaVoteName() + " 投票时间已过，不能重新启动！");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		vote.setLastmodiDate(time);
		vote.setLastmodiId(UtilTool.getEmployeeId(request));

		vote = oaChartterService.saveVoteNoOption(vote);
		logger.info("手动设置投票状态...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据投票主键获取其选项信息
	 * 
	 * @param context
	 * @param request
	 * @param id
	 *            投票主键
	 * @return
	 */
	public ResultBean getAlloptionByVote(ServletContext context, HttpServletRequest request, long id) {
		List<OaVoteOption> list = new ArrayList<OaVoteOption>();

		list = oaChartterService.getAlloptionByVote(id, UtilTool.getCompanyId(request));

		logger.info("获取投票选项记录...");
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 修改投票记录 只能对未开始和已结束的投票记录进行修改
	 * 
	 * @param context
	 * @param request
	 * @param vote
	 * @param options
	 *            新的选项
	 * @return
	 */
	public ResultBean updateVote(ServletContext context, HttpServletRequest request, OaVote vote, String[] options) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());

		OaVote vo = oaChartterService.getVoteByid(vote.getPrimaryKey(), UtilTool.getCompanyId(request));
		if (vo.getOaVoteStatus() != null) {
			if (vo.getOaVoteStatus() == EnumUtil.OA_VOTE_STATUS.NOSTART.value) {
				// 投票状态为未开始
				vote.setLastmodiDate(time);
				vote.setLastmodiId(UtilTool.getEmployeeId(request));
				vote.setCompanyId(vo.getCompanyId());
				vote.setRecordDate(vo.getRecordDate());
				vote.setRecordId(vo.getRecordId());
				vote.setOaVoteStatus(vo.getOaVoteStatus());
				vote.setOaVoteEmp(UtilTool.getEmployeeId(request));

				List<OaVoteOption> opList = new ArrayList<OaVoteOption>();
				for (String s : options) {
					OaVoteOption option = new OaVoteOption();
					option.setOaOptionName(s);
					option.setOaOptionCount(0);
					option.setCompanyId(UtilTool.getCompanyId(request));
					option.setRecordId(UtilTool.getEmployeeId(request));
					option.setRecordDate(time);
					option.setLastmodiId(UtilTool.getEmployeeId(request));
					option.setLastmodiDate(time);
					option.setOaVoteId((int) vote.getPrimaryKey());

					opList.add(option);
				}
				oaChartterService.updateVote(vote, opList);
			} else if (vo.getOaVoteStatus() == EnumUtil.OA_VOTE_STATUS.END.value) {
				// 投票状态为已结束
				vote.setLastmodiDate(time);
				vote.setLastmodiId(UtilTool.getEmployeeId(request));
				vote.setCompanyId(vo.getCompanyId());
				vote.setRecordDate(vo.getRecordDate());
				vote.setRecordId(vo.getRecordId());
				vote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.NOSTART.value);
				vote.setOaVoteEmp(UtilTool.getEmployeeId(request));

				List<OaVoteOption> optionList = new ArrayList<OaVoteOption>();
				for (String s : options) {
					OaVoteOption option = new OaVoteOption();
					option.setOaOptionName(s);
					option.setOaOptionCount(0);
					option.setCompanyId(UtilTool.getCompanyId(request));
					option.setRecordId(UtilTool.getEmployeeId(request));
					option.setRecordDate(time);
					option.setLastmodiId(UtilTool.getEmployeeId(request));
					option.setLastmodiDate(time);
					option.setOaVoteId((int) vote.getPrimaryKey());

					optionList.add(option);
				}
				oaChartterService.updateVote(vote, optionList);
			} else {
				return new ResultBean(false, " 只能对未开始和已结束修改，此投票不能修改！");
			}
		}

		logger.info("修改投票记录...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 进行投票
	 * 
	 * @param context
	 * @param request
	 * @param ids
	 *            选项主键
	 * @param isAn
	 *            是否匿名
	 * @return
	 */
	public ResultBean votingSet(ServletContext context, HttpServletRequest request, long[] ids, long voteid, int isAn) {
		boolean voting = true;
		List<OaVoteOption> list = new ArrayList<OaVoteOption>();
		// 获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());

		// 投票判断
		if (voting) {
			for (long l : ids) {
				OaVoteOption op = oaChartterService.getOptionByid(l);
				op.setOaOptionCount(op.getOaOptionCount() + 1);
				op.setLastmodiDate(time);
				op.setLastmodiId(UtilTool.getEmployeeId(request));
				list.add(op);
			}
			oaChartterService.updateOption(list);

			// 保存投票人员状态信息
			OaVoteStatus ods = new OaVoteStatus();
			ods.setCompanyId(UtilTool.getCompanyId(request));
			ods.setLastmodiDate(time);
			ods.setLastmodiId(UtilTool.getEmployeeId(request));
			ods.setOaIsAnonymous(isAn);
			ods.setOaVoteEmp(UtilTool.getEmployeeId(request));
			Long votepk = (Long) voteid;
			ods.setOaVoteRec(votepk.toString());
			ods.setRecordDate(time);
			ods.setRecordId(UtilTool.getEmployeeId(request));

			oaChartterService.saveVoteStatus(ods);
		}

		logger.info("进行投票...");
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 根据当前操作人员与投票主键获取该人员投票状态信息 是否已经投过
	 * 
	 * @param context
	 * @param request
	 * @param voteid
	 *            投票主键
	 * @return
	 */
	public ResultBean getVoteStatus(ServletContext context, HttpServletRequest request, long voteid) {
		List<OaVoteStatus> os = oaChartterService.getStatusByEmpAndVoteId(UtilTool.getEmployeeId(request), voteid, UtilTool.getCompanyId(request));
		return WebUtilWork.WebResultPack(os);
	}

	/**
	 * 根据投票主键查询所有已投人员信息
	 * 
	 * @param context
	 * @param request
	 * @param voteid
	 *            投票主键
	 * @return
	 */
	public ResultBean getAllVoteStatus(ServletContext context, HttpServletRequest request, long voteid) {
		OaVote vote = oaChartterService.getVoteByid(voteid, UtilTool.getCompanyId(request));
		String empid = UtilTool.getEmployeeId(request);

		List<OaVoteStatus> vs = new ArrayList<OaVoteStatus>();
		if (empid.equals(vote.getOaVoteEmp())) {
			vs = oaChartterService.getStatusByVoteIdNoAnonymous(voteid, UtilTool.getCompanyId(request));
		} else {
			vs = oaChartterService.getStatusByVoteId(voteid, UtilTool.getCompanyId(request));
		}

		for (OaVoteStatus s : vs) {
			HrmEmployee emp = employeeinfoService.getEmployeeByPK(s.getOaVoteEmp());
			if (emp != null) {
				s.setOaVoteEmp(emp.getHrmEmployeeName());
			} else {
				s.setOaVoteEmp("");
			}
		}

		return WebUtilWork.WebResultPack(vs);
	}
}
