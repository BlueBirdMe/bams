package com.pinhuba.core.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinhuba.common.pack.OaCmpreHqlPack;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.core.dao.IHrmEmployeeDao;
import com.pinhuba.core.dao.IOaAlbumDao;
import com.pinhuba.core.dao.IOaFormsDao;
import com.pinhuba.core.dao.IOaJournalsDao;
import com.pinhuba.core.dao.IOaJournalsTypeDao;
import com.pinhuba.core.dao.IOaPhotoDao;
import com.pinhuba.core.dao.IOaRegulationsDao;
import com.pinhuba.core.dao.IOaWareTypeDao;
import com.pinhuba.core.dao.IOaWarehouseDao;
import com.pinhuba.core.dao.IOaWaretypeRangeDao;
import com.pinhuba.core.dao.ISysLibraryInfoDao;
import com.pinhuba.core.iservice.IOaCompanyResourcesService;
import com.pinhuba.core.pojo.OaAlbum;
import com.pinhuba.core.pojo.OaForms;
import com.pinhuba.core.pojo.OaJournals;
import com.pinhuba.core.pojo.OaJournalsType;
import com.pinhuba.core.pojo.OaPhoto;
import com.pinhuba.core.pojo.OaRegulations;
import com.pinhuba.core.pojo.OaWareType;
import com.pinhuba.core.pojo.OaWarehouse;
import com.pinhuba.core.pojo.OaWaretypeRange;

@Service
@Transactional
public class OaCompanyResourcesService implements IOaCompanyResourcesService {

	@Resource
	private IOaWarehouseDao oaWarehouseDao;
	@Resource
	private IOaWareTypeDao oaWareTypeDao;
	@Resource
	private IOaWaretypeRangeDao oaWaretypeRangeDao;
	@Resource
	private IHrmEmployeeDao hrmEmployeeDao;
	@Resource
	private IOaFormsDao oaFormsDao;
	@Resource
	private IOaJournalsTypeDao oaJournalsTypeDao;
	@Resource
	private IOaJournalsDao oaJournalsDao;
	@Resource
	private IOaAlbumDao oaAlbumDao;
	@Resource
	private IOaPhotoDao oaPhotoDao;
	@Resource
	private ISysLibraryInfoDao libraryInfoDao;
	@Resource
	private IOaRegulationsDao oaRegulationsDao;
	
	//取得知识类带分页
	public List<OaWareType> getAllOaWageTypeByPager(OaWareType waretype, Pager pager) {
		List<OaWareType> list = oaWareTypeDao.findByHqlWherePage(OaCmpreHqlPack.getOaWareTypeSql(waretype), pager);
		return list;
	}
	//取得知识类型 不带分页
	public List<OaWareType> getAllOaWageTypeBytype(OaWareType waretype) {
		List<OaWareType> list = oaWareTypeDao.findByHqlWhere(OaCmpreHqlPack.getOaWareTypeSql(waretype));
		return list;
	}
	
	//添加知识类型时 检查 知识类型的名称 是否已存在
	public  boolean clikeoaWagetypeName(OaWareType waretype){
		String hql = " and model.oaTypeName = '"+waretype.getOaTypeName()+"' and model.formsorware="+waretype.getFormsorware()+" and model.companyId="+waretype.getCompanyId();
		if(waretype.getPrimaryKey()>0){
			hql += " and model.primaryKey<>"+waretype.getPrimaryKey();
		}
		int count = oaWareTypeDao.findByHqlWhereCount(hql);
		if(count>0){
			return false;
		}else{
			return true;
		}
	}
	
	//获得类型的数量 用于分页
	public int getAllOaWageTypeCount(OaWareType waretype) {
		return oaWareTypeDao.findByHqlWhereCount(OaCmpreHqlPack.getOaWareTypeSql(waretype));
	}
	
	//保存知识类型
	public OaWareType saveOaWareType(String empids, OaWareType wareType) {
		if (wareType.getPrimaryKey() > 0) {
			// 删除查看人员范围
			this.deleteWareTypeRangeByTypePk(wareType.getPrimaryKey());
		}
		OaWareType type = (OaWareType) oaWareTypeDao.save(wareType);
		if (type != null && empids.length() > 0) {
			String[] empid = empids.split(",");

			for (String eid : empid) {
				if (eid != null && eid.trim().length() > 0) {
					OaWaretypeRange range = new OaWaretypeRange();
					range.setOaWareTypeId((int) type.getPrimaryKey());
					range.setHrmEmployeeId(eid);
					range.setCompanyId(type.getCompanyId());
					range.setRangeType(type.getFormsorware());
					oaWaretypeRangeDao.save(range);
				}
			}
		}
		return type;
	}
	
