package com.pinhuba.mobile.action;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.ResponseUtils;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.iservice.IOaNewsService;
import com.pinhuba.core.iservice.ISysProcessService;
import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.OaAnnouncement;
import com.pinhuba.core.pojo.SysAttachmentInfo;

@Controller
@RequestMapping("/mobile/")
public class AnnounceAction {

	@Resource
	private IOaNewsService oaNewsService;

	@Resource
	private IHrmEmployeeService employeeinfoService;

	@Resource
	private ISysProcessService sysProcessService;

	@RequestMapping(value = "announceList.do")
	public void listAnnouncement(String sessionId, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		JSONObject json = new JSONObject();

		HrmEmployee employee = UtilTool.getEmployee(sessionId, request);

		if (employee != null) {
			OaAnnouncement announce = new OaAnnouncement();
			Pager pager = new Pager();
			pager.setCurrentPage(currentPage);
			pager.setPageSize(pageSize);

			List<OaAnnouncement> list = null;

			int totalRows = oaNewsService.listAnnouncementCount(announce, employee.getCompanyId());
			pager = PagerHelper.getPager(pager, totalRows);
			list = oaNewsService.getAllAnnouncement(announce, employee.getCompanyId(), pager);
			for (OaAnnouncement oaAnnouncement : list) {
				oaAnnouncement.setOaAnnoLib(oaNewsService.getLibraryInfoByPK(oaAnnouncement.getOaAnnoType()));
				oaAnnouncement.setEmployee(employeeinfoService.getEmployeeByPK(oaAnnouncement.getOaAnnoEmp()));
			}

			json.put("code", 0);
			json.put("totalRows", totalRows);
			json.put("list", list);
		} else {
			json.put("code", 1);
			json.put("errorMsg", "请登录后再进行操作！");
		}
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequestMapping(value = "announceDetail.do")
	public void getAnnouncementByPk(String sessionId, long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		JSONObject json = new JSONObject();

		HrmEmployee employee = UtilTool.getEmployee(sessionId, request);

		if (employee != null) {
			OaAnnouncement announce = oaNewsService.getAnnouncementByPK(id);
			announce.setOaAnnoLib(oaNewsService.getLibraryInfoByPK(announce.getOaAnnoType()));
			announce.setEmployee(employeeinfoService.getEmployeeByPK(announce.getOaAnnoEmp()));

			List<SysAttachmentInfo> list = sysProcessService.getAttachmentInfoListByIds(announce.getOaAnnoAcce());

			json.put("code", 0);
			json.put("announce", announce);
			json.put("attachmentList", list);
		} else {
			json.put("code", 1);
			json.put("errorMsg", "请登录后再进行操作！");
		}
		ResponseUtils.renderJson(response, json.toString());
	}

}
