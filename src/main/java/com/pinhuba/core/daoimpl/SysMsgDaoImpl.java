package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_MSG 对应daoImpl
 */
@Repository
public class SysMsgDaoImpl extends BaseHapiDaoimpl<SysMsg, Long> implements ISysMsgDao {

   public SysMsgDaoImpl(){
      super(SysMsg.class);
   }
}