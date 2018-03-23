package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_CAR_MAINTEN 对应daoImpl
 */
@Repository
public class OaCarMaintenDaoImpl extends BaseHapiDaoimpl<OaCarMainten, Long> implements IOaCarMaintenDao {
 
   public OaCarMaintenDaoImpl(){
      super(OaCarMainten.class);
   }
}