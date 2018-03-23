package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_PARAM 对应daoImpl
 */
@Repository
public class SysParamDaoImpl extends BaseHapiDaoimpl<SysParam, Long> implements ISysParamDao {

   public SysParamDaoImpl(){
      super(SysParam.class);
   }
}