package com.pinhuba.common.pack;

import com.pinhuba.core.pojo.DxArchivement;
import com.pinhuba.common.util.UtilWork;

public class DxArchivementPack{

    public static String packDxArchivementQuery(DxArchivement dxArchivement){
        StringBuffer result = new StringBuffer();
        HqlPack.getStringEqualPack(dxArchivement.getEmpid(), "empid", result);
        HqlPack.getStringLikerPack(dxArchivement.getName(), "name", result);
        //result.append(" order by model.recordDate desc");
        return result.toString();
    }

}