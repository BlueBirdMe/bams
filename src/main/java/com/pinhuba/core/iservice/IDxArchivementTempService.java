package com.pinhuba.core.iservice;

import com.pinhuba.core.pojo.DxArchivementTemp;
import java.util.List;
import com.pinhuba.common.pages.Pager;

public interface IDxArchivementTempService{

    public int listDxArchivementTempCount(DxArchivementTemp dxArchivementTemp);
    public List<DxArchivementTemp> listDxArchivementTemp(DxArchivementTemp dxArchivementTemp, Pager pager);
    public List<DxArchivementTemp> listDxArchivementTemp(DxArchivementTemp dxArchivementTemp);
    public DxArchivementTemp saveDxArchivementTemp(DxArchivementTemp dxArchivementTemp);
    public DxArchivementTemp getDxArchivementTempByPk(String pk);
    public void deleteDxArchivementTempByPks(String[] pks);
}