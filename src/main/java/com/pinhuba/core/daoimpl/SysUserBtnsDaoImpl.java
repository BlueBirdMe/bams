package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;

@Repository
public class SysUserBtnsDaoImpl extends BaseHapiDaoimpl<SysUserBtns, Long> implements ISysUserBtnsDao {

   public SysUserBtnsDaoImpl(){
      super(SysUserBtns.class);
   }
}