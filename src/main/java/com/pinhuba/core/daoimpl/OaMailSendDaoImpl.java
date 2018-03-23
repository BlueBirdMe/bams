package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_MAIL_SEND 对应daoImpl
 */
@Repository
public class OaMailSendDaoImpl extends BaseHapiDaoimpl<OaMailSend, Long> implements IOaMailSendDao {

   public OaMailSendDaoImpl(){
      super(OaMailSend.class);
   }
}