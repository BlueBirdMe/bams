package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;
import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：dx_corporation 对应daoImpl
 */
@Repository
public class DxCorporationDaoImpl extends BaseHapiDaoimpl<DxCorporation, String> implements IDxCorporationDao {

    public DxCorporationDaoImpl(){
        super(DxCorporation.class);
    }
}