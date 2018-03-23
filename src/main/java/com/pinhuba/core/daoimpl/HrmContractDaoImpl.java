package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：hrm_contract 对应daoImpl
 */
@Repository
public class HrmContractDaoImpl extends BaseHapiDaoimpl<HrmContract, String> implements IHrmContractDao {

    public HrmContractDaoImpl(){
        super(HrmContract.class);
    }
}