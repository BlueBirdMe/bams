package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_ROLE_DETAIL 对应daoImpl
 */
@Repository
public class SysRoleDetailDaoImpl extends BaseHapiDaoimpl<SysRoleDetail, Long> implements ISysRoleDetailDao {

   public SysRoleDetailDaoImpl(){
      super(SysRoleDetail.class);
   }
}