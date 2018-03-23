package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：HRM_WORKAREA 对应daoImpl
 */
@Repository
public class HrmWorkareaDaoImpl extends BaseHapiDaoimpl<HrmWorkarea, Long> implements IHrmWorkareaDao {

   public HrmWorkareaDaoImpl(){
      super(HrmWorkarea.class);
   }
}