package com.pinhuba.core.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinhuba.common.pack.OfficeResourcesHqlPack;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.core.dao.IOaBoardroomDao;
import com.pinhuba.core.dao.IOaBookBrDao;
import com.pinhuba.core.dao.IOaBookDao;
import com.pinhuba.core.dao.IOaBookTypeDao;
import com.pinhuba.core.dao.IOaCarApplyDao;
import com.pinhuba.core.dao.IOaCarDao;
import com.pinhuba.core.dao.IOaCarMaintainDao;
import com.pinhuba.core.dao.IOaMeetapplyDao;
import com.pinhuba.core.dao.IOaSummaryDao;
import com.pinhuba.core.dao.ISysLibraryInfoDao;
import com.pinhuba.core.iservice.IOfficeResourcesService;
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
/**********************************************
Class name: 办公资源service服务
Description:提供办公资源模块的各种服务
Others:         
History:        
peng.ning    2010.4.27     v3.0
**********************************************/
@Service
@Transactional
public class OfficeResourcesService implements IOfficeResourcesService {
	@Resource
	private IOaBoardroomDao oaBoardroomDao;
	@Resource
	private IOaMeetapplyDao oaMeetapplyDao;
	@Resource
	private IOaSummaryDao oaSummaryDao;
	@Resource
	private IOaCarDao oaCarDao;
	@Resource
	private IOaBookDao oaBookDao;
	@Resource
	private IOaBookBrDao oaBookBrDao;
	@Resource
	private IOaBookTypeDao oaBookTypeDao;
	@Resource
	private IOaCarApplyDao oaCarApplyDao;
	@Resource
	private IOaCarMaintainDao oacarMaintaindao;
	@Resource
	private ISysLibraryInfoDao sysLibraryInfoDao;

	public int listBoadroomCount(OaBoardroom oaBoardroom) {
		int count = oaBoardroomDao.findByHqlWhereCount(OfficeResourcesHqlPack.packOaBoardroomQuery(oaBoardroom));
		return count;
	}

	public List<OaBoardroom> getAllBoadroom(OaBoardroom oaBoardroom, Pager pager) {
		List<OaBoardroom> oaBoardroomList = oaBoardroomDao.findByHqlWherePage(OfficeResourcesHqlPack.packOaBoardroomQuery(oaBoardroom), pager);
		return oaBoardroomList;
	}

	public int getAllBoadRoomCount() {
		return oaBoardroomDao.listCount();
	}

