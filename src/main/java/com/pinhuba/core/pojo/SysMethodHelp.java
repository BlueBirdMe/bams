package com.pinhuba.core.pojo;

/**
 * 数据库表名：sys_method_help
 */
public class SysMethodHelp extends BaseBean implements java.io.Serializable {

   private String helpImg;
   private String helpDesc;
   private String methodId;
   private int priority;//排序
   
   private String methodName;
   private SysMethodInfo methodInfo;

   //默认构造方法
   public SysMethodHelp(){
      super();
   }

   //构造方法(手工生成)
   

  //get和set方法
   public String getHelpImg(){
      return helpImg;
   }

   public void setHelpImg(String aHelpImg){
      this.helpImg = aHelpImg;
   }

   public String getHelpDesc(){
      return helpDesc;
   }

   public void setHelpDesc(String aHelpDesc){
      this.helpDesc = aHelpDesc;
   }

   public String getMethodId(){
      return methodId;
   }

   public void setMethodId(String aMethodId){
      this.methodId = aMethodId;
   }

	public SysMethodInfo getMethodInfo() {
		return methodInfo;
	}
	
	public void setMethodInfo(SysMethodInfo methodInfo) {
		this.methodInfo = methodInfo;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}