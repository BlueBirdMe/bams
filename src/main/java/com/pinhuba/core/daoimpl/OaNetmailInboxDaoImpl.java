package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_NETMAIL_INBOX 对应daoImpl
 */
@Repository
public class OaNetmailInboxDaoImpl extends BaseHapiDaoimpl<OaNetmailInbox, Long> implements IOaNetmailInboxDao {

   public OaNetmailInboxDaoImpl(){
      super(OaNetmailInbox.class);
   }
}