	public int getAllCarCount() {
		return oaCarDao.listCount();
	}
	/**
	 * 根据时间列出未报废汽车数量
	 */
	public int getAllayyCarCount(String starttime, String endtime, OaCar car, int companyId) {
		car.setCompanyId(companyId);
		return oaCarDao.findByHqlWhereCount((OfficeResourcesHqlPack.packOacarstatic(car)));
	}
	/**
	 * 根据时间列出所有会议室
	 */
	public List<OaBoardroom> getAllBoadRoomByDate(String starttime, String endtime, int companyId, Pager pager) {
		List<OaMeetapply> meetlist = oaMeetapplyDao.findByHqlWherePage(OfficeResourcesHqlPack.packOaBoadRoomStatusQuery(starttime, endtime, companyId), pager);
		List<OaBoardroom> roomList = oaBoardroomDao.list();
		for (OaBoardroom oaBoardroom : roomList) {
			boolean bl = false;
			for (OaMeetapply meet : meetlist) {
				if (oaBoardroom.getPrimaryKey() == meet.getOaMeetapplyRoom().intValue()) {
					bl = true;
					break;
				}
			}
			if (bl) {
				oaBoardroom.setRoomStatus(EnumUtil.OA_ROOM_STATUS.Use.value);
			} else {
				oaBoardroom.setRoomStatus(EnumUtil.OA_ROOM_STATUS.Free.value);
			}
		}
		return roomList;
	}
	/**
	 * 取得未报废车数量
	 */
	public int getAllCarByDateCount(OaCar car) {
		return oaCarDao.findByHqlWhereCount((OfficeResourcesHqlPack.packOacarstatic(car)));

	}
	/**
	 * 根据时间获得未报废车辆集合
	 */
	public List<OaCar> getAllCarByDate(String starttime, String endtime, OaCar car, int companyId, Pager pager) {
		List<OaCarApply> OaCarApplylist = oaCarApplyDao.findByHqlWherePage(OfficeResourcesHqlPack.packOaApplyQuery(starttime, endtime, companyId)+" order by model.oaCarStatus desc", pager);
		car.setCompanyId(companyId);
		List<OaCar> oaCarlis = oaCarDao.findByHqlWhere(OfficeResourcesHqlPack.packOacarstatic(car));
		for (OaCar oaCar : oaCarlis) {
			boolean bl = false;
			for (OaCarApply oaCarapply : OaCarApplylist) {
				if (oaCar.getPrimaryKey() == oaCarapply.getCarId().intValue()) {
					bl = true;
					break;
				}
			}
			if (bl) {
				oaCar.setOaCarStatus(EnumUtil.OA_CAR_STATUS.INUSE.value);
			} else {
				oaCar.setOaCarStatus(EnumUtil.OA_CAR_STATUS.BOOKED.value);
			}
		}
		return oaCarlis;
	}
	/**
	 * 根据类型获得ID
	 * @param starttime
	 * @param endtime
	 * @param car
	 * @param companyId
	 * @param type
	 * @return
	 */
	private String getCarIdsByType(String starttime, String endtime, OaCar car, int companyId, int type) {
		List<OaCarApply> OaCarApplylist = oaCarApplyDao.findByHqlWhere(OfficeResourcesHqlPack.packOaApplyQuery(starttime, endtime, companyId));
		car.setCompanyId(companyId);
		List<OaCar> oaCarlis = oaCarDao.findByHqlWhere(OfficeResourcesHqlPack.packOacarstatic(car));
		Set<String> bookedids = new HashSet<String>();
		Set<String> inuseids = new HashSet<String>();
		for (OaCar oaCar : oaCarlis) {
			boolean bl = false;
			for (OaCarApply oaCarapply : OaCarApplylist) {
				if (oaCar.getPrimaryKey() == oaCarapply.getCarId().intValue()) {
					bl = true;
					break;
				}
			}
			if (bl) {
				inuseids.add(String.valueOf(oaCar.getPrimaryKey()));
			} else {
				bookedids.add(String.valueOf(oaCar.getPrimaryKey()));
			}
		}
		String ids = "";
		if (type == EnumUtil.OA_CAR_STATUS.BOOKED.value) {
			ids = UtilTool.getStringFormSetIsNum(bookedids);
		} else {
			ids = UtilTool.getStringFormSetIsNum(inuseids);
		}
		return ids;
	}
	/**
	 * 获得在用汽车数量
	 */
	public int getUseCarCount(String starttime, String endtime, OaCar car, int companyId, Pager pager, int type) {
		String ids = this.getCarIdsByType(starttime, endtime, car, companyId, type);
		if (ids.length() == 0) {
			return 0;
		} else {
			return oaCarDao.findByHqlWhereCount(OfficeResourcesHqlPack.packOacar(ids));
		}
	}
	/**
	 * 获得在用汽车
	 */
	public List<OaCar> getUseCar(String starttime, String endtime, OaCar car, int companyId, Pager pager, int type) {
		String ids = this.getCarIdsByType(starttime, endtime, car, companyId, type);
		List<OaCar> oaCarlis = null;
		if (ids.length() == 0) {
			return new ArrayList<OaCar>();
		} else {
			oaCarlis = oaCarDao.findByHqlWherePage(OfficeResourcesHqlPack.packOacar(ids), pager);
			if (type == EnumUtil.OA_CAR_STATUS.BOOKED.value) {
				for (OaCar oaCar : oaCarlis) {
					oaCar.setOaCarStatus(EnumUtil.OA_CAR_STATUS.BOOKED.value);
				}
			}
			if (type == EnumUtil.OA_CAR_STATUS.INUSE.value) {
				for (OaCar oaCar : oaCarlis) {
					oaCar.setOaCarStatus(EnumUtil.OA_CAR_STATUS.INUSE.value);
				}
			}
			return oaCarlis;
		}
	}
	/**
	 * 保存会议室
	 */
	public OaBoardroom saveBoadroom(OaBoardroom boardroom) {
		OaBoardroom temp = (OaBoardroom) oaBoardroomDao.save(boardroom);
		return temp;
	}
	/**
	 * 根据主键获得会议室
	 */
	public OaBoardroom getBoadroomByPk(long boadroomPk) {
		OaBoardroom boadroom = oaBoardroomDao.getByPK(boadroomPk);
		return boadroom;
	}
	/**
	 * 根据主键删除会议室
	 */
	public void deleteBoadroomsByPks(long[] boadroomPks) {
		for (long l : boadroomPks) {
			OaBoardroom boadroom = oaBoardroomDao.getByPK(l);
			oaBoardroomDao.remove(boadroom);
		}
	}
	/**
	 * 取得会议室数量
	 */
	public int listMeetapplyCount(OaMeetapply oaMeetapply,String empID) {
		int count = oaMeetapplyDao.findBySqlCount(OfficeResourcesHqlPack.packMyMeetapply(oaMeetapply,empID));
		return count;
	}
	/**
	 * 取得所有会议数量
	 */
	public int listAllMeetapplyCount(OaMeetapply oaMeetapply) {
		int count = oaMeetapplyDao.findBySqlCount(OfficeResourcesHqlPack.packOaMeetapplyQuery(oaMeetapply));
		return count;
	}
	/**
	 * 待参加会议数量
	 */
	public int listAttendedCount(OaMeetapply oaMeetapply,String empID) {
		int count = oaMeetapplyDao.findBySqlCount(OfficeResourcesHqlPack.packAttendedMeet(oaMeetapply,empID));
		return count;
	}
	/**
	 * 将要参加会议的数量
	 */
	public int listWillAttendMeetCount(OaMeetapply oaMeetapply,String empID)  {
		int count = oaMeetapplyDao.findBySqlCount(OfficeResourcesHqlPack.packWillAttendMeet(oaMeetapply,empID));
		return count;
	}
	/**
	 * 列出我是会议纪要人的纪要
	 */
	public int listMySummaryMeetCount(OaMeetapply oaMeetapply,String empID) {
		int count = oaMeetapplyDao.findBySqlCount(OfficeResourcesHqlPack.packMySummaryMeet(oaMeetapply,empID));
		return count;
	}
	
