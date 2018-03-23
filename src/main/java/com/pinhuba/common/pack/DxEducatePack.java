package com.pinhuba.common.pack;

import com.pinhuba.core.pojo.DxEducate;
import com.pinhuba.common.util.UtilWork;

public class DxEducatePack{

    public static String packDxEducateQuery(DxEducate dxEducate){
        StringBuffer result = new StringBuffer();
        HqlPack.getStringEqualPack(dxEducate.getEmpid(), "empid", result);
        HqlPack.getStringLikerPack(dxEducate.getName(), "name", result);
        //result.append(" order by model.recordDate desc");
        return result.toString();
    }

}