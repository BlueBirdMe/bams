package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_TOOLS 对应daoImpl
 */
@Repository
public class OaToolsDaoImpl extends BaseHapiDaoimpl<OaTools, Long> implements IOaToolsDao {

   public OaToolsDaoImpl(){
      super(OaTools.class);
   }
}