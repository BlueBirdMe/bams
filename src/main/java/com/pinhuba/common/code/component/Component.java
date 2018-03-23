package com.pinhuba.common.code.component;

public abstract class Component {
	
	public static final int NICEFORM = 1;//普通文本框
	public static final int NUMFORM = 2;//数字框
	public static final int RMBFORM = 3;//金额框
	public static final int DATEFORM = 4;//日期框
	public static final int SELECT = 5;//下拉框select
	public static final int RADIO = 6;//单选框radio
	public static final int CHECKBOX = 7;//多选框checkbox
	public static final int TAKEFORM_TEXT = 8;//弹出单选框
	public static final int TAKEFORM_TEXTAREA = 9;//弹出多选框
	public static final int TEXTAREA = 10;//文本域
	public static final int RICHTEXTAREA = 11;//FCK富文本
	public static final int UPLOADIMG = 12;//上传图片组件
	public static final int UPLOADFILE = 13;//上传附件组件
	
	/**
	 * 获得对应组件新增、编辑页面赋值字符串
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public String getCommponentSetValueStr(String fieldName, String fieldValue) {
		String str = "DWRUtil.setValue(\""+fieldName+"\","+fieldValue+");";
		return str;
	}
	
	/**
	 * 获得对应组件新增、编辑页面取值字符串
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public String getCommponentGetValueStr(String pojoName, String fieldName) {
		String str = pojoName+"."+fieldName+" = DWRUtil.getValue(\""+fieldName+"\");";
		return str;
	}
	
	/**
	 * 获得对应组件明细页面赋值字符串
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public String getCommponentSetDetailStr(String fieldName, String fieldValue) {
		String str = "DWRUtil.setValue(\""+fieldName+"\","+fieldValue+");";
		return str;
	}
	
	public String getCommponentDetailDefine(String fieldName) {
		String str = "<td id=\"" + fieldName + "\" class=\"detailtabletd\"></td>";
		return str;
	}
	
	public abstract String getCommponentDefine(String fieldName, String requiredString, String requiredLabel);
}
