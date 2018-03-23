package com.pinhuba.common.pack;

import com.pinhuba.core.pojo.OaAlbum;
import com.pinhuba.core.pojo.OaForms;
import com.pinhuba.core.pojo.OaJournals;
import com.pinhuba.core.pojo.OaJournalsType;
import com.pinhuba.core.pojo.OaPhoto;
import com.pinhuba.core.pojo.OaRegulations;
import com.pinhuba.core.pojo.OaWareType;
import com.pinhuba.core.pojo.OaWarehouse;

/**
 * 公司资源模块查询语句封装
 * @author peng.ning
 *
 */
public class OaCmpreHqlPack {
	//==============公司资源-知识仓库============
	public static String getOaWareTypeSql(OaWareType wareType){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(wareType.getOaTypeName(), "oaTypeName", result);
		HqlPack.getNumEqualPack(wareType.getFormsorware(), "formsorware", result);
		HqlPack.getNumEqualPack(wareType.getCompanyId(), "companyId",result);
		return result.toString();
	}

	public static String getOaWareHouseSql(OaWarehouse wareHouse){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(wareHouse.getOaWareEmp(), "oaWareEmp", result);
		HqlPack.getStringLikerPack(wareHouse.getOaWareName(), "oaWareName", result);
		HqlPack.getStringLikerPack(wareHouse.getOaKeyword(), "oaKeyword", result);
		HqlPack.timeBuilder(wareHouse.getOaWareTime(), "oaWareTime", result, false,false);
		HqlPack.getNumEqualPack(wareHouse.getOaWareType(), "oaWareType", result);
		HqlPack.getNumEqualPack(wareHouse.getCompanyId(), "companyId",result);
		return result.toString();
	}
	
	public static String getOaWareHouseAndPremSql(OaWarehouse wareHouse,String typeids){
		StringBuffer result = new StringBuffer();
		result.append(getOaWareHouseSql(wareHouse));
		HqlPack.getInPack(typeids, "oaWareType", result);
		return result.toString();
	}
	//========常用表格=============================
	public static String getOaFormsAndPremSql(OaForms forms,String typeids){
		StringBuffer result = new StringBuffer();
		result.append(getOaFormsSql(forms));
		HqlPack.getInPack(typeids, "oaFormType", result);
		return result.toString();
	}
	
	public static String getOaFormsSql(OaForms forms){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(forms.getOaFormEmp(), "oaFormEmp", result);
		HqlPack.getStringLikerPack(forms.getOaFormName(), "oaFormName", result);
		HqlPack.timeBuilder(forms.getOaFormTime(), "oaFormTime", result, false,false);
		HqlPack.getNumEqualPack(forms.getOaFormType(), "oaFormType", result);
		HqlPack.getNumEqualPack(forms.getCompanyId(), "companyId",result);
		return result.toString();
	}
	//=============期刊==================
	public static String getOaJournalsTypeSql(OaJournalsType journalsType){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(journalsType.getJournalsTypeName(), "journalsTypeName", result);
		HqlPack.getStringLikerPack(journalsType.getJournalsTypePress(), "journalsTypePress", result);
		HqlPack.getNumEqualPack(journalsType.getCompanyId(), "companyId",result);
		return result.toString();
	}
	
	public static String getOaJournalsSql(OaJournals journals){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(journals.getRecordId(), "recordId", result);
		HqlPack.getStringLikerPack(journals.getJournalsCount(), "journalsCount", result);
		HqlPack.getStringLikerPack(journals.getJournalsCode(), "journalsCode", result);
		HqlPack.getNumEqualPack(journals.getJournalsTypeId(), "journalsTypeId", result);
		HqlPack.getNumEqualPack(journals.getCompanyId(), "companyId",result);
		return result.toString();
	}
	
	//==============公司相册======================
	public static String getOaAlbumSql(OaAlbum album){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(album.getAlbumCreateEmployee(), "albumCreateEmployee", result);
		HqlPack.getNumNOEqualPack(album.getPrimaryKey(), "primaryKey", result);
		HqlPack.getStringLikerPack(album.getAlbumName(), "albumName", result);
		HqlPack.getNumEqualPack(album.getAlbumType(), "albumType", result);
		HqlPack.timeBuilder(album.getAlbumTime(), "albumTime", result,false,false);
		HqlPack.getNumEqualPack(album.getCompanyId(), "companyId",result);
		return result.toString();
	}
	
	public static String getOaAlbumAndPremSql(OaAlbum album,String empid,String deptid){
		StringBuffer result = new StringBuffer();
		result.append(getOaAlbumSql(album));
		//result.append(" and ( model.albumEmps like '%"+empid+"%' or model.albumDeps like '%"+deptid+"%') ");
//		result.append(" and (CheckStrInArr('"+deptid+"',model.albumDeps)>0 or model.albumEmps like '%"+empid+"%')");
		result.append(" and (INSTR(model.albumDeps,'"+deptid+"')>0 or model.albumEmps like '%"+empid+"%')");
		return result.toString();
	}
	
	public static String getOaPhoto(OaPhoto photo){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(photo.getPhotoName(), "photoName", result);
		HqlPack.getNumEqualPack(photo.getAlbumId(), "albumId", result);
		HqlPack.getNumEqualPack(photo.getCompanyId(), "companyId",result);
		return result.toString();
	}
	
	//===================规章制度==================
	public static String getOaRegulAtions(OaRegulations regulations){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(regulations.getOaRegulationsTitle(), "oaRegulationsTitle", result);
		HqlPack.getNumEqualPack(regulations.getOaRegulationsType(), "oaRegulationsType", result);
		HqlPack.timeBuilder(regulations.getOaRegulationsTime(), "oaRegulationsTime", result,false,false);
		HqlPack.getNumEqualPack(regulations.getRegulationsStatus(), "regulationsStatus", result);
		HqlPack.timeBuilder(regulations.getRegulatStratTime(), "regulatStratTime", result,false,false);
		HqlPack.getNumEqualPack(regulations.getCompanyId(), "companyId",result);
		
		if (regulations.getTmpDatetime()!=null&&regulations.getTmpDatetime().length()>0) {
			result.append(" and model.regulatStratTime<='"+regulations.getTmpDatetime()+"'");
		}
		
		return result.toString();
	}
}
