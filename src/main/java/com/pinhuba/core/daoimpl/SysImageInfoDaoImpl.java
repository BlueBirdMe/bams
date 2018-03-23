package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_IMAGE_INFO 对应daoImpl
 */
@Repository
public class SysImageInfoDaoImpl extends BaseHapiDaoimpl<SysImageInfo, Long> implements ISysImageInfoDao {

   public SysImageInfoDaoImpl(){
      super(SysImageInfo.class);
   }
}