	/**
	 * 取得所有会议
	 */
	public List<OaMeetapply> getAllMeetapply(OaMeetapply oaMeetapply, Pager pager) {
		List<OaMeetapply> oaMeetapplyList = oaMeetapplyDao.findBySqlPage(OfficeResourcesHqlPack.packOaMeetapplyQuery(oaMeetapply)+" order by meet.oa_meetapply_star desc,oa_meetapply_degree", OaMeetapply.class, pager);
		// 根据当前时间断会议状态
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String nowtime = sf.format(new Date());
		for (OaMeetapply meetapply : oaMeetapplyList) {
			// 更新状态
			String starttime = meetapply.getOaMeetapplyStar();
			String endtime = meetapply.getOaMeetapplyEnd();
			boolean bls = UtilWork.checkDayBySF(nowtime, starttime, sf) || nowtime.equals(starttime);
			boolean ble = UtilWork.checkDayBySF(nowtime, endtime, sf) || nowtime.equals(endtime);
			if (bls) {
				meetapply.setOaMeetapplyStatus(EnumUtil.OA_MEETAPPLY_STATUS.PROCESSING.value);
			}
			if (ble) {
				meetapply.setOaMeetapplyStatus(EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value);
			}
			if(bls || ble) {
				meetapply = (OaMeetapply) oaMeetapplyDao.save(meetapply);
			}

		}

		return oaMeetapplyList;
	}
	
