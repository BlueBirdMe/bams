package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_TRSVEL 对应daoImpl
 */
@Repository
public class OaTrsvelDaoImpl extends BaseHapiDaoimpl<OaTrsvel, Long> implements IOaTrsvelDao {

   public OaTrsvelDaoImpl(){
      super(OaTrsvel.class);
   }
}