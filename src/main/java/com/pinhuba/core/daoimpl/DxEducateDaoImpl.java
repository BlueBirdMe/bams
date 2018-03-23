package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;
import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：dx_educate 对应daoImpl
 */
@Repository
public class DxEducateDaoImpl extends BaseHapiDaoimpl<DxEducate, String> implements IDxEducateDao {

    public DxEducateDaoImpl(){
        super(DxEducate.class);
    }
}