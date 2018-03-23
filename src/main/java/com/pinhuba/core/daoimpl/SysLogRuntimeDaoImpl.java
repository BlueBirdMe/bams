package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：sys_log_runtime 对应daoImpl
 */
@Repository
public class SysLogRuntimeDaoImpl extends BaseHapiDaoimpl<SysLogRuntime, Long> implements ISysLogRuntimeDao {

   public SysLogRuntimeDaoImpl(){
      super(SysLogRuntime.class);
   }
}