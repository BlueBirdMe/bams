package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_CALENDER
 */
public class OaCalender extends BaseBean implements java.io.Serializable {

   /**
	 * 工作安排
	 */
   private static final long serialVersionUID = 7021559177332051859L;
   private Integer oaCalenderLevel;		//工作安排等级  1 重要紧急 2重要不紧急 3不重要紧急 4不重要不紧急
   private Integer oaCalenderType;		//工作安排类型 字典表
   private String oaCalenderStart;		//工作安排开始时间	
   private String oaCalenderEnd;		//工作安排结束时间
   private Integer oaCalenderStatus;	//工作安排状态 1 未完成 2已完成
   private String oaCalenderContent;	//工作安排内容	
   private String oaCalenderEmp;		//工作安排人
   private String recordId;				//记录人ID
   private String recordDate;			//记录人访问时间
   private String lastmodiId;			//最后访问人id
   private String lastmodiDate;			//最后访问时间
   private Integer companyId;			//公司ID
   private SysLibraryInfo library;		//字典表

   //默认构造方法
   public OaCalender(){
      super();
   }

   //get和set方法
   public Integer getOaCalenderLevel(){
      return oaCalenderLevel;
   }

   public void setOaCalenderLevel(Integer aOaCalenderLevel){
      this.oaCalenderLevel = aOaCalenderLevel;
   }

   public Integer getOaCalenderType(){
      return oaCalenderType;
   }

   public void setOaCalenderType(Integer aOaCalenderType){
      this.oaCalenderType = aOaCalenderType;
   }

   public String getOaCalenderStart(){
      return oaCalenderStart;
   }

   public void setOaCalenderStart(String aOaCalenderStart){
      this.oaCalenderStart = aOaCalenderStart;
   }

   public String getOaCalenderEnd(){
      return oaCalenderEnd;
   }

   public void setOaCalenderEnd(String aOaCalenderEnd){
      this.oaCalenderEnd = aOaCalenderEnd;
   }

   public Integer getOaCalenderStatus(){
      return oaCalenderStatus;
   }

   public void setOaCalenderStatus(Integer aOaCalenderStatus){
      this.oaCalenderStatus = aOaCalenderStatus;
   }

   public String getOaCalenderContent(){
      return oaCalenderContent;
   }

   public void setOaCalenderContent(String aOaCalenderContent){
      this.oaCalenderContent = aOaCalenderContent;
   }

   public String getOaCalenderEmp(){
      return oaCalenderEmp;
   }

   public void setOaCalenderEmp(String aOaCalenderEmp){
      this.oaCalenderEmp = aOaCalenderEmp;
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

   public String getLastmodiId(){
      return lastmodiId;
   }

   public void setLastmodiId(String aLastmodiId){
      this.lastmodiId = aLastmodiId;
   }

   public String getLastmodiDate(){
      return lastmodiDate;
   }

   public void setLastmodiDate(String aLastmodiDate){
      this.lastmodiDate = aLastmodiDate;
   }

   public Integer getCompanyId(){
      return companyId;
   }

   public void setCompanyId(Integer aCompanyId){
      this.companyId = aCompanyId;
   }

/**
 * @return the library
 */
public SysLibraryInfo getLibrary() {
	return library;
}

/**
 * @param library the library to set
 */
public void setLibrary(SysLibraryInfo library) {
	this.library = library;
}

}