package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_BOARDROOM 对应daoImpl
 */
@Repository
public class OaBoardroomDaoImpl extends BaseHapiDaoimpl<OaBoardroom, Long> implements IOaBoardroomDao {

   public OaBoardroomDaoImpl(){
      super(OaBoardroom.class);
   }
}