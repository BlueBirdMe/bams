package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_BOOK_BR 对应daoImpl
 */
@Repository
public class OaBookBrDaoImpl extends BaseHapiDaoimpl<OaBookBr, Long> implements IOaBookBrDao {

   public OaBookBrDaoImpl(){
      super(OaBookBr.class);
   }
}