	//取得单个知识类型
	public OaWareType getOaWareTypeByPk(long pk) {
		OaWareType type = oaWareTypeDao.getByPK(pk);
		return type;
	}
	
	//中间类 用于方便查询 知识类型 和表格类型
	public List<OaWaretypeRange> getOaWaretypeRangeList(long typePk) {
		List<OaWaretypeRange> list = oaWaretypeRangeDao.findByHqlWhere(" and model.oaWareTypeId = " + typePk);
		for (OaWaretypeRange oaWaretypeRange : list) {
			oaWaretypeRange.setHrmEmployee(hrmEmployeeDao.getByPK(oaWaretypeRange.getHrmEmployeeId()));
		}
		return list;
	}
	 
	//删除类型
	public void deleteWareTypeByPk(long pk) {
		OaWareType type = oaWareTypeDao.getByPK(pk);
		oaWareTypeDao.remove(type);
		List<OaWaretypeRange> list = oaWaretypeRangeDao.findByHqlWhere(" and model.oaWareTypeId = " + pk);
		for (OaWaretypeRange oaWaretypeRange : list) {
			oaWaretypeRangeDao.remove(oaWaretypeRange);
		}
	}
	
	//删除中间类 类型
	public void deleteWareTypeRangeByTypePk(long pk) {
		List<OaWaretypeRange> list = oaWaretypeRangeDao.findByHqlWhere(" and model.oaWareTypeId = " + pk);
		for (OaWaretypeRange oaWaretypeRange : list) {
			oaWaretypeRangeDao.remove(oaWaretypeRange);
		}
	}

	//根据类型 取得知识
	public List<OaWarehouse> getWarehoseByTypePk(long pk) {
		List<OaWarehouse> list = oaWarehouseDao.findByHqlWhere(" and model.oaWareType = " + pk);
		return list;
	}
		
	//取得知识
	public List<OaWarehouse> getWareHouseBypager(OaWarehouse wareHouse, Pager pager) {
		List<OaWarehouse> list = oaWarehouseDao.findByHqlWherePage(OaCmpreHqlPack.getOaWareHouseSql(wareHouse)+"order by model.oaWareTime desc", pager);
		for (OaWarehouse oaWarehouse : list) {
			oaWarehouse.setWareType(this.getOaWareTypeByPk(oaWarehouse.getOaWareType().longValue()));
			oaWarehouse.setOaWareEmployee(hrmEmployeeDao.getByPK(oaWarehouse.getOaWareEmp()));
		}
		return list;
	}

	/**
	 * 根据权限查询知识仓库
	 * 
	 * @param wareHouse
	 * @param pager
	 * @param empid
	 * @param companyId
	 * @return
	 */
	public List<OaWarehouse> getWareHouseByPagerAndPerm(OaWarehouse wareHouse, Pager pager, String empid, int companyId, int type) {
		List<OaWarehouse> list = new ArrayList<OaWarehouse>();
		// 取得可查询类型
		String tids = this.getQueryWareTypeSetId(empid, companyId, type);
		if (tids.length() == 0) {
			return list;
		} else {
			list = oaWarehouseDao.findByHqlWherePage(OaCmpreHqlPack.getOaWareHouseAndPremSql(wareHouse, tids)+"order by model.recordDate desc", pager);
			for (OaWarehouse oaWarehouse : list) {
				oaWarehouse.setWareType(this.getOaWareTypeByPk(oaWarehouse.getOaWareType().longValue()));
				oaWarehouse.setOaWareEmployee(hrmEmployeeDao.getByPK(oaWarehouse.getOaWareEmp()));
			}
			return list;
		}
	}
	
	//根据条件查询知识数量
	public int getWareHouseAndPermCount(OaWarehouse wareHouse, String empid, int companyId, int type) {
		int count = 0;
		String tids = this.getQueryWareTypeSetId(empid, companyId, type);
		if (tids.length() == 0) {
			return count;
		} else {
			return oaWarehouseDao.findByHqlWhereCount(OaCmpreHqlPack.getOaWareHouseAndPremSql(wareHouse, tids));
		}
	}

