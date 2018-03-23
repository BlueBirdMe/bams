package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_BOOK 对应daoImpl
 */
@Repository
public class OaBookDaoImpl extends BaseHapiDaoimpl<OaBook, Long> implements IOaBookDao {

   public OaBookDaoImpl(){
      super(OaBook.class);
   }
}