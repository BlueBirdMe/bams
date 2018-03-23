package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：oa_car_apply 对应daoImpl
 */
@Repository
public class OaCarApplyDaoImpl extends BaseHapiDaoimpl<OaCarApply, Long> implements IOaCarApplyDao {

   public OaCarApplyDaoImpl(){
      super(OaCarApply.class);
   }
}