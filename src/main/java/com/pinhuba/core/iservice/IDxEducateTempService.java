package com.pinhuba.core.iservice;

import com.pinhuba.core.pojo.DxEducateTemp;
import java.util.List;
import com.pinhuba.common.pages.Pager;

public interface IDxEducateTempService{

    public int listDxEducateTempCount(DxEducateTemp dxEducateTemp);
    public List<DxEducateTemp> listDxEducateTemp(DxEducateTemp dxEducateTemp, Pager pager);
    public List<DxEducateTemp> listDxEducateTemp(DxEducateTemp dxEducateTemp);
    public DxEducateTemp saveDxEducateTemp(DxEducateTemp dxEducateTemp);
    public DxEducateTemp getDxEducateTempByPk(String pk);
    public void deleteDxEducateTempByPks(String[] pks);
}