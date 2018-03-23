package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_NOTICE
 */
public class OaNotice extends BaseBean implements java.io.Serializable {

   /**
	 * 内部通知
	 */
   private static final long serialVersionUID = -2398264327012449509L;
   private String oaNotiName;                    //通知标题
   private Integer oaNotiType;                   //通知类型
   private String oaNotiEmp;                     //发布人
   private String oaNotiTime;                    //发布时间
   private String oaObjEmp;                      //阅读范围（人员）
   private String oaObjDep;                      //阅读范围（部门）
   private String oaNotiText;                    //通知内容
   private Integer oaNotiStatus;                 //通知状态
   private String oaNotiAcce;                    //附件
   private String recordId;                      //记录人
   private String recordDate;                    //记录时间
   private String lastmodiId;                    //最后修改人
   private String lastmodiDate;                  //最后修改时间
   private Integer companyId;                    //公司ID
   //临时使用
   private HrmEmployee employee;                 //发布人员对象
   private String depList;                       //阅读范围（人员）名字集合
   private String empLIst;                       //阅读范围（部门）名字集合
   private SysLibraryInfo oaNoticeLib;           //通知类型对象

    public SysLibraryInfo getOaNoticeLib() {
		return oaNoticeLib;
	}

	public void setOaNoticeLib(SysLibraryInfo oaNoticeLib) {
		this.oaNoticeLib = oaNoticeLib;
	}

	public String getDepList() {
		return depList;
	}

	public void setDepList(String depList) {
		this.depList = depList;
	}

	public String getEmpLIst() {
		return empLIst;
	}

	public void setEmpLIst(String empLIst) {
		this.empLIst = empLIst;
	}

	public HrmEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}

	// 默认构造方法
	public OaNotice() {
		super();
	}

   //get和set方法
   public String getOaNotiName(){
      return oaNotiName;
   }

   public void setOaNotiName(String aOaNotiName){
      this.oaNotiName = aOaNotiName;
   }

   public Integer getOaNotiType(){
      return oaNotiType;
   }

   public void setOaNotiType(Integer aOaNotiType){
      this.oaNotiType = aOaNotiType;
   }

   public String getOaNotiEmp(){
      return oaNotiEmp;
   }

   public void setOaNotiEmp(String aOaNotiEmp){
      this.oaNotiEmp = aOaNotiEmp;
   }

   public String getOaNotiTime(){
      return oaNotiTime;
   }

   public void setOaNotiTime(String aOaNotiTime){
      this.oaNotiTime = aOaNotiTime;
   }

   public String getOaObjEmp(){
      return oaObjEmp;
   }

   public void setOaObjEmp(String aOaObjEmp){
      this.oaObjEmp = aOaObjEmp;
   }

   public String getOaObjDep(){
      return oaObjDep;
   }

   public void setOaObjDep(String aOaObjDep){
      this.oaObjDep = aOaObjDep;
   }

   public String getOaNotiText(){
      return oaNotiText;
   }

   public void setOaNotiText(String aOaNotiText){
      this.oaNotiText = aOaNotiText;
   }

   public Integer getOaNotiStatus(){
      return oaNotiStatus;
   }

   public void setOaNotiStatus(Integer aOaNotiStatus){
      this.oaNotiStatus = aOaNotiStatus;
   }

   public String getOaNotiAcce(){
      return oaNotiAcce;
   }

   public void setOaNotiAcce(String aOaNotiAcce){
      this.oaNotiAcce = aOaNotiAcce;
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

}