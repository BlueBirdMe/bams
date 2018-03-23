package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_ANNOUNCEMENT
 */
public class OaAnnouncement extends BaseBean implements java.io.Serializable {

   /**
	 * 公司公告
	 */
   private static final long serialVersionUID = 2745890407733601131L;
   private String oaAnnoName;                   //公告标题
   private Integer oaAnnoType;                  //公告类型
   private String oaAnnoText;                   //公告内容
   private String oaAnnoEmp;                    //发布人
   private String oaAnnoTime;                   //发布时间
   private Integer oaAnnoLevel;                 //重要级
   private Integer oaAnnoStatus;                //公告状态
   private String oaAnnoAcce;                   //附件
   private String recordId;                     //记录人
   private String recordDate;                   //记录时间
   private String lastmodiId;                   //最后修改人
   private String lastmodiDate;                 //最后修改时间
   private Integer companyId;                   //公司ID
   //临时使用
   private HrmEmployee employee;                //发布人员对象
   private SysLibraryInfo oaAnnoLib;            //公告类型对象

   public SysLibraryInfo getOaAnnoLib() {
		return oaAnnoLib;
	}

	public void setOaAnnoLib(SysLibraryInfo oaAnnoLib) {
		this.oaAnnoLib = oaAnnoLib;
	}

	public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

   //默认构造方法
   public OaAnnouncement(){
      super();
   }

  //get和set方法
   public String getOaAnnoName(){
      return oaAnnoName;
   }

   public void setOaAnnoName(String aOaAnnoName){
      this.oaAnnoName = aOaAnnoName;
   }

   public Integer getOaAnnoType(){
      return oaAnnoType;
   }

   public void setOaAnnoType(Integer aOaAnnoType){
      this.oaAnnoType = aOaAnnoType;
   }

   public String getOaAnnoText(){
      return oaAnnoText;
   }

   public void setOaAnnoText(String aOaAnnoText){
      this.oaAnnoText = aOaAnnoText;
   }

   public String getOaAnnoEmp(){
      return oaAnnoEmp;
   }

   public void setOaAnnoEmp(String aOaAnnoEmp){
      this.oaAnnoEmp = aOaAnnoEmp;
   }

   public String getOaAnnoTime(){
      return oaAnnoTime;
   }

   public void setOaAnnoTime(String aOaAnnoTime){
      this.oaAnnoTime = aOaAnnoTime;
   }

   public Integer getOaAnnoStatus(){
      return oaAnnoStatus;
   }

   public void setOaAnnoStatus(Integer aOaAnnoStatus){
      this.oaAnnoStatus = aOaAnnoStatus;
   }

   public String getOaAnnoAcce(){
      return oaAnnoAcce;
   }

   public void setOaAnnoAcce(String aOaAnnoAcce){
      this.oaAnnoAcce = aOaAnnoAcce;
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

   public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

	public Integer getOaAnnoLevel() {
		return oaAnnoLevel;
	}

	public void setOaAnnoLevel(Integer oaAnnoLevel) {
		this.oaAnnoLevel = oaAnnoLevel;
	}

}