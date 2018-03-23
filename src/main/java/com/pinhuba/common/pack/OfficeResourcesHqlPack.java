package com.pinhuba.common.pack;

import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.core.pojo.OaBoardroom;
import com.pinhuba.core.pojo.OaBook;
import com.pinhuba.core.pojo.OaBookBr;
import com.pinhuba.core.pojo.OaBookType;
import com.pinhuba.core.pojo.OaCar;
import com.pinhuba.core.pojo.OaCarApply;
import com.pinhuba.core.pojo.OaCarMaintain;
import com.pinhuba.core.pojo.OaMeetapply;
import com.pinhuba.core.pojo.OaSummary;

/**
 * 办公资源模块查询语句封装
 * @author peng.ning
 *
 */
public class OfficeResourcesHqlPack {

	/**
	 * 办公资源-会议室
	 * @param oaBoardroom 会议室对象
	 * @return 
	 */
	public static String packOaBoardroomQuery(OaBoardroom oaBoardroom){
		StringBuffer result = new StringBuffer();
		
		HqlPack.getStringLikerPack(oaBoardroom.getOaBoardroomName(), "oaBoardroomName", result);
		HqlPack.getStringLikerPack(oaBoardroom.getOaBoardroomAddress(), "oaBoardroomAddress", result);
		HqlPack.getNumEqualPack(oaBoardroom.getOaBoardroomCapacity(), "oaBoardroomCapacity",result);
		HqlPack.getNumEqualPack(oaBoardroom.getCompanyId(), "companyId",result);
		
		if(oaBoardroom.getOaBoardroomCapacity()!= null){
			result.append(" and model.oaBoardroomCapacity >= "+oaBoardroom.getOaBoardroomCapacity());
		}
		
		return result.toString();
	}
	/*
	 * 申请会议时，选择会议室的时候列出会议室的状态
	 */
	public static String packOaBoadRoomStatusQuery(String sdate,String edate,int companyId){
		StringBuffer result = new StringBuffer();
		HqlPack.getNumEqualPack(companyId, "companyId",result);
		HqlPack.getNumNOEqualPack(EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value, "oaMeetapplyStatus", result);
		if (sdate!=null&&sdate.length()>0) {
			result.append(" and '"+sdate+"' between model.oaMeetapplyStar and model.oaMeetapplyEnd");
		}
		if (edate!=null&&edate.length()>0) {
			result.append(" or ('"+edate+"' between model.oaMeetapplyStar and model.oaMeetapplyEnd )");
		}
		if (sdate!=null&&sdate.length()>0&&edate!=null&&edate.length()>0) {
			result.append(" or ( model.oaMeetapplyStar>'"+sdate+"' and model.oaMeetapplyEnd<'"+edate+"' ) ");
		}
		
		return  result.toString();
	}
	
	
	
	
	 public static String packoaapply(long i){
			StringBuffer result = new StringBuffer();
			HqlPack.getNumEqualPack(i, "carId", result);
			
			return result.toString();
	 }
	/**
	 * 车辆选择
	 * @param sdate
	 * @param edate
	 * @param companyId
	 * @return
	 */
	public static String packOaApplyQuery(String sdate,String edate,int companyId){
		StringBuffer result = new StringBuffer();
		HqlPack.getNumEqualPack(companyId, "companyId",result);
		HqlPack.getNumNOEqualPack(EnumUtil.OA_CAR_STATUS.INUSE.value, "oaCarStatus", result);
		if (sdate!=null&&sdate.length()>0) {
			result.append(" and '"+sdate+"' between model.applyBegindate and model.applyEnddate");
		}
		if (edate!=null&&edate.length()>0) {
			result.append(" or ('"+edate+"' between model.applyBegindate and model.applyEnddate )");
		}
		if (sdate!=null&&sdate.length()>0&&edate!=null&&edate.length()>0) {
			result.append(" or ( model.applyBegindate>'"+sdate+"' and model.applyEnddate<'"+edate+"' ) ");
		}
		
		if(sdate!=null && sdate.length()>0){
			result.append("or (model.applyEnddate>'"+sdate+"' and model.applyEnddate<'"+edate+"')");
		}
		
		return  result.toString();
	}
	
	
	/**
	 * 办公资源-会议申请
	 * @param oaMeetapply 会议申请对象
	 * @return 
	 */
	public static String packOaMeetapplyQuery(OaMeetapply oaMeetapply) {
		StringBuffer result = new StringBuffer();
		result.append(" select meet.* from oa_meetapply meet,oa_boardroom room,hrm_employee emp where meet.oa_meetapply_emp = emp.hrm_employee_id and meet.oa_meetapply_room = room.oa_boardroom_id ");
		SqlPack.getStringLikerPack(oaMeetapply.getOaMeetapplyName(), "meet.oa_meetapply_name", result);
		if (oaMeetapply.getEmployee()!=null) {
			SqlPack.getStringLikerPack(oaMeetapply.getEmployee().getHrmEmployeeName(), "emp.hrm_employee_name", result);
		}
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyType(), "meet.oa_meetapply_type",result);
		if (oaMeetapply.getMeetApplyRoomObj()!=null) {
			SqlPack.getStringLikerPack(oaMeetapply.getMeetApplyRoomObj().getOaBoardroomName(), "room.oa_boardroom_name", result);
		}

