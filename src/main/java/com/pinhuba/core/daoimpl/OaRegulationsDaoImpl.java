package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_REGULATIONS 对应daoImpl
 */
@Repository
public class OaRegulationsDaoImpl extends BaseHapiDaoimpl<OaRegulations, Long> implements IOaRegulationsDao {

   public OaRegulationsDaoImpl(){
      super(OaRegulations.class);
   }
}