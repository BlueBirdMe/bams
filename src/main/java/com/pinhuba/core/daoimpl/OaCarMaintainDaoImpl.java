package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_CAR_MAINTAIN 对应daoImpl
 */
@Repository
public class OaCarMaintainDaoImpl extends BaseHapiDaoimpl<OaCarMaintain, Long> implements IOaCarMaintainDao {

   public OaCarMaintainDaoImpl(){
      super(OaCarMaintain.class);
   }
}