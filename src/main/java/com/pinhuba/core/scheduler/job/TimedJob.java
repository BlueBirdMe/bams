package com.pinhuba.core.scheduler.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.core.iservice.IPersonalOfficeSerivce;
import com.pinhuba.core.pojo.HrmTimedrecord;
import com.pinhuba.core.scheduler.TimedScheduler;

/**
 * 生成定时任务
 */
public class TimedJob implements Job {
	private Logger log = Logger.getLogger(this.getClass());
	private Map<String, List<HrmTimedrecord>> map = null;

	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {

			ServletContext serContext = (ServletContext) context.getScheduler().getContext().get("serContext");
			String timeTaskId = context.getJobDetail().getJobDataMap().getString("tid").replaceAll(TimedScheduler.JOB_PREFIX_NAME, "");// 定时器主键
			WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(serContext);
			IPersonalOfficeSerivce personalOfficeSerivce = (IPersonalOfficeSerivce) webAppContext.getBean("personalOfficeSerivce");
			HrmTimedrecord record = personalOfficeSerivce.getTimedRecordByPk(Long.parseLong(timeTaskId));
			if (record != null && record.getTimedType() == EnumUtil.TIMED_TYPE.No_Vaild.value) {
				TimedScheduler.removeJob(context.getJobDetail().getJobDataMap().getString("tid"), serContext);
			}

			if (serContext.getAttribute(ConstWords.servletContext_Timer) == null) {
				map = new HashMap<String, List<HrmTimedrecord>>();
				serContext.setAttribute(ConstWords.servletContext_Timer, map);
			} else {
				map = (Map<String, List<HrmTimedrecord>>) serContext.getAttribute(ConstWords.servletContext_Timer);
			}

			String empid = record.getRecordId();

			if (map.containsKey(empid)) {
				List<HrmTimedrecord> tmplist =  map.get(empid);
				tmplist.add(record);
			} else {
				List<HrmTimedrecord> tmplist = new ArrayList<HrmTimedrecord>();
				tmplist.add(record);
				map.put(empid, tmplist);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

}
