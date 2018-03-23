package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_ROLE_BIND 对应daoImpl
 */
@Repository
public class SysRoleBindDaoImpl extends BaseHapiDaoimpl<SysRoleBind, Long> implements ISysRoleBindDao {

   public SysRoleBindDaoImpl(){
      super(SysRoleBind.class);
   }
}