package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;
import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：dx_archivement 对应daoImpl
 */
@Repository
public class DxArchivementDaoImpl extends BaseHapiDaoimpl<DxArchivement, String> implements IDxArchivementDao {

    public DxArchivementDaoImpl(){
        super(DxArchivement.class);
    }
}