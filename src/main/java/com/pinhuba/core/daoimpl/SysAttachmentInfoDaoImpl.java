package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_ATTACHMENT_INFO 对应daoImpl
 */
@Repository
public class SysAttachmentInfoDaoImpl extends BaseHapiDaoimpl<SysAttachmentInfo, Long> implements ISysAttachmentInfoDao {

   public SysAttachmentInfoDaoImpl(){
      super(SysAttachmentInfo.class);
   }
}