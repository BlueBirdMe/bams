package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_FORUMS 对应daoImpl
 */
@Repository
public class OaForumsDaoImpl extends BaseHapiDaoimpl<OaForums, Long> implements IOaForumsDao {

   public OaForumsDaoImpl(){
      super(OaForums.class);
   }
}