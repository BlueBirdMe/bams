package com.pinhuba.web.taglib.table;
/**
 * 操作提示bean
 * @author peng.ning
 *
 */
public class SysGridTitleBean {
	private String imgTilte;
	
	private String strTitle;

	public String getImgTilte() {
		return imgTilte;
	}

	public void setImgTilte(String imgTilte) {
		this.imgTilte = imgTilte;
	}

	public String getStrTitle() {
		return strTitle;
	}

	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}

	public SysGridTitleBean(String imgTilte, String strTitle) {
		super();
		this.imgTilte = imgTilte;
		this.strTitle = strTitle;
	}

	public SysGridTitleBean(String strTitle) {
		super();
		this.strTitle = strTitle;
	}
	
	
}
