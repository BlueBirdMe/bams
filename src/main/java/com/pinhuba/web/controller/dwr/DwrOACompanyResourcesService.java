package com.pinhuba.web.controller.dwr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.core.iservice.IOaCompanyResourcesService;
import com.pinhuba.core.pojo.OaAlbum;
import com.pinhuba.core.pojo.OaForms;
import com.pinhuba.core.pojo.OaJournals;
import com.pinhuba.core.pojo.OaJournalsType;
import com.pinhuba.core.pojo.OaPhoto;
import com.pinhuba.core.pojo.OaRegulations;
import com.pinhuba.core.pojo.OaWareType;
import com.pinhuba.core.pojo.OaWarehouse;


/**********************************************
  Class name: 公司资源
  Description:对DWR服务进行描述
  Others:         // 
  History:        
  tang.liang    2010.4.28     v3.0
 **********************************************/
public class DwrOACompanyResourcesService {
	//配置日志
	private final static Logger logger = Logger.getLogger(DwrOACompanyResourcesService.class);
	@Resource
	private IOaCompanyResourcesService cmpReService;


	/**
	 * 获得知识类型函数
	 * @param context
	 * @param request
	 * @param oawaretype 实体类
	 * @param pager		分页
	 * @return 返回知识类型集合
	 */
	public ResultBean getWareTypeByPager(ServletContext context, HttpServletRequest request, OaWareType oawaretype, Pager pager) {
		oawaretype.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, cmpReService.getAllOaWageTypeCount(oawaretype));
		List<OaWareType> list = cmpReService.getAllOaWageTypeByPager(oawaretype, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 取得单个知识类型数据
	 * @param context
	 * @param request
	 * @param type 类型ID
	 * @return 知识类型
	 */
	public ResultBean getWareTypeByType(ServletContext context, HttpServletRequest request, int type) {
		OaWareType waretype = new OaWareType();
		waretype.setCompanyId(UtilTool.getCompanyId(request));
		waretype.setFormsorware(type);
		List<OaWareType> list = cmpReService.getAllOaWageTypeBytype(waretype);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 保存知识类型方法
	 * @param context
	 * @param request
	 * @param empids
	 * @param oawaretype实体类
	 * @return 不返回
	 */
	public ResultBean saveWareType(ServletContext context, HttpServletRequest request, String empids, OaWareType oawaretype) {
		int count = 0;
		if (empids != null && empids.length() > 0) {
			String[] empid = empids.split(",");
			for (String str : empid) {
				if (str != null && str.trim().length() > 0) {
					count++;
				}
			}
		}
		oawaretype.setCompanyId(UtilTool.getCompanyId(request));
		if(cmpReService.clikeoaWagetypeName(oawaretype)==false){
			return new ResultBean(false,"类型名称不能重复！");
		}
		if (oawaretype.getPrimaryKey() > 0) {
			OaWareType oldtype = cmpReService.getOaWareTypeByPk(oawaretype.getPrimaryKey());
			oldtype.setOaTypeName(oawaretype.getOaTypeName());
			oldtype.setOaTypeText(oawaretype.getOaTypeText());
			oldtype.setLastmodiId(UtilTool.getEmployeeId(request));
			oldtype.setLastmodiDate(UtilWork.getNowTime());
			oldtype.setPremCount(count);
			cmpReService.saveOaWareType(empids, oldtype);
		} else {
			oawaretype.setRecordId(UtilTool.getEmployeeId(request));
			oawaretype.setRecordDate(UtilWork.getNowTime());
			oawaretype.setLastmodiId(UtilTool.getEmployeeId(request));
			oawaretype.setLastmodiDate(UtilWork.getNowTime());
			oawaretype.setPremCount(count);
			cmpReService.saveOaWareType(empids, oawaretype);
		}
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 获得单个类型对象 用于编辑
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean getWareTypeByPk(ServletContext context, HttpServletRequest request, long pk) {
		List<OaWareType> list = new ArrayList<OaWareType>();
		list.add(cmpReService.getOaWareTypeByPk(pk));
		return WebUtilWork.WebResultPack(list);
	}
	
    /**放回表格类型
     * 
     * @param context
     * @param request
     * @param pk
     * @return
     */
	public ResultBean getWareTypeRangeListBytypePk(ServletContext context, HttpServletRequest request, long pk) {
		return WebUtilWork.WebResultPack(cmpReService.getOaWaretypeRangeList(pk));
	}
	
    /**
     * 类型删除
     * @param context
     * @param request
     * @param pk
     * @return
     */
	public ResultBean deleteWareTypeBypk(ServletContext context, HttpServletRequest request, long pk) {
		List<OaWarehouse> wareHouserList = cmpReService.getWarehoseByTypePk(pk);
		if (wareHouserList != null && wareHouserList.size() > 0) {
			return new ResultBean(false, "该类型包含有 " + wareHouserList.size() + " 条数据,不能删除!");
		}
		cmpReService.deleteWareTypeByPk(pk);
		return WebUtilWork.WebResultPack(null);
	}
	/**
	 * 删除表格
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean deleteFormsTypeBypk(ServletContext context, HttpServletRequest request, long pk) {
		OaForms fs = new OaForms();
		fs.setOaFormType((int) pk);
		int fcount = cmpReService.getFormsCount(fs);
		if (fcount > 0) {
			return new ResultBean(false, "该类型包含有 " + fcount + " 条数据,不能删除!");
		}
		cmpReService.deleteWareTypeByPk(pk);
		return WebUtilWork.WebResultPack(null);
	}
	/**
	 * 查询知识
	 * @param context
	 * @param request
	 * @param warehouse
	 * @param pager
	 * @return
	 */
	public ResultBean getWarehouselistByPager(ServletContext context, HttpServletRequest request, OaWarehouse warehouse, Pager pager) {
		warehouse.setCompanyId(UtilTool.getCompanyId(request));
		warehouse.setOaWareEmp(UtilTool.getEmployeeId(request));
		pager = PagerHelper.getPager(pager, cmpReService.getWareHouseCount(warehouse));
		List<OaWarehouse> list = cmpReService.getWareHouseBypager(warehouse, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 知识查询
	 * @param context
	 * @param request
	 * @param warehouse
	 * @param pager
	 * @return
	 */
	public ResultBean getSuperWarehouselistByPager(ServletContext context, HttpServletRequest request, OaWarehouse warehouse, Pager pager) {
		warehouse.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, cmpReService.getWareHouseCount(warehouse));
		List<OaWarehouse> list = cmpReService.getWareHouseBypager(warehouse, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**查看知识
	 * 
	 * @param context
	 * @param request
	 * @param warehouse
	 * @param pager
	 * @return
	 */
	public ResultBean getWarehouselistByPagerAndPerm(ServletContext context, HttpServletRequest request, OaWarehouse warehouse, Pager pager) {
		int companyid = UtilTool.getCompanyId(request);
		String empid = UtilTool.getEmployeeId(request);
		int type = EnumUtil.OA_TYPE.WARW.value;
		warehouse.setCompanyId(companyid);
		pager = PagerHelper.getPager(pager, cmpReService.getWareHouseAndPermCount(warehouse, empid, companyid, type));
		List<OaWarehouse> list = cmpReService.getWareHouseByPagerAndPerm(warehouse, pager, empid, companyid, type);
		return WebUtilWork.WebResultPack(list, pager);
	}

	/**
	 * 保存知识
	 * 
	 * @param context
	 * @param request
	 * @param warehouse
	 * @param attachs
	 *            附件
	 * @return
	 */
	public ResultBean saveWarehouse(ServletContext context, HttpServletRequest request, OaWarehouse warehouse, String attachs) {
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		if (warehouse.getPrimaryKey() > 0) {
			OaWarehouse tmp = cmpReService.getWarehouseByPk(warehouse.getPrimaryKey());
			// 删除原附件
			UtilTool.deleteAttachmentsNoFile(context, request, warehouse.getOaWareAcce());
			warehouse.setRecordId(tmp.getRecordId());
			warehouse.setRecordDate(tmp.getRecordDate());

		} else {
			warehouse.setRecordId(empid);
			warehouse.setRecordDate(nowtime);
		}

		// 保存附件
		String ids = UtilTool.saveAttachments(context, request, attachs);
		warehouse.setOaWareAcce(ids);
		warehouse.setCompanyId(UtilTool.getCompanyId(request));
		warehouse.setOaWareEmp(empid);
		warehouse.setLastmodiId(empid);
		warehouse.setOaWareTime(UtilWork.getToday());
		warehouse.setLastmodiDate(nowtime);
		cmpReService.saveWarehouse(warehouse);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 取得单个知识类型
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean getWarehouseByPk(ServletContext context, HttpServletRequest request, long pk) {
		OaWarehouse house = cmpReService.getWarehouseByPk(pk);
		List<OaWarehouse> houselist = new ArrayList<OaWarehouse>();
		houselist.add(house);
		return WebUtilWork.WebResultPack(houselist);
	}

	/**
	 * 批量删除类型
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public ResultBean deleteWarehouseByIds(ServletContext context, HttpServletRequest request, long[] ids) {
		for (long l : ids) {
			OaWarehouse tmp = cmpReService.deleteWarehouseByIds(l);
			UtilTool.deleteAttachmentsAndFile(context, request, tmp.getOaWareAcce());
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 取得单个知识
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean getWarehouseAndObjByPk(ServletContext context, HttpServletRequest request, long pk) {
		OaWarehouse house = cmpReService.getWarehouseAndObjByPk(pk);
		List<OaWarehouse> houselist = new ArrayList<OaWarehouse>();
		houselist.add(house);
		return WebUtilWork.WebResultPack(houselist);
	}
	
	/**
	 * 取得表格集合 只是当前登录人的 
	 * @param context
	 * @param request
	 * @param forms
	 * @param pager
	 * @return
	 */
	public ResultBean getFormsListByPager(ServletContext context, HttpServletRequest request, OaForms forms, Pager pager) {
		forms.setCompanyId(UtilTool.getCompanyId(request));
		forms.setOaFormEmp(UtilTool.getEmployeeId(request));
		pager = PagerHelper.getPager(pager, cmpReService.getFormsCount(forms));
		List<OaForms> list = cmpReService.getFormsBypager(forms, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 取得表格所有人
	 * @param context
	 * @param request
	 * @param forms
	 * @param pager
	 * @return
	 */
	public ResultBean getSuperFormsListByPager(ServletContext context, HttpServletRequest request, OaForms forms, Pager pager) {
		forms.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, cmpReService.getFormsCount(forms));
		List<OaForms> list = cmpReService.getFormsBypager(forms, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 根据表格类型 查找表格
	 * @param context
	 * @param request
	 * @param forms
	 * @param pager
	 * @return
	 */
	public ResultBean getFormsByPagerAndPerm(ServletContext context, HttpServletRequest request, OaForms forms, Pager pager) {
		int companyid = UtilTool.getCompanyId(request);
		String empid = UtilTool.getEmployeeId(request);
		int type = EnumUtil.OA_TYPE.FORMS.value;
		forms.setCompanyId(companyid);
		pager = PagerHelper.getPager(pager, cmpReService.getFormsAndPermCount(forms, empid, companyid, type));
		List<OaForms> list = cmpReService.getFormsByPagerAndPerm(forms, pager, empid, companyid, type);
		return WebUtilWork.WebResultPack(list, pager);
	}
	/**
	 * 取得单个表格信息
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean getFormsByPk(ServletContext context, HttpServletRequest request, long pk) {
		OaForms form = cmpReService.getFormsByPk(pk);
		List<OaForms> list = new ArrayList<OaForms>();
		list.add(form);
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 批量删除表格
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public ResultBean deleteFormsByIds(ServletContext context, HttpServletRequest request,long[] ids){
		for (long l : ids) {
			OaForms tmp = cmpReService.deleteFormsByIds(l);
			UtilTool.deleteAttachmentsAndFile(context, request, tmp.getOaFormAcce());
		}
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 保存表格
	 * 
	 * @param context
	 * @param request
	 * @param warehouse
	 * @param attachs
	 *            附件
	 * @return
	 */
	public ResultBean saveForms(ServletContext context, HttpServletRequest request, OaForms forms, String attachs) {
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		if (forms.getPrimaryKey() > 0) {
			OaForms tmp = cmpReService.getFormsByPk(forms.getPrimaryKey());
			// 删除原附件记录
			UtilTool.deleteAttachmentsNoFile(context, request, tmp.getOaFormAcce());
			forms.setRecordId(tmp.getRecordId());
			forms.setRecordDate(tmp.getRecordDate());

		} else {
			forms.setRecordId(empid);
			forms.setRecordDate(nowtime);
		}

		// 保存附件记录
		String ids = UtilTool.saveAttachments(context, request, attachs);
		forms.setOaFormAcce(ids);
		forms.setCompanyId(UtilTool.getCompanyId(request));
		forms.setOaFormEmp(empid);
		forms.setLastmodiId(empid);
		forms.setOaFormTime(UtilWork.getToday());
		forms.setLastmodiDate(nowtime);
		cmpReService.saveOaForms(forms);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 取得单个表格
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean getFormsAndObjByPk(ServletContext context, HttpServletRequest request, long pk) {
		OaForms forms = cmpReService.getFormsAndObjByPk(pk);
		List<OaForms> list = new ArrayList<OaForms>();
		list.add(forms);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 取得期刊类型 分页
	 * @param context
	 * @param request
	 * @param jourType
	 * @param pager
	 * @return
	 */
	public ResultBean getJournalsTypeListPager(ServletContext context, HttpServletRequest request, OaJournalsType jourType, Pager pager) {
		jourType.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, cmpReService.getJournalsTypeCount(jourType));
		List<OaJournalsType> list = cmpReService.getJournalsTypePager(jourType, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 取得期刊类型 没有分页
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getJournalsTypeList(ServletContext context, HttpServletRequest request) {
		OaJournalsType jourType = new OaJournalsType();
		jourType.setCompanyId(UtilTool.getCompanyId(request));
		List<OaJournalsType> list = cmpReService.getJournalsType(jourType);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**保存期刊类型
	 * 
	 * @param context
	 * @param request
	 * @param jtype
	 * @return
	 */
	public ResultBean saveJournalsType(ServletContext context, HttpServletRequest request, OaJournalsType jtype) {
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		jtype.setCompanyId(UtilTool.getCompanyId(request));
		if(cmpReService.clikeJournalsName(jtype)==false){
			return new ResultBean(false,"类型名称不能重复！");
		}
		if (jtype.getPrimaryKey() > 0) {
			OaJournalsType tmp = cmpReService.getJournalsTypeByPk(jtype.getPrimaryKey());
			jtype.setRecordId(tmp.getRecordId());
			jtype.setRecordDate(tmp.getRecordDate());
		} else {
			jtype.setRecordId(empid);
			jtype.setRecordDate(nowtime);
		}
		
		jtype.setLastmodiId(empid);
		jtype.setLastmodiDate(nowtime);
		cmpReService.saveJournalsType(jtype);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 获得单个期刊类型
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean getJournalsByPk(ServletContext context, HttpServletRequest request, long pk) {
		OaJournalsType type = cmpReService.getJournalsTypeByPk(pk);
		List<OaJournalsType> list = new ArrayList<OaJournalsType>();
		list.add(type);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 删除期刊类型
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean deleteJournalsByPk(ServletContext context, HttpServletRequest request, long pk) {
		OaJournals jour = new OaJournals();
		jour.setJournalsTypeId((int) pk);
		int count = cmpReService.getJournalsCount(jour);
		if (count > 0) {
			return new ResultBean(false, "该类型包含有 " + count + " 条数据,不能删除!");
		}
		cmpReService.deleteJournalsTypeByPk(pk);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 取得期刊 当前登录人的
	 * @param context
	 * @param request
	 * @param journals
	 * @param pager
	 * @return
	 */
	public ResultBean getJournalsByPager(ServletContext context, HttpServletRequest request, OaJournals journals, Pager pager) {
		journals.setCompanyId(UtilTool.getCompanyId(request));
		journals.setRecordId(UtilTool.getEmployeeId(request));
		pager = PagerHelper.getPager(pager, cmpReService.getJournalsCount(journals));
		List<OaJournals> jlist = cmpReService.getJournalsByPager(journals, pager);
		return WebUtilWork.WebResultPack(jlist, pager);
	}
	
	/**
	 * 取得期刊 所有人的
	 * @param context
	 * @param request
	 * @param journals
	 * @param pager
	 * @return
	 */
	public ResultBean getSuperJournalsByPager(ServletContext context, HttpServletRequest request, OaJournals journals, Pager pager) {
		journals.setCompanyId(UtilTool.getCompanyId(request));
	
		pager = PagerHelper.getPager(pager, cmpReService.getJournalsCount(journals));
		List<OaJournals> jlist = cmpReService.getJournalsByPager(journals, pager);
		return WebUtilWork.WebResultPack(jlist, pager);
	}
	
	/**取得期刊
	 * 
	 * @param context
	 * @param request
	 * @param journals
	 * @param pager
	 * @return
	 */
	public ResultBean getJournalsByPagers(ServletContext context, HttpServletRequest request, OaJournals journals, Pager pager) {
		journals.setCompanyId(UtilTool.getCompanyId(request));
		//journals.setRecordId(UtilTool.getEmployeeId(request));
		pager = PagerHelper.getPager(pager, cmpReService.getJournalsCount(journals));
		List<OaJournals> jlist = cmpReService.getJournalsByPager(journals, pager);
		return WebUtilWork.WebResultPack(jlist, pager);
	}
	
	/**
	 * 保存期刊
	 * 
	 * @param context
	 * @param request
	 * @param journals
	 * @param attachs
	 * @param face
	 * @return
	 * @throws Exception
	 */
	public ResultBean saveJournals(ServletContext context, HttpServletRequest request, OaJournals journals, String attachs, String face) throws Exception {
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		if (journals.getPrimaryKey() > 0) {
			OaJournals tmp = cmpReService.getJournalsByPk(journals.getPrimaryKey());
			// 删除原附件
			UtilTool.deleteAttachmentsNoFile(context, request, tmp.getJournalsAffix());
			// 删除原封面
			if (tmp.getJournalsFace() != null && tmp.getJournalsFace().intValue() > 0) {
				UtilTool.deleteImagesNoFile(context, request, tmp.getJournalsFace().toString());
			}
			
			journals.setRecordId(tmp.getRecordId());
			journals.setRecordDate(tmp.getRecordDate());
		} else {
			journals.setRecordId(empid);
			journals.setRecordDate(nowtime);
		}

		// 保存附件
		String ids = UtilTool.saveAttachments(context, request, attachs);
		journals.setJournalsAffix(ids);
		// 保存封面
		String faceid = UtilTool.saveImages(context, request, face);
		int f = 0;
		if (faceid != null && faceid.length() > 0) {
			f = Integer.parseInt(faceid);
		}

		journals.setJournalsFace(f);
		journals.setCompanyId(UtilTool.getCompanyId(request));
		journals.setLastmodiId(empid);
		journals.setLastmodiDate(nowtime);
		cmpReService.saveJournals(journals);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**取得单个期刊
	 * 
	 * @param context
	 * @param request
	 * @param p
	 * @return
	 */
	public ResultBean getJournalsMangerByPk(ServletContext context, HttpServletRequest request, long pk) {
		List<OaJournals> list = new ArrayList<OaJournals>();
		OaJournals journals = cmpReService.getJournalsByPk(pk);
		list.add(journals);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 删除期刊
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public ResultBean deleteJournalsByIds(ServletContext context, HttpServletRequest request, long[] ids) {
		for (long l : ids) {
			OaJournals tmp = cmpReService.deleteJournalsById(l);
			UtilTool.deleteAttachmentsAndFile(context, request, tmp.getJournalsAffix());
			if (tmp.getJournalsFace() != null && tmp.getJournalsFace().intValue() > 0) {
				UtilTool.deleteImagesAndFile(context, request, tmp.getJournalsFace().toString());
			}
		}
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 取得单个期刊
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean getJournalsMangerObjByPk(ServletContext context, HttpServletRequest request, long pk) {
		List<OaJournals> list = new ArrayList<OaJournals>();
		OaJournals journals = cmpReService.getJournalsObjectByPk(pk);
		list.add(journals);
		return WebUtilWork.WebResultPack(list);
	}

	/**
	 * 保存相册
	 * @param context
	 * @param request
	 * @param album
	 * @return
	 */
	public ResultBean saveAlbum(ServletContext context, HttpServletRequest request, OaAlbum album) {
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		if (album.getPrimaryKey() > 0) {
			OaAlbum tmp = cmpReService.getOaAlbumByPk(album.getPrimaryKey(), false);
			album.setRecordId(tmp.getRecordId());
			album.setRecordDate(tmp.getRecordDate());
			album.setAlbumPhotoCount(tmp.getAlbumPhotoCount());
			album.setAlbumCreateEmployee(tmp.getRecordId());
			album.setAlbumTime(tmp.getRecordDate());
			album.setAlbumPhotoId(tmp.getAlbumPhotoId());
		} else {
			album.setRecordId(empid);
			album.setRecordDate(nowtime);
			album.setAlbumPhotoCount(0);
			album.setAlbumCreateEmployee(empid);
			album.setAlbumTime(UtilWork.getToday());
		}
		album.setCompanyId(UtilTool.getCompanyId(request));
		album.setLastmodiId(empid);
		album.setLastmodiDate(nowtime);
		cmpReService.saveAlbum(album);
		return WebUtilWork.WebResultPack(null);
	}

	/**
	 * 获取相册 只能看见登录人自己的
	 * @param context
	 * @param request
	 * @param album
	 * @param pager
	 * @return
	 */
	public ResultBean getAlbumListByPager(ServletContext context, HttpServletRequest request, OaAlbum album, Pager pager) {
		album.setCompanyId(UtilTool.getCompanyId(request));
		album.setAlbumCreateEmployee(UtilTool.getEmployeeId(request));
		pager = PagerHelper.getPager(pager, cmpReService.getOaAlbumListCount(album));
		List<OaAlbum> list = cmpReService.getOaAlbumListByPager(album, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 获取相册 可以看见所有人的
	 * @param context
	 * @param request
	 * @param album
	 * @param pager
	 * @return
	 */
	public ResultBean getsuperAlbumListByPager(ServletContext context, HttpServletRequest request, OaAlbum album, Pager pager) {
		album.setCompanyId(UtilTool.getCompanyId(request));
	//	album.setAlbumCreateEmployee(UtilTool.getEmployeeId(request));
		pager = PagerHelper.getPager(pager, cmpReService.getOaAlbumListCount(album));
		List<OaAlbum> list = cmpReService.getOaAlbumListByPager(album, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 根据查询条件 获取相册
	 * @param context
	 * @param request
	 * @param album
	 * @param pager
	 * @return
	 */
	public ResultBean getAlbumListByPagerAndPrem(ServletContext context, HttpServletRequest request, OaAlbum album, Pager pager) {
		album.setCompanyId(UtilTool.getCompanyId(request));
		String empid = UtilTool.getEmployeeId(request)+",";
		String deptid = UtilTool.getDeptId(request)+",";
		pager = PagerHelper.getPager(pager, cmpReService.getOaAlbumListCountPrem(album, empid, deptid));
		List<OaAlbum> list = cmpReService.getOaAlbumListByPagerAndPrem(album, empid, deptid, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}
		
	/**
	 * UtiTool 调用
	 * @param context
	 * @param request
	 * @return
	 */
	public ResultBean getAllAlbumList(ServletContext context, HttpServletRequest request) {
		OaAlbum album = new OaAlbum();
		album.setCompanyId(UtilTool.getCompanyId(request));
		List<OaAlbum> list = cmpReService.getAllAlbumList(album);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 打开单个相册
	 * @param context
	 * @param request
	 * @param pk
	 * @param bl
	 * @return
	 */
	public ResultBean getAlbumByPk(ServletContext context, HttpServletRequest request, long pk, boolean bl) {
		List<OaAlbum> list = new ArrayList<OaAlbum>();
		OaAlbum am = cmpReService.getOaAlbumByPk(pk, bl);
		list.add(am);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 删除相册
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean deleteAlbumByPk(ServletContext context, HttpServletRequest request, long pk) {
		OaAlbum am = cmpReService.getOaAlbumByPk(pk, false);
		if (am.getAlbumPhotoCount() > 0) {
			return new ResultBean(false, "相册包含有 " + am.getAlbumPhotoCount() + " 张相片，不能删除");
		}
		cmpReService.deleteOaAlbum(am);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 获取相片
	 * @param context
	 * @param request
	 * @param photo
	 * @param pager
	 * @return
	 */
	public ResultBean getPhotoByPager(ServletContext context, HttpServletRequest request, OaPhoto photo, Pager pager) {
		pager = PagerHelper.getPager(pager, cmpReService.getPhotoCount(photo));
		List<OaPhoto> list = cmpReService.getPhotoListPager(photo, pager);
		return WebUtilWork.WebResultPack(list, pager);
	}
	
	/**
	 * 保存相片
	 * @param context
	 * @param request
	 * @param photo
	 * @param file
	 * @param files
	 * @return
	 */
	public ResultBean savePhotos(ServletContext context, HttpServletRequest request, OaPhoto photo, String file, String files) {
		ArrayList<OaPhoto> phslist = new ArrayList<OaPhoto>();
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		// 上传照片并判断照片名称生成方式
		int no = 1;
		String id = UtilTool.saveImages(context, request, file);
		String[] tmps1 = null;
		int idlen = 0;
		if (id.length() > 0) {
			tmps1 = id.split(",");
			idlen = tmps1.length;
		}
		String ids = UtilTool.saveImages(context, request, files);
		String[] tmps2 = null;
		int idslen = 0;
		if (ids.length() > 0) {
			tmps2 = ids.split(",");
			idslen = tmps2.length;
		}
		int fcount = idlen + idslen;

		String fes1[] = file.split(",");
		if (tmps1 != null && tmps1.length > 0 && fes1.length > 0) {
			for (int i = 0; i < tmps1.length; i++) {
				OaPhoto ph = new OaPhoto();
				ph.setImageId(Integer.parseInt(tmps1[i]));
				if (photo.getPhotoName().length() > 0) {
					if (fcount > 1) {
						ph.setPhotoName(photo.getPhotoName() + "-" + no);
						no++;
					} else {
						ph.setPhotoName(photo.getPhotoName());
					}
				} else {
					String[] tmps = fes1[i].split("\\|");
					ph.setPhotoName(tmps[0]);
				}
				phslist.add(ph);
			}
		}
		String fes2[] = files.split(",");
		if (tmps2 != null && tmps2.length > 0 && fes2.length > 0) {
			for (int i = 0; i < tmps2.length; i++) {
				OaPhoto ph = new OaPhoto();
				ph.setImageId(Integer.parseInt(tmps2[i]));
				if (photo.getPhotoName().length() > 0) {
					if (fcount > 1) {
						ph.setPhotoName(photo.getPhotoName() + "-" + no);
						no++;
					} else {
						ph.setPhotoName(photo.getPhotoName());
					}
				} else {
					String[] tmps = fes2[i].split("\\|");
					ph.setPhotoName(tmps[0]);
				}
				phslist.add(ph);
			}
		}
		for (OaPhoto ph : phslist) {
			ph.setRecordId(empid);
			ph.setRecordDate(nowtime);
			ph.setPhotoTime(UtilWork.getToday());
			ph.setLastmodiId(empid);
			ph.setLastmodiDate(nowtime);
			ph.setCompanyId(UtilTool.getCompanyId(request));
			ph.setAlbumId(photo.getAlbumId());
			ph.setPhotoDesc(photo.getPhotoDesc());
		}
		cmpReService.saveOaPhotos(phslist);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 修改相片
	 * @param context
	 * @param request
	 * @param photo
	 * @return
	 */
	public ResultBean updatePhoto(ServletContext context, HttpServletRequest request, OaPhoto photo) {
		OaPhoto tmp = cmpReService.getPhotoByPk(photo.getPrimaryKey());
		
		photo.setRecordDate(tmp.getRecordDate());
		photo.setRecordId(tmp.getRecordDate());
		photo.setPhotoTime(UtilWork.getToday());
		photo.setLastmodiId(UtilTool.getEmployeeId(request));
		photo.setLastmodiDate(UtilWork.getNowTime());
		photo.setCompanyId(UtilTool.getCompanyId(request));
		photo.setImageId(tmp.getImageId());
		photo.setAlbumId(tmp.getAlbumId());
		cmpReService.saveOaPhoto(photo);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 转移相片到相册
	 * @param context
	 * @param request
	 * @param photoId
	 * @return
	 */
	public ResultBean updateAlbumByPhoto(ServletContext context, HttpServletRequest request,long photoId){
		OaPhoto photo = cmpReService.getPhotoByPk(photoId);
		OaAlbum album = cmpReService.getOaAlbumByPk(photo.getAlbumId().longValue(), false);
		album.setAlbumPhotoId((int)photoId);
		album.setLastmodiId(UtilTool.getEmployeeId(request));
		album.setLastmodiDate(UtilWork.getNowTime());
		cmpReService.saveAlbum(album);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 设置相册浏览范围
	 * @param context
	 * @param request
	 * @param album
	 * @return
	 */
	public ResultBean updateAlbum(ServletContext context, HttpServletRequest request,OaAlbum album){
		OaAlbum tmp = cmpReService.getOaAlbumByPk(album.getPrimaryKey(), false);
		tmp.setLastmodiDate(UtilWork.getNowTime());
		tmp.setLastmodiId(UtilTool.getEmployeeId(request));
		tmp.setAlbumEmps(album.getAlbumEmps());
		tmp.setAlbumDeps(album.getAlbumDeps());
		cmpReService.saveAlbum(tmp);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 获取单个相片
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean getPhotoByPk(ServletContext context, HttpServletRequest request,long pk){
		List<OaPhoto> list = new ArrayList<OaPhoto>();
		OaPhoto photo = cmpReService.getPhotoByPk(pk);
		OaAlbum album = cmpReService.getOaAlbumByPk(photo.getAlbumId().longValue(), false);
		photo.setAlbum(album);
		if (album.getAlbumPhotoId().longValue()==pk) {
			photo.setIsAlubmFace("相册封面");
		}
		list.add(photo);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 删除相片
	 * @param context
	 * @param request
	 * @param pks
	 * @return
	 */
	public ResultBean deletePhoto(ServletContext context, HttpServletRequest request,long[] pks){
		ArrayList<OaPhoto> list = cmpReService.deleteOaPhoto(pks);
		for (OaPhoto oaPhoto : list) {
			UtilTool.deleteImagesAndFile(context, request, String.valueOf(oaPhoto.getImageId()));
		}
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 获取规制制度
	 * @param context
	 * @param request
	 * @param regul
	 * @param pager
	 * @return
	 */
	public ResultBean getAllRegulationsByPager(ServletContext context,HttpServletRequest request,OaRegulations regul,Pager pager){
		regul.setCompanyId(UtilTool.getCompanyId(request));
		pager = PagerHelper.getPager(pager, cmpReService.getOaRegulationsCount(regul));
		List<OaRegulations> list = cmpReService.getOaRegulationsByPager(regul, pager);
		return WebUtilWork.WebResultPack(list,pager);
	}
	
	/**
	 * 查找有效的规制制度
	 * @param context
	 * @param request
	 * @param regul
	 * @param pager
	 * @return
	 */
	public ResultBean getAllRegulationsVaildByPager(ServletContext context,HttpServletRequest request,OaRegulations regul,Pager pager){
		regul.setCompanyId(UtilTool.getCompanyId(request));
		regul.setRegulationsStatus(EnumUtil.SYS_ISACTION.Vaild.value);
		regul.setTmpDatetime(UtilWork.getToday());
		pager = PagerHelper.getPager(pager, cmpReService.getOaRegulationsCount(regul));
		List<OaRegulations> list = cmpReService.getOaRegulationsByPager(regul, pager);
		return WebUtilWork.WebResultPack(list,pager);
	}
	
	/**
	 * 保存规制制度
	 * @param context
	 * @param request
	 * @param regul
	 * @param files
	 * @return
	 */
	public ResultBean saveRegulations(ServletContext context,HttpServletRequest request,OaRegulations regul,String files){
		String empid = UtilTool.getEmployeeId(request);
		String nowtime = UtilWork.getNowTime();
		if (regul.getPrimaryKey() > 0) {
			OaRegulations tmp = cmpReService.getOaRegulAtionsByPk(regul.getPrimaryKey(),false);		
			// 删除原附件记录
			UtilTool.deleteAttachmentsNoFile(context, request, tmp.getOaRegulationsAttachs());
			
			regul.setRecordId(tmp.getRecordId());
			regul.setRecordDate(tmp.getRecordDate());
			regul.setOaRegulationsEmp(tmp.getOaRegulationsEmp());
		} else {
			regul.setRecordId(empid);
			regul.setRecordDate(nowtime);
			regul.setOaRegulationsEmp(empid);
		}
		String ids = UtilTool.saveAttachments(context, request, files);
		regul.setOaRegulationsAttachs(ids);
		regul.setCompanyId(UtilTool.getCompanyId(request));
		regul.setLastmodiId(empid);
		regul.setLastmodiDate(nowtime);
		cmpReService.saveOaRegulAtions(regul);
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 获取单条规制制度
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean getOaRegulations(ServletContext context,HttpServletRequest request,long pk){
		List<OaRegulations> list = new ArrayList<OaRegulations>();
		OaRegulations regul = cmpReService.getOaRegulAtionsByPk(pk, false);
		list.add(regul);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 *  获取单条规制制度
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean getOaRegulationsAndObj(ServletContext context,HttpServletRequest request,long pk){
		List<OaRegulations> list = new ArrayList<OaRegulations>();
		OaRegulations regul = cmpReService.getOaRegulAtionsByPk(pk, true);
		list.add(regul);
		return WebUtilWork.WebResultPack(list);
	}
	
	/**
	 * 设置规章制度 有效 或无效
	 * @param context
	 * @param request
	 * @param pk
	 * @return
	 */
	public ResultBean setOaRegulationsStatus(ServletContext context,HttpServletRequest request,long[] pk){
		cmpReService.updateOaRegulAtionsStatus(pk, UtilTool.getEmployeeId(request));
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 删除规章制度
	 * @param context
	 * @param request
	 * @param ids
	 * @return
	 */
	public ResultBean deleteOaRegulationsIds(ServletContext context,HttpServletRequest request,long[] ids){
		for (long l : ids) {
			OaRegulations tmp = cmpReService.deleteOaRegulations(l);
			UtilTool.deleteAttachmentsAndFile(context, request, tmp.getOaRegulationsAttachs());
		}
		return WebUtilWork.WebResultPack(null);
	}
	
	/**
	 * 删除相片
	 * @param context
	 * @param request
	 * @param ids
	 * @param albumId
	 * @return
	 */
	public ResultBean movePhotos(ServletContext context,HttpServletRequest request,long[] ids,int albumId){
		cmpReService.moveOaPhotos(ids, albumId);
		return WebUtilWork.WebResultPack(null);
	}
}
