package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_NETMAIL_SET 对应daoImpl
 */
@Repository
public class OaNetmailSetDaoImpl extends BaseHapiDaoimpl<OaNetmailSet, Long> implements IOaNetmailSetDao {

   public OaNetmailSetDaoImpl(){
      super(OaNetmailSet.class);
   }
}