package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_VOTE_OPTION 对应daoImpl
 */
@Repository
public class OaVoteOptionDaoImpl extends BaseHapiDaoimpl<OaVoteOption, Long> implements IOaVoteOptionDao {

   public OaVoteOptionDaoImpl(){
      super(OaVoteOption.class);
   }
}