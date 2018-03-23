package com.pinhuba.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinhuba.common.pack.PersonalOfficeHqlPack;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.core.dao.IHrmTimedrecordDao;
import com.pinhuba.core.dao.IOaLeaveregistrationDao;
import com.pinhuba.core.dao.IOaNotebookDao;
import com.pinhuba.core.dao.IOaToolsDao;
import com.pinhuba.core.dao.IOaTrsvelDao;
import com.pinhuba.core.dao.ISysLibraryInfoDao;
import com.pinhuba.core.iservice.IPersonalOfficeSerivce;
import com.pinhuba.core.pojo.HrmTimedrecord;
import com.pinhuba.core.pojo.OaLeaveregistration;
import com.pinhuba.core.pojo.OaNotebook;
import com.pinhuba.core.pojo.OaTools;
import com.pinhuba.core.pojo.OaTrsvel;
import com.pinhuba.core.pojo.SysLibraryInfo;
@Service
@Transactional
public class PersonalOfficeSerivce implements IPersonalOfficeSerivce {
	private Logger log = Logger.getLogger(this.getClass());
	@Resource
	private IOaLeaveregistrationDao oaLeaveregistrationDao;
	@Resource
	private IOaTrsvelDao oaTrsvelDao; 
	@Resource
	private IOaToolsDao oaToolsDao;
	@Resource
	private ISysLibraryInfoDao librayInfoDao;
	@Resource
	private IOaNotebookDao  oaNotebookDao;
	@Resource
    private IHrmTimedrecordDao hrmTimedrecordDao;
	@Resource
    private IdentityService identityService;
	@Resource
    private RuntimeService runtimeService;
	@Resource
    private HistoryService historyService;
	
	// 请假删除
	public void deleteOaLeaveregistration(long[] oaLeaverpk) {
		for (long l : oaLeaverpk) {
			OaLeaveregistration oaleaver = oaLeaveregistrationDao.getByPK(l);
			oaLeaveregistrationDao.remove(oaleaver);
		}
	}
	
	//删除请假流程实例数据，业务数据
	public void deleteOaLeave(long[] oaLeaverpk) {
		for (long l : oaLeaverpk) {
			OaLeaveregistration oaleaver = oaLeaveregistrationDao.getByPK(l);
			String processInstanceId = oaleaver.getProcessInstanceId();
			if(StringUtils.isNotBlank(processInstanceId)){
				deleteProcessInstance(processInstanceId);
			}
			oaLeaveregistrationDao.remove(oaleaver);
		}
	}
	
	//请假显示
	public List<OaLeaveregistration> getOaLeaveregistration(OaLeaveregistration oaleaver, Pager pager) {
		return oaLeaveregistrationDao.findByHqlWherePage(PersonalOfficeHqlPack.packOaLeaveregistrationQuery(oaleaver)+"order by model.applydata desc", pager);
	}
	//请假审批
	public int getOaLeaveregistrationCountApprove(OaLeaveregistration oaLeaveregistration,String ids){
		return oaLeaveregistrationDao.findBySqlCount(PersonalOfficeHqlPack.packOaLeaveregistrationApprove(oaLeaveregistration, ids));
	}
	
	public List<OaLeaveregistration> getOaLeaveregistrationApprove(OaLeaveregistration oaLeaveregistration,String ids,Pager pager){
		return oaLeaveregistrationDao.findBySqlPage(PersonalOfficeHqlPack.packOaLeaveregistrationApprove(oaLeaveregistration, ids)+"order by lea.applydata desc", OaLeaveregistration.class, pager);
	}
	
	
	//请假ID取对象
	public OaLeaveregistration getOaLeaveregistrationByPk(long Pk) {
		return oaLeaveregistrationDao.getByPK(Pk);

	}

	// 总数
	public int listOaLeaveregistrationCount(OaLeaveregistration oaLeaveregistration) {
		int count = oaLeaveregistrationDao.findByHqlWhereCount(PersonalOfficeHqlPack.packOaLeaveregistrationQuery(oaLeaveregistration));
		return count;
	}
	
	/**
	 * 查询请假次数 统计使用
	 */
	public int getoaLeaveregistrationCount(OaLeaveregistration oaLeaveregistration,String startDate,String endDate){
		StringBuffer hql = new StringBuffer();
		hql.append(" and model.applyuser = '" + oaLeaveregistration.getApplyuser() + "' ");
		hql.append(" and ( model.startdata between '" + startDate + " 00:00:00'  AND '" + endDate + " 23:59:59' ");
		hql.append(" or model.enddata between '" + startDate + " 00:00:00'  AND '" + endDate + " 23:59:59' )");
		int count = oaLeaveregistrationDao.findByHqlWhereCount(hql.toString());
		return count;
	}

