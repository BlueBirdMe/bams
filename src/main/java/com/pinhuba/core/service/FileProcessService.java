package com.pinhuba.core.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinhuba.core.dao.ISysAttachmentInfoDao;
import com.pinhuba.core.dao.ISysImageInfoDao;
import com.pinhuba.core.iservice.IFileProcessService;
import com.pinhuba.core.pojo.SysAttachmentInfo;
import com.pinhuba.core.pojo.SysImageInfo;
@Service
@Transactional
public class FileProcessService implements IFileProcessService {
	@Resource
	private ISysImageInfoDao sysImageInfodao;
	@Resource
	private ISysAttachmentInfoDao sysAttachmentDao;
	
	public SysImageInfo getImageInfoByPk(long pk){
		return sysImageInfodao.getByPK(pk);
	}
	
	public SysAttachmentInfo getAttachmentInfoByPk(long pk){
		return sysAttachmentDao.getByPK(pk);
	}
}
