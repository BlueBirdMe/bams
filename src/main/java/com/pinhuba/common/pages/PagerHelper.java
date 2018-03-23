package com.pinhuba.common.pages;

import java.util.List;

public class PagerHelper {
	protected static Pager getPager(String pageSize,String currentPage,int totalRows,String pageMethod) {
		// 定义pager对象，用于传到页面
		Pager pager = null;
		
		if(pageSize != null){
			pager = new Pager(totalRows,Integer.parseInt(pageSize));
		}else{
			pager = new Pager(totalRows);
		}

		// 如果当前页号为空，表示为首次查询该页
		// 如果不为空，则刷新pager对象，输入当前页号等信息
		if (currentPage != null) {
			pager.refresh(Integer.parseInt(currentPage));
		}
		
		if (pageMethod != null) {
			if (pageMethod.equals("first")) {
				pager.first();
			} else if (pageMethod.equals("previous")) {
				pager.previous();
			} else if (pageMethod.equals("next")) {
				pager.next();
			} else if (pageMethod.equals("last")) {
				pager.last();
			}else{
				pager.go();
			}
		}else{
			pager.go();
		}
		return pager;
	}
	
	protected static Pager getSessionPager(String pageSize,String currentPage,int totalRows,String pageMethod,List list) {
		// 定义pager对象，用于传到页面
		Pager pager = null;
		
		if(pageSize != null){
			pager = new Pager(totalRows,Integer.parseInt(pageSize));
		}else{
			pager = new Pager(totalRows);
		}
		
		pager.setResultList(list);

		// 如果当前页号为空，表示为首次查询该页
		// 如果不为空，则刷新pager对象，输入当前页号等信息
		if (currentPage != null) {
			pager.refresh(Integer.parseInt(currentPage));
		}
		
		if (pageMethod != null) {
			if (pageMethod.equals("first")) {
				pager.first();
			} else if (pageMethod.equals("previous")) {
				pager.previous();
			} else if (pageMethod.equals("next")) {
				pager.next();
			} else if (pageMethod.equals("last")) {
				pager.last();
			}else{
				pager.go();
			}
		}else{
			pager.go();
		}
		
		return pager;
	}
	
	public static Pager getPager(Pager oldPager,int rowCount){
		Pager newPager = getPager(String.valueOf(oldPager.getPageSize()), String.valueOf(oldPager.getCurrentPage()), rowCount, oldPager.getPageMethod());
		return newPager;
	}
	
	public static Pager getSessionPager(Pager oldPager,int rowCount,List resultList){
		Pager newPager = getSessionPager(String.valueOf(oldPager.getPageSize()), String.valueOf(oldPager.getCurrentPage()), rowCount, oldPager.getPageMethod(), resultList);
		return newPager;
	}
	
	/**
	 * 检查页码 checkPageNo
	 * 
	 * @param pageNo
	 * @return if pageNo==null or pageNo<1 then return 1 else return pageNo
	 */
	public static int cpn(Integer pageNo) {
		return (pageNo == null || pageNo < 1) ? 1 : pageNo;
	}
}