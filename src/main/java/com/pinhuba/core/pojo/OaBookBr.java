package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_BOOK_BR
 */
public class OaBookBr extends BaseBean implements java.io.Serializable {

	/**
	 * 图书出借归还表
	 */
	private static final long serialVersionUID = 5714689780103079877L;
	private Integer oaBrBookid; // 图书主键
	private String oaBrLendn; // 借书人（内部）
	private String oaBrLendw; // 借书人（外部）
	private String oaBrBdate; // 借书时间
	private String oaBrRdate; // 还书时间
	private String oaBrDate; // 实际还书时间
	private Integer oaBrCount; // 数量
	private String oaBrBooker; // 登记人
	private Integer oaBrStatus; // 书籍状态
	private String oaBrRemark; // 备注
	private String recordId; // 记录人
	private String recordDate; // 记录时间
	private String lastmodiId; // 最后修改人
	private String lastmodiDate; // 最后修改时间
	private Integer companyId; // 公司主键
	private OaBook bookInfo; // 图书对象
	private HrmEmployee bookerEmp; // 登记人对象
	private HrmEmployee lendnEmp; // 借书人（内部）对象

	// 默认构造方法
	public OaBookBr() {
		super();
	}

	// get和set方法
	public Integer getOaBrBookid() {
		return oaBrBookid;
	}

	public void setOaBrBookid(Integer aOaBrBookid) {
		this.oaBrBookid = aOaBrBookid;
	}

	public String getOaBrLendn() {
		return oaBrLendn;
	}

	public void setOaBrLendn(String aOaBrLendn) {
		this.oaBrLendn = aOaBrLendn;
	}

	public String getOaBrLendw() {
		return oaBrLendw;
	}

	public void setOaBrLendw(String aOaBrLendw) {
		this.oaBrLendw = aOaBrLendw;
	}

	public String getOaBrBdate() {
		return oaBrBdate;
	}

	public void setOaBrBdate(String aOaBrBdate) {
		this.oaBrBdate = aOaBrBdate;
	}

	public String getOaBrRdate() {
		return oaBrRdate;
	}

	public void setOaBrRdate(String aOaBrRdate) {
		this.oaBrRdate = aOaBrRdate;
	}

	public String getOaBrDate() {
		return oaBrDate;
	}

	public void setOaBrDate(String aOaBrDate) {
		this.oaBrDate = aOaBrDate;
	}

	public Integer getOaBrCount() {
		return oaBrCount;
	}

	public void setOaBrCount(Integer aOaBrCount) {
		this.oaBrCount = aOaBrCount;
	}

	public String getOaBrBooker() {
		return oaBrBooker;
	}

	public void setOaBrBooker(String aOaBrBooker) {
		this.oaBrBooker = aOaBrBooker;
	}

	public Integer getOaBrStatus() {
		return oaBrStatus;
	}

	public void setOaBrStatus(Integer aOaBrStatus) {
		this.oaBrStatus = aOaBrStatus;
	}

	public String getOaBrRemark() {
		return oaBrRemark;
	}

	public void setOaBrRemark(String aOaBrRemark) {
		this.oaBrRemark = aOaBrRemark;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String aRecordId) {
		this.recordId = aRecordId;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String aRecordDate) {
		this.recordDate = aRecordDate;
	}

	public String getLastmodiId() {
		return lastmodiId;
	}

	public void setLastmodiId(String aLastmodiId) {
		this.lastmodiId = aLastmodiId;
	}

	public String getLastmodiDate() {
		return lastmodiDate;
	}

	public void setLastmodiDate(String aLastmodiDate) {
		this.lastmodiDate = aLastmodiDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

	public OaBook getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(OaBook bookInfo) {
		this.bookInfo = bookInfo;
	}

	public HrmEmployee getBookerEmp() {
		return bookerEmp;
	}

	public void setBookerEmp(HrmEmployee bookerEmp) {
		this.bookerEmp = bookerEmp;
	}

	public HrmEmployee getLendnEmp() {
		return lendnEmp;
	}

	public void setLendnEmp(HrmEmployee lendnEmp) {
		this.lendnEmp = lendnEmp;
	}

}