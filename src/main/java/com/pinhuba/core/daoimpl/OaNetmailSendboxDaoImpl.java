package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_NETMAIL_SENDBOX 对应daoImpl
 */
@Repository
public class OaNetmailSendboxDaoImpl extends BaseHapiDaoimpl<OaNetmailSendbox, Long> implements IOaNetmailSendboxDao {

   public OaNetmailSendboxDaoImpl(){
      super(OaNetmailSendbox.class);
   }
}