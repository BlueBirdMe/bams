package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_VOTE_STATUS 对应daoImpl
 */
@Repository
public class OaVoteStatusDaoImpl extends BaseHapiDaoimpl<OaVoteStatus, Long> implements IOaVoteStatusDao {

   public OaVoteStatusDaoImpl(){
      super(OaVoteStatus.class);
   }
}