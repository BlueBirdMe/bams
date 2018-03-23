package com.pinhuba.core.pojo;

/**
 * 数据库表名：SYS_MSG
 */
public class SysMsg extends BaseBean implements java.io.Serializable {

	/**
	 * 系统公告数据表
	 */
	private static final long serialVersionUID = -2432664504094108108L;
	private String msgTitle;		//公告标题
	private String msgContext;		//公告内容
	private String msgDate;			//发布日期
	private String msgVsdate;		//有效期开始日期
	private String msgVedate;		//有效期结束日期
	private String msgPerson;		//录入人
    private Integer msgIsEffective;	//是否有效

	// 默认构造方法
	public SysMsg() {
		super();
	}

	// get和set方法
	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String aMsgTitle) {
		this.msgTitle = aMsgTitle;
	}

	public String getMsgContext() {
		return msgContext;
	}

	public void setMsgContext(String aMsgContext) {
		this.msgContext = aMsgContext;
	}

	public String getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(String aMsgDate) {
		this.msgDate = aMsgDate;
	}

	public String getMsgVsdate() {
		return msgVsdate;
	}

	public void setMsgVsdate(String aMsgVsdate) {
		this.msgVsdate = aMsgVsdate;
	}

	public String getMsgVedate() {
		return msgVedate;
	}

	public void setMsgVedate(String aMsgVedate) {
		this.msgVedate = aMsgVedate;
	}

	public String getMsgPerson() {
		return msgPerson;
	}

	public void setMsgPerson(String aMsgPerson) {
		this.msgPerson = aMsgPerson;
	}

	public Integer getMsgIsEffective() {
		return msgIsEffective;
	}

	public void setMsgIsEffective(Integer msgIsEffective) {
		this.msgIsEffective = msgIsEffective;
	}

}