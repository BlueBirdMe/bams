package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_USER_INFO 对应daoImpl
 */
@Repository
public class SysUserInfoDaoImpl extends BaseHapiDaoimpl<SysUserInfo, Long> implements ISysUserInfoDao {

   public SysUserInfoDaoImpl(){
      super(SysUserInfo.class);
   }
}