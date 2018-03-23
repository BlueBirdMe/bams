package com.pinhuba.common.pack;

import com.pinhuba.core.pojo.DxUserinfo;
import com.pinhuba.common.util.UtilWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DxUserinfoPack{

    public static String packDxUserinfoQuery(DxUserinfo dxUserinfo){
        StringBuffer result = new StringBuffer();
        HqlPack.getStringEqualPack(dxUserinfo.getEmpid(), "empid", result);
        HqlPack.getStringLikerPack(dxUserinfo.getName(), "name", result);
        //result.append(" order by model.recordDate desc");
        return result.toString();
    }
}