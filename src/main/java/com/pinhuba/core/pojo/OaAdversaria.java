package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_ADVERSARIA
 */
public class OaAdversaria extends BaseBean implements java.io.Serializable {

   /**
	 *    公司记事
	 */
	private static final long serialVersionUID = 8029330780872785183L;
   private String oaAdverTitle; //记事标题
   private Integer oaAdverLevel;  //重要级
   private String oaAdverText;   //记事内容
   private String oaAdverEmp;// 发布人
   private String oaAdverTime;  //记事时间
   private String oaAdverAcce;  //附件
   private Integer oaAdverStatus; //记事状态
   private String recordId;    //记录人
   private String recordDate;  //记录时间
   private String lastmodiId;  //最后修改人
   private String lastmodiDate; //最后修改时间
   private Integer companyId;   //公司ID
   //临时使用
   private HrmEmployee employee;   //发布人对象
   private SysLibraryInfo oaAdverLib;  //公司记事对象

   public SysLibraryInfo getOaAdverLib() {
		return oaAdverLib;
	}

	public void setOaAdverLib(SysLibraryInfo oaAdverLib) {
		this.oaAdverLib = oaAdverLib;
	}

	// 默认构造方法
	public OaAdversaria() {
		super();
	}
	
   //get和set方法ce
   public String getOaAdverTitle(){
      return oaAdverTitle;
   }

   public void setOaAdverTitle(String aOaAdverTitle){
      this.oaAdverTitle = aOaAdverTitle;
   }

   public Integer getOaAdverLevel(){
      return oaAdverLevel;
   }

   public void setOaAdverLevel(Integer aOaAdverLevel){
      this.oaAdverLevel = aOaAdverLevel;
   }

   public String getOaAdverText(){
      return oaAdverText;
   }

   public void setOaAdverText(String aOaAdverText){
      this.oaAdverText = aOaAdverText;
   }

   public String getOaAdverEmp(){
      return oaAdverEmp;
   }

   public void setOaAdverEmp(String aOaAdverEmp){
      this.oaAdverEmp = aOaAdverEmp;
   }

   public String getOaAdverTime(){
      return oaAdverTime;
   }

   public void setOaAdverTime(String aOaAdverTime){
      this.oaAdverTime = aOaAdverTime;
   }

   public Integer getOaAdverStatus(){
      return oaAdverStatus;
   }

   public void setOaAdverStatus(Integer aOaAdverStatus){
      this.oaAdverStatus = aOaAdverStatus;
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

	public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

	public String getOaAdverAcce() {
		return oaAdverAcce;
	}

	public void setOaAdverAcce(String oaAdverAcce) {
		this.oaAdverAcce = oaAdverAcce;
	}

}