package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：sys_method_help 对应daoImpl
 */
@Repository
public class SysMethodHelpDaoImpl extends BaseHapiDaoimpl<SysMethodHelp, Long> implements ISysMethodHelpDao {

   public SysMethodHelpDaoImpl(){
      super(SysMethodHelp.class);
   }
}