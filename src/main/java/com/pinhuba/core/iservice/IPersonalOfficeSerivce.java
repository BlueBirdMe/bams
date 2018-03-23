package com.pinhuba.core.iservice;

import java.util.List;
import java.util.Map;
import org.activiti.engine.runtime.ProcessInstance;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.core.pojo.HrmTimedrecord;
import com.pinhuba.core.pojo.OaLeaveregistration;
import com.pinhuba.core.pojo.OaNotebook;
import com.pinhuba.core.pojo.OaTools;
import com.pinhuba.core.pojo.OaTrsvel;
import com.pinhuba.core.pojo.SysLibraryInfo;

public interface IPersonalOfficeSerivce {
	
	public int getOaLeaveregistrationCountApprove(OaLeaveregistration oaLeaveregistration,String ids);
	
	public List<OaLeaveregistration> getOaLeaveregistrationApprove(OaLeaveregistration oaLeaveregistration,String ids,Pager pager);
	
	public int getOaTrsvelCountApprove(OaTrsvel oaTrsvel,String ids);
	
	public List<OaTrsvel> getOaTrsvelApprove(OaTrsvel oaTrsvel,String ids,Pager pager);

	//请假登记显示方法
	public List<OaLeaveregistration> getOaLeaveregistration(OaLeaveregistration oaLeaveregistration,Pager pager);
	//请假登记添加
	public OaLeaveregistration saveOaLeaveregistration(OaLeaveregistration oaLeaveregistration);
	//请假登记删除
	public void deleteOaLeaveregistration(long[] carPks);
	public void deleteOaLeave(long[] carPks);
	//请假查询总是 分页使用
	public int listOaLeaveregistrationCount(OaLeaveregistration oaLeaveregistration);
    //请假根据ID获得对象
	public OaLeaveregistration getOaLeaveregistrationByPk(long Pk);
	
	//出差登记显示方法
	public List<OaTrsvel> getOaTrsvel(OaTrsvel oaLeaveregistration,Pager pager);
	//出差登记添加
	public OaTrsvel saveOaTrsvel(OaTrsvel oaLeaveregistration);
	//出差登记删除
	public void deleteOaTrsvel(long[] carPks);
	public void deleteOaTrsvelByPksSuper(long[] pks);
	//出差查询总是 分页使用
	public int listOaTrsvelCount(OaTrsvel oaLeaveregistration);
    //出差根据ID获得对象
	public OaTrsvel getOaTrsvelByPk(long Pk);
	//统计出差次数 报表使用
	public int getoaTrsvelCount(OaTrsvel oaTrsvel,String startDate,String endDate);
	
	//统计请假次数 报表使用
	public int getoaLeaveregistrationCount(OaLeaveregistration oaLeaveregistration,String startDate,String endDate);
	
	public void deleteOaTool(long pk);
	
	public OaTools saveOaTools(OaTools oaTools);
	
	public List<OaTools> getAllOaTools(OaTools oaTools);
	
	public void isHashToolsByEmpId(OaTools oaTools);
	
	public List<SysLibraryInfo> getSysLibraryInfoByCode();
	
	//根据类型得到工具集
	public List<OaTools> getOaToolsListByType(int type);
	
	public OaTools getOaToolsByPk(long pk);
	//根据类型和主键查询工具
	public List<OaTools> getOaToolByTypeAndPk(int type,int pk);

	public int getNotebookCount(OaNotebook notebook);

	public List<OaNotebook> getAllNotebook(OaNotebook notebook, Pager pager);

	public OaNotebook savePersonalNotebook(OaNotebook notebook);

	public void delectNotebookByid(long[] ids);

	public OaNotebook getNotebookById(long id);
	/**
	 * 查询所有有效的定时设置
	 */
	public List<HrmTimedrecord> getAllCompanyTimedRecordValid();
	/**
	 * 查询有效的定时设置， by 公司ID 和 雇员ID
	 */
	public List<HrmTimedrecord> getTimedValidByCompanyAndEmpId(HrmTimedrecord record, Pager pager);
	/**
	 * 返回有效地定时提醒数目
	 */
	public int getTimedValidCount(HrmTimedrecord record) ;
	
	/**
	 * 查询无效的定时设置， by 公司ID 和 雇员ID
	 */
	public List<HrmTimedrecord> getTimedInValidByCompanyAndEmpId(HrmTimedrecord record, Pager pager);
	/**
	 * 返回无效地定时提醒数目
	 */
	public int getTimedInValidCount(HrmTimedrecord record) ;
	/**
	 * 清空无效的提醒数目
	 */
	public void deleteTimedInvalid(int companyId, String hrmEmpId) ;
	
	/**
	 * 根据主键查询
	 */
	public HrmTimedrecord getTimedRecordByPk(long id) ;

	/**
	 * //删除定时提醒
	 */
	public void deleteTimedRecordByPks(long[] ids);
	/**
	 * 添加定时提醒
	 */
	public HrmTimedrecord saveTimedRecord(HrmTimedrecord hrmTimedrecord) ;
	
	public int listAllOaLeaveregistrationCount(OaLeaveregistration oaLeaveregistration);
	public List<OaLeaveregistration> getAllOaLeaveregistration(OaLeaveregistration oaleaver, Pager pager);
	public int listsuperOaTrsvelCount(OaTrsvel oaTrsvel) ;
	
	public List<OaTrsvel> getsuperOaTrsvel(OaTrsvel oaTrsvel, Pager pager);
	
	
	/**************************工作流新增 2013-08-31******************************/

	public ProcessInstance startOaLeaveWorkflow(OaLeaveregistration oaLeaver,Map<String, Object> variables);

	public ProcessInstance startOaTrsvelWorkflow(OaTrsvel oaTrsvel,Map<String, Object> variables);
}
