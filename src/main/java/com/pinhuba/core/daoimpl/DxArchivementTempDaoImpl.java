package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;
import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：dx_archivement_temp 对应daoImpl
 */
@Repository
public class DxArchivementTempDaoImpl extends BaseHapiDaoimpl<DxArchivementTemp, String> implements IDxArchivementTempDao {

    public DxArchivementTempDaoImpl(){
        super(DxArchivementTemp.class);
    }
}