package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_ADVERSARIA 对应daoImpl
 */
@Repository
public class OaAdversariaDaoImpl extends BaseHapiDaoimpl<OaAdversaria, Long> implements IOaAdversariaDao {

   public OaAdversariaDaoImpl(){
      super(OaAdversaria.class);
   }
}