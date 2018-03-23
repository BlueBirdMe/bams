package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_ANNOUNCEMENT 对应daoImpl
 */
@Repository
public class OaAnnouncementDaoImpl extends BaseHapiDaoimpl<OaAnnouncement, Long> implements IOaAnnouncementDao {

   public OaAnnouncementDaoImpl(){
      super(OaAnnouncement.class);
   }
}