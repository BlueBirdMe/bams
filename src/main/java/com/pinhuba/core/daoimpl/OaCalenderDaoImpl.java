package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_CALENDER 对应daoImpl
 */
@Repository
public class OaCalenderDaoImpl extends BaseHapiDaoimpl<OaCalender, Long> implements IOaCalenderDao {

   public OaCalenderDaoImpl(){
      super(OaCalender.class);
   }
}