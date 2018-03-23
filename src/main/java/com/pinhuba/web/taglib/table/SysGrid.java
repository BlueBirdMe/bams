package com.pinhuba.web.taglib.table;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.security.Base64;
import com.pinhuba.web.taglib.table.cloumntype.AbscolumnType;
import com.pinhuba.web.taglib.table.cloumntype.CheckBoxType;
import com.pinhuba.web.taglib.table.cloumntype.DateType;
import com.pinhuba.web.taglib.table.cloumntype.GridColumnType;
import com.pinhuba.web.taglib.table.cloumntype.OtherType;
import com.pinhuba.web.taglib.table.cloumntype.RadioType;
import com.pinhuba.web.taglib.table.cloumntype.SelectType;
import com.pinhuba.web.taglib.table.cloumntype.TextType;
import com.pinhuba.web.taglib.table.cloumntype.TimeType;

public class SysGrid {
	
	//视图显示情况
	public static final int SHOW_ALL = 1;

	public static final int SHOW_TABLE = 2;

	public static final int SHOW_IMAGE = 3;

	//默认打开的试图
	public static final int DEFAULT_SHOWTABLE = 1;

	public static final int DEFAULT_SHOWIMAGE = 2;

	// =====================================

	private HttpServletRequest request;

	private String tableWidth = "100%";

	private String tableHeight = "100%";

	private String bodyScroll = "hidden";

	private String tableTitle;

	private boolean isautoQuery = true;// 自动查询

	private int tableRowSize = 50;// 每页显示行数

	private int border = 0;// 边框

	private ArrayList<SysGridBtnBean> btnList = new ArrayList<SysGridBtnBean>(); // 按钮集合

	private ArrayList<SysGridColumnBean> columnList = new ArrayList<SysGridColumnBean>(); // 列集合

	private boolean isShowImg = true;// 是否显示图片 此属性辅助showview属性(不影响以前代码扩展)

	private int showView = 1;// 默认显示表格和视图

	private int defaultShow = 1;// 默认显示的视图

	private int imgShowNum = 5; // 每行显示图标个数

	private String imgShowUrl;// 图片显示的属性

	private String imgShowCode;// 缩略图标识

	private String imgNoDefaultPath;// 如果图片不存的图片

	private boolean imgShowMethod = false;	//显示图片名称的true为方法，false为列
	
	private String imgShowText; // 图标显示的文字

	private String imgwidth = "auto";// 图片显示宽度

	private String imgheight = "auto"; // 图片显示高度

	private String imgdivwidth = "280"; // 显示详细的宽度

	private int imgShowTextLen = 8;// 图片显示名称长度

	private String queryFunction; // 查询方法

	private String dblFunction; // 双击列的方法名，又返回值，为列对象

	private String dblBundle; // 双击列的绑定的列值

	private boolean isCheckboxOrNum = true;// ture显示为checkbox false显示为序号

	private boolean isShowImageDetail = true;// 默认移动到图片显示详细是否选中

	// 增加行操作
	private boolean isShowProcess = false;// 是否显示操作列

	private String processMethodName;// 生成操作对象方法
	
	private String processPosition = "right";//操作栏位置 默认显示在右侧

	// 2010-1-12扩展功能
	private boolean isshowProcessTool = true;// 是否显示功能栏

	private boolean isshowSimpleTool = true;// 是否显示简单查询功能栏栏

	private ArrayList<SysGridTitleBean> helpList = null;// 帮助提示

	private int AdvancedMaxRow = 3;// 高级查询显示最大行数

	private int AdvancedRowHeight = 28;// 高级查询行高

	private int processTitleMaxRow = 4;// 操作提示显示最大行数

	private int processTitleMaxColume = 2;// 操作提示显示列

	private int processTitleRowHeight = 18;// 操作提示行高度

	private boolean isShowBlankTable = true;// 是否显示空白行表格
	
	//2014-06-23
	private boolean isOrderFromDatabase = false;//是否通过数据库进行排序
	
	private String[] orderExcludes = {};//不能进行排序的数据列
	
	//2015-01-24
	private int rowHeight = 30;//行高单位为像素px

	public String[] getOrderExcludes() {
		return orderExcludes;
	}

	public void setOrderExcludes(String[] orderExcludes) {
		this.orderExcludes = orderExcludes;
	}

	public boolean isOrderFromDatabase() {
		return isOrderFromDatabase;
	}

	public void setOrderFromDatabase(boolean isOrderFromDatabase) {
		this.isOrderFromDatabase = isOrderFromDatabase;
	}
	
