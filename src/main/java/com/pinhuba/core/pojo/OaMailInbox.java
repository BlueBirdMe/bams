package com.pinhuba.core.pojo;

/**
 * 数据库表名：OaMailInbox
 */
public class OaMailInbox extends BaseBean implements java.io.Serializable {

   /**
	 * 收件箱（内部邮件）
	 */
   private static final long serialVersionUID = 4395490795358724039L;
   private String oaMailInboxSendid;   //发件人id 
   private String oaMailInboxSendName;   //发件人姓名
   private String oaMailInboxSenderdep; //发件人部门id
   private String oaMailInboxSendtime;  //发件时间
   private Integer oaMailInboxIsurgent; //是否紧急
   private String oaMailInboxTitle;     //邮件标题
   private String oaMailInboxContent;   //邮件内容
   private String oaMailInboxAffix;     //附件
   private String oaMailInboxIntime;    //收件时间
   
   private String oaMailInboxEmpids;       // 收件人id集合
   private String oaMailInboxEmpCSids;     //抄送人id集合
   
   private String oaMailInboxEmpNames;       // 收件人姓名集合
   private String oaMailInboxEmpCSNames;       // 抄送人姓名集合
   private String oaMailInboxEmpMSNames;       // 密送人姓名集合
   private Integer oaMailInboxReceipt;   // 1 要求回执  2 不要求回执
   private String recordId;
   private String recordDate;
   private Integer companyId;

   //默认构造方法
   public OaMailInbox(){
      super();
   }

  //get和set方法
   public String getOaMailInboxSendid(){
      return oaMailInboxSendid;
   }

   public void setOaMailInboxSendid(String aOaMailInboxSendid){
      this.oaMailInboxSendid = aOaMailInboxSendid;
   }

   public String getOaMailInboxSenderdep(){
      return oaMailInboxSenderdep;
   }

   public void setOaMailInboxSenderdep(String aOaMailInboxSenderdep){
      this.oaMailInboxSenderdep = aOaMailInboxSenderdep;
   }

   public String getOaMailInboxSendtime(){
      return oaMailInboxSendtime;
   }

   public void setOaMailInboxSendtime(String aOaMailInboxSendtime){
      this.oaMailInboxSendtime = aOaMailInboxSendtime;
   }

   public Integer getOaMailInboxIsurgent(){
      return oaMailInboxIsurgent;
   }

   public void setOaMailInboxIsurgent(Integer aOaMailInboxIsurgent){
      this.oaMailInboxIsurgent = aOaMailInboxIsurgent;
   }

   public String getOaMailInboxTitle(){
      return oaMailInboxTitle;
   }

   public void setOaMailInboxTitle(String aOaMailInboxTitle){
      this.oaMailInboxTitle = aOaMailInboxTitle;
   }

   public String getOaMailInboxContent(){
      return oaMailInboxContent;
   }

   public void setOaMailInboxContent(String aOaMailInboxContent){
      this.oaMailInboxContent = aOaMailInboxContent;
   }

   public String getOaMailInboxAffix(){
      return oaMailInboxAffix;
   }

   public void setOaMailInboxAffix(String aOaMailInboxAffix){
      this.oaMailInboxAffix = aOaMailInboxAffix;
   }

   public String getOaMailInboxIntime(){
      return oaMailInboxIntime;
   }

   public void setOaMailInboxIntime(String aOaMailInboxIntime){
      this.oaMailInboxIntime = aOaMailInboxIntime;
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

	public String getOaMailInboxSendName() {
		return oaMailInboxSendName;
	}

	public void setOaMailInboxSendName(String oaMailInboxSendName) {
		this.oaMailInboxSendName = oaMailInboxSendName;
	}

	public String getOaMailInboxEmpNames() {
		return oaMailInboxEmpNames;
	}

	public void setOaMailInboxEmpNames(String oaMailInboxEmpNames) {
		this.oaMailInboxEmpNames = oaMailInboxEmpNames;
	}

	public String getOaMailInboxEmpCSNames() {
		return oaMailInboxEmpCSNames;
	}

	public void setOaMailInboxEmpCSNames(String oaMailInboxEmpCSNames) {
		this.oaMailInboxEmpCSNames = oaMailInboxEmpCSNames;
	}

	public String getOaMailInboxEmpMSNames() {
		return oaMailInboxEmpMSNames;
	}

	public void setOaMailInboxEmpMSNames(String oaMailInboxEmpMSNames) {
		this.oaMailInboxEmpMSNames = oaMailInboxEmpMSNames;
	}

	public String getOaMailInboxEmpids() {
		return oaMailInboxEmpids;
	}

	public void setOaMailInboxEmpids(String oaMailInboxEmpids) {
		this.oaMailInboxEmpids = oaMailInboxEmpids;
	}

	public String getOaMailInboxEmpCSids() {
		return oaMailInboxEmpCSids;
	}

	public void setOaMailInboxEmpCSids(String oaMailInboxEmpCSids) {
		this.oaMailInboxEmpCSids = oaMailInboxEmpCSids;
	}

	public Integer getOaMailInboxReceipt() {
		return oaMailInboxReceipt;
	}

	public void setOaMailInboxReceipt(Integer oaMailInboxReceipt) {
		this.oaMailInboxReceipt = oaMailInboxReceipt;
	}

}