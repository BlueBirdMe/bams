package com.pinhuba.common.util;

import java.util.HashMap;
import java.util.Map;
public class EnumUtil {
		
	/**
	 * 申请状态
	 */
	public enum APPLY_STATUS {
		DRAFT(1, "草稿"), DOING(2, "办理中"), FINISH(3, "已结束");

		public int value;
		public String valueName;

		private APPLY_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}
		
		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}
	}	

	
	/**
	 * 任务状态
	 */
	public enum TASK_STATUS {
		DOING(1, "办理中"), FINISH(2, "已办结");

		public int value;
		public String valueName;

		private TASK_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}
		
		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}
	}
	
	
	/**
	 * 流程状态
	 */
	public enum PROCESS_STATUS {
			DOING(1, "办理中"), FINISH(2, "已结束");

			public int value;
			public String valueName;

			private PROCESS_STATUS(int value, String valueName) {
				this.value = value;
				this.valueName = valueName;
			}
			
			public static String valueOf(int a) {
				
				String tmp = "";
				for (int i = 0; i < values().length; i++) {
					if (values()[i].value == a) {
						tmp = values()[i].valueName;
						break;
					}
				}
				return tmp;
			}
			
			public static String getSelectAndText(String addStr) {
				String tmp = "";
				if (addStr != null && addStr.length() > 0) {
					tmp = addStr + "|";
				}
				for (int i = 0; i < values().length; i++) {
					String vn = values()[i].valueName;
					int key = values()[i].value;
					tmp += key + "," + vn + "|";
				}
				if (tmp != null && tmp.length() > 0)
					return tmp.substring(0, tmp.length() - 1);
				else
					return tmp;
			}
		}
	
	
	/**
	 * 出差流程中各步骤ID
	 * 需要和流程图定义的ID相同
	 */
	public enum TRSVEL_TASK {
		DEPT_LEADER_AUDIT("deptLeaderAudit", "部门领导审批"),
		MODIFY_APPLY("modifyApply", "调整申请"),
		HR_AUDIT("hrAudit", "人事审批");

		public String key;
		public String name;

		private TRSVEL_TASK(String key, String name) {
			this.key = key;
			this.name = name;
		}
		
		public static String getName(String a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (a.equals(values()[i].key)) {
					tmp = values()[i].name;
					break;
				}
			}
			return tmp;
		}
	}
	
	/**
	 * 请假流程中各个任务ID、名称
	 * 需要和流程图定义的ID相同
	 */
	public enum LEAVE_TASK {
		DEPT_LEADER_AUDIT("deptLeaderAudit", "部门领导审批"),
		SUBMIT_OR_MODIFY("submitOrModify", "提交/修改申请"),
		HR_AUDIT("hrAudit", "人事审批");

		public String key;
		public String name;

		private LEAVE_TASK(String key, String name) {
			this.key = key;
			this.name = name;
		}
		
		public static String getName(String a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (a.equals(values()[i].key)) {
					tmp = values()[i].name;
					break;
				}
			}
			return tmp;
		}
	}
	
	/**
	 * 流程种类定义
	 */
	public enum WORKFLOW_TYPE {
		LEAVE("leave", "请假流程"),TRSVEL("trsvel", "出差流程");

		public String key;
		public String valueName;

		private WORKFLOW_TYPE(String key, String valueName) {
			this.key = key;
			this.valueName = valueName;
		}
		
		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				String key = values()[i].key;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	/**
	 * 流程范围
	 */
	public enum WORKFLOW_SCOPE {
		MYSTART(1, "我发起的"), MYJOIN(2, "我经办的");

		public int value;
		public String valueName;

		private WORKFLOW_SCOPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}
		
		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	/**
	 * 流程定义是否挂起，和源码中定义相同
	 * SuspensionState ACTIVE = new SuspensionStateImpl(1, "active");
  	 * SuspensionState SUSPENDED = new SuspensionStateImpl(2, "suspended");
	 * 
	 * @author Administrator 1 有效 2无效
	 */
	public enum SUSPENSION_STATE {
		ACTIVE(1, "未挂起"), SUSPENDED(2, "已挂起");

		public int value;
		public String valueName;

		private SUSPENSION_STATE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}
		
		
		public static String valueOf(int a) {
			
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	
	/**
	 * 功能菜单是否有效
	 * 
	 * @author Administrator 1 有效 2无效
	 */
	public enum SYS_ISACTION {
		Vaild(1, "有效"), No_Vaild(2, "无效");

		public int value;
		public String valueName;

		private SYS_ISACTION(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}
		
		
		public static String valueOf(int a) {
			
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 个人桌面
	 * @author peng.ning
	 * @date   Mar 31, 2010
	 */
	public enum OA_DESKTOP_TYPE{
		Weather(1,"天气预报"),
		Timer(2,"定时提醒"),
		Notes(3,"个人便签"),
		Regular(4,"规章制度"),
		Post(5,"公司公告"),
		Notice(6,"公司通知"),
		Vote(7,"公司投票"),
		Notepad(8,"公司记事"),
		Approve(9,"待办工作"),
		Apply(10,"我的申请"),
		Schedule(11,"日程安排"),
		Sms(12,"我的短信"),
		Mail(13,"我的邮件");
		
		public int value;
		public String valueName;

		private OA_DESKTOP_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static Map<Integer, String> getValuesMap() {
			Map<Integer,String> map = new HashMap<Integer, String>();
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				map.put(key, vn);
			}
			return map;
		}
	}
	
	/**
	 * 个人桌面是否必须显示
	 * @author peng.ning
	 * @date   Mar 31, 2010
	 */
	public enum OA_DESKTOP_TYPE_ISMUST{
		Weather(1,false),
		Timer(2,false),
		Notes(3,false),
		Regular(4,false),
		Post(5,false),
		Notice(6,false),
		Vote(7,false),
		Notepad(8,false),
		Approve(9,false),
		APPly(10,false),
		Schedule(11,false),
		Sms(12,false),
		Mail(13,false);
		
		public int value;
		public boolean valueName;

		private OA_DESKTOP_TYPE_ISMUST(int value, boolean valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static boolean valueOf(int a) {
			boolean tmp = false;
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}
	}
	/**
	 * 邮件类型
	 * @author peng.ning
	 * @date   Apr 26, 2010
	 */
	public enum OA_MAIL_STATUS{
		APPECT(1, "收件"), DELETE(2, "删除");

		public int value;
		public String valueName;

		private OA_MAIL_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	
	/**
	 * 系统异常状态
	 * 
	 * @author Administrator 1 未处理 2已处理
	 */
	public enum SYS_EXCEPTION_STATUS {
		Vaild(1, "未处理"), No_Vaild(2, "已处理");

		public int value;
		public String valueName;

		private SYS_EXCEPTION_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	/**
	 * 系统运行参数
	 * 
	 * @author Administrator 1 普通文本框 2数字输入框 3下拉框
	 */
	public enum SYS_PARAM_TYPE {
		TEXT(1, "普通文本框"), NUM(2, "数字输入框"),SELECT(3,"下拉选择框");

		public int value;
		public String valueName;

		private SYS_PARAM_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	public enum HRM_EMPLOYEE_SEX{
		Man(1,"男"),Woman(2,"女");
		
		public int value;
		public String valueName;
		
		private HRM_EMPLOYEE_SEX(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	
	/**
	 * 类型编号
	 * 
	 * @author 
	 */
	public enum OA_TYPE{
		WARW(1, "知识仓库"), FORMS(2, "常用表格");

		public int value;
		public String valueName;

		private OA_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}
	}
	
	
	/**
	 * 功能菜单是否有效
	 * 
	 * @author Administrator 1 是 2否
	 */
	public enum SYS_ISEDIT{
		EDIT(1, "是"), No_EDIT(2, "否");

		public int value;
		public String valueName;

		private SYS_ISEDIT(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}


	/**
	 * 人员状态
	 * 
	 * @author Administrator 1试用 2正常 3离职
	 */
	public enum HRM_EMPLOYEE_STATUS {
		Trial(1, "试用"), Official(2, "正式"),Separation(3,"离职");

		public int value;
		public String valueName;

		private HRM_EMPLOYEE_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}
		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	
	/**
	 * 合同状态
	 * 
	 */
	public enum CONTRACT_STATUS {
		YX(1, "有效"), ZZ(2, "中止"),GQ(3,"过期");

		public int value;
		public String valueName;

		private CONTRACT_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}
		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 合同期限类型
	 * 
	 */
	public enum CONTRACT_LIMIT_TYPE {
		GD(1, "固定期限"), WGD(2, "无固定期限");

		public int value;
		public String valueName;

		private CONTRACT_LIMIT_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}
		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	
	/**
	 * 手机短信类型，
	 * 
	 * @author Administrator 1 个人短信 2 提醒短信
	 */
	public enum OA_SMS_TYPE {
		one(1, "个人短信"), two(2, "提醒短信");

		public int value;
		public String valueName;

		private OA_SMS_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 手机收件箱短信状态，
	 * 
	 * @author Administrator 1 已读 2 未读
	 */
	public enum OA_SMS_INBOX_ISREAD {
		one(1, "已读"), two(2, "未读");

		public int value;
		public String valueName;

		private OA_SMS_INBOX_ISREAD(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 手机收件箱短信状态，
	 * 
	 * @author Administrator 3未读 4已读
	 */
	public enum OA_SMS_RECEIVE_STATUS {
		UNREAD(3, "未读"), READED(4, "已读");

		public int value;
		public String valueName;

		private OA_SMS_RECEIVE_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 所有消息类的状态类型，
	 * 
	 * @author Administrator 1 有效 2 无效
	 */
	public enum OA_ISSUEINFO_STATUS {
		EFFECT(1, "有效"), FAILURE(2, "无效");

		public int value;
		public String valueName;

		private OA_ISSUEINFO_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}


	/**
	 * 通知类型，
	 * 
	 * @author Administrator 1紧急, 2一般, 3不紧急
	 */
	public enum OA_NOTICE_TYPE {
		EMERGENCY(1, "紧急"), GENERAL(2, "一般"), UNEMERGENCY(3, "不紧急");

		public int value;
		public String valueName;

		private OA_NOTICE_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}



	/**
	 * 公司记事重要级，
	 * 
	 * @author Administrator 1重要, 2 普通
	 */
	public enum OA_ADVERSARIA_LEVEL_TYPE {
		MID(2, "普通"), HIGH(1, "重要");

		public int value;
		public String valueName;

		private OA_ADVERSARIA_LEVEL_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 投票选项类型，
	 * 
	 * @author Administrator 1单选, 2多选
	 */
	public enum OA_VOTE_OPTIONS_TYPE {
		RADIO(1, "单选"), CHECKBOX(2, "多选");

		public int value;
		public String valueName;

		private OA_VOTE_OPTIONS_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	/**
	 * 投票查看类型，
	 * 
	 * @author Administrator 1投票后查看, 2投票前查看, 3不允许查看, 4终止后查看
	 */
	public enum OA_VOTE_VIEW_TYPE {
		AFTER(1, "投票后查看"), PREVIOUS(2, "投票前查看"), NO(3, "不允许查看"), LAST(4, "终止后查看");

		public int value;
		public String valueName;

		private OA_VOTE_VIEW_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 是否匿名投票，
	 * 
	 * @author Administrator 1是, 2否
	 */
	public enum OA_VOTE_IS_ANONYMOUS {
		YES(1, "是"), NO(2, "否");

		public int value;
		public String valueName;

		private OA_VOTE_IS_ANONYMOUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 投票状态
	 * 
	 * @author Administrator 1未开始, 2投票中,3已结束
	 */
	public enum OA_VOTE_STATUS {
		NOSTART(1, "未开始"), VOTING(2, "投票中"),END(3,"已结束");

		public int value;
		public String valueName;

		private OA_VOTE_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 通讯手册是否共享
	 * 
	 * @author Administrator 1共享, 2私有
	 */
	public enum OA_COMMUNICATION_IS_SHARE {
		SHARE(1, "共享"), UNSHARE(2, "私有");

		public int value;
		public String valueName;

		private OA_COMMUNICATION_IS_SHARE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 角色绑定类型
	 * 
	 * @author Administrator 1 用户 2 部门 3 岗位 4 用户组
	 */
	public enum SYS_ROLE_BIND_TYPE {
		BIND_USER(1, "用户"), BIND_DEPT(2, "部门"), BIND_POST(3, "岗位"), BIND_GROUP(4, "用户组");

		public int value;
		public String valueName;

		private SYS_ROLE_BIND_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 公司状态
	 * 
	 * @author Administrator 1 申请中 2 已处理
	 */
	public enum SYS_COMPANY_STATUS{
		APPROVE(1, "申请中"), TAKE(2, "已处理");

		public int value;
		public String valueName;

		private SYS_COMPANY_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	/**
	 * 公司类型
	 * 
	 * @author Administrator 1 申请客户 2 试用客户 3 正式客户 4 系统管理账户
	 */
	public enum SYS_COMPANY_TYPE {
		APPROVE(1, "申请"), TRIAL(2, "试用"), OFFICIAL(3, "正式"), SYSTEM(4, "系统管理");

		public int value;
		public String valueName;

		private SYS_COMPANY_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	/**
	 * 下拉列表公司类型
	 * 
	 * @author Administrator  2 试用客户 3 正式客户
	 */
	public enum SYS_COMPANY_TYPE_SEL {
		TRIAL(2, "试用"), OFFICIAL(3, "正式");

		public int value;
		public String valueName;

		private SYS_COMPANY_TYPE_SEL(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 用户类型
	 * 
	 * @author Administrator 1 普通用户 2 测试账户 3 系统管理账户
	 */
	public enum SYS_USER_TYPE {
		DEFAULT(1, "普通"), TSET(2, "测试账户"), SYSTEM(3, "系统管理");

		public int value;
		public String valueName;

		private SYS_USER_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/** 
	 * 用户类型   -1 顶级，1 一级，2 二级，3 三级
	 * 
	 * @author Administrator
	 */
	public enum SYS_METHOD_LEVEL {
		TOP(-1, "顶级"), ONE(1, "一级"), TWO(2, "二级"), THREE(3, "三级");

		public int value;
		public String valueName;

		private SYS_METHOD_LEVEL(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 知识类型
	 * 
	 * @author Administrator
	 */
	public enum OA_WARE_TYPE {
		PRODUCT(1, "产品知识"), SALES(2, "销售经验");

		public int value;
		public String valueName;

		private OA_WARE_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	

	/**
	 * 日志类型
	 * 
	 * @author Administrator 1 工作日志 2 个人日志
	 * 
	 */
	public enum OA_WORKLOG_TYPE {
		Work(1, "工作日志"), Project(2, "个人日志");

		public int value;
		public String valueName;

		private OA_WORKLOG_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		/**
		 * 获取下拉框、替换颜色
		 * 
		 * @param addStr
		 *            select下拉库附加选项 可以为null
		 * @param map
		 *            替换文本显示颜色 可以为null
		 * @return
		 */
		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 日志范围
	 * 
	 * @author Administrator 1 私有 2 共享
	 * 
	 */
	public enum OA_WORKLOG_RANGE {
		one(1, "私有"), two(2, "共享");

		public int value;
		public String valueName;

		private OA_WORKLOG_RANGE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		/**
		 * 获取下拉框、替换颜色
		 * 
		 * @param addStr
		 *            select下拉库附加选项 可以为null
		 * @param map
		 *            替换文本显示颜色 可以为null
		 * @return
		 */
		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 日程优先级
	 * 
	 * @author Administrator 1 重要—紧急 2 重要—不紧急 3 不重要—紧急 4 不重要—不紧急
	 * 
	 */
	public enum OA_CALENDER_LEVEL {
		four(4, "一般"), three(3, "紧急"),two(2, "重要"), one(1, "重要且紧急") ;

		public int value;
		public String valueName;

		private OA_CALENDER_LEVEL(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		/**
		 * 获取下拉框、替换颜色
		 * 
		 * @param addStr
		 *            select下拉库附加选项 可以为null
		 * @param map
		 *            替换文本显示颜色 可以为null
		 * @return
		 */
		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}


	/**
	 * 日程完成状态
	 * 
	 * @author Administrator 1 已完成 2 未完成
	 * 
	 */
	public enum OA_CALENDER_STATUS {
		one(1, "已完成"), two(2, "未完成");

		public int value;
		public String valueName;

		private OA_CALENDER_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		/**
		 * 获取下拉框、替换颜色
		 * 
		 * @param addStr
		 *            select下拉库附加选项 可以为null
		 * @param map
		 *            替换文本显示颜色 可以为null
		 * @return
		 */
		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 请假类型
	 * 
	 * @author Administrator 1 事假 2 病假 3 产假
	 * 
	 */
	public enum OA_PERSONAL_LEAVE_TYPE {
		One(1, "事假"), Two(2, "病假"), Three(3, "产假");

		public int value;
		public String valueName;

		private OA_PERSONAL_LEAVE_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		/**
		 * 获取下拉框、替换颜色
		 * 
		 * @param addStr
		 *            select下拉库附加选项 可以为null
		 * @param map
		 *            替换文本显示颜色 可以为null
		 * @return
		 */
		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	
	
	/**
	 * 会议状态
	 * 
	 * @author Administrator
	 */
	public enum OA_MEETAPPLY_STATUS {
		APPLYING(1, "未开始"), PROCESSING(2, "进行中"), COMPLETE(3, "已结束");

		public int value;
		public String valueName;

		private OA_MEETAPPLY_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 会议重要程度
	 * 
	 * @author Administrator
	 */
	public enum OA_MEET_TYPE {
		ONE(1, "一般"), TWO(2, "重要");

		public int value;
		public String valueName;

		private OA_MEET_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 会议室状态
	 * 
	 * @author Administrator
	 */
	public enum OA_ROOM_STATUS {
		Use(1, "占用"), Free(2, "空闲");

		public int value;
		public String valueName;

		private OA_ROOM_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	/**
	 * 图书类别
	 * 
	 * @author Administrator
	 */
	public enum OA_BOOK_TYPE {
		APPLYING(1, "计算机"), PROCESSING(2, "科学");

		public int value;
		public String valueName;

		private OA_BOOK_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 图书状态   1 已借出，2 已归还
	 * 
	 * @author Administrator
	 */
	public enum OA_BOOKBR_STATUS {
		LEND(1, "已借出"), BACK(2, "已归还");

		public int value;
		public String valueName;

		private OA_BOOKBR_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 当前状态
	 * 
	 * @author Administrator
	 */
	public enum OA_CAR_STATUS {
		BOOKED(1, "可用"),INUSE(2, "在用");

		public int value;
		public String valueName;

		private OA_CAR_STATUS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}

	/**
	 * 车辆本身状态
	 * 
	 * @author Administrator
	 */
	public enum OA_CAR_STA {
		GOOD(1, "正常"),FAIL(2, "已报废");

		public int value;
		public String valueName;

		private OA_CAR_STA(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	

	/**
	 * 论坛帖子类型    1，精华帖   2，普通帖
	 * 
	 * @author Administrator
	 */
	public enum OA_POSTS_IS_BOUTIQUE {
		BOUTIQUE(1, "精华帖"), UNBOUTIQUE(2, "普通帖");

		public int value;
		public String valueName;

		private OA_POSTS_IS_BOUTIQUE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	/**
	 * 区分帖子和回复    1，帖子   2，回复
	 * 
	 * @author Administrator
	 */
	public enum OA_IS_POSTS {
		POSTS(1, "帖子"), REG(2, "回复");

		public int value;
		public String valueName;

		private OA_IS_POSTS(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	
	}
	
	/**
	 * 1 回复 2 全部回复 3 重发 4 转发 5 在线人员
	 * 
	 */
	public enum OA_MAIL_MAILTYPE{
		ONE(1, "回复"), TWO(2, "全部回复"), THREE(3, "重发"),FOUR(4,"转发"),FIVE(5,"在线");
		public int value;
		public String valueName;
		private OA_MAIL_MAILTYPE(int value,String valueName){
			this.value = value;
			this.valueName = valueName;
		}
		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}
	}
	/**
	 * 1 要求回执  2 不要求回执
	 *
	 */
	public enum OA_MAIL_RECEIPT{
		ONE(1,"要求回执"),TWO(2,"不要求回执");
		public int value;
		public String valueName;
		private OA_MAIL_RECEIPT(int value,String valueName){
			this.value = value;
			this.valueName = valueName;
		}
		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}
	}
	
	/**
	 * 定时类型
	 * 
	 * @author Administrator 0 每天 1 一次
	 */
	public enum TIMED_TYPE {
		Vaild(0, "每天"), No_Vaild(1, "一次");

		public int value;
		public String valueName;

		private TIMED_TYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}
		
		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	/**
	 *投票查看类型
	 * 
	 * @author Administrator 
	 */
	public enum VOTINHSELECTTYPE {
		AFTER(1, "投票后查看"), FORMER(2, "投票前查看"),NOTSELECT(3,"不允许查看"),FINALLYSELECT(4,"终止后查看");

		public int value;
		public String valueName;

		private VOTINHSELECTTYPE(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	/**
	 *是否匿名投票
	 * 
	 * @author Administrator 
	 */
	public enum ANONYMOUSVOTINH {
		YES(1, "是"), NO(2, "否");

		public int value;
		public String valueName;

		private ANONYMOUSVOTINH(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	/**
	 *投票选项类型
	 * 
	 * @author Administrator 
	 */
	public enum VOTINHSELECT {
		SINGLE(1, "单选"), MULTIPLE(2, "多选");

		public int value;
		public String valueName;

		private VOTINHSELECT(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	/**
	 * 树显示顺序移动
	 * @author peng.ning
	 * @date   Jul 6, 2010
	 */
	public enum Tree_MOVE_Type{
		MOVE_UP(1,"向上移动"),MOVE_DOWN(2,"向下移动");
		public int value;
		public String valueName;

		private Tree_MOVE_Type(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}
	}
	
	/**
	 * 外部邮箱类型
	 * @author peng.ning
	 * @date   Jul 12, 2010
	 */
	public enum Net_Mail_Type{
		Send(1,"发送"),Accp(2,"接收"),Other(3,"其他");
		public int value;
		public String valueName;

		private Net_Mail_Type(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}
	}
	
	/**
	 * 发件箱存放邮件类型
	 * @author peng.ning
	 * @date   Jul 13, 2010
	 */
	public enum Net_Mail_SendBox_Type{
		Send(1,"发件箱"),Sketch(2,"草稿箱");
		public int value;
		public String valueName;

		private Net_Mail_SendBox_Type(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}
	}
	
	/**
	 *功能菜单是否默认
	 *  1 是, 0 否
	 * @author Mask
	 * @version Apr 1, 2011 9:59:48 AM
	 */
	public enum SYS_IS_DEFAULT {
		YES(1, "是"), NO(0, "否");

		public int value;
		public String valueName;

		private SYS_IS_DEFAULT(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else
				return tmp;
		}
	}
	
	/**
	 * 新闻重要级  1 重要，2 普通
	 * 
	 * @author Administrator
	 */
	public enum OA_NEWS_ISTOP {
		NO(2, "普通"), YES(1, "重要");

		public int value;
		public String valueName;

		private OA_NEWS_ISTOP(int value, String valueName) {
			this.value = value;
			this.valueName = valueName;
		}

		public static String valueOf(int a) {
			String tmp = "";
			for (int i = 0; i < values().length; i++) {
				if (values()[i].value == a) {
					tmp = values()[i].valueName;
					break;
				}
			}
			return tmp;
		}

		public static String getSelectAndText(String addStr) {
			String tmp = "";
			if (addStr != null && addStr.length() > 0) {
				tmp = addStr + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else{
				return tmp;
			}
		}
		
		public static String getRadioAndText(String str){
			String tmp = "";
			if (str != null && str.length() > 0) {
				tmp = str + "|";
			}
			for (int i = 0; i < values().length; i++) {
				String vn = values()[i].valueName;
				int key = values()[i].value;
				tmp += key + "," + vn + "|";
			}
			if (tmp != null && tmp.length() > 0)
				return tmp.substring(0, tmp.length() - 1);
			else{
				return tmp;
			}
		}
	}
}