	public int getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}

	public void setShowBlankTable(boolean isShowBlankTable) {
		this.isShowBlankTable = isShowBlankTable;
	}

	public void setIsshowSimpleTool(boolean isshowSimpleTool) {
		this.isshowSimpleTool = isshowSimpleTool;
	}

	public void setProcessTitleMaxRow(int processTitleMaxRow) {
		this.processTitleMaxRow = processTitleMaxRow;
	}

	public void setAdvancedMaxRow(int advancedMaxRow) {
		AdvancedMaxRow = advancedMaxRow;
	}

	public void setHelpList(ArrayList<SysGridTitleBean> helpList) {
		this.helpList = helpList;
	}

	public void setIsshowProcessTool(boolean isshowProcessTool) {
		this.isshowProcessTool = isshowProcessTool;
	}

	public void setDefaultShow(int defaultShow) {
		this.defaultShow = defaultShow;
	}

	public void setShowView(int showView) {
		this.showView = showView;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public void setProcessMethodName(String processMethodName) {
		this.processMethodName = processMethodName;
	}

	public void setProcessPosition(String processPosition) {
		this.processPosition = processPosition;
	}

	public void setShowProcess(boolean isShowProcess) {
		this.isShowProcess = isShowProcess;
	}

	public void setCheckboxOrNum(boolean isCheckboxOrNum) {
		this.isCheckboxOrNum = isCheckboxOrNum;
	}

	public SysGrid(HttpServletRequest request, String tableWidth, String tableHeight, String tableTitle) {
		super();
		this.request = request;
		this.tableWidth = tableWidth;
		this.tableHeight = tableHeight;
		this.tableTitle = tableTitle;
		this.setParam(request);
	}

	public void setShowImageDetail(boolean isShowImageDetail) {
		this.isShowImageDetail = isShowImageDetail;
	}

	public SysGrid(HttpServletRequest request, String tableWidth, String tableHeight, String tableTitle, ArrayList<SysGridBtnBean> btnList, ArrayList<SysGridColumnBean> columnList, int imgShowNum,
			String imgShowText, String imgwidth, String imgheight, String queryFunction, String dblFunction, String dblBundle) {
		super();
		this.request = request;
		this.tableWidth = tableWidth;
		this.tableHeight = tableHeight;
		this.tableTitle = tableTitle;
		this.btnList = btnList;
		this.columnList = columnList;
		this.imgShowNum = imgShowNum;
		this.imgShowText = imgShowText;
		this.imgwidth = imgwidth;
		this.imgheight = imgheight;
		this.queryFunction = queryFunction;
		this.dblFunction = dblFunction;
		this.dblBundle = dblBundle;
		this.setParam(request);
	}

	public void setTableRowSize(int tableRowSize) {
		this.tableRowSize = tableRowSize;
	}

	public void setIsautoQuery(boolean isautoQuery) {
		this.isautoQuery = isautoQuery;
	}

	public void setImgShowTextLen(int imgShowTextLen) {
		this.imgShowTextLen = imgShowTextLen;
	}

	public void setImgdivwidth(String imgdivwidth) {
		this.imgdivwidth = imgdivwidth;
	}

	public void setImgShowCode(String imgShowCode) {
		this.imgShowCode = imgShowCode;
	}

	public void setImgwidth(String imgwidth) {
		this.imgwidth = imgwidth;
	}

	public void setImgheight(String imgheight) {
		this.imgheight = imgheight;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setTableWidth(String tableWidth) {
		this.tableWidth = tableWidth;
	}

	public void setTableHeight(String tableHeight) {
		this.tableHeight = tableHeight;
	}

	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}

	public SysGrid(HttpServletRequest request) {
		super();
		this.request = request;
		this.setParam(request);
	}

	public void setBtnList(ArrayList<SysGridBtnBean> btnList) {
		this.btnList = btnList;
	}

	public void setColumnList(ArrayList<SysGridColumnBean> columnList) {
		this.columnList = columnList;
	}

	public void setImgShowNum(int imgShowNum) {
		this.imgShowNum = imgShowNum;
	}

	public void setImgShowText(String imgShowText) {
		this.imgShowText = imgShowText;
	}

	public void setQueryFunction(String queryFunction) {
		this.queryFunction = queryFunction;
	}

	public void setDblFunction(String dblFunction) {
		this.dblFunction = dblFunction;
	}

	public void setDblBundle(String dblBundle) {
		this.dblBundle = dblBundle;
	}

	private String getPath() {
		return request.getContextPath();
	}

	public void setImgShowUrl(String imgShowUrl) {
		this.imgShowUrl = imgShowUrl;
	}

	public void setImgNoDefaultPath(String imgNoDefaultPath) {
		this.imgNoDefaultPath = imgNoDefaultPath;
	}

	public void setShowImg(boolean isShowImg) {
		this.isShowImg = isShowImg;
		if (isShowImg) {
			this.showView = SHOW_ALL;
		} else {
			this.showView = SHOW_TABLE;
		}
	}

	public void setImgShowMethod(boolean imgShowMethod) {
		this.imgShowMethod = imgShowMethod;
	}

	public void setBodyScroll(String bodyScroll) {
		this.bodyScroll = bodyScroll;
	}

	public SysGrid(HttpServletRequest request, String tableTitle, boolean isautoQuery) {
		super();
		this.request = request;
		this.tableTitle = tableTitle;
		this.isautoQuery = isautoQuery;
		this.setParam(request);
	}
	
	public SysGrid(HttpServletRequest request, String tableTitle) {
		super();
		this.request = request;
		this.tableTitle = tableTitle;
		this.setParam(request);
	}

	
	private void setParam(HttpServletRequest request){
		this.tableRowSize = Integer.parseInt(UtilTool.getSysParamByIndex(request, "erp.grid.pageSize"));
		this.isShowImageDetail = UtilTool.getSysParamByIndex(request, "erp.grid.imgViewCk").equals("是");
	}
	
	public String createTable() {
		StringBuffer table = new StringBuffer();
		// table使用
		table.append("<input type='hidden' id='pageMethod' value='null'/>\n");
		table.append("<input type='hidden' id='searchType' value='0'/>\n");
		table.append("<input type='hidden' id='noimgpath' value='" + Base64.getBase64FromString(imgNoDefaultPath) + "'/>\n");
		table.append("<table width='" + tableWidth + "' cellpadding='0' cellspacing='0' class='grid_table' align ='center' height ='" + tableHeight + "' style ='table-layout:fixed;border:" + border
				+ "px solid #D8D6D6'>\n");
		table.append("<tr style='BACKGROUND-IMAGE: url(" + getPath() + "/images/grid_images/wbg.gif);'>\n");
		table.append("<td align='left' class='grid_title' nowrap='nowrap'>\n");
		if (this.showView == SHOW_ALL || this.showView == SHOW_TABLE) {
			table.append("<a href='javascript:void(0);'><img src='" + getPath() + "/images/grid_images/datalist.png' title='列表显示' onclick=\"changeShow(" + DEFAULT_SHOWTABLE
					+ ")\" border ='0' style ='vertical-align: middle;' id='dataimg'/></a>\n");
		}
		if (this.showView == SHOW_ALL || this.showView == SHOW_IMAGE) {
			table.append("<a href='javascript:void(0);'><img src='" + getPath() + "/images/grid_images/imglist_.png' title='图标显示' onclick=\"changeShow(" + DEFAULT_SHOWIMAGE
					+ ")\"  border='0' style ='vertical-align: middle;' id ='imglist'/></a>\n");
		}
		table.append("&nbsp;<label id='sysGridTableTitle'>" + tableTitle + "</label>\n");
		table.append("</td>\n");
		table.append("<td align='right' class='grid_search' nowrap='nowrap'>\n");
		if (isshowSimpleTool) {
			if (getSimpleCount() > 0) {
				table.append(this.createQuerySelect());// 绑定查询下拉框
			}
			if (getAdvanedCount() > 0 || getSimpleCount() > 0) {
				table.append("<img id='searchImg' onclick='" + queryFunction + "();' class='grid_img' src='" + this.getPath() + "/images/grid_images/magnifier.png' alt='查询' title='查询'/>\n");
			}
		}
		table.append("</td>\n");
		table.append("</tr>\n");
		if (isshowProcessTool) {
			table.append("<tr style='BACKGROUND-IMAGE: url(" + this.getPath() + "/images/grid_images/bg.gif);'>\n");
			table.append("<td align='left' class='grid_fun' colspan='2' style ='padding-left:5px;' valign='middle'>\n");
			// 按钮
			if (btnList != null && btnList.size() > 0) {
				table.append(this.createBtn());// 绑定按钮
			}
			// 操作提示
			if (helpList != null && helpList.size() > 0) {
				table
						.append("<div style='float:right;padding-right:5px;vertical-align: middle;padding-top:1px;' class='grid_btn' onmouseover ='this.className =\"grid_btn_hover\"' onmouseout ='this.className =\"grid_btn\"' id=\"helpProcessTitle\"><label  class='grid_img'>&nbsp;操作提示&nbsp;<img id='helptitleImg' src='"
								+ this.getPath() + "/images/grid_images/ddn.png' alt='操作提示' title='操作提示'/></label></div>\n");
			}
			// 高级查询
			if (getShowColumnCount() > 0) {
				table.append("<div style='float:right;padding-right:5px;vertical-align: middle;padding-top:1px;' class='grid_btn' onmouseover ='this.className =\"grid_btn_hover\"' onmouseout ='this.className =\"grid_btn\"' id='showMore'><label  class='grid_img'>&nbsp;显示设置&nbsp;<img id='showImg' src='"
						+ this.getPath() + "/images/grid_images/ddn.png' alt='显示设置' title='显示设置'/></label></div>\n");
			}
			// 操作控制
			if (getAdvanedCount() > 0) {
				table.append("<div style='float:right;padding-right:5px;vertical-align: middle;padding-top:1px;' class='grid_btn' onmouseover ='this.className =\"grid_btn_hover\"' onmouseout ='this.className =\"grid_btn\"' id=\"queryMore\"><label  class='grid_img'>&nbsp;高级查询&nbsp;<img id='queryImg' src='"
						+ this.getPath() + "/images/grid_images/ddn.png' alt='高级查询' title='高级查询'/></label></div>\n");
			}

			table.append("</td>\n");
			table.append("</tr>\n");
			table.append("<tr valign='center'>\n");
			table.append("<td colspan='2' valign='top' nowrap='nowrap'>\n");

			if (getAdvanedCount() > 0) {
				int adheight = getAdvancedQueryHeight();
				String tmp = "";
				if (adheight > 0 && adheight > AdvancedMaxRow * AdvancedRowHeight) {
					tmp = "overflow:auto;height:" + AdvancedMaxRow * AdvancedRowHeight + "px;";
				}
				table.append("<div id='queryParam' style =\"display:none;width:100%;" + tmp + "\">\n");
				table.append(this.createAdvancedQuery());// 创建高级查询
				table.append("</div>\n");
			}
			if (getShowColumnCount() > 0) {
				String tmp = "";
				int ptheight = getProcessTitleTableHeight();

				if (ptheight > 0) {
					tmp = "border-bottom:1px solid #D8D6D6";
				}
				table.append("<div id='showParam'  style =\"display:none;width:100%;" + tmp + "\">\n");
				if (this.showView == SHOW_ALL) {
					table.append(this.createTableColumn());// 创建列显示设置
					table.append(this.createImgProcess());
				} else if (this.showView == SHOW_TABLE) {
					table.append(this.createTableColumn());// 创建列显示设置
				} else if (this.showView == SHOW_IMAGE) {
					table.append(this.createImgProcess());
				}
				table.append("</div>\n");
			}
			if (helpList != null && helpList.size() > 0) {// 操作提示
				int ptheight = getProcessTitleTableHeight();
				String tmp = "";
				if (ptheight > 0 && ptheight > processTitleMaxRow * processTitleRowHeight) {
					tmp = "overflow:auto;height:" + processTitleMaxRow * processTitleRowHeight + "px;";
				}
				table.append("<div id='processTitleTable'  style =\"display:none;width:100%;text-align:left;" + tmp + "\">\n");
				table.append(this.createProcessTitle());
				table.append("</div>\n");
			}
			table.append("</td>\n");
			table.append("</tr>\n");
		}
		table.append("<tr valign='center'>\n");
		table.append("<td colspan='2' valign='top' id='allshowtd' style='height:100%;'>\n");
		table.append("<div style='overflow:auto;width:100%;' id ='allshowdiv'>\n");

		if (this.showView == SHOW_ALL || this.showView == SHOW_TABLE) {
			table.append("<div id='listShow' width='100%'>\n");
			table.append("<table width='100%' cellSpacing='0' cellPadding='0'>\n<tr>\n");// 主表格，包括行数表格和显示表格

			table.append("<td width='30' valign='top'>\n");// 显示序号或复选框
			table.append("<table class='tableStyleSolidLine' cellSpacing='0' cellPadding='4' width='100%' align='center' id='rowCountTable'>\n");
			table.append("<tr>\n");
			String tmpstr = "&nbsp;&nbsp;<input type='checkbox' class='nonice' style='display:none'/>";
			if (isCheckboxOrNum) {
				tmpstr = "<div class='tabimgdiv'><input style='margin:0px;padding:0px;height:13px;width:13px;' type='checkbox' title='全选/取消' id='rowCountTableTdTopCheck' onclick='rowCountTableTdTopCheckFun(this);'/></div>";
			}
			table.append("<td style='background-image: url(" + this.getPath() + "/images/grid_images/fhbg.gif);'>" + tmpstr + "</td>\n</tr>\n");
			table.append("</table>\n");
			if (isShowBlankTable) {
				table.append("<table class='tableStyleSolidLine2' cellSpacing='0' cellPadding='4' width='100%' align='center' border='0' id='rowCountTableBlank'>\n");
				table.append("</table>\n");
			}
			table.append("</td>\n");

			//是否在左侧显示操作栏
			if (isShowProcess && "left".equals(processPosition)) {
				table.append("<td valign='top'>\n");
				table.append(this.createProcessTable());
				table.append("</td>\n");
			}
			
			table.append("<td width='100%' valign='top'>\n");
			table.append(this.createMainTable());// 开始显示表格
			table.append("</td>\n");
			
			//是否在右侧显示操作栏
			if (isShowProcess && "right".equals(processPosition)) {
				table.append("<td valign='top'>\n");
				table.append(this.createProcessTable());
				table.append("</td>\n");
			}
			
			table.append("</tr>\n</table>\n");
			table.append("</div>\n");
		}
		if (this.showView == SHOW_ALL || this.showView == SHOW_IMAGE) {
			if (this.showView == SHOW_IMAGE) {
				table.append("<div style='display:none;'><TABLE id='rowCountTable'>\n");
				table.append("<thead style='background-image: url(" + this.getPath() + "/images/grid_images/fhbg2.gif);'>\n");
				String tmpstr = "&nbsp;<input type='checkbox' class='nonice' style='display:none'/>";
				table.append("<TD class='tableTitle1'>" + tmpstr + "</TD>\n</thead>\n");
				table.append("<tbody></tbody>\n");
				table.append("</TABLE></div>\n");
			}
			table.append("<div id='imgShow' width='100%' style=\"display:none;border-top:1px solid #D8D6D6\">\n");// 显示图片div
			table.append("<table width='100%' cellSpacing='6' cellPadding='3' id='imgResult' border='0'></table>\n");
			table.append("</div>\n");
		}
		table.append("</div>\n");
		table.append("</td>\n");
		table.append("</tr>\n");
		table.append("<tr style='background-image: url(" + this.getPath() + "/images/grid_images/wbg.gif);'>\n");
		table.append("<td align='left' class='grid_refresh' nowrap='nowrap'>\n");
		table.append("<label id='refreshLab'>\n");
		table.append("<div id='refreshDiv' style='display:none;'><img class='grid_img' src='" + this.getPath()
				+ "/images/grid_images/refresh.png' onclick='goPage();' alt='刷新' title='刷新'/>&nbsp;<img class='grid_img'  src='" + this.getPath() + "/images/grid_images/line.gif'/>&nbsp;当前显示 "
				+ this.createPageSizeSelect() + " 条，共&nbsp;<label id='rowCount'>0</label>&nbsp;条记录</div>\n");
		table.append("<div id='waitingDiv'><img src='" + this.getPath() + "/images/grid_images/load.gif'/>&nbsp;<img src='" + this.getPath()
				+ "/images/grid_images/line.gif'/>&nbsp;&nbsp;正在加载数据，请稍等...&nbsp;</div>");
		table.append("</label>");
		table.append("</td>\n");
		table.append("<td align='right' class='grid_pager' style='cursor: default;' nowrap='nowrap'>&nbsp;<img class='grid_img'  src='" + this.getPath() + "/images/grid_images/line.gif'/>&nbsp;");
		table.append("<img src='" + this.getPath() + "/images/grid_images/first_.gif' alt='第一页' title='第一页' id='firstPage'/>");
		table.append("<img src='" + this.getPath() + "/images/grid_images/prev_.gif' alt='上一页' title='上一页' id='prevPage'/>&nbsp;<img class='grid_img'  src='" + this.getPath()
				+ "/images/grid_images/line.gif'/>&nbsp;");
		table
				.append("第&nbsp;<label class='grid_page_btn' id='currentPage' onclick='selectcpage(this);' onmouseover ='this.className =\"grid_page_btn_hover\"' onmouseout ='this.className =\"grid_page_btn\"' title='快速跳转'></label>&nbsp;页&nbsp;");
		table.append("<img class='grid_img'  src='" + this.getPath() + "/images/grid_images/line.gif'/>&nbsp;共&nbsp;<label id='pageCount'> 0 </label>&nbsp;页&nbsp;<img class='grid_img'  src='"
				+ this.getPath() + "/images/grid_images/line.gif'/>&nbsp;");
		table.append("<img src='" + this.getPath() + "/images/grid_images/next_.gif' alt='下一页' title='下一页' id='nextPage'/>");
		table.append("<img src='" + this.getPath() + "/images/grid_images/last_.gif' alt='最后一页' title='最后一页' id='lastPage'/>&nbsp;<img class='grid_img'  src='" + this.getPath()
				+ "/images/grid_images/line.gif'/>&nbsp;");
		table.append("</td>\n");
		table.append("</tr>\n");
		table.append("</table>\n");
		if (this.showView == SHOW_ALL || this.showView == SHOW_IMAGE) {
			table.append("<div id=\"imgdivshow\" style=\"width:" + imgdivwidth + "px;display:none;\" class ='detailDivClass'></div>\n");
		}
		table.append("<div id=\"pagesenddiv\" style=\"display:none;\" class ='pagesendDivClass'>\n");
		table.append("<table cellpadding='0' cellspacing='0' border='0' id='pagesendtable'><tr>\n");
		table.append("<td align='center' style='padding-left:5px;' id ='labelshowtd'  nowrap='nowrap'>跳转到&nbsp;<label id='culabel' style='color:#000016'></label>&nbsp;页</td>\n");
		table.append("<td id='pagesendslidertd' width='120'  nowrap='nowrap'><div id='pagesendsliderdiv'></div></td>\n");
		table
				.append("<td  nowrap='nowrap'><div class='grid_pagesend_btn' onmouseover ='this.className =\"grid_pagesend_btn_hover\"' onmouseout ='this.className =\"grid_pagesend_btn\"' id='gridpagesendbtn' onclick='pagetosend();'>确定</div></td>\n");
		table.append("</tr></table></div>\n");
		table.append(this.initJaveScript());// 创建脚本
		return table.toString();
	}

	// =====================================验证方法===============================
	private int getAdvanedCount() {
		if (columnList == null) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < columnList.size(); i++) {
			SysGridColumnBean bc = columnList.get(i);
			if (bc.isShowAdvanced()) {
				count++;
				break;
			}
		}
		return count;
	}

	private int getShowColumnCount() {
		if (columnList == null) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < columnList.size(); i++) {
			SysGridColumnBean bc = columnList.get(i);
			if (bc.isShowColumn()) {
				count++;
				break;
			}
		}
		return count;
	}

	private int getSimpleCount() {
		if (columnList == null) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < columnList.size(); i++) {
			SysGridColumnBean bc = columnList.get(i);
			if (bc.isShowQuerySelsect()) {
				count++;
				break;
			}
		}
		return count;

	}

	private int getProcessTitleTableHeight() {
		int height = 0;
		if (helpList != null && helpList.size() > 0) {
			int count = helpList.size();
			if (count % processTitleMaxColume != 0) {
				height = (count / processTitleMaxColume + 1) * processTitleRowHeight;
			} else {
				height = (count / processTitleMaxColume) * processTitleRowHeight;
			}
		}
		return height;
	}

	private int getAdvancedQueryHeight() {
		int height = 0;
		int count = 0;
		if (columnList != null && columnList.size() > 0) {
			for (int i = 0; i < columnList.size(); i++) {
				SysGridColumnBean bc = columnList.get(i);
				if (bc.isShowAdvanced()) {
					count++;
				}
			}
			if (count % 2 != 0) {
				height = (count / 2 + 1) * AdvancedRowHeight;
			} else {
				height = (count / 2) * AdvancedRowHeight;
			}
		}
		return height;
	}

	// =========================创建表格=============================================

	private String createProcessTitle() {
		StringBuffer title = new StringBuffer();
		title.append("<table cellSpacing='0' cellPadding='2' border='0' width='100%'/>\n");
		
		for (int i = 0; i < helpList.size(); i++) {
			SysGridTitleBean tb = helpList.get(i);
			title.append("<tr style='BACKGROUND-color:#F6F8F9;height:" + processTitleRowHeight + "px;line-height:" + processTitleRowHeight + "px;'>\n");
			if (i==0) {
				title.append("<td align='center' rowspan='"+helpList.size()+"' valign='middle' style='padding-left:10px;padding-right:10px;width:50px;'><img src='"+request.getContextPath()+"/images/grid_images/helpinfo.png' border='0'/></td>");
			}
			if (tb.getImgTilte() != null && tb.getImgTilte().length() > 0) {
				title.append("<td align='center' valign='middle' style='padding-left:10px;width:15px;'><img class='grid_img'  src='" + this.getPath() + "/images/grid_images/" + tb.getImgTilte() + "' height='12'/></td>\n");
			} else {
				title.append("<td align='center' valign='middle' style='padding-left:10px;width:1px;'>&nbsp;</td>\n");
			}
			title.append("<td align='left' valign='middle' style='padding-left:10px;padding-right:15px;'>" + tb.getStrTitle() + "</td>\n");
			title.append("</tr>\n");
		}

		title.append("</table>\n");
		return title.toString();
	}

	private String createPageSizeSelect() {
		StringBuffer select = new StringBuffer();
		Integer[] row = new Integer[] { 30, 50, 100, 150, 200 };
		select.append("<select id='showRows' style=\"font:13px '宋体';text-align:center;width:auto;top:0px;left:0px;\" onchange='" + queryFunction + "();'>");
		for (int i = 0; i < row.length; i++) {
			int r = row[i].intValue();
			if (r == tableRowSize) {
				select.append("<option value='" + r + "' selected ='selected'>" + r + "</option>");
			} else {
				select.append("<option value='" + r + "'>" + r + "</option>");
			}

		}
		select.append("</select>");
		return select.toString();
	}
	
	private String createProcessTable() {
		StringBuffer processTable = new StringBuffer();
		processTable.append("<table class='tableStyleSolidLine' cellSpacing='0' cellPadding='4' width='100%' align='center' id='dataProcessTable'>\n");
		processTable.append("<tr>\n");
		processTable.append("<td class='tableTitle1'>&nbsp;操 作&nbsp;</td>\n</tr>\n");
		processTable.append("</table>\n");
		if (isShowBlankTable) {
			processTable.append("<table class='tableStyleSolidLine2' cellSpacing='0' cellPadding='4' width='100%' align='center' border='0' id='dataProcessTableBlank'>\n");
			processTable.append("</table>\n");
		}
		return processTable.toString();
	}
	

	private String createMainTable() {
		StringBuffer maintable = new StringBuffer();
		maintable.append("<table class='tableStyleSolidLine' cellSpacing='0' cellPadding='4' width='100%' align='center' id='tableResult'>\n");
		maintable.append("<tr>\n");
		if (columnList != null && columnList.size() > 0) {
			if(isOrderFromDatabase()){
				//数据库排序
				for (int i = 0; i < columnList.size(); i++) {
					SysGridColumnBean bc = columnList.get(i);
					
					if (bc.isShowColumn()) {
						String tmp = "<TD class='tableTitle1' nowrap onclick=\"ts_resortTable2(this, '" + bc.getDataName() + "');\">" + bc.getShowName()
								+ "&nbsp;<span class='sortarrow'></span></TD>\n";
						
						for (String dataName : orderExcludes) {
							if(bc.getDataName().equals(dataName)){
								tmp = "<TD class='tableTitle1' nowrap >" + bc.getShowName() + "</TD>\n";
							}
						}
						
						maintable.append(tmp);
					}
				}
			}else{
				//页面排序
				int t = 0;
				for (int i = 0; i < columnList.size(); i++) {
					SysGridColumnBean bc = columnList.get(i);
					if (bc.isShowColumn()) {
						maintable.append("<TD class='tableTitle1' nowrap onclick=\"ts_resortTable(this, '" + t + "','" + this.getPath() + "');\">" + bc.getShowName()
								+ "&nbsp;<span class='sortarrow'></span></TD>\n");
						t++;
					}
				}
			}
		}
		maintable.append("</tr>\n");
		maintable.append("</table>\n");
		return maintable.toString();
	}

	private String createQuerySelect() {
		StringBuffer cols = new StringBuffer();
		cols.append("查询&nbsp;&nbsp;<select style =\"width:auto;\" id='simpleQueryParam' >");
		for (int i = 0; i < columnList.size(); i++) {
			SysGridColumnBean bc = columnList.get(i);
			if (bc.isShowQuerySelsect()) {
				cols.append("<option value='" + bc.getDataName() + "'>" + bc.getShowName() + "</option>\n");
			}
		}
		cols.append("</select>\n");
		cols.append("&nbsp;<input type='text' class='niceform' onkeydown='if(event.keyCode==13)" + queryFunction + "();' id='simpleQueryValue'/>&nbsp;");
		return cols.toString();
	}

	private String createBtn() {
		StringBuffer btns = new StringBuffer();
		for (int i = 0; i < btnList.size(); i++) {
			SysGridBtnBean btn = btnList.get(i);
			String tmp = "";
			if (btn.getBtnImage() != null && btn.getBtnImage().equalsIgnoreCase("default")) {
				tmp = "magnifier.png";
			} else {
				tmp = btn.getBtnImage();
			}
			btns.append("<div style='padding-top:1px;' class='grid_btn' onmouseover ='this.className =\"grid_btn_hover\"' onmouseout ='this.className =\"grid_btn\"'   onclick=\"" + btn.getBtnMethod()
					+ "\">&nbsp;<img class='grid_img'  src='" + this.getPath() + "/images/grid_images/" + tmp + "'/>&nbsp;" + btn.getBtnTitle() + "&nbsp;</div>\n");
		}
		return btns.toString();
	}

	private String createAdvancedQuery() {
		StringBuffer adquerys = new StringBuffer();
		if (columnList != null && columnList.size() > 0) {
			adquerys.append("<table width='99%' cellpadding='0' cellspacing='0' class='grid_table' border='0' id='resultTable'>\n");
			ArrayList<SysGridColumnBean> tmpList = new ArrayList<SysGridColumnBean>();
			for (int i = 0; i < columnList.size(); i++) {
				SysGridColumnBean bc = columnList.get(i);
				if (bc.isShowAdvanced()) {
					tmpList.add(bc);
				}
			}
			if (tmpList.size() > 0) {
				int colspan = 0;
				if (tmpList.size() % 2 != 0) {
					colspan = tmpList.size() % 2 + 1;
				}
				int td = 0;
				for (int i = 0; i < tmpList.size(); i++) {
					SysGridColumnBean col = tmpList.get(i);
					if (i % 2 == 0) {
						adquerys.append("<tr style='BACKGROUND-color:#grid_table;height:" + AdvancedRowHeight + "px'>\n");
					}
					adquerys.append("<td align='center' width='15%' nowrap='nowrap'>" + col.getShowName() + "</td>\n");
					adquerys.append("<td align='left' width='35%' nowrap='nowrap'>\n");
					adquerys.append(this.createColumeByType(col));
					adquerys.append("</td>\n");
					td++;
					if (i == tmpList.size() - 1 && colspan > 0) {
						adquerys.append("<td colspan='" + colspan + "'>&nbsp;</td>\n</tr>\n");
					}
					if (td == 2) {
						adquerys.append("</tr>\n");
						td = 0;
					}
				}
			}
			adquerys.append("</table>\n");
		}

		return adquerys.toString();
	}

	private String bindingCustomer(String[] customerFunction, String[] customerAttribute) {
		StringBuffer customer = new StringBuffer();
		if (customerFunction != null && customerFunction.length > 0) {
			for (int i = 0; i < customerFunction.length; i++) {
				customer.append(" " + customerFunction[i]);
			}
		}
		if (customerAttribute != null && customerAttribute.length > 0) {
			for (int j = 0; j < customerAttribute.length; j++) {
				customer.append(" " + customerAttribute[j]);
			}
		}
		return customer.toString();
	}

	private String createColumeByType(SysGridColumnBean column) {
		StringBuffer tmp = new StringBuffer();
		AbscolumnType columnType = column.getColumnTypeClass();
		if (columnType != null) {
			switch (columnType.getColumType()) {
			case GridColumnType.TYPE_CHECKBOX: {
				CheckBoxType checkbox = (CheckBoxType) columnType;
				tmp.append("<input type='checkbox' id='" + column.getDataName() + "'");
				if (checkbox.getValue() != null && checkbox.getValue().length() > 0) {
					tmp.append(" value= '" + checkbox.getValue() + "'");
				}
				if (checkbox.isIschecked()) {
					tmp.append(" checked='checked'");
				}
				tmp.append(this.bindingCustomer(checkbox.getCustomerFunction(), checkbox.getCustomerAttribute()));
				tmp.append("/>");
				if (checkbox.getShowText() != null && checkbox.getShowText().length() > 0) {
					tmp.append("<label for='" + column.getDataName() + "'>" + checkbox.getShowText() + "</label>\n");
				}
				break;
			}
			case GridColumnType.TYPE_RADIO: {
				RadioType radio = (RadioType) columnType;
				if (radio.getValue() != null && radio.getValue().length > 0) {
					for (int i = 0; i < radio.getValue().length; i++) {
						tmp.append("<input type='radio' name ='" + column.getDataName() + "' value ='" + radio.getValue()[i] + "' id ='" + column.getDataName() + i + "'");
						if (radio.getCheckedValue() != null && radio.getCheckedValue().length() > 0 && radio.getValue()[i].equals(radio.getCheckedValue())) {
							tmp.append(" checked='checked'");
						}
						tmp.append(this.bindingCustomer(radio.getCustomerFunction(), radio.getCustomerAttribute()));
						tmp.append("/>");
						tmp.append("<label for='" + column.getDataName() + i + "'>" + radio.getShowText()[i] + "</label>\n");
					}
				}
				break;
			}
			case GridColumnType.TYPE_DATE: {
				DateType date = (DateType) columnType;
				String df = "";
				String cust = this.bindingCustomer(date.getCustomerAttribute(), date.getCustomerAttribute());
				if (date.getDefaultDate() != null && date.getDefaultDate().length() > 0) {
					df = "value='" + date.getDefaultDate() + "'";
				}
				if (date.isStratAndEnd()) {
					String sdid = column.getDataName() + "StartDate";
					String edid = column.getDataName() + "EndDate";
					tmp.append("<input id='"+sdid+"' class='Wdate' type='text' onfocus=\"WdatePicker({dateFmt:'" + date.getDateFmt() + "',maxDate:'#F{$dp.$D(\\'"+edid+"\\')}'})\" readonly='readonly' " + df
							+ " " + cust + "/>&nbsp;至&nbsp;");
					tmp.append("<input id='"+edid+"' class='Wdate' type='text' onfocus=\"WdatePicker({dateFmt:'" + date.getDateFmt() + "',minDate:'#F{$dp.$D(\\'"+sdid+"\\')}'})\" readonly='readonly' " + df
							+ " " + cust + "/>");
				} else {
					tmp.append("<input id='" + column.getDataName() + "StartDate' class='Wdate' type='text' onfocus=\"WdatePicker({dateFmt:'" + date.getDateFmt() + "'});\" readonly='readonly' " + df
							+ " " + cust + "/>");
				}
				break;
			}
			case GridColumnType.TYPE_TIME: {
				TimeType time = (TimeType) columnType;
				String tf = "";
				String cust = this.bindingCustomer(time.getCustomerAttribute(), time.getCustomerAttribute());
				if (time.getDefaultTime() != null && time.getDefaultTime().length() > 0) {
					tf = "value='" + time.getDefaultTime() + "'";
				}
				if (time.isStratAndEnd()) {
					tmp.append("<input id='" + column.getDataName() + "StartTime' class='Wtime' type='text' onfocus=\"WdatePicker({dateFmt:'" + time.getTimeFmt() + "'});\" readonly='readonly' " + tf
							+ " " + cust + "/>&nbsp;至&nbsp;");
					tmp.append("<input id='" + column.getDataName() + "EndTime' class='Wtime' type='text' onfocus=\"WdatePicker({dateFmt:'" + time.getTimeFmt() + "'});\" readonly='readonly' " + tf
							+ " " + cust + "/>");
				} else {
					tmp.append("<input id='" + column.getDataName() + "Start' class='Wtime' type='text' onfocus=\"WdatePicker({dateFmt:'" + time.getTimeFmt() + "'});\" readonly='readonly' " + tf
							+ " " + cust + "/>");
				}
				break;
			}
			case GridColumnType.TYPE_SELECT: {
				SelectType select = (SelectType) columnType;
				tmp.append("<select id='" + column.getDataName() + "'");
				tmp.append(this.bindingCustomer(select.getCustomerFunction(), select.getCustomerAttribute()));
				tmp.append(">\n");
				if (select.getOptions() != null && select.getOptions().length() > 0) {
					String[] options = select.getOptions().split("\\|");
					for (int j = 0; j < options.length; j++) {
						String[] tmpstr = options[j].split(",");
						String ck = "";
						if (select.getDefaultChecked() != null && !"-1".equals(select.getDefaultChecked()) && tmpstr[0].equals(select.getDefaultChecked())) {
							ck = "selected='selected'";
						}
						tmp.append("<option value ='" + tmpstr[0] + "' " + ck + ">" + tmpstr[1] + "</option>\n");
					}
				}
				tmp.append("</select>\n");
				break;
			}
			case GridColumnType.TYPE_TEXT: {
				TextType text = (TextType) columnType;
				String df = "";
				String cust = this.bindingCustomer(text.getCustomerAttribute(), text.getCustomerAttribute());
				if (text.getDefaultText() != null && text.getDefaultText().length() > 0) {
					df = "value = '" + text.getDefaultText() + "'";
				}
				tmp.append("<input type='text' class='niceform' id='" + column.getDataName() + "' " + df + " " + cust + "/>\n");
				break;
			}
			default: {
				OtherType other = (OtherType) columnType;
				if (other.getHtml() != null && other.getHtml().length() > 0) {
					tmp.append(other.getHtml() + "\n");
				}
				break;
			}
			}
		} else {// 为空显示为文本框
			tmp.append("<input type='text' class='niceform' id='" + column.getDataName() + "'/>\n");
		}
		return tmp.toString();
	}

	private String createTableColumn() {
		StringBuffer tablecols = new StringBuffer();
		tablecols.append("<table width='100%' cellpadding='0' cellspacing='0' class='grid_table' border='0' id='listtablecolumn'>\n");
		tablecols.append("<tr style='BACKGROUND-IMAGE: url(" + this.getPath() + "/images/grid_images/bgt.png);height:" + AdvancedRowHeight + "px'>\n");
		tablecols.append("<td align='right'>\n");
		if (columnList != null && columnList.size() > 0) {
			int t = 0;
			for (int i = 0; i < columnList.size(); i++) {
				SysGridColumnBean bc = columnList.get(i);
				if (bc.isShowColumn()) {
					tablecols.append("<input style='margin:0px;padding:0px;height:13px;width:18px;' type='checkbox' checked='checked' onclick='colsControl(tableResult," + t + ",this.checked);' id='checkbox" + t + "'/><label for='checkbox" + t + "'>"
							+ bc.getShowName() + "</label>&nbsp;&nbsp;\n");
					t++;
				}
			}
		}
		tablecols.append("</td>\n");
		tablecols.append("</tr>\n");
		tablecols.append("</table>\n");
		return tablecols.toString();
	}

	private String createImgProcess() {
		StringBuffer tablecols = new StringBuffer();
		String tmp="";
		if(getAdvanedCount()>0){
			tmp="border-top:1px solid #D8D6D6";	
		}
		tablecols.append("<table width='100%' cellpadding='0' cellspacing='0' class='grid_table' border='0' id='listimgprocess' style='display:none;"+tmp+"'>\n");
		tablecols.append("<tr style='BACKGROUND-color:#F6F8F9;height:" + AdvancedRowHeight + "px'>\n");
		tablecols.append("<td nowrap='nowrap' align='right' id ='imgviewsize' style='display:none;'>\n");
		tablecols.append("<table cellpadding='0' cellspacing='0' border='0'>");
		tablecols.append("<tr>\n");
		tablecols.append("<td>显示比例:&nbsp;<img border='0' src='" + this.getPath()
				+ "/images/grid_images/imgmin.gif' alt='缩小' style ='vertical-align: middle;cursor:pointer;' onclick = 'changimg(1);'/></td>");
		tablecols.append("<td id='sliderdiv' width='200'></td>");
		tablecols.append("<td style='padding-left:0px;'><img border='0' src='" + this.getPath()
				+ "/images/grid_images/imgmax.gif' alt='放大' style ='vertical-align: middle;cursor:pointer;' onclick = 'changimg(2);'/></td>");
		tablecols.append("</tr>\n");
		tablecols.append("</table>");
		tablecols.append("</td>\n");
		tablecols.append("<td align='right' width ='110' id='imgchktitle'>\n");
		String ck = "checked='checked'";
		if (!isShowImageDetail) {
			ck = "";
		}
		tablecols.append("<input  style='margin:0px;padding:0px;height:13px;width:18px;' type ='checkbox' id='showorhiddenimgdiv' " + ck + "/><label for='showorhiddenimgdiv'>显示图片提示</label>&nbsp;&nbsp;\n");
		tablecols.append("</td>\n");
		tablecols.append("</tr>\n");
		tablecols.append("</table>\n");
		return tablecols.toString();
	}

	// ============================创建js脚本===================
	private String initJaveScript() {
		StringBuffer js = new StringBuffer();

		js.append("<script type='text/javascript' src='"+this.getPath()+"/js/sysgrid.js'></script>\n");
		js.append("<script type='text/javascript' src='"+this.getPath()+"/js/sys_grid/gridvaluetype.js'></script>\n");
		js.append("<link type='text/css' rel='stylesheet' href='"+this.getPath()+"/js/slider/dhtmlxslider.css'/>\n");
		js.append("<script type='text/javascript' src='"+this.getPath()+"/js/slider/dhtmlxcommon.js'></script>\n");
		js.append("<script type='text/javascript' src='"+this.getPath()+"/js/slider/dhtmlxslider.js'></script>\n");
		js.append("<script type='text/javascript' src='"+this.getPath()+"/js/slider/ext/dhtmlxslider_start.js'></script>\n");
		js.append("<script type='text/javascript'>window.dhx_globalImgPath='"+this.getPath()+"/js/slider/imgs/';</script>\n");
		js.append("<script type='text/javascript' src='"+this.getPath()+"/js/sys_grid/sysgrid.js'></script>\n");
		js.append("<script type='text/javascript' src='"+this.getPath()+"/js/sys_grid/table.js'></script>\n");
		
		if(this.showView==SHOW_ALL || this.showView==SHOW_TABLE){
			js.append("<script type='text/javascript' src='"+this.getPath()+"/js/sys_grid/tdsort.js'></script>\n");
			js.append("<script type='text/javascript' src='"+this.getPath()+"/js/sys_grid/sorttab.js'></script>\n");
		}
		
		js.append("<script type=\"text/javascript\">\n");
		js.append("var sysGrid = new Object();\n");
		js.append("sysGrid.bodyscroll = \"" + bodyScroll + "\";\n");
		js.append("sysGrid.isAutoQuery = " + isautoQuery + ";\n");
		js.append("sysGrid.showView = " + this.showView + ";\n");
		js.append("sysGrid.queryFunction =\"" + queryFunction + "\";//查询的方法名\n");
		js.append("sysGrid.contextPath =\"" + this.getPath() + "\";\n");
		js.append("sysGrid.defaultShow =\"" + this.defaultShow + "\";\n");
		js.append("sysGrid.isshowBlank =" + this.isShowBlankTable + ";\n");
		js.append("sysGrid.rowHeight =" + this.rowHeight + ";\n");
		
		if (this.showView == SHOW_ALL || this.showView == SHOW_TABLE) {
			js.append("sysGrid.isCheckBox = " + isCheckboxOrNum + ";\n");
			js.append("sysGrid.isShowProcess = " + isShowProcess + ";\n");
			js.append("sysGrid.processMethodName =\"" + processMethodName + "\";//生成行操作对象方法\n");
		}
		if (this.showView == SHOW_ALL || this.showView == SHOW_IMAGE) {
			js.append("sysGrid.imgShowNum = " + imgShowNum + ";//每页显示图片列数\n");
			js.append("sysGrid.imgShowUrl = \"" + imgShowUrl + "\";//显示图片的字段\n");
			js.append("sysGrid.imgWidth = \"" + imgwidth + "\";\n");
			js.append("sysGrid.imgHeight = \"" + imgheight + "\";\n");
			js.append("sysGrid.imgShowText = \"" + imgShowText + "\";\n");
			js.append("sysGrid.imgdivWidth = " + imgdivwidth + ";\n");
			js.append("sysGrid.imgShowCode = \"" + imgShowCode + "\";\n");
			js.append("sysGrid.imgTextLen = " + imgShowTextLen + ";\n");
			js.append("sysGrid.imgMethod = "+this.imgShowMethod+";\n");
		}
		if (dblFunction != null && dblFunction.length() > 0) {
			js.append("sysGrid.dblFunction =\"" + dblFunction + "\";//双击列的方法名\n");
		}
		if (dblBundle != null && dblBundle.length() > 0) {
			js.append("sysGrid.dblBundle =\"" + dblBundle + "\";//双击列的绑定的列值\n");
		}
		if (columnList != null && columnList.size() > 0) {
			if (getAdvanedCount() > 0) {
				// 绑定高级查询
				js.append("sysGrid.advancedlist = new Array();//高级查询\n");
				int c = 0;
				for (int i = 0; i < columnList.size(); i++) {
					SysGridColumnBean bc = columnList.get(i);
					if (bc.isShowAdvanced()) {
						String tmp = bc.getColumnTypeClass().getTypeValue() + "('" + bc.getDataName() + "')";
						if (bc.getColumnTypeClass().getColumType() == GridColumnType.TYPE_OTHER) {
							if (bc.getColumnTypeClass().getTypeValue() != null && bc.getColumnTypeClass().getTypeValue().length() > 0) {
								tmp = bc.getColumnTypeClass().getTypeValue();
							}
						}
						js.append("sysGrid.advancedlist[" + c + "] = \"" + bc.getDataName() + "@@" + tmp + "@@" + bc.isColumnToObject() + "\";\n");
						c++;
					}
				}
			}
			// 绑定列
			js.append("sysGrid.collist = new Array();//列\n");
			int l = 0;
			for (int i = 0; i < columnList.size(); i++) {
				SysGridColumnBean bc = columnList.get(i);
				if (bc.isShowColumn()) {
					js.append("sysGrid.collist[" + l + "] = \"" + bc.getDataName() + "@@" + bc.getColumnReplace() + "@@" + bc.getColumnStrCount() + "@@" + bc.getShowName() + "\";\n");
					l++;
				}
			}

			// 绑定列样式
			js.append("sysGrid.colstyle = new Array();//列样式\n");
			int s = 0;
			for (int i = 0; i < columnList.size(); i++) {
				SysGridColumnBean bc = columnList.get(i);
				if (bc.getColumnStyle() != null && bc.getColumnStyle().length() > 0) {
					js.append("sysGrid.colstyle[" + s + "] = \"" + bc.getDataName() + "@@" + bc.getColumnStyle() + "\";\n");
					s++;
				}
			}
		}
		js.append("sysGridConfing.init(sysGrid);\n");
		js.append("</script>\n");
		return js.toString();
	}
}