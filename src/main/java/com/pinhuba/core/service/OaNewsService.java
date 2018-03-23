package com.pinhuba.core.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinhuba.common.pack.HqlPack;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.core.dao.IOaAdversariaDao;
import com.pinhuba.core.dao.IOaAnnouncementDao;
import com.pinhuba.core.dao.IOaNoticeDao;
import com.pinhuba.core.dao.ISysLibraryInfoDao;
import com.pinhuba.core.iservice.IOaNewsService;
import com.pinhuba.core.pojo.OaAdversaria;
import com.pinhuba.core.pojo.OaAnnouncement;
import com.pinhuba.core.pojo.OaNotice;
import com.pinhuba.core.pojo.SysLibraryInfo;

/**********************************************
Class name: 信息发布
Description:对Sevice服务进行描述
Others:         // 
History:        
liurunkai    2010.4.28     v3.0
**********************************************/
@Service
@Transactional
public class OaNewsService implements IOaNewsService {
	@Resource
	private IOaAnnouncementDao oaAnnouncementDao;
	@Resource
	private IOaNoticeDao oaNoticeDao;
	@Resource
	private IOaAdversariaDao oaAdversariaDao;
	@Resource
	private ISysLibraryInfoDao sysLibraryInfoDao;

	/**
	 * 获取所有公告
	 */
	public List<OaAnnouncement> getAllAnnouncement(OaAnnouncement announcement, long l, Pager pager) {
		List<OaAnnouncement> list = oaAnnouncementDao.findBySqlPage(HqlPack.packAnnouncementQuery(announcement,l)+" order by announcement.oa_anno_time desc,announcement.oa_anno_level",OaAnnouncement.class, pager);
		return list;
	}
	
	/**
	 * 统计公告数量
	 */
	public int listAnnouncementCount(OaAnnouncement announcement, long l) {
		int cout = oaAnnouncementDao.findBySqlCount(HqlPack.packAnnouncementQuery(announcement,l));
		return cout;
	}
	
	/**
	 * 删除公告
	 * @param ids
	 */
	public void deleteAnnouncementByPk(long[] ids) {
		for (long l : ids) {
			OaAnnouncement announcement = oaAnnouncementDao.getByPK(l);
			oaAnnouncementDao.remove(announcement);
		}
	}
	
	/**
	 * 根据主键获取公告
	 */
	public OaAnnouncement getAnnouncementByPK(long id) {
		OaAnnouncement announcement = oaAnnouncementDao.getByPK(id);
		return announcement;
	}
	
	/**
	 * 保存公告
	 */
	public OaAnnouncement saveAnnouncement(OaAnnouncement announcement) {
		OaAnnouncement oaAnnouncement = (OaAnnouncement)oaAnnouncementDao.save(announcement);
		return oaAnnouncement;
	}
	
	/**
	 * 删除通知信息
	 * @param ids
	 */
	public void deleteNoticeByPk(long[] ids) {
		for (long l : ids) {
			OaNotice notice = oaNoticeDao.getByPK(l);
			oaNoticeDao.remove(notice);
		}
	}
	
	/**
	 * 获取所有通知
	 */
	public List<OaNotice> getAllNotice(OaNotice notice, long l, Pager pager) {
		List<OaNotice> list = oaNoticeDao.findBySqlPage(HqlPack.packNoticeQuery(notice,l)+" order by notice.oa_noti_time desc,notice.oa_noti_type",OaNotice.class, pager);
		return list;
	}
	
	/**
	 * 根据主键获取通知
	 */
	public OaNotice getNoticeByPK(long id) {
		OaNotice notive = oaNoticeDao.getByPK(id);
		return notive;
	}
	
    /**
     * 统计通知数量
     */
	public int listNoticeCount(OaNotice notice, long l) {
		int count = oaNoticeDao.findBySqlCount(HqlPack.packNoticeQuery(notice,l));
		return count;
	}
	
	/**
	 * 保存通知信息
	 */
	public OaNotice saveNotice(OaNotice notice) {
		OaNotice oaNotice = (OaNotice) oaNoticeDao.save(notice);
		return oaNotice;
	}
	
	/**
	 * 删除公司记事
	 */
	public void deleteAdversariaByPk(long[] ids) {
		for (long l : ids) {
			OaAdversaria adversaria = oaAdversariaDao.getByPK(l);
			oaAdversariaDao.remove(adversaria);
		}
	}
	
	/**
	 * 根据主键获取公司记事
	 */
	public OaAdversaria getAdversariaByPK(long id) {
		OaAdversaria adversaria = oaAdversariaDao.getByPK(id);
		return adversaria;
	}
	
	/**
	 * 获取所有公司记事
	 */
	public List<OaAdversaria> getAllAdversaria(OaAdversaria adversaria, long l, Pager pager) {
		List<OaAdversaria> oaAdversaria = oaAdversariaDao.findBySqlPage(HqlPack.packAdversariaQuery(adversaria,l)+" order by adversaria.oa_adver_time desc,adversaria.oa_adver_level",OaAdversaria.class, pager);
		return oaAdversaria;
	}
	
    /**
     * 统计公司记事数量
     */
	public int listAdversariaCount(OaAdversaria adversaria, long l) {
		int count = oaAdversariaDao.findBySqlCount(HqlPack.packAdversariaQuery(adversaria,l));
		return count;
	}
	
	/**
	 * 保存公司记事
	 */
	public OaAdversaria saveAdversaria(OaAdversaria adversaria) {
		OaAdversaria oaAdversaria = (OaAdversaria) oaAdversariaDao.save(adversaria);
		return oaAdversaria;
	}
	
	/**
	 * 获取数据字典
	 */
	public SysLibraryInfo getLibraryInfoByPK(long id) {
		SysLibraryInfo libraryInfo = sysLibraryInfoDao.getByPK(id);
		return libraryInfo;
	}
}
