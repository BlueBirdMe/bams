package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：hrm_contract_type 对应daoImpl
 */
@Repository
public class HrmContractTypeDaoImpl extends BaseHapiDaoimpl<HrmContractType, String> implements IHrmContractTypeDao {

    public HrmContractTypeDaoImpl(){
        super(HrmContractType.class);
    }
}