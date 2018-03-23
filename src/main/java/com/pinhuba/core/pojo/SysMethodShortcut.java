package com.pinhuba.core.pojo;

/**
 * 数据库表名：sys_method_shortcut
 */
public class SysMethodShortcut extends BaseBean implements java.io.Serializable {

   private String methodId;
   private String empId;
   private Integer companyId;
   private Integer autoOpen;
   
   private SysMethodInfo method;

   //默认构造方法
   public SysMethodShortcut(){
      super();
   }

   //构造方法(手工生成)
   

  //get和set方法
   public String getMethodId(){
      return methodId;
   }

   public void setMethodId(String aMethodId){
      this.methodId = aMethodId;
   }
   
	public String getEmpId() {
		return empId;
	}
	
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public Integer getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public SysMethodInfo getMethod() {
		return method;
	}

	public void setMethod(SysMethodInfo method) {
		this.method = method;
	}

	public Integer getAutoOpen() {
		return autoOpen;
	}

	public void setAutoOpen(Integer autoOpen) {
		this.autoOpen = autoOpen;
	}
	
}