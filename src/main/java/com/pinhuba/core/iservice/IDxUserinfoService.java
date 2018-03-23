package com.pinhuba.core.iservice;

import com.pinhuba.core.pojo.DxUserinfo;
import java.util.List;
import java.util.Map;

import com.pinhuba.common.pages.Pager;

public interface IDxUserinfoService{

    public int listDxUserinfoCount(DxUserinfo dxUserinfo);
    public List<DxUserinfo> listDxUserinfo(DxUserinfo dxUserinfo, Pager pager);
    public List<DxUserinfo> listDxUserinfo(DxUserinfo dxUserinfo);
    public DxUserinfo saveDxUserinfo(DxUserinfo dxUserinfo);
    public DxUserinfo getDxUserinfoByPk(String pk);
    public void deleteDxUserinfoByPks(String[] pks);
    public List<Object[]> countUserByDept_Age(String s_age,String e_age, String deptName);
}