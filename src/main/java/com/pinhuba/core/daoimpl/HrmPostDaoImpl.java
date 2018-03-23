package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：HRM_POST 对应daoImpl
 */
@Repository
public class HrmPostDaoImpl extends BaseHapiDaoimpl<HrmPost, Long> implements IHrmPostDao {

   public HrmPostDaoImpl(){
      super(HrmPost.class);
   }
}