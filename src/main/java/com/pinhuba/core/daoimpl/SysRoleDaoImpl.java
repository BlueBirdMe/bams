package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_ROLE 对应daoImpl
 */
@Repository
public class SysRoleDaoImpl extends BaseHapiDaoimpl<SysRole, Long> implements ISysRoleDao {

   public SysRoleDaoImpl(){
      super(SysRole.class);
   }
}