	/**
	 * 根据权限查询常用表格
	 * 
	 * @param wareHouse
	 * @param pager
	 * @param empid
	 * @param companyId
	 * @return
	 */
	public List<OaForms> getFormsByPagerAndPerm(OaForms forms, Pager pager, String empid, int companyId, int type) {
		List<OaForms> list = new ArrayList<OaForms>();
		// 取得可查询类型
		String tids = this.getQueryWareTypeSetId(empid, companyId, type);
		if (tids.length() == 0) {
			return list;
		} else {
			list = oaFormsDao.findByHqlWherePage(OaCmpreHqlPack.getOaFormsAndPremSql(forms, tids)+"order by model.oaFormTime desc", pager);
			for (OaForms form : list) {
				form.setWareType(this.getOaWareTypeByPk(form.getOaFormType().longValue()));
				form.setFormEmployee(hrmEmployeeDao.getByPK(form.getOaFormEmp()));
			}
			return list;
		}
	}

	public int getFormsAndPermCount(OaForms forms, String empid, int companyId, int type) {
		int count = 0;
		String tids = this.getQueryWareTypeSetId(empid, companyId, type);
		if (tids.length() == 0) {
			return count;
		} else {
			return oaFormsDao.findByHqlWhereCount(OaCmpreHqlPack.getOaFormsAndPremSql(forms, tids));
		}
	}

	/**
	 * 取得可查询知识类型id并组装为字符串
	 * 
	 * @param empid
	 * @param companyId
	 * @param type
	 * @return
	 */
	private String getQueryWareTypeSetId(String empid, int companyId, int type) {
		String typeidstr = "";
		Set<Integer> typeSet = new HashSet<Integer>();
		OaWareType waretype = new OaWareType();
		waretype.setFormsorware(type);
		waretype.setCompanyId(companyId);
		List<OaWareType> list = oaWareTypeDao.findByHqlWhere(OaCmpreHqlPack.getOaWareTypeSql(waretype));
		List<OaWaretypeRange> rangelist = oaWaretypeRangeDao.findByHqlWhere(" and model.hrmEmployeeId = '" + empid + "' and model.companyId =" + companyId + " and model.rangeType =" + type);

		for (OaWareType oaWareType : list) {
			if (oaWareType.getPremCount() == 0) {
				typeSet.add((int) oaWareType.getPrimaryKey());
			}
		}
		for (OaWaretypeRange waretypeRange : rangelist) {
			typeSet.add(waretypeRange.getOaWareTypeId());
		}
		Iterator<Integer> it = typeSet.iterator();
		while (it.hasNext()) {
			Integer elem = (Integer) it.next();
			typeidstr += elem + ",";
		}
		if (typeidstr.length() > 0) {
			typeidstr = typeidstr.substring(0, typeidstr.length() - 1);
		}
		return typeidstr;
	}

	//查找知识数量 用于分页
	public int getWareHouseCount(OaWarehouse wareHouse) {
		return oaWarehouseDao.findByHqlWhereCount(OaCmpreHqlPack.getOaWareHouseSql(wareHouse));
	}

	//保存知识
	public OaWarehouse saveWarehouse(OaWarehouse wareHouse) {
		OaWarehouse obj = (OaWarehouse) oaWarehouseDao.save(wareHouse);
		return obj;
	}
	
	//保存表格
	public OaForms saveOaForms(OaForms forms) {
		return (OaForms) oaFormsDao.save(forms);
	}
	
	//取得单个知识
	public OaWarehouse getWarehouseByPk(long pk) {
		return oaWarehouseDao.getByPK(pk);
	}
	
	//取得单个类型
	public OaWarehouse getWarehouseAndObjByPk(long pk) {
		OaWarehouse tmp = oaWarehouseDao.getByPK(pk);
		tmp.setWareType(oaWareTypeDao.getByPK((long) tmp.getOaWareType()));
		tmp.setOaWareEmployee(hrmEmployeeDao.getByPK(tmp.getOaWareEmp()));
		return tmp;
	}

