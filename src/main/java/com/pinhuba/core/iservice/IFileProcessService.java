package com.pinhuba.core.iservice;

import com.pinhuba.core.pojo.SysAttachmentInfo;
import com.pinhuba.core.pojo.SysImageInfo;

public interface IFileProcessService {
	public SysImageInfo getImageInfoByPk(long pk);
	
	public SysAttachmentInfo getAttachmentInfoByPk(long pk);
}
