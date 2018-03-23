package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_SMS_INBOX 对应daoImpl
 */
@Repository
public class OaSmsInboxDaoImpl extends BaseHapiDaoimpl<OaSmsInbox, Long> implements IOaSmsInboxDao {

   public OaSmsInboxDaoImpl(){
      super(OaSmsInbox.class);
   }
}