	/**
	 * //列出我申请的会议
	 */
	public List<OaMeetapply> getMyMeetapply(OaMeetapply oaMeetapply,String emp,Pager pager){
		List<OaMeetapply> meetapplyList = oaMeetapplyDao.findBySqlPage(OfficeResourcesHqlPack.packMyMeetapply(oaMeetapply, emp)+" order by meet.oa_meetapply_star desc,oa_meetapply_degree",OaMeetapply.class,pager);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String nowtime = sf.format(new Date());
		if(meetapplyList!=null) {
			for(OaMeetapply meetapply : meetapplyList) {
				if (meetapply.getOaMeetapplyStatus()!=EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value) {
					// 更新状态
					String starttime = meetapply.getOaMeetapplyStar();
					String endtime = meetapply.getOaMeetapplyEnd();
					boolean bls = UtilWork.checkDayBySF(nowtime, starttime, sf) || nowtime.equals(starttime);
					boolean ble = UtilWork.checkDayBySF(nowtime, endtime, sf) || nowtime.equals(endtime);
					if (bls) {
						meetapply.setOaMeetapplyStatus(EnumUtil.OA_MEETAPPLY_STATUS.PROCESSING.value);
					}
					if (ble) {
						meetapply.setOaMeetapplyStatus(EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value);
					}
					if(bls || ble) {
						meetapply = (OaMeetapply) oaMeetapplyDao.save(meetapply);
					}
				}
			}
		}
		return meetapplyList; 
	}
	/**
	 * 列出我待参加的会议
	 */
	public List<OaMeetapply> getWillAttendMeet(OaMeetapply oaMeetapply,String empId,Pager pager){
		List<OaMeetapply> willAttendMeet = oaMeetapplyDao.findBySqlPage(OfficeResourcesHqlPack.packWillAttendMeet(oaMeetapply, empId)+" order by meet.oa_meetapply_star desc,oa_meetapply_degree",OaMeetapply.class, pager);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String nowtime = sf.format(new Date());
		if(willAttendMeet!=null) {
			for(OaMeetapply meetapply : willAttendMeet) {
				if (meetapply.getOaMeetapplyStatus()!=EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value) {
					// 更新状态
					String starttime = meetapply.getOaMeetapplyStar();
					String endtime = meetapply.getOaMeetapplyEnd();
					boolean bls = UtilWork.checkDayBySF(nowtime, starttime, sf) || nowtime.equals(starttime);
					boolean ble = UtilWork.checkDayBySF(nowtime, endtime, sf) || nowtime.equals(endtime);
					if (bls) {
						meetapply.setOaMeetapplyStatus(EnumUtil.OA_MEETAPPLY_STATUS.PROCESSING.value);
					}
					if (ble) {
						meetapply.setOaMeetapplyStatus(EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value);
					}
					if(bls || ble) {
						meetapply = (OaMeetapply) oaMeetapplyDao.save(meetapply);
					}
				}
			}
		}
		return willAttendMeet;
	}
	
	/**
	 * //列出我已参加的会议
	 */
	public List<OaMeetapply> getAttendedMeet(OaMeetapply oaMeetapply,String empId,Pager pager){
		List<OaMeetapply> attendedMeet = oaMeetapplyDao.findBySqlPage(OfficeResourcesHqlPack.packAttendedMeet(oaMeetapply, empId)+" order by meet.oa_meetapply_star desc,oa_meetapply_degree", OaMeetapply.class,pager);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String nowtime = sf.format(new Date());
		if(attendedMeet!=null) {
			for(OaMeetapply meetapply : attendedMeet) {
				if (meetapply.getOaMeetapplyStatus()!=EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value) {
					// 更新状态
					String starttime = meetapply.getOaMeetapplyStar();
					String endtime = meetapply.getOaMeetapplyEnd();
					boolean bls = UtilWork.checkDayBySF(nowtime, starttime, sf) || nowtime.equals(starttime);
					boolean ble = UtilWork.checkDayBySF(nowtime, endtime, sf) || nowtime.equals(endtime);
					if (bls) {
						meetapply.setOaMeetapplyStatus(EnumUtil.OA_MEETAPPLY_STATUS.PROCESSING.value);
					}
					if (ble) {
						meetapply.setOaMeetapplyStatus(EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value);
					}
					if(bls || ble) {
						meetapply = (OaMeetapply) oaMeetapplyDao.save(meetapply);
					}
				}
			}
		}
		return attendedMeet;
	}
	
