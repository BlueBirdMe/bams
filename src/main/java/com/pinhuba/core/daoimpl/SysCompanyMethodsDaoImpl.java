package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_COMPANY_METHODS 对应daoImpl
 */
@Repository
public class SysCompanyMethodsDaoImpl extends BaseHapiDaoimpl<SysCompanyMethods, Long> implements ISysCompanyMethodsDao {

   public SysCompanyMethodsDaoImpl(){
      super(SysCompanyMethods.class);
   }
}