	//删除知识
	public OaWarehouse deleteWarehouseByIds(long id) {
		OaWarehouse tmp = oaWarehouseDao.getByPK(id);
		oaWarehouseDao.remove(tmp);
		return tmp;
	}
	
	//获取表格
	public List<OaForms> getFormsBypager(OaForms forms, Pager pager) {
		List<OaForms> list = oaFormsDao.findByHqlWherePage(OaCmpreHqlPack.getOaFormsSql(forms)+"order by model.oaFormTime desc", pager);
		for (OaForms form : list) {
			form.setWareType(this.getOaWareTypeByPk(form.getOaFormType().longValue()));
			form.setFormEmployee(hrmEmployeeDao.getByPK(form.getOaFormEmp()));
			
		}
		return list;
	}
	
	//获取表格数量
	public int getFormsCount(OaForms forms) {
		return oaFormsDao.findByHqlWhereCount(OaCmpreHqlPack.getOaFormsSql(forms));
	}
	
	//获取单个表格
	public OaForms getFormsByPk(long pk) {
		return oaFormsDao.getByPK(pk);
	}
	
	//删除单个表格
	public OaForms deleteFormsByIds(long id) {
		OaForms tmp = oaFormsDao.getByPK(id);
		oaFormsDao.remove(tmp);
		return tmp;
	}
	
	//获取单个表格
	public OaForms getFormsAndObjByPk(long pk) {
		OaForms tmp = oaFormsDao.getByPK(pk);
		tmp.setWareType(oaWareTypeDao.getByPK((long) tmp.getOaFormType()));
		tmp.setFormEmployee(hrmEmployeeDao.getByPK(tmp.getOaFormEmp()));
		return tmp;
	}
	
	//期刊数量
	public int getJournalsTypeCount(OaJournalsType jourType) {
		return oaJournalsTypeDao.findByHqlWhereCount(OaCmpreHqlPack.getOaJournalsTypeSql(jourType));
	}
	
	//期刊类型分页
	public List<OaJournalsType> getJournalsTypePager(OaJournalsType jourType, Pager pager) {
		List<OaJournalsType> list = oaJournalsTypeDao.findByHqlWherePage(OaCmpreHqlPack.getOaJournalsTypeSql(jourType), pager);
		return list;
	}
	
	//期刊类型不分页
	public List<OaJournalsType> getJournalsType(OaJournalsType jourType) {
		List<OaJournalsType> list = oaJournalsTypeDao.findByHqlWhere(OaCmpreHqlPack.getOaJournalsTypeSql(jourType));
		return list;
	}
	//保存期刊类型
	public OaJournalsType saveJournalsType(OaJournalsType jtype) {
		return (OaJournalsType) oaJournalsTypeDao.save(jtype);
	}
	
	//获取期刊类型
	public OaJournalsType getJournalsTypeByPk(long pk) {
		return oaJournalsTypeDao.getByPK(pk);
	}
	
	//添加期刊 检查期刊名称 是否已经有了~
   public boolean clikeJournalsName(OaJournalsType jourType){
	   String hql = " and model.journalsTypeName = '"+jourType.getJournalsTypeName()+"' and model.companyId="+jourType.getCompanyId();
		if(jourType.getPrimaryKey()>0){
			hql += " and model.primaryKey<>"+jourType.getPrimaryKey();
		}
		int count = oaJournalsTypeDao.findByHqlWhereCount(hql);
		if(count>0){
			return false;
		}else{
			return true;
		}
   }
   
   //删除期刊类型
	public void deleteJournalsTypeByPk(long pk) {
		OaJournalsType type = oaJournalsTypeDao.getByPK(pk);
		oaJournalsTypeDao.remove(type);
	}

	//期刊数量
	public int getJournalsCount(OaJournals jour) {
		int count = oaJournalsDao.findByHqlWhereCount(OaCmpreHqlPack.getOaJournalsSql(jour));
		return count;
	}

	//获得期刊
	public List<OaJournals> getJournalsByPager(OaJournals jour, Pager pager) {
		List<OaJournals> list = oaJournalsDao.findByHqlWherePage(OaCmpreHqlPack.getOaJournalsSql(jour), pager);
		for (OaJournals oaJournals : list) {
			oaJournals.setJournalsType(oaJournalsTypeDao.getByPK(oaJournals.getJournalsTypeId().longValue()));
		}
		return list;
	}
	
