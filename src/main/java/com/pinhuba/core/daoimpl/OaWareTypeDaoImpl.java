package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_WARE_TYPE 对应daoImpl
 */
@Repository
public class OaWareTypeDaoImpl extends BaseHapiDaoimpl<OaWareType, Long> implements IOaWareTypeDao {

   public OaWareTypeDaoImpl(){
      super(OaWareType.class);
   }
}