package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：HRM_DEPARTMENT 对应daoImpl
 */
@Repository
public class HrmDepartmentDaoImpl extends BaseHapiDaoimpl<HrmDepartment, Long> implements IHrmDepartmentDao {

   public HrmDepartmentDaoImpl(){
      super(HrmDepartment.class);
   }
}