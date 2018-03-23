package com.pinhuba.core.iservice;

import com.pinhuba.core.pojo.DxArchivement;
import java.util.List;
import com.pinhuba.common.pages.Pager;

public interface IDxArchivementService{

    public int listDxArchivementCount(DxArchivement dxArchivement);
    public List<DxArchivement> listDxArchivement(DxArchivement dxArchivement, Pager pager);
    public List<DxArchivement> listDxArchivement(DxArchivement dxArchivement);
    public DxArchivement saveDxArchivement(DxArchivement dxArchivement);
    public DxArchivement getDxArchivementByPk(String pk);
    public void deleteDxArchivementByPks(String[] pks);
    public DxArchivement getModDxArchivementByPk(String pk);
}