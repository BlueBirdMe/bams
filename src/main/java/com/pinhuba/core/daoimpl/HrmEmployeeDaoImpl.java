package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：HRM_EMPLOYEE 对应daoImpl
 */
@Repository
public class HrmEmployeeDaoImpl extends BaseHapiDaoimpl<HrmEmployee, String> implements IHrmEmployeeDao {

   public HrmEmployeeDaoImpl(){
      super(HrmEmployee.class);
   }
}