	// 添加
	public OaLeaveregistration saveOaLeaveregistration(OaLeaveregistration oaleaver) {
		return (OaLeaveregistration) oaLeaveregistrationDao.save(oaleaver);

	}

	// 出差删除
	public void deleteOaTrsvel(long[] oaTrsvelPks) {
		for (long l : oaTrsvelPks) {
			OaTrsvel oaTrsvel = oaTrsvelDao.getByPK(l);
			oaTrsvelDao.remove(oaTrsvel);
		}
	}
	
	public void deleteOaTrsvelByPksSuper(long[] pks){
		for (long l : pks) {
			OaTrsvel oaTrsvel = oaTrsvelDao.getByPK(l);
			String processInstanceId = oaTrsvel.getProcessInstanceId();
			if(StringUtils.isNotBlank(processInstanceId)){
				deleteProcessInstance(processInstanceId);
			}
			oaTrsvelDao.remove(oaTrsvel);
		}
	}

	private void deleteProcessInstance(String processInstanceId) {
		ProcessInstance instance = 
				runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if(instance != null)
			runtimeService.deleteProcessInstance(processInstanceId, null);
		
		HistoricProcessInstance historicProcessInstance = 
				historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if(historicProcessInstance != null)
			historyService.deleteHistoricProcessInstance(processInstanceId);
	}

	// 出差显示
	public List<OaTrsvel> getOaTrsvel(OaTrsvel oaTrsvel, Pager pager) {
		return oaTrsvelDao.findByHqlWherePage(PersonalOfficeHqlPack.packoaTrsvelQuery(oaTrsvel)+" order by model.applydata desc", pager);

	}
	// 出差显示
	public List<OaTrsvel> getsuperOaTrsvel(OaTrsvel oaTrsvel, Pager pager) {
		return oaTrsvelDao.findByHqlWherePage(PersonalOfficeHqlPack.packsuperoaTrsvelQuery(oaTrsvel)+" order by model.applydata desc", pager);

	}

	// 出差ID取值
	public OaTrsvel getOaTrsvelByPk(long Pk) {
		return oaTrsvelDao.getByPK(Pk);

	}

	// 出差总数
	public int listOaTrsvelCount(OaTrsvel oaTrsvel) {
		int count = oaTrsvelDao.findByHqlWhereCount(PersonalOfficeHqlPack.packoaTrsvelQuery(oaTrsvel));
		return count;
	}
	// 出差总数
	public int listsuperOaTrsvelCount(OaTrsvel oaTrsvel) {
		int count = oaTrsvelDao.findByHqlWhereCount(PersonalOfficeHqlPack.packsuperoaTrsvelQuery(oaTrsvel));
		return count;
	}
	
	/**
	 * 查询出差次数 统计使用
	 */
	public int getoaTrsvelCount(OaTrsvel oaTrsvel,String startDate,String endDate){
		StringBuffer hql = new StringBuffer();
		hql.append(" and model.trsvelAppalyuser = '" + oaTrsvel.getTrsvelApplyuser() + "' ");
		hql.append(" and ( model.trsvelBegindata between '" + startDate + " 00:00:00'  AND '" + endDate + " 23:59:59' ");
		hql.append(" or model.trsvelEnddata between '" + startDate + " 00:00:00'  AND '" + endDate + " 23:59:59' )");
		int count = oaTrsvelDao.findByHqlWhereCount(hql.toString());
		return count;
	}
	
	// 出差添加
	public OaTrsvel saveOaTrsvel(OaTrsvel oaTrsvel) {
		return (OaTrsvel) oaTrsvelDao.save(oaTrsvel);
	}
	
	//出差审批
	public int getOaTrsvelCountApprove(OaTrsvel oaTrsvel,String ids){
		return oaTrsvelDao.findBySqlCount(PersonalOfficeHqlPack.packOaTrsvelApprove(oaTrsvel, ids));
	}
	
	public List<OaTrsvel> getOaTrsvelApprove(OaTrsvel oaTrsvel,String ids,Pager pager){
		return oaTrsvelDao.findBySqlPage(PersonalOfficeHqlPack.packOaTrsvelApprove(oaTrsvel, ids)+"order by trs.applydata desc",OaTrsvel.class,pager);
	}
	
	//根据PK删除oaTool
	public void deleteOaTool(long pk) {
		oaToolsDao.remove(oaToolsDao.getByPK(pk));

	}

	// 列出所有工具
	public List<OaTools> getAllOaTools(OaTools oaTools) {
		List<OaTools> list = oaToolsDao.findByHqlWhere(PersonalOfficeHqlPack.packoaToolsQuery(oaTools));
		return list;
	}

