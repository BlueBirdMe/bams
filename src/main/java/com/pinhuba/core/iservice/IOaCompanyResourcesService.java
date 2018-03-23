package com.pinhuba.core.iservice;

import java.util.ArrayList;
import java.util.List;

import com.pinhuba.common.pages.Pager;
import com.pinhuba.core.pojo.OaAlbum;
import com.pinhuba.core.pojo.OaForms;
import com.pinhuba.core.pojo.OaJournals;
import com.pinhuba.core.pojo.OaJournalsType;
import com.pinhuba.core.pojo.OaPhoto;
import com.pinhuba.core.pojo.OaRegulations;
import com.pinhuba.core.pojo.OaTools;
import com.pinhuba.core.pojo.OaWareType;
import com.pinhuba.core.pojo.OaWarehouse;
import com.pinhuba.core.pojo.OaWaretypeRange;

/**
 * 公司资源
 * @author peng.ning
 *
 */
public interface IOaCompanyResourcesService {
	public List<OaWareType> getAllOaWageTypeByPager(OaWareType waretype,Pager pager);
	
	public int getAllOaWageTypeCount(OaWareType waretype);
	
	public OaWareType saveOaWareType(String empids,OaWareType wareType);
	
	public OaWareType getOaWareTypeByPk(long pk);
	
	public List<OaWaretypeRange> getOaWaretypeRangeList(long typePk);
	
	public List<OaWarehouse> getWarehoseByTypePk(long pk);
	
	public void deleteWareTypeByPk(long pk);
	
	public void deleteWareTypeRangeByTypePk(long pk);
	
	public List<OaWareType> getAllOaWageTypeBytype(OaWareType waretype);
	
	public List<OaWarehouse> getWareHouseBypager(OaWarehouse wareHouse,Pager pager);
	
	public int getWareHouseCount(OaWarehouse wareHouse);
	
	public OaWarehouse saveWarehouse(OaWarehouse wareHouse);
	
	public OaWarehouse getWarehouseByPk(long pk);
	
	public OaWarehouse deleteWarehouseByIds(long id);
	
	public OaWarehouse getWarehouseAndObjByPk(long pk);
	
	public List<OaWarehouse> getWareHouseByPagerAndPerm(OaWarehouse wareHouse,Pager pager,String empid,int companyId,int type);
	
	public int getWareHouseAndPermCount(OaWarehouse wareHouse,String empid,int companyId,int type);
	
	public List<OaForms> getFormsBypager(OaForms forms,Pager pager);
	
	public int getFormsCount(OaForms forms);
	
	public OaForms getFormsByPk(long pk);
	
	public OaForms saveOaForms(OaForms forms);
	
	public OaForms getFormsAndObjByPk(long pk);
	
	public List<OaForms> getFormsByPagerAndPerm(OaForms forms,Pager pager,String empid,int companyId,int type);
	
	public int getFormsAndPermCount(OaForms forms,String empid,int companyId,int type);
	
	public List<OaJournalsType> getJournalsTypePager(OaJournalsType jourType,Pager pager);
	
	public int getJournalsTypeCount(OaJournalsType jourType);
	
	public OaJournalsType saveJournalsType(OaJournalsType jtype);
	
	public OaJournalsType getJournalsTypeByPk(long pk);
	
	public void deleteJournalsTypeByPk(long pk);
	
	public int getJournalsCount(OaJournals jour);
	
	public List<OaJournals> getJournalsByPager(OaJournals jour,Pager pager);
	
	public List<OaJournalsType> getJournalsType(OaJournalsType jourType);
	
	public OaJournals saveJournals(OaJournals journals);
	
	public OaJournals getJournalsByPk(long pk);
	
	public OaJournals deleteJournalsById(long id);
	
	public OaJournals getJournalsObjectByPk(long pk);
	
	public OaAlbum saveAlbum(OaAlbum album);
	
	public OaAlbum getOaAlbumByPk(long pk,boolean bl);
	
	public List<OaAlbum> getOaAlbumListByPager(OaAlbum album,Pager pager);
	
	public int getOaAlbumListCount(OaAlbum album);
	
	public List<OaAlbum> getAllAlbumList(OaAlbum album);
	
	public void deleteOaAlbum(OaAlbum album);
	
	public List<OaPhoto> getPhotoListPager(OaPhoto photo,Pager pager);
	
	public int getPhotoCount(OaPhoto photo);
	
	public OaPhoto getPhotoByPk(long pk);
	
	public ArrayList<OaPhoto> saveOaPhotos(ArrayList<OaPhoto> photolist);
	
	public ArrayList<OaPhoto> deleteOaPhoto(long[] pks);
	
	public OaPhoto saveOaPhoto(OaPhoto photo);
	
	public List<OaAlbum> getAllAlbumListPrem(OaAlbum album,String empid,String deptid);
	
	public int getOaAlbumListCountPrem(OaAlbum album,String empid,String deptid);
	
	public List<OaAlbum> getOaAlbumListByPagerAndPrem(OaAlbum album,String empid,String deptid,Pager pager) ;
	
	public List<OaRegulations> getOaRegulationsByPager(OaRegulations regulations,Pager pager);
	
	public int getOaRegulationsCount(OaRegulations regulations);
	
	public OaRegulations getOaRegulAtionsByPk(long pk,boolean bl);
	
	public OaRegulations saveOaRegulAtions(OaRegulations regul);
	
	public void updateOaRegulAtionsStatus(long[] pk,String empid);
	
	public OaRegulations deleteOaRegulations(long id);
	
	public void moveOaPhotos(long[] pks,int albumId);
	
	public OaForms deleteFormsByIds(long id);
	
	public  boolean clikeoaWagetypeName(OaWareType oaWareType);
	
	 public boolean clikeJournalsName(OaJournalsType jourType);
	
	
}
