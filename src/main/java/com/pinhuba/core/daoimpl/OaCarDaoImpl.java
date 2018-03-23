package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_CAR 对应daoImpl
 */
@Repository
public class OaCarDaoImpl extends BaseHapiDaoimpl<OaCar, Long> implements IOaCarDao {

   public OaCarDaoImpl(){
      super(OaCar.class);
   }
}