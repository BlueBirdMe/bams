package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_USER_GROUP 对应daoImpl
 */
@Repository
public class SysUserGroupDaoImpl extends BaseHapiDaoimpl<SysUserGroup, Long> implements ISysUserGroupDao {

   public SysUserGroupDaoImpl(){
      super(SysUserGroup.class);
   }
}