	//保存期刊
	public OaJournals saveJournals(OaJournals journals) {
		return (OaJournals) oaJournalsDao.save(journals);
	}
	
	//获得单个期刊
	public OaJournals getJournalsByPk(long pk) {
		return oaJournalsDao.getByPK(pk);
	}
	
	//删除期刊
	public OaJournals deleteJournalsById(long id) {
		OaJournals journals = oaJournalsDao.getByPK(id);
		oaJournalsDao.remove(journals);
		return journals;
	}
	
	//获取期刊
	public OaJournals getJournalsObjectByPk(long pk) {
		OaJournals tmp = oaJournalsDao.getByPK(pk);
		tmp.setJournalsType(oaJournalsTypeDao.getByPK((long) tmp.getJournalsTypeId()));
		return tmp;
	}

	//保存相册
	public OaAlbum saveAlbum(OaAlbum album) {
		return (OaAlbum) oaAlbumDao.save(album);
	}

	/**
	 * 根据主键获取相册信息
	 * 
	 * @param pk
	 * @param bl
	 *            是否关联相册对象
	 * @return
	 */
	public OaAlbum getOaAlbumByPk(long pk, boolean bl) {
		OaAlbum tmp = oaAlbumDao.getByPK(pk);
		if (bl) {
			tmp.setCreateEmployee(hrmEmployeeDao.getByPK(tmp.getAlbumCreateEmployee()));
			tmp.setLibraryType(libraryInfoDao.getByPK(tmp.getAlbumType().longValue()));
			if (tmp.getAlbumPhotoId() != null && tmp.getAlbumPhotoId().intValue() > 0) {
				tmp.setOaPhoto(oaPhotoDao.getByPK(tmp.getAlbumPhotoId().longValue()));
			} 
		}
		return tmp;
	}

	//获取相册 人员 相片
	public List<OaAlbum> getOaAlbumListByPager(OaAlbum album, Pager pager) {
		List<OaAlbum> list = oaAlbumDao.findByHqlWherePage(OaCmpreHqlPack.getOaAlbumSql(album), pager);
		for (OaAlbum am : list) {
			am.setCreateEmployee(hrmEmployeeDao.getByPK(am.getAlbumCreateEmployee()));
			am.setLibraryType(libraryInfoDao.getByPK(am.getAlbumType().longValue()));
			if (am.getAlbumPhotoId() == null || am.getAlbumPhotoId().intValue() <= 0) {
				OaPhoto ph = new OaPhoto();
				ph.setImageId(-1);
				am.setOaPhoto(ph);
			} else {
				am.setOaPhoto(oaPhotoDao.getByPK(am.getAlbumPhotoId().longValue()));
			}
		}
		return list;
	}
	
	//获取相册
	public List<OaAlbum> getAllAlbumList(OaAlbum album) {
		List<OaAlbum> list = oaAlbumDao.findByHqlWhere(OaCmpreHqlPack.getOaAlbumSql(album));
		return list;
	}
	
	//相册数量
	public int getOaAlbumListCount(OaAlbum album) {
		return oaAlbumDao.findByHqlWhereCount(OaCmpreHqlPack.getOaAlbumSql(album));
	}
	
	//根据时间 获取相册 人员 相片
	public List<OaAlbum> getOaAlbumListByPagerAndPrem(OaAlbum album,String empid,String deptid,Pager pager) {
		List<OaAlbum> list = oaAlbumDao.findByHqlWherePage(OaCmpreHqlPack.getOaAlbumAndPremSql(album,empid,deptid), pager);
		for (OaAlbum am : list) {
			am.setCreateEmployee(hrmEmployeeDao.getByPK(am.getAlbumCreateEmployee()));
			am.setLibraryType(libraryInfoDao.getByPK(am.getAlbumType().longValue()));
			if (am.getAlbumPhotoId() == null || am.getAlbumPhotoId().intValue() <= 0) {
				OaPhoto ph = new OaPhoto();
				ph.setImageId(-1);
				am.setOaPhoto(ph);
			} else {
				am.setOaPhoto(oaPhotoDao.getByPK(am.getAlbumPhotoId().longValue()));
			}
		}
		return list;
	}
	