	/**
	 * //列出包含我的纪要的会议
	 */
	public List<OaMeetapply> getMySummaryMeet(OaMeetapply oaMeetapply,String empId,Pager pager){
		List<OaMeetapply> mySummaryMeet = oaMeetapplyDao.findBySqlPage(OfficeResourcesHqlPack.packMySummaryMeet(oaMeetapply, empId)+" order by meet.oa_meetapply_star desc,oa_meetapply_degree", OaMeetapply.class, pager);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String nowtime = sf.format(new Date());
		if(mySummaryMeet!=null) {
			for(OaMeetapply meetapply : mySummaryMeet) {
				if (meetapply.getOaMeetapplyStatus()!=EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value) {
					// 更新状态
					String starttime = meetapply.getOaMeetapplyStar();
					String endtime = meetapply.getOaMeetapplyEnd();
					boolean bls = UtilWork.checkDayBySF(nowtime, starttime, sf) || nowtime.equals(starttime);
					boolean ble = UtilWork.checkDayBySF(nowtime, endtime, sf) || nowtime.equals(endtime);
					if (bls) {
						meetapply.setOaMeetapplyStatus(EnumUtil.OA_MEETAPPLY_STATUS.PROCESSING.value);
					}
					if (ble) {
						meetapply.setOaMeetapplyStatus(EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value);
					}
					if(bls || ble) {
						meetapply = (OaMeetapply) oaMeetapplyDao.save(meetapply);
					}
				}
			}
		}
		return mySummaryMeet;
		
	}
	
	
	/**
	 * //保存会议
	 */
	public OaMeetapply saveMeetapply(OaMeetapply meetapply) {
		OaMeetapply temp = (OaMeetapply) oaMeetapplyDao.save(meetapply);
		return temp;
	}
	/**
	 * 根据主键获得会议
	 */
	public OaMeetapply getMeetapplyByPk(long meetapplyPk) {
		OaMeetapply meetapply = oaMeetapplyDao.getByPK(meetapplyPk);
		return meetapply;
	}
	/**
	 * 删除会议
	 */
	public OaMeetapply deleteMeetByPk(long pk) {
		OaMeetapply meetapply = oaMeetapplyDao.getByPK(pk);
		oaMeetapplyDao.remove(meetapply);
		return meetapply;
	}
	/**
	 * 批量删除会议
	 */
	public void deleteMeetapplysByPks(long[] meetapplyPks) {
		for (long l : meetapplyPks) {
			OaMeetapply meetapply = oaMeetapplyDao.getByPK(l);
			List<OaSummary> list = oaSummaryDao.findByHqlWhere(" and model.oaSummaryMeetId =" + meetapply.getPrimaryKey());
			for (OaSummary oaSummary : list) {
				oaSummaryDao.remove(oaSummary);
			}
			oaMeetapplyDao.remove(meetapply);
		}
	}
	/**
	 * 获得会议纪要
	 */
	public List<OaSummary> getOaSummaryByPk(long meetapplyPks) {
		List<OaSummary> oaSummaryList = oaSummaryDao.findByHqlWhere(" and model.oaSummaryMeetId =" + meetapplyPks);

		return oaSummaryList;
	}

	/**
	 * 统计纪要数量
	 */

	public int listSummaryCount(OaSummary oaSummary) {
		int count = oaSummaryDao.findBySqlCount(OfficeResourcesHqlPack.packOaSummaryQuery(oaSummary));
		return count;
	}
	/**
	 * 获得所有会议纪要
	 */
	public List<OaSummary> getAllSummary(OaSummary oaSummary, int companyId, Pager pager) {
		List<OaSummary> oaSummaryList = oaSummaryDao.findBySqlPage(OfficeResourcesHqlPack.packOaSummaryQuery(oaSummary), OaSummary.class, pager);
		return oaSummaryList;
	}
	/**
	 * 保存会议纪要
	 */
	public OaSummary saveSummary(OaSummary summary) {
		OaSummary temp = (OaSummary) oaSummaryDao.save(summary);
		return temp;
	}
	/**
	 * 根据主键获得会议纪要
	 */
	public OaSummary getSummaryByPk(long summaryPk) {
		OaSummary summary = oaSummaryDao.getByPK(summaryPk);
		return summary;
	}
	/**
	 * 删除会议纪要
	 */
	public void deleteSummarysByPks(long[] summaryPks) {
		for (long l : summaryPks) {
			OaSummary summary = oaSummaryDao.getByPK(l);
			oaSummaryDao.remove(summary);
		}
	}
	/**
	 * 取得汽车数量
	 */
	public int listCarCount(OaCar oaCar) {
		int count = oaCarDao.findByHqlWhereCount(OfficeResourcesHqlPack.packOaCarQuery(oaCar));
		return count;
	}
	/**
	 * 取得所有汽车
	 */
	public List<OaCar> getAllCar(OaCar oaCar, Pager pager) {
		List<OaCar> oaCarList = oaCarDao.findByHqlWherePage(OfficeResourcesHqlPack.packOaCarQuery(oaCar), pager);
		return oaCarList;
	}
	/**
	 * 保存汽车
	 */
	public OaCar saveCar(OaCar car) {
		OaCar temp = (OaCar) oaCarDao.save(car);
		return temp;
	}
	/**
	 * 根据主键得到汽车
	 */
	public OaCar getCarByPk(long carPk) {
		OaCar car = oaCarDao.getByPK(carPk);
		return car;
	}
	/**
	 * 删除汽车
	 */
	public void deleteCarsByPks(long[] carPks) {
		for (long l : carPks) {
			OaCar car = oaCarDao.getByPK(l);

			oaCarDao.remove(car);
		}
	}

