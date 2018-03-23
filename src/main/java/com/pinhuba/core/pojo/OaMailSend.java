package com.pinhuba.core.pojo;

/**
 * 数据库表名：OaMailSend
 */
public class OaMailSend extends BaseBean implements java.io.Serializable {

   /**
	 * 发件箱（内部邮件）
	 */
   private static final long serialVersionUID = 5870912366788023943L;
   private String oaMailSendSenderid;     // 发件人id
   private String oaMailSendSenderName;   // 发件人姓名
   private String oaMailSendSenddep;      // 发件人单位
   private String oaMailSendEmpids;       // 收件人id集合
   private String oaMailSendEmpCSids;     //抄送人id集合
   private String oaMailSendEmpMSids;     //密送人id集合

   private String oaMailSendEmpNames;       // 收件人姓名集合
   private String oaMailSendEmpCSNames;       // 抄送人姓名集合
   private String oaMailSendEmpMSNames;       // 密送人姓名集合
   
   private String oaMailSendTime;         // 发件时间
   private String oaMailSendSaveTime;         // 存储时间
   private Integer oaMailSendIsurgent;    // 是否紧急
   private String oaMailSendTitle;        // 邮件标题
   private String oaMailSendContent;      // 邮件内容
   private String oaMailSendAffix;        // 附件
   private Integer oaMailSendType;   // 1 已发送  2 草稿箱
   private Integer oaMailInboxReceipt;   // 1 要求回执  2 不要求回执
   private String recordId;				  //修改人ID
   private String recordDate;			  //修改人时间
   private Integer companyId;			// 公司ID

   //默认构造方法
   public OaMailSend(){
      super();
   }

  //get和set方法 
   public String getOaMailSendSenderid(){
      return oaMailSendSenderid;
   }

   public void setOaMailSendSenderid(String aOaMailSendSenderid){
      this.oaMailSendSenderid = aOaMailSendSenderid;
   }

   public String getOaMailSendSenddep(){
      return oaMailSendSenddep;
   }

   public void setOaMailSendSenddep(String aOaMailSendSenddep){
      this.oaMailSendSenddep = aOaMailSendSenddep;
   }

   public String getOaMailSendEmpids(){
      return oaMailSendEmpids;
   }

   public void setOaMailSendEmpids(String aOaMailSendEmpids){
      this.oaMailSendEmpids = aOaMailSendEmpids;
   }

   public String getOaMailSendTime(){
      return oaMailSendTime;
   }

   public void setOaMailSendTime(String aOaMailSendTime){
      this.oaMailSendTime = aOaMailSendTime;
   }

   public Integer getOaMailSendIsurgent(){
      return oaMailSendIsurgent;
   }

   public void setOaMailSendIsurgent(Integer aOaMailSendIsurgent){
      this.oaMailSendIsurgent = aOaMailSendIsurgent;
   }

   public String getOaMailSendTitle(){
      return oaMailSendTitle;
   }

   public void setOaMailSendTitle(String aOaMailSendTitle){
      this.oaMailSendTitle = aOaMailSendTitle;
   }

   public String getOaMailSendContent(){
      return oaMailSendContent;
   }

   public void setOaMailSendContent(String aOaMailSendContent){
      this.oaMailSendContent = aOaMailSendContent;
   }

   public String getOaMailSendAffix(){
      return oaMailSendAffix;
   }

   public void setOaMailSendAffix(String aOaMailSendAffix){
      this.oaMailSendAffix = aOaMailSendAffix;
   }

   public Integer getOaMailSendType(){
      return oaMailSendType;
   }

   public void setOaMailSendType(Integer aOaMailSendType){
      this.oaMailSendType = aOaMailSendType;
   }

   public String getRecordId(){
      return recordId;
   }

   public void setRecordId(String aRecordId){
      this.recordId = aRecordId;
   }

   public String getRecordDate(){
      return recordDate;
   }

   public void setRecordDate(String aRecordDate){
      this.recordDate = aRecordDate;
   }

   public Integer getCompanyId(){
      return companyId;
   }

   public void setCompanyId(Integer aCompanyId){
      this.companyId = aCompanyId;
   }

	public String getOaMailSendSenderName() {
		return oaMailSendSenderName;
	}

	public void setOaMailSendSenderName(String oaMailSendSenderName) {
		this.oaMailSendSenderName = oaMailSendSenderName;
	}

	public String getOaMailSendEmpCSids() {
		return oaMailSendEmpCSids;
	}

	public void setOaMailSendEmpCSids(String oaMailSendEmpCSids) {
		this.oaMailSendEmpCSids = oaMailSendEmpCSids;
	}

	public String getOaMailSendEmpMSids() {
		return oaMailSendEmpMSids;
	}

	public void setOaMailSendEmpMSids(String oaMailSendEmpMSids) {
		this.oaMailSendEmpMSids = oaMailSendEmpMSids;
	}

	public String getOaMailSendSaveTime() {
		return oaMailSendSaveTime;
	}

	public void setOaMailSendSaveTime(String oaMailSendSaveTime) {
		this.oaMailSendSaveTime = oaMailSendSaveTime;
	}

	public String getOaMailSendEmpNames() {
		return oaMailSendEmpNames;
	}

	public void setOaMailSendEmpNames(String oaMailSendEmpNames) {
		this.oaMailSendEmpNames = oaMailSendEmpNames;
	}
	
	public String getOaMailSendEmpCSNames() {
		return oaMailSendEmpCSNames;
	}

	public void setOaMailSendEmpCSNames(String oaMailSendEmpCSNames) {
		this.oaMailSendEmpCSNames = oaMailSendEmpCSNames;
	}

	public String getOaMailSendEmpMSNames() {
		return oaMailSendEmpMSNames;
	}

	public void setOaMailSendEmpMSNames(String oaMailSendEmpMSNames) {
		this.oaMailSendEmpMSNames = oaMailSendEmpMSNames;
	}

	public Integer getOaMailInboxReceipt() {
		return oaMailInboxReceipt;
	}

	public void setOaMailInboxReceipt(Integer oaMailInboxReceipt) {
		this.oaMailInboxReceipt = oaMailInboxReceipt;
	}

}