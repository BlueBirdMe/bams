package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_SUMMARY 对应daoImpl
 */
@Repository
public class OaSummaryDaoImpl extends BaseHapiDaoimpl<OaSummary, Long> implements IOaSummaryDao {

   public OaSummaryDaoImpl(){
      super(OaSummary.class);
   }
}