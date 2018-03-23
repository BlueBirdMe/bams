package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_LIBRARY_INFO 对应daoImpl
 */
@Repository
public class SysLibraryInfoDaoImpl extends BaseHapiDaoimpl<SysLibraryInfo, Long> implements ISysLibraryInfoDao {

   public SysLibraryInfoDaoImpl(){
      super(SysLibraryInfo.class);
   }
}