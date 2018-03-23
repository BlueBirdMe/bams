package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_SMS_SEND 发件箱（短信）
 */
public class OaSmsSend extends BaseBean implements java.io.Serializable {

   private static final long serialVersionUID = -8424523208939267082L;
   private String oaSmsSendEmp;     //发件人id
   private String oaSmsSendEmpName;     //发件人姓名
   private String oaSmsSendAcpemp;  //收件人id集合
   private String oaSmsSendAcpempName;  //收件人姓名集合
   private String oaSmsSendTime;    //发件时间
   private String oaSmsSendContent; //发件内容
   private Integer oaSmsType;       //发件类型
   private String recordId;			//修改人ID
   private String recordDate;		//修改时间
   private Integer companyId;		//公司时间

   //默认构造方法
   public OaSmsSend(){
      super();
   }

  //get和set方法
   public String getOaSmsSendEmp(){
      return oaSmsSendEmp;
   }

   public void setOaSmsSendEmp(String aOaSmsSendEmp){
      this.oaSmsSendEmp = aOaSmsSendEmp;
   }

   public String getOaSmsSendAcpemp(){
      return oaSmsSendAcpemp;
   }

   public void setOaSmsSendAcpemp(String aOaSmsSendAcpemp){
      this.oaSmsSendAcpemp = aOaSmsSendAcpemp;
   }

   public String getOaSmsSendTime(){
      return oaSmsSendTime;
   }

   public void setOaSmsSendTime(String aOaSmsSendTime){
      this.oaSmsSendTime = aOaSmsSendTime;
   }

   public String getOaSmsSendContent(){
      return oaSmsSendContent;
   }

   public void setOaSmsSendContent(String aOaSmsSendContent){
      this.oaSmsSendContent = aOaSmsSendContent;
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

/**
 * @return the oaSmsType
 */
public Integer getOaSmsType() {
	return oaSmsType;
}

/**
 * @param oaSmsType the oaSmsType to set
 */
public void setOaSmsType(Integer oaSmsType) {
	this.oaSmsType = oaSmsType;
}

/**
 * @return the oaSmsSendAcpempName
 */
public String getOaSmsSendAcpempName() {
	return oaSmsSendAcpempName;
}

/**
 * @param oaSmsSendAcpempName the oaSmsSendAcpempName to set
 */
public void setOaSmsSendAcpempName(String oaSmsSendAcpempName) {
	this.oaSmsSendAcpempName = oaSmsSendAcpempName;
}

/**
 * @return the oaSmsSendEmpName
 */
public String getOaSmsSendEmpName() {
	return oaSmsSendEmpName;
}

/**
 * @param oaSmsSendEmpName the oaSmsSendEmpName to set
 */
public void setOaSmsSendEmpName(String oaSmsSendEmpName) {
	this.oaSmsSendEmpName = oaSmsSendEmpName;
}

}