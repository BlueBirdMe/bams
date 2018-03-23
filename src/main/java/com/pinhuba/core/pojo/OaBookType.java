package com.pinhuba.core.pojo;

/**
 * 
 * 数据库表名：OA_BOOK_TYPE
 */
public class OaBookType extends BaseBean implements java.io.Serializable {

   /**
	 * 图示类型表
	 */
   private static final long serialVersionUID = -8204995418237622993L;
   private String oaBooktypeName;               //图书类型名称
   private String oaBooktypeRemark;             //图书类型说明
   private String bookCount;                     //图书数
   private String recordId;     
   private String recordDate;
   private String lastmodiId;
   private String lastmodiDate;
   private Integer companyId;                    //公司主键
   //默认构造方法
   public OaBookType(){
      super();
   }

   //get和set方法
   public String getOaBooktypeName(){
      return oaBooktypeName;
   }

   public void setOaBooktypeName(String aOaBooktypeName){
      this.oaBooktypeName = aOaBooktypeName;
   }

   public String getOaBooktypeRemark(){
      return oaBooktypeRemark;
   }

   public void setOaBooktypeRemark(String aOaBooktypeRemark){
      this.oaBooktypeRemark = aOaBooktypeRemark;
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

	public String getBookCount() {
		return bookCount;
	}

	public void setBookCount(String bookCount) {
		this.bookCount = bookCount;
	}

}