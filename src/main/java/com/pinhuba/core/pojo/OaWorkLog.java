package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_WORK_LOG
 */
public class OaWorkLog extends BaseBean implements java.io.Serializable {

   /**
	 * 工作日志
	 */
   private static final long serialVersionUID = -361002143792967668L;
   private String oaWorklogTitle;  //标题
   private Integer oaWorklogType;  //类型 在字典表中
   private String oaWorklogDate;  // 日期
   private Integer oaWorklogRange;  // 范围 1 私有 2 共享
   private String oaWorklogDeps;  //查看部门id集合
   private String oaWorklogEmps;  //查看人员id集合
   
   private String oaWorklogDepsName;  //查看部门名字集合
   private String oaWorklogEmpsName;  //查看人员名字集合
   
   private String oaWorklogContent;  //日志内容
   private String oaWorklogLogger;  //日志作者
   private String oaWorklogTankid;  //网络文件柜，未使用
   private String oaWorklogAnnexid;  //附件
   private String recordId;			//记录人ID
   private String recordDate;		//最后记录时间
   private String lastmodiId;		//最后记录ID
   private String lastmodiDate;		//最后记录时间
   private Integer companyId;		//公司ID
   private SysLibraryInfo library;   //字典表
   private HrmEmployee hrmEmployee;   //人员表

   //默认构造方法
   public OaWorkLog(){
      super();
   }

  //get和set方法
   public String getOaWorklogTitle(){
      return oaWorklogTitle;
   }

   public void setOaWorklogTitle(String aOaWorklogTitle){
      this.oaWorklogTitle = aOaWorklogTitle;
   }

   public Integer getOaWorklogType(){
      return oaWorklogType;
   }

   public void setOaWorklogType(Integer aOaWorklogType){
      this.oaWorklogType = aOaWorklogType;
   }

   public String getOaWorklogDate(){
      return oaWorklogDate;
   }

   public void setOaWorklogDate(String aOaWorklogDate){
      this.oaWorklogDate = aOaWorklogDate;
   }

   public Integer getOaWorklogRange(){
      return oaWorklogRange;
   }

   public void setOaWorklogRange(Integer aOaWorklogRange){
      this.oaWorklogRange = aOaWorklogRange;
   }

   public String getOaWorklogDeps(){
      return oaWorklogDeps;
   }

   public void setOaWorklogDeps(String aOaWorklogDeps){
      this.oaWorklogDeps = aOaWorklogDeps;
   }

   public String getOaWorklogEmps(){
      return oaWorklogEmps;
   }

   public void setOaWorklogEmps(String aOaWorklogEmps){
      this.oaWorklogEmps = aOaWorklogEmps;
   }

   public String getOaWorklogContent(){
      return oaWorklogContent;
   }

   public void setOaWorklogContent(String aOaWorklogContent){
      this.oaWorklogContent = aOaWorklogContent;
   }

   public String getOaWorklogLogger(){
      return oaWorklogLogger;
   }

   public void setOaWorklogLogger(String aOaWorklogLogger){
      this.oaWorklogLogger = aOaWorklogLogger;
   }

   public String getOaWorklogTankid(){
      return oaWorklogTankid;
   }

   public void setOaWorklogTankid(String aOaWorklogTankid){
      this.oaWorklogTankid = aOaWorklogTankid;
   }

   public String getOaWorklogAnnexid(){
      return oaWorklogAnnexid;
   }

   public void setOaWorklogAnnexid(String aOaWorklogAnnexid){
      this.oaWorklogAnnexid = aOaWorklogAnnexid;
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

/**
 * @return the oaWorklogDepsName
 */
public String getOaWorklogDepsName() {
	return oaWorklogDepsName;
}

/**
 * @param oaWorklogDepsName the oaWorklogDepsName to set
 */
public void setOaWorklogDepsName(String oaWorklogDepsName) {
	this.oaWorklogDepsName = oaWorklogDepsName;
}

/**
 * @return the oaWorklogEmpsName
 */
public String getOaWorklogEmpsName() {
	return oaWorklogEmpsName;
}

/**
 * @param oaWorklogEmpsName the oaWorklogEmpsName to set
 */
public void setOaWorklogEmpsName(String oaWorklogEmpsName) {
	this.oaWorklogEmpsName = oaWorklogEmpsName;
}

/**
 * @return the hrmEmployee
 */
public HrmEmployee getHrmEmployee() {
	return hrmEmployee;
}

/**
 * @param hrmEmployee the hrmEmployee to set
 */
public void setHrmEmployee(HrmEmployee hrmEmployee) {
	this.hrmEmployee = hrmEmployee;
}

}