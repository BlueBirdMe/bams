package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;
import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：dx_educate_temp 对应daoImpl
 */
@Repository
public class DxEducateTempDaoImpl extends BaseHapiDaoimpl<DxEducateTemp, String> implements IDxEducateTempDao {

    public DxEducateTempDaoImpl(){
        super(DxEducateTemp.class);
    }
}