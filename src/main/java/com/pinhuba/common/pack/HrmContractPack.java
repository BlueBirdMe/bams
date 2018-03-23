package com.pinhuba.common.pack;

import com.pinhuba.core.pojo.HrmContract;
import com.pinhuba.core.pojo.HrmContractType;
import com.pinhuba.common.util.UtilWork;

public class HrmContractPack{

    public static String packHrmContractQuery(HrmContract hrmContract){
        StringBuffer result = new StringBuffer();
        
        if(hrmContract.getEmployee() != null)
        	HqlPack.getStringLikerPack(hrmContract.getEmployee().getHrmEmployeeName(), "employee.hrmEmployeeName", result);
       
        HqlPack.getStringLikerPack(hrmContract.getContractCode(), "contractCode", result);
        HqlPack.getStringLikerPack(hrmContract.getContractName(), "contractName", result);
        
        HqlPack.getNumEqualPack(hrmContract.getContractLimitType(), "contractLimitType", result);
        HqlPack.getNumEqualPack(hrmContract.getContractStatus(), "contractStatus", result);
           
        if(hrmContract.getContractType() != null)
        	HqlPack.getStringEqualPack(hrmContract.getContractType().getPrimaryKey(), "contractType.primaryKey", result);
        
        HqlPack.timeBuilder(hrmContract.getContractBegindate(), "contractBegindate", result, false, false);
        HqlPack.timeBuilder(hrmContract.getContractEnddate(), "contractEnddate", result, false, false);
        HqlPack.getNumEqualPack(hrmContract.getCompanyId(), "companyId", result);
        result.append(" order by model.recordDate desc");
        return result.toString();
    }

    public static String packHrmContractTypeQuery(HrmContractType hrmContractType){
        StringBuffer result = new StringBuffer();
        HqlPack.getStringLikerPack(hrmContractType.getTypeName(), "typeName", result);
        HqlPack.getNumEqualPack(hrmContractType.getCompanyId(), "companyId", result);
        result.append(" order by model.recordDate desc");
        return result.toString();
    }

}