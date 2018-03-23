package com.pinhuba.core.iservice;

import com.pinhuba.core.pojo.DxCorporation;
import java.util.List;
import com.pinhuba.common.pages.Pager;

public interface IDxCorporationService{

    public int listDxCorporationCount(DxCorporation dxCorporation);
    public List<DxCorporation> listDxCorporation(DxCorporation dxCorporation, Pager pager);
    public List<DxCorporation> listDxCorporation(DxCorporation dxCorporation);
    public DxCorporation saveDxCorporation(DxCorporation dxCorporation);
    public DxCorporation getDxCorporationByPk(String pk);
    public void deleteDxCorporationByPks(String[] pks);
}