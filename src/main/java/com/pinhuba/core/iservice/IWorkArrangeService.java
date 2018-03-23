package com.pinhuba.core.iservice;

import java.util.List;

import com.pinhuba.core.pojo.OaCalender;
import com.pinhuba.core.pojo.OaWorkLog;
import com.pinhuba.core.pojo.SysMethodInfo;
import com.pinhuba.common.pages.Pager;

/**
 * 计划安排
 * @author frin
 *
 */
public interface IWorkArrangeService {

	int listOaWorkLogCount(OaWorkLog oaWorkLog);

	List<OaWorkLog> getAllOaWorkLog(OaWorkLog oaWorkLog,
			Pager pager);

	OaWorkLog saveWorkLog(OaWorkLog oaWorkLog);

	OaWorkLog getWorkLogByPK(long worklogpk);

	void deleteworklogByPks(long worklogPKs);

	public List<Object[]> getAllOaWorkLogByDateAndLogger(OaWorkLog oaWorkLog);

	int listOaShareWorkLogCount(OaWorkLog oaWorkLog);

	List<OaWorkLog> getAllOaShareWorkLog(OaWorkLog oaWorkLog, Pager pager);

	List<OaCalender> listOaCalender(OaCalender oaCalender);

	int listOaCalenderCount(OaCalender oaCalender);

	List<OaCalender> getAllOaCalender(OaCalender oaCalender, Pager pager);

	OaCalender saveOaCalender(OaCalender oaCalender);

	OaCalender getOaCalenderByPk(long oaCalenderpk);

	void deleteOaCalenderByPks(long[] oaCalenderPKs);
	
}
