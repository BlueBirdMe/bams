package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_NOTICE 对应daoImpl
 */
@Repository
public class OaNoticeDaoImpl extends BaseHapiDaoimpl<OaNotice, Long> implements IOaNoticeDao {

   public OaNoticeDaoImpl(){
      super(OaNotice.class);
   }
}