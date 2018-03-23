package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_SMS_SEND 对应daoImpl
 */
@Repository
public class OaSmsSendDaoImpl extends BaseHapiDaoimpl<OaSmsSend, Long> implements IOaSmsSendDao {

   public OaSmsSendDaoImpl(){
      super(OaSmsSend.class);
   }
}