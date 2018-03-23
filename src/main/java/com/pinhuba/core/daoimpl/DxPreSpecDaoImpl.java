package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;
import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：dx_pre_spec 对应daoImpl
 */
@Repository
public class DxPreSpecDaoImpl extends BaseHapiDaoimpl<DxPreSpec, String> implements IDxPreSpecDao {

    public DxPreSpecDaoImpl(){
        super(DxPreSpec.class);
    }
}