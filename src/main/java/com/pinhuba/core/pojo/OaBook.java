package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_BOOK
 */
public class OaBook extends BaseBean implements java.io.Serializable {

	/**
	 * 图书数据表
	 */
	private static final long serialVersionUID = -1293351038788585676L;
	private String oaBookName; // 图书名称
	private Integer oaBookDep; // 所属部门
	private String oaBookCode; // 图书编码
	private Integer oaBookType; // 图书类型
	private String oaBookAuthor; // 作者
	private String oaBookIsbn; // ISBN编号
	private String oaBookConcern; // 出版社
	private String oaPublishDate; // 出版日期
	private String oaBookAddress; // 存放位置
	private Integer oaBookCount; // 总数量
	private Integer oaBookRemain; // 剩余数量
	private Double oaBookPrice; // 单价
	private String oaBuyDate; // 购买日期
	private String oaBookSynopsis; // 内容简介
	private String oaRegistyDate; // 登记时间
	private String oaBookBooker; // 登记人
	private String oaBookRemark; // 备注
	private String oaBookAcce; // 附件
	private String recordId;
	private String recordDate;
	private String lastmodiId;
	private String lastmodiDate;
	private Integer companyId;

	//临时使用
	private HrmEmployee employee;
	private HrmDepartment department;
	private OaBookType bookType;

	// 默认构造方法
	public OaBook() {
		super();
	}

	// 构造方法(手工生成)

	// get和set方法
	public String getOaBookName() {
		return oaBookName;
	}

	public void setOaBookName(String aOaBookName) {
		this.oaBookName = aOaBookName;
	}

	public Integer getOaBookDep() {
		return oaBookDep;
	}

	public void setOaBookDep(Integer aOaBookDep) {
		this.oaBookDep = aOaBookDep;
	}

	public String getOaBookCode() {
		return oaBookCode;
	}

	public void setOaBookCode(String aOaBookCode) {
		this.oaBookCode = aOaBookCode;
	}

	public Integer getOaBookType() {
		return oaBookType;
	}

	public void setOaBookType(Integer aOaBookType) {
		this.oaBookType = aOaBookType;
	}

	public String getOaBookAuthor() {
		return oaBookAuthor;
	}

	public void setOaBookAuthor(String aOaBookAuthor) {
		this.oaBookAuthor = aOaBookAuthor;
	}

	public String getOaBookIsbn() {
		return oaBookIsbn;
	}

	public void setOaBookIsbn(String aOaBookIsbn) {
		this.oaBookIsbn = aOaBookIsbn;
	}

	public String getOaBookConcern() {
		return oaBookConcern;
	}

	public void setOaBookConcern(String aOaBookConcern) {
		this.oaBookConcern = aOaBookConcern;
	}

	public String getOaPublishDate() {
		return oaPublishDate;
	}

	public void setOaPublishDate(String aOaPublishDate) {
		this.oaPublishDate = aOaPublishDate;
	}

	public String getOaBookAddress() {
		return oaBookAddress;
	}

	public void setOaBookAddress(String aOaBookAddress) {
		this.oaBookAddress = aOaBookAddress;
	}

	public Integer getOaBookCount() {
		return oaBookCount;
	}

	public void setOaBookCount(Integer aOaBookCount) {
		this.oaBookCount = aOaBookCount;
	}

	public Integer getOaBookRemain() {
		return oaBookRemain;
	}

	public void setOaBookRemain(Integer aOaBookRemain) {
		this.oaBookRemain = aOaBookRemain;
	}

	public Double getOaBookPrice() {
		return oaBookPrice;
	}

	public void setOaBookPrice(Double aOaBookPrice) {
		this.oaBookPrice = aOaBookPrice;
	}

	public String getOaBuyDate() {
		return oaBuyDate;
	}

	public void setOaBuyDate(String aOaBuyDate) {
		this.oaBuyDate = aOaBuyDate;
	}

	public String getOaBookSynopsis() {
		return oaBookSynopsis;
	}

	public void setOaBookSynopsis(String aOaBookSynopsis) {
		this.oaBookSynopsis = aOaBookSynopsis;
	}

	public String getOaRegistyDate() {
		return oaRegistyDate;
	}

	public void setOaRegistyDate(String aOaRegistyDate) {
		this.oaRegistyDate = aOaRegistyDate;
	}

	public String getOaBookBooker() {
		return oaBookBooker;
	}

	public void setOaBookBooker(String aOaBookBooker) {
		this.oaBookBooker = aOaBookBooker;
	}

	public String getOaBookRemark() {
		return oaBookRemark;
	}

	public void setOaBookRemark(String aOaBookRemark) {
		this.oaBookRemark = aOaBookRemark;
	}

	public String getOaBookAcce() {
		return oaBookAcce;
	}

	public void setOaBookAcce(String aOaBookAcce) {
		this.oaBookAcce = aOaBookAcce;
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

	public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

	public HrmDepartment getDepartment() {
		return department;
	}

	public void setDepartment(HrmDepartment department) {
		this.department = department;
	}

	public OaBookType getBookType() {
		return bookType;
	}

	public void setBookType(OaBookType bookType) {
		this.bookType = bookType;
	}

}