	public int getappy(long carpks) {
		int app = 0;
		app = oaCarApplyDao.findByHqlWhereCount(OfficeResourcesHqlPack.packoaapply(carpks));
		return app;
	}
	/**
	 * 删除申请
	 */
	public void deletecarapply(long id) {
		List<OaCarApply> lists= oaCarApplyDao.findByHqlWhere(OfficeResourcesHqlPack.packoaapply(id));
	    for (OaCarApply oaCarApply : lists) {
	    	oaCarApplyDao.remove(oaCarApply);
		}
	}
	/**
	 * 删除维修记录
	 */
	public void deletemaintain(long id) {
		List<OaCarMaintain> lists= oacarMaintaindao.findByHqlWhere(OfficeResourcesHqlPack.packoaapply(id));
         for (OaCarMaintain oaCarMaintain : lists) {
        	 oacarMaintaindao.remove(oaCarMaintain);
		}
	}

	public int getmaintain(long carpks) {
		int maintain = 0;
		maintain = oacarMaintaindao.findByHqlWhereCount(OfficeResourcesHqlPack.packoaapply(carpks));
		return maintain;
	}
	/**
	 * 取得汽车使用数量
	 */
	public int listCaruseCount(OaCarApply oaCarApply) {
		int count = oaCarApplyDao.findBySqlCount(OfficeResourcesHqlPack.packOaCarUseQuery(oaCarApply));
		return count;
	}
	
	
	public List<OaCarApply> getAllCaruse(OaCarApply oaCarApply, Pager pager) {
		List<OaCarApply> oaCarUseList = oaCarApplyDao.findBySqlPage(OfficeResourcesHqlPack.packOaCarUseQuery(oaCarApply)+" order by app.apply_begindate desc", OaCarApply.class, pager);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String nowtime = sf.format(new Date());
		for (OaCarApply carApply : oaCarUseList) {
			// 更新状态
			String starttime = carApply.getApplyBegindate();
			String endtime = carApply.getApplyEnddate();
			boolean bls = UtilWork.checkDayBySF(nowtime, starttime, sf) || nowtime.equals(starttime);
			boolean ble = UtilWork.checkDayBySF(nowtime, endtime, sf) || nowtime.equals(endtime);
			if (bls) {
				carApply.setOaCarStatus(EnumUtil.OA_MEETAPPLY_STATUS.PROCESSING.value);
			} else {
				carApply.setOaCarStatus(EnumUtil.OA_MEETAPPLY_STATUS.APPLYING.value);
			}
			if (ble) {
				carApply.setOaCarStatus(EnumUtil.OA_MEETAPPLY_STATUS.COMPLETE.value);
			}
			oaCarApplyDao.save(carApply);
		}
		return oaCarUseList;
	}
	/**
	 * 保存汽车申请
	 */
	public OaCarApply saveCaruse(OaCarApply caruse) {
		OaCarApply temp = (OaCarApply) oaCarApplyDao.save(caruse);
		return temp;
	}
	/**
	 * 得理汽车使用申请
	 */
	public OaCarApply getCaruseByPk(long carusePk) {
		OaCarApply caruse = oaCarApplyDao.getByPK(carusePk);
		return caruse;
	}
	/**
	 * 根据主键删除申请
	 */
	public boolean deleteCarusesByPks(long[] carusePks) {
		for (long l : carusePks) {
			OaCarApply caruse = oaCarApplyDao.getByPK(l);
			if (caruse.getOaCarStatus() == 2) {
				return false;
			} else if (caruse.getOaCarStatus() == 3) {
				return false;
			} else {
				oaCarApplyDao.remove(caruse);
				return true;
			}

		}
		return false;
	}
	/**
	 * 删除汽车
	 */
	public void SuperdeleteCarusesByPks(long[] carusePks) {
		for (long l : carusePks) {
			OaCarApply caruse = oaCarApplyDao.getByPK(l);

			oaCarApplyDao.remove(caruse);

		}

	}
	/**
	 * 取得所有维护记录
	 */
	public List<OaCarMaintain> getAllCarmainten(OaCarMaintain oaCarMainten, Pager pager) {
		List<OaCarMaintain> OaCarmaintenList = oacarMaintaindao.findByHqlWherePage(OfficeResourcesHqlPack.packOaCarMaintenQuery(oaCarMainten), pager);
		return OaCarmaintenList;
	}
	/**
	 * 显示维修次数
	 */
	public int listCarmaintenCount(OaCarMaintain oaCarMainten) {
		int count = oacarMaintaindao.findByHqlWhereCount(OfficeResourcesHqlPack.packOaCarMaintenQuery(oaCarMainten));
		return count;
	}
	/**
	 * 保存维护记录
	 */
	public OaCarMaintain saveCarmainten(OaCarMaintain carmainten) {
		OaCarMaintain temp = (OaCarMaintain) oacarMaintaindao.save(carmainten);
		return temp;
	}
	/**
	 * 取得维护记录
	 */
	public OaCarMaintain getCarmaintenByPk(long carmaintenPk) {
		OaCarMaintain carmainten = oacarMaintaindao.getByPK(carmaintenPk);
		return carmainten;
	}
	/**
	 * 删除汽车维护记录
	 */
	public void deleteCarmaintensByPks(long[] carmaintenPks) {
		for (long l : carmaintenPks) {
			OaCarMaintain carmainten = oacarMaintaindao.getByPK(l);
			oacarMaintaindao.remove(carmainten);
		}
	}
	
