package com.pinhuba.core.iservice;

import com.pinhuba.core.pojo.DxPreSpec;
import java.util.List;
import com.pinhuba.common.pages.Pager;

public interface IDxPreSpecService{

    public int listDxPreSpecCount(DxPreSpec dxPreSpec);
    public List<DxPreSpec> listDxPreSpec(DxPreSpec dxPreSpec, Pager pager);
    public List<DxPreSpec> listDxPreSpec(DxPreSpec dxPreSpec);
    public DxPreSpec saveDxPreSpec(DxPreSpec dxPreSpec);
    public DxPreSpec getDxPreSpecByPk(String pk);
    public void deleteDxPreSpecByPks(String[] pks);
}