package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_JOURNALS 对应daoImpl
 */
@Repository
public class OaJournalsDaoImpl extends BaseHapiDaoimpl<OaJournals, Long> implements IOaJournalsDao {

   public OaJournalsDaoImpl(){
      super(OaJournals.class);
   }
}