package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_USER_VIEW 对应daoImpl
 */
@Repository
public class SysUserViewDaoImpl extends BaseHapiDaoimpl<SysUserView, Long> implements ISysUserViewDao {

   public SysUserViewDaoImpl(){
      super(SysUserView.class);
   }
}