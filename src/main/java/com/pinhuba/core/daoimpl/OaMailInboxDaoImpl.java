package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_MAIL_INBOX 对应daoImpl
 */
@Repository
public class OaMailInboxDaoImpl extends BaseHapiDaoimpl<OaMailInbox, Long> implements IOaMailInboxDao {

   public OaMailInboxDaoImpl(){
      super(OaMailInbox.class);
   }
}