	/**
	 * 统计图书数量
	 */
	public int listBookCount(OaBook oaBook) {
		int count = oaBookDao.findBySqlCount(OfficeResourcesHqlPack.packOaBookQuery(oaBook));
		return count;
	}

	/**
	 * 获取所有图书信息 oaBook 查询条件 pager 分页
	 */
	public List<OaBook> getAllBook(OaBook oaBook, Pager pager) {
		List<OaBook> OaBookList = oaBookDao.findBySqlPage(OfficeResourcesHqlPack.packOaBookQuery(oaBook)+" order by book.oa_book_type desc,book.oa_book_code", OaBook.class, pager);
		return OaBookList;
	}

	/**
	 * 保存图书信息
	 */
	public OaBook saveBook(OaBook book) {
		OaBook temp = (OaBook) oaBookDao.save(book);
		return temp;
	}

	/**
	 * 根据主键获取图书信息
	 */
	public OaBook getBookByPk(long bookPk) {
		OaBook book = oaBookDao.getByPK(bookPk);
		return book;
	}

	/**
	 * 删除图书信息
	 */
	public void deleteBooksByPks(long[] bookPks, long com) {
		for (long l : bookPks) {
			// 删除该图书所有借阅信息
			List<OaBookBr> brList = oaBookBrDao.findByHqlWhere(" and model.oaBrBookid=" + l + " and model.companyId=" + com);
			for (OaBookBr br : brList) {
				oaBookBrDao.remove(br);
			}

			// 删除图书信息
			OaBook book = oaBookDao.getByPK(l);
			oaBookDao.remove(book);
		}
	}

	/**
	 * 统计图书出借信息数量
	 */
	public int listBookbrCount(OaBookBr oaBookBr) {
		int count = oaBookBrDao.findBySqlCount(OfficeResourcesHqlPack.packOaBookBrQuery(oaBookBr));
		return count;
	}

	/**
	 * 获取所有图书出借记录
	 */
	public List<OaBookBr> getAllBookbr(OaBookBr oaBookBr, Pager pager) {
		List<OaBookBr> OaBookBrList = oaBookBrDao.findBySqlPage(OfficeResourcesHqlPack.packOaBookBrQuery(oaBookBr)+" order by br.oa_br_rdate desc", OaBookBr.class, pager);
		return OaBookBrList;
	}

