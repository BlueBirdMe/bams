package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_VOTE 对应daoImpl
 */
@Repository
public class OaVoteDaoImpl extends BaseHapiDaoimpl<OaVote, Long> implements IOaVoteDao {

   public OaVoteDaoImpl(){
      super(OaVote.class);
   }
}