		SqlPack.timeBuilder(oaMeetapply.getOaMeetapplyStar(), "meet.oa_meetapply_star",result,false,true);
		SqlPack.timeBuilder(oaMeetapply.getOaMeetapplyEnd(), "meet.oa_meetapply_end",result,false,true);
		
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyRoom(), "meet.oa_meetapply_room",result);
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyDegree(), "meet.oa_meetapply_degree",result);
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyStatus(), "meet.oa_meetapply_status",result);
		SqlPack.getNumEqualPack(oaMeetapply.getCompanyId(), "meet.company_id",result);
		return result.toString();
	}
	/**
	 * 只查询我申请的会议
	 * @param oaMeetapply
	 * @param emdId
	 * @return
	 */
	public static String packMyMeetapply(OaMeetapply oaMeetapply,String emdId){
		StringBuffer result = new StringBuffer();
		result.append(" select meet.* from oa_meetapply meet,oa_boardroom room where meet.oa_meetapply_emp='"+emdId+"'"+"and meet.oa_meetapply_room=room.oa_boardroom_id ");
		SqlPack.getStringLikerPack(oaMeetapply.getOaMeetapplyName(), "meet.oa_meetapply_name", result);
		if (oaMeetapply.getEmployee()!=null) {
			SqlPack.getStringEqualPack(oaMeetapply.getEmployee().getHrmEmployeeName(), "emp.hrm_employee_name", result);
		}
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyType(), "meet.oa_meetapply_type",result);
		
		if (oaMeetapply.getMeetApplyRoomObj()!=null) {
			SqlPack.getStringLikerPack(oaMeetapply.getMeetApplyRoomObj().getOaBoardroomName(), "room.oa_boardroom_name", result);
		}
		
		SqlPack.timeBuilder(oaMeetapply.getOaMeetapplyStar(), "meet.oa_meetapply_star",result,false,true);
		SqlPack.timeBuilder(oaMeetapply.getOaMeetapplyEnd(), "meet.oa_meetapply_end",result,false,true);
		
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyDegree(), "meet.oa_meetapply_degree",result);
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyStatus(), "meet.oa_meetapply_status",result);
		SqlPack.getNumEqualPack(oaMeetapply.getCompanyId(), "meet.company_id",result);
		return result.toString();
	}
	/**
	 * 列出待我参加的会议
	 * @param oaMeetapply
	 * @param empnId
	 * @return
	 */
	public static String packWillAttendMeet(OaMeetapply oaMeetapply,String empnId){
		StringBuffer result = new StringBuffer();
		result.append(" select meet.* from oa_meetapply meet,oa_boardroom room where meet.oa_meetapply_empn like '%"+empnId+"%' and meet.oa_meetapply_status="+EnumUtil.OA_MEETAPPLY_STATUS.APPLYING.value+" and meet.oa_meetapply_room=room.oa_boardroom_id ");
		SqlPack.getStringLikerPack(oaMeetapply.getOaMeetapplyName(), "meet.oa_meetapply_name", result);
//		if (oaMeetapply.getEmployee()!=null) {
//			SqlPack.getStringEqualPack(oaMeetapply.getEmployee().getHrmEmployeeName(), "emp.hrm_employee_name", result);
//		}
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyType(), "meet.oa_meetapply_type",result);
		if (oaMeetapply.getMeetApplyRoomObj()!=null) {
			SqlPack.getStringLikerPack(oaMeetapply.getMeetApplyRoomObj().getOaBoardroomName(), "room.oa_boardroom_name", result);
		}
		
		SqlPack.timeBuilder(oaMeetapply.getOaMeetapplyStar(), "meet.oa_meetapply_star",result,false,true);
		SqlPack.timeBuilder(oaMeetapply.getOaMeetapplyEnd(), "meet.oa_meetapply_end",result,false,true);
		
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyDegree(), "meet.oa_meetapply_degree",result);
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyStatus(), "meet.oa_meetapply_status",result);
		SqlPack.getNumEqualPack(oaMeetapply.getCompanyId(), "meet.company_id",result);
		return result.toString();
	}
	
	
	
	/**
	 * 列出已参加的会议
	 * @param oaMeetapply
	 * @param empnId
	 * @return
	 */
	public static String packAttendedMeet(OaMeetapply oaMeetapply,String empnId){
		StringBuffer result = new StringBuffer();
		result.append(" select meet.* from oa_meetapply meet,oa_boardroom room where meet.oa_meetapply_empn like '%"+empnId+"%' and meet.oa_meetapply_status="+EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value+" and meet.oa_meetapply_room=room.oa_boardroom_id ");
		SqlPack.getStringLikerPack(oaMeetapply.getOaMeetapplyName(), "meet.oa_meetapply_name", result);
		if (oaMeetapply.getEmployee()!=null) {
			SqlPack.getStringEqualPack(oaMeetapply.getEmployee().getHrmEmployeeName(), "emp.hrm_employee_name", result);
		}
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyType(), "meet.oa_meetapply_type",result);
		if (oaMeetapply.getMeetApplyRoomObj()!=null) {
			SqlPack.getStringLikerPack(oaMeetapply.getMeetApplyRoomObj().getOaBoardroomName(), "room.oa_boardroom_name", result);
		}
		
		SqlPack.timeBuilder(oaMeetapply.getOaMeetapplyStar(), "meet.oa_meetapply_star",result,false,true);
		SqlPack.timeBuilder(oaMeetapply.getOaMeetapplyEnd(), "meet.oa_meetapply_end",result,false,true);
		
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyDegree(), "meet.oa_meetapply_degree",result);
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyStatus(), "meet.oa_meetapply_status",result);
		SqlPack.getNumEqualPack(oaMeetapply.getCompanyId(), "meet.company_id",result);
		return result.toString();
	}
	/*
	 * 列出我是会议纪要人的会议
	 */
	public static String packMySummaryMeet(OaMeetapply oaMeetapply,String empId){
		StringBuffer result = new StringBuffer();
		result.append(" select meet.* from oa_meetapply meet where meet.oa_meetapply_summary like '%"+empId+"%'"+"and meet.oa_meetapply_status <>"+EnumUtil.OA_MEETAPPLY_STATUS.APPLYING.value);
		SqlPack.getStringLikerPack(oaMeetapply.getOaMeetapplyName(), "meet.oa_meetapply_name", result);
//		if (oaMeetapply.getEmployee()!=null) {
//			SqlPack.getStringEqualPack(oaMeetapply.getEmployee().getHrmEmployeeName(), "emp.hrm_employee_name", result);
//		}
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyType(), "meet.oa_meetapply_type",result);
		if (oaMeetapply.getMeetApplyRoomObj()!=null) {
			SqlPack.getStringLikerPack(oaMeetapply.getMeetApplyRoomObj().getOaBoardroomName(), "room.oa_boardroom_name", result);
		}
		
		SqlPack.timeBuilder(oaMeetapply.getOaMeetapplyStar(), "meet.oa_meetapply_star",result,false,true);
		SqlPack.timeBuilder(oaMeetapply.getOaMeetapplyEnd(), "meet.oa_meetapply_end",result,false,true);
		
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyDegree(), "meet.oa_meetapply_degree",result);
		SqlPack.getNumEqualPack(oaMeetapply.getOaMeetapplyStatus(), "meet.oa_meetapply_status",result);
		SqlPack.getNumEqualPack(oaMeetapply.getCompanyId(), "meet.company_id",result);
		return result.toString();
	}
	
	/**
	 * 办公资源-会议纪要
	 * @param oaSummary 会议纪要对象
	 * @return
	 */
	public static String packOaSummaryQuery(OaSummary oaSummary) {
		StringBuffer result = new StringBuffer();
		result.append(" select summary.* from oa_summary summary,oa_meetapply meet where summary.oa_summary_meet_id = meet.oa_meetapply_id ");
		SqlPack.getStringLikerPack(oaSummary.getOaSummaryName(), "summary.oa_summary_name", result);
		SqlPack.getStringLikerPack(oaSummary.getOaSummaryMeetId().toString(), "summary.oa_summary_meet_id", result);
		SqlPack.timeBuilder(oaSummary.getOaSummaryDate(), "summary.oa_summary_date",result,false,true);
		SqlPack.getNumEqualPack(oaSummary.getCompanyId(), "summary.company_id",result);
		return result.toString();
	}
	
	/**
	 * 办公资源-车辆
	 * @param oaCar 车辆
	 * @return
	 */
	//高级查询设置参数
	public static String packOaCarQuery(OaCar oaCar) {
		StringBuffer result = new StringBuffer();
		
		HqlPack.getStringLikerPack(oaCar.getOaCarCards(), "oaCarCards", result);
		
		HqlPack.getNumEqualPack(oaCar.getOaCarType(), "oaCarType",result);
		HqlPack.getStringLikerPack(oaCar.getOaCarName(), "oaCarName",result);
		HqlPack.getStringLikerPack(oaCar.getOaCarMotorman(), "oaCarMotoeman",result);
	//	HqlPack.getNumEqualPack(oaCar.getOaCarStatus(), "oaCarStatus",result);
		HqlPack.timeBuilder(oaCar.getOaCarBuydate(), "oaCarBuydate",result,false,false);
		HqlPack.getNumEqualPack(oaCar.getCompanyId(), "companyId",result);
		HqlPack.getNumEqualPack(oaCar.getOaCarSta(), "oaCarSta", result);
		return result.toString();
	}
	
	/**
	 * 办公资源-车辆申请
	 * @param oaCarUse 车辆申请
	 * @return
	 */

	public static String packOaCarUseQuery(OaCarApply oaCarApply) {
		StringBuffer result = new StringBuffer();
		result.append("select distinct app.* from oa_car_apply app,oa_car car,hrm_employee emp where app.apply_user = emp.hrm_employee_id and app.car_id = car.oa_car_id ");
		if (oaCarApply.getApplyEmployee()!=null) {
			SqlPack.getStringLikerPack(oaCarApply.getApplyEmployee().getHrmEmployeeName(), "emp.hrm_employee_name", result);
		}
		if (oaCarApply.getOaCar()!=null) {
			SqlPack.getStringLikerPack(oaCarApply.getOaCar().getOaCarName(), "car.oa_car_name", result);
		}
		SqlPack.getNumEqualPack(oaCarApply.getCarId(), "app.car_id", result);
		SqlPack.getStringEqualPack(oaCarApply.getApplyUser(), "apply_user", result);
		SqlPack.getNumEqualPack(oaCarApply.getCompanyId(), "app.company_id", result);
		SqlPack.timeBuilder(oaCarApply.getApplyBegindate(), "app.apply_begindate", result, false, true);
		SqlPack.timeBuilder(oaCarApply.getApplyEnddate(), "app.apply_enddate", result, false, true);
		
		return result.toString();
	}
	/**
	 * 删除条件车辆
	 * @param oaCarUse 车辆申请
	 * @return
	 */public static String packOaCarapplyde(OaCarApply oaCarApply){
			StringBuffer result = new StringBuffer();
			HqlPack.getNumEqualPack(oaCarApply.getCarId(),"carId", result);
			return result.toString();
	 }
	/**
	 * 办公资源-车辆维护
	 * @param oaCarMainten 车辆维护
	 * @return
	 */

	public static String packOaCarMaintenQuery(OaCarMaintain oaCarMainten) {
		StringBuffer result = new StringBuffer();
		HqlPack.getNumEqualPack(oaCarMainten.getCarId(), "carId", result);
		HqlPack.getStringEqualPack(oaCarMainten.getMaintainDate(), "maintainDate", result);
		HqlPack.getStringEqualPack(oaCarMainten.getMaintainMoney(), "maintainMoney",result);
		HqlPack.getStringEqualPack(oaCarMainten.getMaintainUser(), "maintainUser",result);
		HqlPack.getNumEqualPack(oaCarMainten.getMaintainType(), "maintainType",result);
		HqlPack.getNumEqualPack(oaCarMainten.getCompanyId(), "companyId",result);
		return result.toString();
	}
	
	/**
	 * 办公资源-图书
	 * @param oaBook 图书信息
	 * @return
	 */
	public static String packOaBookQuery(OaBook oaBook) {
		StringBuffer result = new StringBuffer();
		result.append("select book.* from oa_book book,hrm_employee emp where book.oa_book_booker = emp.hrm_employee_id and book.company_id = emp.company_id");
		
		SqlPack.getStringLikerPack(oaBook.getOaBookCode(), "book.oa_book_code", result);
		SqlPack.getStringLikerPack(oaBook.getOaBookName(), "book.oa_book_name",result);
		if(oaBook.getEmployee() != null){
	        SqlPack.getStringLikerPack(oaBook.getEmployee().getHrmEmployeeName(), "emp.hrm_employee_name", result);
	    }
		SqlPack.getNumEqualPack(oaBook.getOaBookDep(), "book.oa_book_dep",result);
		SqlPack.getStringLikerPack(oaBook.getOaBookAuthor(), "book.oa_book_author",result);
		SqlPack.getStringLikerPack(oaBook.getOaBookAddress(), "book.oa_book_address",result);
		SqlPack.getStringLikerPack(oaBook.getOaBookConcern(), "book.oa_book_concern",result);
		SqlPack.getNumEqualPack(oaBook.getOaBookType(), "book.oa_book_type",result);
		SqlPack.getNumEqualPack(oaBook.getCompanyId(), "book.company_id",result);
		
		return result.toString();
	}

	
	/**
	 * 
	 * @param oaBookBr 借书信息
	 * @return
	 */
	public static String packOaBookBrQuery(OaBookBr oaBr) {
		StringBuffer result = new StringBuffer();
		result.append("select br.* from oa_book_br br,OA_BOOK book where br.oa_br_bookid = book.oa_book_id and br.company_id = book.company_id");
		
		if(oaBr.getBookInfo() != null){
			SqlPack.getStringLikerPack(oaBr.getBookInfo().getOaBookName(), "book.oa_book_name", result);
		}
		SqlPack.getStringEqualPack(oaBr.getOaBrBooker(), "br.oa_br_booker", result);
		SqlPack.timeBuilder(oaBr.getOaBrBdate()," br.oa_br_bdate", result, false, false);
		SqlPack.timeBuilder(oaBr.getOaBrRdate()," br.oa_br_rdate", result, false, false);
		SqlPack.getStringLikerPack(oaBr.getOaBrLendw(), "br.oa_br_lendw", result);
		SqlPack.getNumEqualPack(oaBr.getOaBrStatus(), "br.oa_br_status", result);
		
		SqlPack.getNumEqualPack(oaBr.getCompanyId(), "br.company_id",result);
		
		return result.toString();
	}

	/**
	 * 
	 * @param oaBookType 图书类别
	 * @return
	 */
	public static String packOaBookTypeQuery(OaBookType oaBookType,long com) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(oaBookType.getOaBooktypeName(), "oaBooktypeName", result);
	    HqlPack.getNumEqualPack(com, "companyId",result);
		
		return result.toString();
	}
	
	//申请车辆取出未报废的
	public static String packOacarstatic(OaCar car){
		StringBuffer result = new StringBuffer();
		HqlPack.getNumEqualPack(1, "oaCarSta", result);
		HqlPack.getStringLikerPack(car.getOaCarName(), "oaCarName", result);
		HqlPack.getStringLikerPack(car.getOaCarCards(), "oaCarCards", result);
		HqlPack.getNumEqualPack(car.getCompanyId(), "companyId", result);
		return result.toString();

	}
	public static String packOacar(String ids){
		StringBuffer result = new StringBuffer();
		HqlPack.getInPack(ids, "primaryKey", result);
		return result.toString();
	}
	

}
