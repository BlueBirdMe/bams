package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_CAR_USE 对应daoImpl
 */
@Repository
public class OaCarUseDaoImpl extends BaseHapiDaoimpl<OaCarUse, Long> implements IOaCarUseDao {

   public OaCarUseDaoImpl(){
      super(OaCarUse.class);
   }
}