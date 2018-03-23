package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;

@Repository
public class SysMethodBtnDaoImpl extends BaseHapiDaoimpl<SysMethodBtn, Long> implements ISysMethodBtnDao {

   public SysMethodBtnDaoImpl(){
      super(SysMethodBtn.class);
   }
}