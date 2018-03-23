package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_NETDISK_SHARE 对应daoImpl
 */
@Repository
public class OaNetdiskShareDaoImpl extends BaseHapiDaoimpl<OaNetdiskShare, Long> implements IOaNetdiskShareDao {

   public OaNetdiskShareDaoImpl(){
      super(OaNetdiskShare.class);
   }
}