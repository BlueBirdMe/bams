package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_CHAT_GROUPS 对应daoImpl
 */
@Repository
public class OaChatGroupsDaoImpl extends BaseHapiDaoimpl<OaChatGroups, Long> implements IOaChatGroupsDao {

   public OaChatGroupsDaoImpl(){
      super(OaChatGroups.class);
   }
}