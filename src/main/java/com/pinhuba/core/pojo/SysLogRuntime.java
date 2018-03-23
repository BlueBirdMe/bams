package com.pinhuba.core.pojo;

import com.pinhuba.common.annotation.Remark;

/**
 * 数据库表名：sys_log_runtime
 */
public class SysLogRuntime extends BaseBean implements java.io.Serializable {

   @Remark("类名|2|2|1|2")
   private String className;
   @Remark("方法名|2|2|1|2")
   private String methodName;
   @Remark("生成时间|2|1|1|2")
   private String createTime;
   @Remark("日志级别|2|1|1|2")
   private String logLevel;
   @Remark("运行信息|2|2|1|2")
   private String msg;

   //默认构造方法
   public SysLogRuntime(){
      super();
   }

   //构造方法(手工生成)
   

  //get和set方法
   public String getClassName(){
      return className;
   }

   public void setClassName(String aClassName){
      this.className = aClassName;
   }

   public String getMethodName(){
      return methodName;
   }

   public void setMethodName(String aMethodName){
      this.methodName = aMethodName;
   }

   public String getCreateTime(){
      return createTime;
   }

   public void setCreateTime(String aCreateTime){
      this.createTime = aCreateTime;
   }

   public String getLogLevel(){
      return logLevel;
   }

   public void setLogLevel(String aLogLevel){
      this.logLevel = aLogLevel;
   }

   public String getMsg(){
      return msg;
   }

   public void setMsg(String aMsg){
      this.msg = aMsg;
   }

}