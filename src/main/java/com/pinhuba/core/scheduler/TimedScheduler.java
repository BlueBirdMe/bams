package com.pinhuba.core.scheduler;

import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pinhuba.common.util.UtilTool;
import com.pinhuba.core.iservice.IPersonalOfficeSerivce;
import com.pinhuba.core.pojo.HrmTimedrecord;
import com.pinhuba.core.scheduler.job.TimedJob;


/**
 * 定时器类
 * **/

public class TimedScheduler {
	private Logger logger = Logger.getLogger(this.getClass());

	private static SchedulerFactory sf = new StdSchedulerFactory();

	private static String JOB_GROUP_NAME = "group_timed";

	private static String TRIGGER_GROUP_NAME = "trigger_timed";

	public static String JOB_PREFIX_NAME = "jobname_timed_";
	
	// 初始化加载
	public void run(ServletContext ctx) throws SchedulerException, ParseException {
		logger.info("Init timer start.....");
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
		IPersonalOfficeSerivce personOfficeService = (IPersonalOfficeSerivce) webAppContext.getBean("personalOfficeSerivce");
		
		TimedJob job = new TimedJob();
		List<HrmTimedrecord> list = personOfficeService.getAllCompanyTimedRecordValid();
	    for (HrmTimedrecord hrmTimedrecord : list) {
	    	String timedDate = hrmTimedrecord.getTimedDate();
	    	String cronStr = UtilTool.getDateToQuartzStr(hrmTimedrecord.getTimedType(), timedDate);
	    	TimedScheduler.addJob(JOB_PREFIX_NAME + hrmTimedrecord.getPrimaryKey(), job, cronStr,ctx);
	    }
		
		logger.info("Init timer end.....");
	}

	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param job
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void addJob(String jobName, Job job, String time,ServletContext ctx) throws SchedulerException, ParseException {
		Scheduler sched = sf.getScheduler();
		sched.getContext().put("serContext", ctx);
		JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job.getClass());// 任务名，任务组，任务执行类
		jobDetail.getJobDataMap().put("tid", jobName);
		// 触发器
		CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);// 触发器名,触发器组
		trigger.setCronExpression(time);// 触发器时间设定
		sched.scheduleJob(jobDetail, trigger);
		// 启动
		if (!sched.isShutdown())
			sched.start();
	}
	
	/**
	 * 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param job
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Job job, String time) throws SchedulerException, ParseException {
		Scheduler sched = sf.getScheduler();
		JobDetail jobDetail = new JobDetail(jobName, jobGroupName, job.getClass());// 任务名，任务组，任务执行类
		// 触发器
		CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组
		trigger.setCronExpression(time);// 触发器时间设定
		sched.scheduleJob(jobDetail, trigger);
		if (!sched.isShutdown())
			sched.start();
	}

	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void modifyJobTime(String jobName, String time,ServletContext ctx) throws SchedulerException, ParseException {
		Scheduler sched = sf.getScheduler();
		sched.getContext().put("serContext", ctx);
		Trigger trigger = sched.getTrigger(jobName, TRIGGER_GROUP_NAME);
		if (trigger != null) {
			CronTrigger ct = (CronTrigger) trigger;
			ct.setCronExpression(time);
			sched.resumeTrigger(jobName, TRIGGER_GROUP_NAME);
		}
	}

	/**
	 * 修改一个任务的触发时间
	 * 
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void modifyJobTime(String triggerName, String triggerGroupName, String time) throws SchedulerException, ParseException {
		Scheduler sched = sf.getScheduler();
		Trigger trigger = sched.getTrigger(triggerName, triggerGroupName);
		if (trigger != null) {
			CronTrigger ct = (CronTrigger) trigger;
			// 修改时间
			ct.setCronExpression(time);
			// 重启触发器
			sched.resumeTrigger(triggerName, triggerGroupName);
		}
	}

	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @throws SchedulerException
	 */
	public static void removeJob(String jobName,ServletContext ctx) throws SchedulerException {
		Scheduler sched = sf.getScheduler();
		sched.getContext().put("serContext", ctx);
		sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器
		sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器
		sched.deleteJob(jobName, JOB_GROUP_NAME);// 删除任务
	}

	/**
	 * 移除一个任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @throws SchedulerException
	 */
	public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) throws SchedulerException {
		Scheduler sched = sf.getScheduler();
		sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
		sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
		sched.deleteJob(jobName, jobGroupName);// 删除任务
	}
}