	/**
	 * 新增图书出借信息
	 */
	public OaBookBr saveBookbr(OaBookBr bookbr) {
		// 修改图书剩余数量
		OaBook bk = oaBookDao.getByPK((long) bookbr.getOaBrBookid());
		int count = bk.getOaBookRemain();
		count = count - bookbr.getOaBrCount();
		bk.setOaBookRemain(count);
		oaBookDao.save(bk);

		OaBookBr temp = (OaBookBr) oaBookBrDao.save(bookbr);
		return temp;
	}

	/**
	 * 根据主键获取图书出借记录
	 */
	public OaBookBr getBookbrByPk(long bookbrPk) {
		OaBookBr bookbr = oaBookBrDao.getByPK(bookbrPk);
		return bookbr;
	}

	/**
	 * 归还图书
	 */
	public void setBookbrsByPks(long[] bookbrPks) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());

		for (long l : bookbrPks) {
			OaBookBr bookbr = oaBookBrDao.getByPK(l);
			// 判断书籍状态
			if (bookbr.getOaBrStatus() == EnumUtil.OA_BOOKBR_STATUS.LEND.value) { // 处于已出借状态

				// 修改图书剩余数量
				OaBook bk = oaBookDao.getByPK((long) bookbr.getOaBrBookid());
				int count = bk.getOaBookRemain();
				count = count + bookbr.getOaBrCount();
				bk.setOaBookRemain(count);
				oaBookDao.save(bk);

				// 修改借阅信息
				bookbr.setOaBrDate(time);
				bookbr.setOaBrStatus(EnumUtil.OA_BOOKBR_STATUS.BACK.value);
				oaBookBrDao.save(bookbr);
			}
		}
	}

	/**
	 * 统计图书类型数量
	 */
	public int listBooktypeCount(OaBookType oaBookType, long com) {
		int count = oaBookTypeDao.findByHqlWhereCount(OfficeResourcesHqlPack.packOaBookTypeQuery(oaBookType, com));
		return count;
	}

	/**
	 * 分页获取所有图书类别信息
	 * 
	 * @param oaBookType
	 * @param com
	 *            公司主键
	 * @param pager
	 *            分页对象
	 * @return
	 */
	public List<OaBookType> getAllBooktype(OaBookType oaBookType, long com, Pager pager) {
		List<OaBookType> OaBookTypeList = oaBookTypeDao.findByHqlWherePage(OfficeResourcesHqlPack.packOaBookTypeQuery(oaBookType, com), pager);
		return OaBookTypeList;
	}

	/**
	 * 不分页查询所有图书类别
	 * 
	 * @param com
	 *            公司主键
	 * @return
	 */
	public List<OaBookType> getAllBooktypeNopager(long com) {
		List<OaBookType> list = oaBookTypeDao.findByHqlWhere(" and model.companyId =" + com);

		return list;
	}

	/**
	 * 保存图书类别
	 */
	public OaBookType saveBooktype(OaBookType booktype) {
		OaBookType temp = (OaBookType) oaBookTypeDao.save(booktype);

		return temp;
	}

	/**
	 * 根据主键获取图书类别
	 */
	public OaBookType getBooktypeByPk(long booktypePk) {
		OaBookType booktype = oaBookTypeDao.getByPK(booktypePk);

		return booktype;
	}

	/**
	 * 删除图书类别信息
	 */
	public void deleteBooktypesByPks(long[] booktypePks) {
		for (long l : booktypePks) {
			OaBookType booktype = oaBookTypeDao.getByPK(l);
			oaBookTypeDao.remove(booktype);
		}
	}

	public void setOaCarApplyDao(IOaCarApplyDao oaCarApplyDao) {
		this.oaCarApplyDao = oaCarApplyDao;
	}

	public void setOacarMaintaindao(IOaCarMaintainDao oacarMaintaindao) {
		this.oacarMaintaindao = oacarMaintaindao;
	}

	/**
	 * 根据会议类型主键 获取类型信息
	 */
	public SysLibraryInfo getLibraryInfoBypk(long lid) {
		SysLibraryInfo libraryInfo = sysLibraryInfoDao.getByPK(lid);
		return libraryInfo;
	}
}
