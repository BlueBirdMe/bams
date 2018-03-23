package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_POSTS 对应daoImpl
 */
@Repository
public class OaPostsDaoImpl extends BaseHapiDaoimpl<OaPosts, Long> implements IOaPostsDao {

   public OaPostsDaoImpl(){
      super(OaPosts.class);
   }
}