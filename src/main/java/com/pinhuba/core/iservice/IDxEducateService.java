package com.pinhuba.core.iservice;

import com.pinhuba.core.pojo.DxEducate;
import java.util.List;
import com.pinhuba.common.pages.Pager;

public interface IDxEducateService{

    public int listDxEducateCount(DxEducate dxEducate);
    public List<DxEducate> listDxEducate(DxEducate dxEducate, Pager pager);
    public List<DxEducate> listDxEducate(DxEducate dxEducate);
    public DxEducate saveDxEducate(DxEducate dxEducate);
    public DxEducate getDxEducateByPk(String pk);
    public void deleteDxEducateByPks(String[] pks);
    public DxEducate getModDxEducateByPk(String pk);
}