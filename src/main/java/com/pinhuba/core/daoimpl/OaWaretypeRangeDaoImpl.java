package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_WARETYPE_RANGE 对应daoImpl
 */
@Repository
public class OaWaretypeRangeDaoImpl extends BaseHapiDaoimpl<OaWaretypeRange, Long> implements IOaWaretypeRangeDao {

   public OaWaretypeRangeDaoImpl(){
      super(OaWaretypeRange.class);
   }
}