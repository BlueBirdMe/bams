package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_NETMAIL_PERSON 对应daoImpl
 */
@Repository
public class OaNetmailPersonDaoImpl extends BaseHapiDaoimpl<OaNetmailPerson, Long> implements IOaNetmailPersonDao {

   public OaNetmailPersonDaoImpl(){
      super(OaNetmailPerson.class);
   }
}