package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_JOURNALS_TYPE 对应daoImpl
 */
@Repository
public class OaJournalsTypeDaoImpl extends BaseHapiDaoimpl<OaJournalsType, Long> implements IOaJournalsTypeDao {

   public OaJournalsTypeDaoImpl(){
      super(OaJournalsType.class);
   }
}