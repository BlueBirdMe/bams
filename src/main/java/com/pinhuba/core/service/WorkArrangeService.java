package com.pinhuba.core.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinhuba.common.pack.WorkArrangeHqlPack;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.core.dao.IOaCalenderDao;
import com.pinhuba.core.dao.IOaWorkLogDao;
import com.pinhuba.core.iservice.IWorkArrangeService;
import com.pinhuba.core.pojo.OaCalender;
import com.pinhuba.core.pojo.OaWorkLog;

@Service
@Transactional
public class WorkArrangeService implements IWorkArrangeService {
	@Resource
	private IOaWorkLogDao oaWorkLogDao;
	@Resource
	private IOaCalenderDao oaCalenderDao;

	public List<OaWorkLog> getAllOaWorkLog(OaWorkLog oaWorkLog,
			Pager pager) {
	  List<OaWorkLog> oaWorkLogList = oaWorkLogDao.findByHqlWherePage(WorkArrangeHqlPack.getOaWorkLogSql(oaWorkLog)+" order by model.oaWorklogDate desc", pager);
		return oaWorkLogList;
	}

	public int listOaWorkLogCount(OaWorkLog oaWorkLog) {
		int count = oaWorkLogDao.findByHqlWhereCount(WorkArrangeHqlPack.getOaWorkLogSql(oaWorkLog));
		return count;
	}


	public OaWorkLog saveWorkLog(OaWorkLog oaWorkLog) {
		OaWorkLog oaWorkLogtemp = (OaWorkLog) oaWorkLogDao.save(oaWorkLog);
		return oaWorkLogtemp;
	}


	public OaWorkLog getWorkLogByPK(long worklogpk) {
		OaWorkLog oaWorkLog = oaWorkLogDao.getByPK(worklogpk);
		return oaWorkLog;
	}


	public void deleteworklogByPks(long worklogPKs) {
			OaWorkLog oaWorkLog = oaWorkLogDao.getByPK(worklogPKs);
			oaWorkLogDao.remove(oaWorkLog);
		
	}


	public List<Object[]> getAllOaWorkLogByDateAndLogger(OaWorkLog oaWorkLog) {
		List<Object[]> objlist =oaWorkLogDao.findBySqlObjList("select oa_worklog_date,count(oa_worklog_id) from OA_WORK_LOG where oa_worklog_date between '"+oaWorkLog.getOaWorklogDate()+"' and '"+oaWorkLog.getOaWorklogDeps()+"' and oa_worklog_logger = '"+oaWorkLog.getOaWorklogLogger()+"' and company_id = "+oaWorkLog.getCompanyId()+" group by oa_worklog_date");
		return objlist;
	}


	public List<OaWorkLog> getAllOaShareWorkLog(OaWorkLog oaWorkLog, Pager pager) {
		List<OaWorkLog> oaWorkLogList = oaWorkLogDao.findBySqlPage(WorkArrangeHqlPack.getOaShareWorkLogSql(oaWorkLog)+"  order by worklog.oa_worklog_date desc",OaWorkLog.class, pager);
		return oaWorkLogList;
	}


	public int listOaShareWorkLogCount(OaWorkLog oaWorkLog) {
		int count = oaWorkLogDao.findBySqlCount(WorkArrangeHqlPack.getOaShareWorkLogSql(oaWorkLog));
		return count;
	}


	public List<OaCalender> listOaCalender(OaCalender oaCalender) {
		List<OaCalender>  list = oaCalenderDao.findByHqlWhere(WorkArrangeHqlPack.getOaCalenderSql(oaCalender));
		return list;
	}


	public List<OaCalender> getAllOaCalender(OaCalender oaCalender, Pager pager) {
		List<OaCalender> oaCalenderList = oaCalenderDao.findByHqlWherePage(WorkArrangeHqlPack.getOaCalenderSql(oaCalender)+" order by oaCalenderLevel ", pager);
		return oaCalenderList;
	}


	public int listOaCalenderCount(OaCalender oaCalender) {
		int count = oaCalenderDao.findByHqlWhereCount(WorkArrangeHqlPack.getOaCalenderSql(oaCalender));
		return count;
	}


	public OaCalender saveOaCalender(OaCalender oaCalender) {
		OaCalender oaCalendertemp = (OaCalender) oaCalenderDao.save(oaCalender);
		return oaCalendertemp;
	}


	public OaCalender getOaCalenderByPk(long oaCalenderpk) {
		OaCalender oaCalender = oaCalenderDao.getByPK(oaCalenderpk);
		return oaCalender;
	}


	public void deleteOaCalenderByPks(long[] oaCalenderPKs) {
		for (long l : oaCalenderPKs) {
			OaCalender oaCalender = oaCalenderDao.getByPK(l);
			oaCalenderDao.remove(oaCalender);
		}
	}
	
}
