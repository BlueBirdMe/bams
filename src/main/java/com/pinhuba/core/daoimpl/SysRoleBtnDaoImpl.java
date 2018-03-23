package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;

@Repository
public class SysRoleBtnDaoImpl extends BaseHapiDaoimpl<SysRoleBtn, Long> implements ISysRoleBtnDao {

   public SysRoleBtnDaoImpl(){
      super(SysRoleBtn.class);
   }
}