package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_USER_METHODS 对应daoImpl
 */
@Repository
public class SysUserMethodsDaoImpl extends BaseHapiDaoimpl<SysUserMethods, Long> implements ISysUserMethodsDao {

   public SysUserMethodsDaoImpl(){
      super(SysUserMethods.class);
   }
}