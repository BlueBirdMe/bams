package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：HRM_TIMEDRECORD 对应daoImpl
 */
@Repository
public class HrmTimedrecordDaoImpl extends BaseHapiDaoimpl<HrmTimedrecord, Long> implements IHrmTimedrecordDao {

   public HrmTimedrecordDaoImpl(){
      super(HrmTimedrecord.class);
   }
}