	//根据时间 查找相册
	public List<OaAlbum> getAllAlbumListPrem(OaAlbum album,String empid,String deptid) {
		List<OaAlbum> list = oaAlbumDao.findByHqlWhere(OaCmpreHqlPack.getOaAlbumAndPremSql(album,empid,deptid));
		return list;
	}
	
	//相册数量
	public int getOaAlbumListCountPrem(OaAlbum album,String empid,String deptid) {
		return oaAlbumDao.findByHqlWhereCount(OaCmpreHqlPack.getOaAlbumAndPremSql(album,empid,deptid));
	}
	
	//删除相册
	public void deleteOaAlbum(OaAlbum album) {
		oaAlbumDao.remove(album);
	}
	
	//取得相片
	public List<OaPhoto> getPhotoListPager(OaPhoto photo, Pager pager) {
		List<OaPhoto> photoList = oaPhotoDao.findByHqlWherePage(OaCmpreHqlPack.getOaPhoto(photo), pager);
		OaAlbum ab = oaAlbumDao.getByPK(photo.getAlbumId().longValue());
		for (OaPhoto oaPhoto : photoList) {
			if(ab.getAlbumPhotoId()!=null&&oaPhoto.getPrimaryKey()==ab.getAlbumPhotoId().longValue()){
				oaPhoto.setIsAlubmFace("相册封面");
			}
		}
		return photoList;
	}
	
	//相片数量
	public int getPhotoCount(OaPhoto photo) {
		return oaPhotoDao.findByHqlWhereCount(OaCmpreHqlPack.getOaPhoto(photo));
	}
	
	//取得单个相片
	public OaPhoto getPhotoByPk(long pk) {
		return oaPhotoDao.getByPK(pk);
	}
	
	//保存相片 多个
	public ArrayList<OaPhoto> saveOaPhotos(ArrayList<OaPhoto> photolist) {
		ArrayList<OaPhoto> phs = new ArrayList<OaPhoto>();
		for (int i = 0; i < photolist.size(); i++) {
			phs.add((OaPhoto) oaPhotoDao.save(photolist.get(i)));
		}
		// 更新相册
		if (phs.size() > 0) {
			OaPhoto ptmp = phs.get(0);
			OaAlbum ab = oaAlbumDao.getByPK(ptmp.getAlbumId().longValue());
			if (ab != null) {
				if (ab.getAlbumPhotoId() == null || ab.getAlbumPhotoId() == 0) {
					ab.setAlbumPhotoId((int) ptmp.getPrimaryKey());
				}
				ab.setAlbumPhotoCount(ab.getAlbumPhotoCount() + phs.size());
				oaAlbumDao.save(ab);
			}
		}
		return phs;
	}
	
	//保存相片
	public OaPhoto saveOaPhoto(OaPhoto photo) {
		return (OaPhoto) oaPhotoDao.save(photo);
	}
	
	//删除相片单个
	public ArrayList<OaPhoto> deleteOaPhoto(long[] pks) {
		ArrayList<OaPhoto> list = new ArrayList<OaPhoto>();
		OaPhoto ph =null;
		for (long l : pks) {
			OaPhoto tmp = oaPhotoDao.getByPK(l);
			ph = tmp;
			oaPhotoDao.remove(ph);
			list.add(tmp);
		}
		if (ph!=null) {
			OaAlbum album = oaAlbumDao.getByPK(ph.getAlbumId().longValue());
			//判断所删除照片是否包含封面
			boolean bl = false;
			for (long m : pks) {
				if(m==album.getAlbumPhotoId().longValue()){
					bl = true;
					break;
				}
			}
			if (bl) {
				String ids ="";
				for (long n : pks) {
					ids+=n+",";
				}
				if (ids.length()>1) {
					ids = ids.substring(0, ids.length()-1);
					List<OaPhoto> photolist=oaPhotoDao.findByHqlWhere(" and model.albumId="+ph.getAlbumId()+" and model.primaryKey not in ("+ids+")");
					if (photolist.size()>0) {
						album.setAlbumPhotoId((int)(photolist.get(0).getPrimaryKey()));//随机放入相册封面
					}else{
						album.setAlbumPhotoId(null);
					}
				}
			}
			
			int count = album.getAlbumPhotoCount()-pks.length;
			album.setAlbumPhotoCount(count);
			oaAlbumDao.save(album);
		}
		
		return list;
	}
	
