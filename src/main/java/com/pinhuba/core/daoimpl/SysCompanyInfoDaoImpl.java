package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_COMPANY_INFO 对应daoImpl
 */
@Repository
public class SysCompanyInfoDaoImpl extends BaseHapiDaoimpl<SysCompanyInfo, Long> implements ISysCompanyInfoDao {

   public SysCompanyInfoDaoImpl(){
      super(SysCompanyInfo.class);
   }
}