	// 添加工具
	public OaTools saveOaTools(OaTools oaTools) {
		return (OaTools) oaToolsDao.save(oaTools);
	}

	/**
	 * 判断人员是否存在工具
	 * 
	 * @param oaTools
	 * @return
	 */
	public void isHashToolsByEmpId(OaTools oaTools) {
		int count = oaToolsDao.findByHqlWhereCount(PersonalOfficeHqlPack.packoaToolsQuery(oaTools));
		if (count <= 0) {
			Map<String, ArrayList<OaTools>> map = ConstWords.getToolsMap();
			List<SysLibraryInfo> list = librayInfoDao.findByHqlWhere(" and model.libraryInfoUpcode = '" + ConstWords.Tool_Libriary_Code + "'"
					+ " and model.libraryInfoIsvalid =" + EnumUtil.SYS_ISACTION.Vaild.value + "  order by model.libraryInfoCode");
			for (SysLibraryInfo sysLibraryInfo : list) {
				String tmpcode = sysLibraryInfo.getLibraryInfoCode();
//				System.out.println(tmpcode);
				if (map.containsKey(tmpcode)) {// 判读map中是否存在该主键
					ArrayList<OaTools> tools = map.get(tmpcode);
					for (int i = 0; i < tools.size(); i++) {
						OaTools tl = tools.get(i);
						tl.setCompanyId(oaTools.getCompanyId());
						tl.setLastmodiDate(UtilWork.getNowTime());
						tl.setLastmodiId(oaTools.getOaToolEmp());
						tl.setRecordDate(UtilWork.getNowTime());
						tl.setRecordId(oaTools.getOaToolEmp());
						tl.setOaToolType((int) sysLibraryInfo.getPrimaryKey());
						tl.setOaToolEmp(oaTools.getOaToolEmp());
						oaToolsDao.save(tl);
					}
				}
			}
		}
	}

	public List<SysLibraryInfo> getSysLibraryInfoByCode() {
		List<SysLibraryInfo> list = librayInfoDao.findByHqlWhere(" and model.libraryInfoUpcode = '" + ConstWords.Tool_Libriary_Code + "'"
				+ " and model.libraryInfoIsvalid =" + EnumUtil.SYS_ISACTION.Vaild.value + "  order by model.libraryInfoCode");
		return list;
	}

	public List<OaTools> getOaToolsListByType(int type) {
		List<OaTools> list = oaToolsDao.findByHqlWhere(" and model.oaToolType = '" + type + "'");
		return list;
	}

	// 根据类型和主键查工具
	public List<OaTools> getOaToolByTypeAndPk(int type, int pk) {
		List<OaTools> list = oaToolsDao.findByHqlWhere(" and model.oaToolType = " + type + " and model.primaryKey = " + pk);

		return list;
	}

	public OaTools getOaToolsByPk(long pk) {
		return oaToolsDao.getByPK(pk);
	}

	//返回个人便签条数
	public int getNotebookCount(OaNotebook notebook) {
		int count = oaNotebookDao.findByHqlWhereCount(PersonalOfficeHqlPack.packNotebookQuery(notebook));
		return count;
	}

    //返回个人便签内容表
	public List<OaNotebook> getAllNotebook(OaNotebook notebook, Pager pager) {
		List<OaNotebook> list =  oaNotebookDao.findByHqlWherePage(PersonalOfficeHqlPack.packNotebookQuery(notebook)+" order by oaNotebookCreattime desc", pager);
		return list;
	}
	
	
	//保存个人便签
	public OaNotebook savePersonalNotebook(OaNotebook notebook) {
	    return (OaNotebook)oaNotebookDao.save(notebook);
		
	}

	//删除便签
	public void delectNotebookByid(long[] ids) {
		for (long l : ids) {
			OaNotebook msg =  oaNotebookDao.getByPK(l);			
			oaNotebookDao.remove(msg);			
		}
	}



	//根据ID查询记录
	public OaNotebook getNotebookById(long id) {
		OaNotebook  notebook = oaNotebookDao.getByPK(id);
		return notebook;
	}
	
	/**
	 * 查询所有有效的定时设置
	 */
	public List<HrmTimedrecord> getAllCompanyTimedRecordValid() {
		List<HrmTimedrecord> list = hrmTimedrecordDao.findByHqlWhere(PersonalOfficeHqlPack.packAllCompanyTimedValid());
		return list;
	}
	
	/**
	 * 查询有效定时设置， by 公司ID 和 雇员ID
	 */
	public List<HrmTimedrecord> getTimedValidByCompanyAndEmpId(HrmTimedrecord record, Pager pager) {
		
		List<HrmTimedrecord> list = hrmTimedrecordDao.findByHqlWherePage(PersonalOfficeHqlPack.packHrmTimedRecordQueryValid(record)+" order by model.timedType,model.timedDate",pager);
		return list;
	}
	
