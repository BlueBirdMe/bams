package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_HELP 对应daoImpl
 */
@Repository
public class SysHelpDaoImpl extends BaseHapiDaoimpl<SysHelp, Long> implements ISysHelpDao {

   public SysHelpDaoImpl(){
      super(SysHelp.class);
   }
}