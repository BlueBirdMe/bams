package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_BOOK_TYPE 对应daoImpl
 */
@Repository
public class OaBookTypeDaoImpl extends BaseHapiDaoimpl<OaBookType, Long> implements IOaBookTypeDao {

   public OaBookTypeDaoImpl(){
      super(OaBookType.class);
   }
}