	//删除相片 多个
	public void moveOaPhotos(long[] pks,int albumId) {
		
		OaPhoto ph =null;
		OaAlbum album =null;//旧相册
		for (long l : pks) {
			ph = oaPhotoDao.getByPK(l);
			album = oaAlbumDao.getByPK(ph.getAlbumId().longValue());
			ph.setAlbumId(albumId);
			oaPhotoDao.save(ph);
		}
		if (ph!=null) {
			OaAlbum newalbum = oaAlbumDao.getByPK((long)albumId);
			if (newalbum.getAlbumPhotoId()==null||newalbum.getAlbumPhotoId()<=0) {//判断是否有封面
				newalbum.setAlbumPhotoId((int)pks[0]);
			}
			int c = newalbum.getAlbumPhotoCount()+pks.length;
			newalbum.setAlbumPhotoCount(c);
			oaAlbumDao.save(newalbum);
			
			
			if (album!=null) {
				//判断所移动照片是否包含封面
				boolean bl = false;
				for (long m : pks) {
					if(m==album.getAlbumPhotoId().longValue()){
						bl = true;
						break;
					}
				}
				if (bl) {
					String ids ="";
					for (long n : pks) {
						ids+=n+",";
					}
					if (ids.length()>1) {
						ids = ids.substring(0, ids.length()-1);
						List<OaPhoto> photolist=oaPhotoDao.findByHqlWhere(" and model.albumId="+album.getPrimaryKey()+" and model.primaryKey not in ("+ids+")");
						if (photolist.size()>0) {
							album.setAlbumPhotoId((int)(photolist.get(0).getPrimaryKey()));//随机放入相册封面
						}else{
							album.setAlbumPhotoId(null);
						}
					}
				}
				
				int count = album.getAlbumPhotoCount()-pks.length;
				album.setAlbumPhotoCount(count);
				oaAlbumDao.save(album);
			}
		}
	}
	
	//获取规章制度
	public List<OaRegulations> getOaRegulationsByPager(OaRegulations regulations,Pager pager){
		List<OaRegulations> regulList = oaRegulationsDao.findByHqlWherePage(OaCmpreHqlPack.getOaRegulAtions(regulations)+"order by model.recordDate desc", pager);
		for (OaRegulations reg : regulList) {
			reg.setRegulationsEmployee(hrmEmployeeDao.getByPK(reg.getOaRegulationsEmp()));
			reg.setRegulationsType(libraryInfoDao.getByPK(reg.getOaRegulationsType().longValue()));
		}
		return regulList;
	}
	
	//规章制度 数量
	public int getOaRegulationsCount(OaRegulations regulations){
		return oaRegulationsDao.findByHqlWhereCount(OaCmpreHqlPack.getOaRegulAtions(regulations));
	}
	
	//获取单个规章制度
	public OaRegulations getOaRegulAtionsByPk(long pk,boolean bl){
		OaRegulations regul = oaRegulationsDao.getByPK(pk);
		if (bl) {
			regul.setRegulationsEmployee(hrmEmployeeDao.getByPK(regul.getOaRegulationsEmp()));
			regul.setRegulationsType(libraryInfoDao.getByPK(regul.getOaRegulationsType().longValue()));
		}
		return regul;
	}
	
	//保存规章制度
	public OaRegulations saveOaRegulAtions(OaRegulations regul){
		return (OaRegulations) oaRegulationsDao.save(regul);
	}
	
	//修改制度状态
	public void updateOaRegulAtionsStatus(long[] pk,String empid){
		for (long l : pk) {
			OaRegulations oaregul = oaRegulationsDao.getByPK(l);
			if (oaregul.getRegulationsStatus().intValue() == EnumUtil.SYS_ISACTION.Vaild.value) {
				oaregul.setRegulationsStatus(EnumUtil.SYS_ISACTION.No_Vaild.value);
			}else{
				oaregul.setRegulationsStatus(EnumUtil.SYS_ISACTION.Vaild.value);
			}
			oaregul.setLastmodiId(empid);
			oaregul.setLastmodiDate(UtilWork.getNowTime());
			oaRegulationsDao.save(oaregul);
		}
	}
	
	//删除规章制度
	public OaRegulations deleteOaRegulations(long id){
		OaRegulations tmp = oaRegulationsDao.getByPK(id);
		oaRegulationsDao.remove(tmp);
		return tmp;
	}
}