	/**
	 * 返回有效定时提醒数目
	 */
	public int getTimedValidCount(HrmTimedrecord record) {
		int count = hrmTimedrecordDao.findByHqlWhereCount(PersonalOfficeHqlPack.packHrmTimedRecordQueryValid(record));
		return count;
	}
	
	/**
	 * 查询失效的定时设置， by 公司ID 和 雇员ID
	 */
	public List<HrmTimedrecord> getTimedInValidByCompanyAndEmpId(HrmTimedrecord record, Pager pager) {
		
		List<HrmTimedrecord> list = hrmTimedrecordDao.findByHqlWherePage(PersonalOfficeHqlPack.packHrmTimedRecordQueryInValid(record),pager);
		return list;
	}
	
	/**
	 * 返回失效的定时提醒数目
	 */
	public int getTimedInValidCount(HrmTimedrecord record) {
		int count = hrmTimedrecordDao.findByHqlWhereCount(PersonalOfficeHqlPack.packHrmTimedRecordQueryInValid(record));
		return count;
	}

	/**
	 * 清空无效的定时提醒
	 */
	public void deleteTimedInvalid(int companyId, String hrmEmpId) {
		StringBuffer result = new StringBuffer();
		result.append("delete from hrm_timedrecord where company_id=");
		result.append(companyId);
		result.append(" and record_id='");
		result.append(hrmEmpId);
		result.append("' and timed_date < '");
		result.append(UtilWork.getNowTime());
		result.append("' and timed_type = 1");
		int temp = hrmTimedrecordDao.executeSql(result.toString());
		log.debug("删除无效提醒条数："+temp);	
		
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public HrmTimedrecord getTimedRecordByPk(long id) {
		return hrmTimedrecordDao.getByPK(id);
	}

	/**
	 * 删除定时提醒
	 */
	public void deleteTimedRecordByPks(long[] ids) {
		for (long l : ids) {
			HrmTimedrecord timed =  hrmTimedrecordDao.getByPK(l);			
			hrmTimedrecordDao.remove(timed);			
		}
	}
	
	/**
	 * 添加定时提醒
	 */
	public HrmTimedrecord saveTimedRecord(HrmTimedrecord hrmTimedrecord) {
		return (HrmTimedrecord)hrmTimedrecordDao.save(hrmTimedrecord);
	}
	
	// 总数
	public int listAllOaLeaveregistrationCount(OaLeaveregistration oaLeaveregistration) {
		int count = oaLeaveregistrationDao.findBySqlCount(PersonalOfficeHqlPack.packAllOaLeaveregistrationQuery(oaLeaveregistration));
		return count;
	}
	//请假显示
	public List<OaLeaveregistration> getAllOaLeaveregistration(OaLeaveregistration oaleaver, Pager pager) {
		return oaLeaveregistrationDao.findBySqlPage(PersonalOfficeHqlPack.packAllOaLeaveregistrationQuery(oaleaver)+" order by le.applydata desc",OaLeaveregistration.class, pager);
	}

	/************************************************************************/

	public ProcessInstance startOaLeaveWorkflow(OaLeaveregistration oaLeaver,Map<String, Object> variables) {
		oaLeaver = (OaLeaveregistration) oaLeaveregistrationDao.save(oaLeaver);
	    
		ProcessInstance processInstance = startProcess(oaLeaver.getApplyuser(),
    			EnumUtil.WORKFLOW_TYPE.LEAVE.key, String.valueOf(oaLeaver.getPrimaryKey()), variables);
	
	    oaLeaver.setProcessInstanceId(processInstance.getId());
	    oaLeaveregistrationDao.save(oaLeaver);
	    return processInstance;
	}
	
	public ProcessInstance startOaTrsvelWorkflow(OaTrsvel oaTrsvel,Map<String, Object> variables) {
		oaTrsvel = (OaTrsvel) oaTrsvelDao.save(oaTrsvel);
		
		ProcessInstance processInstance = startProcess(oaTrsvel.getTrsvelApplyuser(),
    			EnumUtil.WORKFLOW_TYPE.TRSVEL.key, String.valueOf(oaTrsvel.getPrimaryKey()), variables);
		
	    oaTrsvel.setProcessInstanceId(processInstance.getId());
	    oaTrsvelDao.save(oaTrsvel);
	    return processInstance;
	}

	public ProcessInstance startProcess(String userId, String key, 
			String businessKey, Map<String, Object> variables) {
		try {
			identityService.setAuthenticatedUserId(userId);// 先设置登录用户
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, businessKey, variables);
			return processInstance;
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}
}
