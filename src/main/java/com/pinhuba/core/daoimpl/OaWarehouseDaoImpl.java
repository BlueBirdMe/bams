package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_WAREHOUSE 对应daoImpl
 */
@Repository
public class OaWarehouseDaoImpl extends BaseHapiDaoimpl<OaWarehouse, Long> implements IOaWarehouseDao {

   public OaWarehouseDaoImpl(){
      super(OaWarehouse.class);
   }
}