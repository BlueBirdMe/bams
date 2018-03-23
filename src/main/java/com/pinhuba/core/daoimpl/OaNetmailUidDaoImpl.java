package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_NETMAIL_UID 对应daoImpl
 */
@Repository
public class OaNetmailUidDaoImpl extends BaseHapiDaoimpl<OaNetmailUid, Long> implements IOaNetmailUidDao {

   public OaNetmailUidDaoImpl(){
      super(OaNetmailUid.class);
   }
}