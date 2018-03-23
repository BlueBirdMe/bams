package com.pinhuba.core.iservice;

import java.util.List;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.core.pojo.OaBoardroom;
import com.pinhuba.core.pojo.OaBook;
import com.pinhuba.core.pojo.OaBookBr;
import com.pinhuba.core.pojo.OaBookType;
import com.pinhuba.core.pojo.OaCar;
import com.pinhuba.core.pojo.OaCarApply;
import com.pinhuba.core.pojo.OaCarMaintain;
import com.pinhuba.core.pojo.OaMeetapply;
import com.pinhuba.core.pojo.OaSummary;
import com.pinhuba.core.pojo.SysLibraryInfo;

public interface IOfficeResourcesService {
	
	public int getappy(long carpks);
	
	public int getmaintain(long carpks);
	
	public int listBoadroomCount(OaBoardroom oaBoardroom);

	public List<OaBoardroom> getAllBoadroom(OaBoardroom oaBoardroom, Pager pager);

	public OaBoardroom saveBoadroom(OaBoardroom boardroom);

	public void deleteBoadroomsByPks(long[] boadroomPks);

	public int listMeetapplyCount(OaMeetapply oaMeetapply,String empID) ;
	/**
	 * 取得所有会议数量
	 */
	public int listAllMeetapplyCount(OaMeetapply oaMeetapply) ;

	public List<OaMeetapply> getAllMeetapply(OaMeetapply oaMeetapply,Pager pager);

	public OaMeetapply saveMeetapply(OaMeetapply meetapply);
    
	public List<OaCar> getAllCarByDate(String starttime,String endtime,OaCar car,int companyId,Pager pager);

	public int listSummaryCount(OaSummary oaSummary);

	public List<OaSummary> getAllSummary(OaSummary oaSummary,int companyId, Pager pager);

	public OaSummary saveSummary(OaSummary summary);

	public OaSummary getSummaryByPk(long summaryPk);

	public void deleteSummarysByPks(long[] summaryPks);

	public int listCarCount(OaCar oaCar);

	public List<OaCar> getAllCar(OaCar oaCar, Pager pager);

	public OaCar saveCar(OaCar car);

	public OaCar getCarByPk(long carPk);

	public void deleteCarsByPks(long[] carPks);
	
	public int listCaruseCount(OaCarApply oaCarUse);

	public List<OaCarApply> getAllCaruse(OaCarApply oaCarUse, Pager pager);

	public OaCarApply saveCaruse(OaCarApply caruse);

	public OaCarApply getCaruseByPk(long carusePk);

	public boolean deleteCarusesByPks(long[] carusePks);

	public int listCarmaintenCount(OaCarMaintain oaCarMainten);

	public List<OaCarMaintain> getAllCarmainten(OaCarMaintain oaCarMainten,
			Pager pager);
	public OaCarMaintain saveCarmainten(OaCarMaintain carmainten);

	public OaCarMaintain getCarmaintenByPk(long carmaintenPk);

	public void deleteCarmaintensByPks(long[] carmaintenPks);

	public int listBookCount(OaBook oaBook);

	public List<OaBook> getAllBook(OaBook oaBook, Pager pager);

	public OaBook saveBook(OaBook book);

	public OaBook getBookByPk(long bookPk);

	public void deleteBooksByPks(long[] bookPks,long com);

	public int listBookbrCount(OaBookBr oaBookBr);

	public List<OaBookBr> getAllBookbr(OaBookBr oaBookBr, Pager pager);

	public OaBookBr saveBookbr(OaBookBr bookbr);

	public OaBookBr getBookbrByPk(long bookbrPk);

	public void setBookbrsByPks(long[] bookbrPks);         //还书

	public OaBoardroom getBoadroomByPk(long boadroomPk);

	public OaMeetapply getMeetapplyByPk(long meetapplyPk);

	public void deleteMeetapplysByPks(long[] meetapplyPks);
	
	public OaMeetapply deleteMeetByPk(long pk);
	//统计图书类型数量
	public int listBooktypeCount(OaBookType oaBookType,long com);
	
	//不分页获取图书类别
	public List<OaBookType> getAllBooktypeNopager(long com);

	//分页获取图书类型信息
	public List<OaBookType> getAllBooktype(OaBookType oaBookType,long com, Pager pager);

	//保存图书类型信息
	public OaBookType saveBooktype(OaBookType booktype);

	public OaBookType getBooktypeByPk(long booktypePk);

	public void deleteBooktypesByPks(long[] booktypePks);
	public int getAllCarCount();
	
	public int getAllBoadRoomCount();
	
	public List<OaBoardroom> getAllBoadRoomByDate(String starttime,String endtime,int companyId,Pager pager);
	
	public SysLibraryInfo getLibraryInfoBypk(long lid);
	
	public List<OaSummary> getOaSummaryByPk(long meetapplyPks);
	
	public int getAllayyCarCount(String starttime, String endtime,OaCar car, int companyId);
	
	public int listMySummaryMeetCount(OaMeetapply oaMeetapply,String empId);
	public int listAttendedCount(OaMeetapply oaMeetapply,String empID) ;
	public int listWillAttendMeetCount(OaMeetapply oaMeetapply,String empID) ;
	
	public List<OaMeetapply> getMyMeetapply(OaMeetapply oaMeetapply,String id,Pager pager);
	
	public List<OaMeetapply> getWillAttendMeet(OaMeetapply oaMeetapply,String empId,Pager pager);
	
	public List<OaMeetapply> getAttendedMeet(OaMeetapply oaMeetapply,String empId,Pager pager);
	
	public List<OaMeetapply> getMySummaryMeet(OaMeetapply oaMeetapply,String empId,Pager pager);
	
	public List<OaCar> getUseCar(String starttime, String endtime,OaCar car, int companyId, Pager pager,int type);
	
	public int getUseCarCount(String starttime, String endtime,OaCar car, int companyId, Pager pager,int type);
	
	public int getAllCarByDateCount(OaCar car);
	
	public void SuperdeleteCarusesByPks(long[] carusePks);
	public void deletecarapply(long id);
	public void deletemaintain(long id);
}
