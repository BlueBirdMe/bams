package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_SUMMARY
 */
public class OaSummary extends BaseBean implements java.io.Serializable {

	/**
	 * 会议纪要
	 */
	private static final long serialVersionUID = -6030339624966004255L;
	private String oaSummaryName; // 纪要名称
	private String oaSummaryDate; // 纪要日期
	private Integer oaSummaryMeetId; // 会议ID
	private String oaSummaryReader; // 纪要访问对象（主键）
	private String oaSummaryContent; // 纪要附件
	private String oaSummaryNeirong;  //纪要内容
	private OaMeetapply oaMeetapply; 
	private HrmEmployee employee;
	private String summaryRecorder;		//纪要人
	private String summaryReaderName;//纪要访问人员名字（页面显示用）
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;
	
	
	// 默认构造方法
	public OaSummary() {
		super();
	}

	// get和set方法
	public String getOaSummaryName() {
		return oaSummaryName;
	}

	public String getOaSummaryNeirong() {
		return oaSummaryNeirong;
	}

	public void setOaSummaryNeirong(String oaSummaryNeirong) {
		this.oaSummaryNeirong = oaSummaryNeirong;
	}

	public void setOaSummaryName(String oaSummaryName) {
		this.oaSummaryName = oaSummaryName;
	}

	public String getOaSummaryDate() {
		return oaSummaryDate;
	}

	public void setOaSummaryDate(String oaSummaryDate) {
		this.oaSummaryDate = oaSummaryDate;
	}

	public String getOaSummaryReader() {
		return oaSummaryReader;
	}

	public void setOaSummaryReader(String oaSummaryReader) {
		this.oaSummaryReader = oaSummaryReader;
	}

	public String getOaSummaryContent() {
		return oaSummaryContent;
	}

	public void setOaSummaryContent(String oaSummaryContent) {
		this.oaSummaryContent = oaSummaryContent;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getLastmodiId() {
		return lastmodiId;
	}

	public void setLastmodiId(String lastmodiId) {
		this.lastmodiId = lastmodiId;
	}

	public String getLastmodiDate() {
		return lastmodiDate;
	}

	public void setLastmodiDate(String lastmodiDate) {
		this.lastmodiDate = lastmodiDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getOaSummaryMeetId() {
		return oaSummaryMeetId;
	}

	public void setOaSummaryMeetId(Integer oaSummaryMeetId) {
		this.oaSummaryMeetId = oaSummaryMeetId;
	}

	public OaMeetapply getOaMeetapply() {
		return oaMeetapply;
	}

	public void setOaMeetapply(OaMeetapply oaMeetapply) {
		this.oaMeetapply = oaMeetapply;
	}

	public String getSummaryRecorder() {
		return summaryRecorder;
	}

	public void setSummaryRecorder(String summaryRecorder) {
		this.summaryRecorder = summaryRecorder;
	}

	public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

	public String getSummaryReaderName() {
		return summaryReaderName;
	}

	public void setSummaryReaderName(String summaryReaderName) {
		this.summaryReaderName = summaryReaderName;
	}

}