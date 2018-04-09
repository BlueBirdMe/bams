package com.pinhuba.core.service;

import com.pinhuba.core.pojo.DxUserinfo;
import com.pinhuba.core.dao.IDxUserinfoDao;
import com.pinhuba.core.iservice.IDxUserinfoService;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pack.DxUserinfoPack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DxUserinfoService implements IDxUserinfoService {

    @Resource
    private IDxUserinfoDao dxUserinfoDao;

    public int listDxUserinfoCount(DxUserinfo dxUserinfo) {
        int count = dxUserinfoDao.findByHqlWhereCount(DxUserinfoPack.packDxUserinfoQuery(dxUserinfo));
        return count;
    }

    public List<DxUserinfo> listDxUserinfo(DxUserinfo dxUserinfo, Pager pager) {
        List<DxUserinfo> list = dxUserinfoDao.findByHqlWherePage(DxUserinfoPack.packDxUserinfoQuery(dxUserinfo), pager);
        return list;
    }

    public List<DxUserinfo> listDxUserinfo(DxUserinfo dxUserinfo) {
        List<DxUserinfo> list = dxUserinfoDao.findByHqlWhere(DxUserinfoPack.packDxUserinfoQuery(dxUserinfo));
        return list;
    }

    public DxUserinfo saveDxUserinfo(DxUserinfo dxUserinfo) {
        DxUserinfo temp = (DxUserinfo) dxUserinfoDao.save(dxUserinfo);
        return temp;
    }

    public DxUserinfo getDxUserinfoByPk(String pk) {
        DxUserinfo dxUserinfo = (DxUserinfo) dxUserinfoDao.getByPK(pk);
        return dxUserinfo;
    }

    public void deleteDxUserinfoByPks(String[] pks) {
        for (String pk : pks) {
            DxUserinfo dxUserinfo = dxUserinfoDao.getByPK(pk);
            dxUserinfoDao.remove(dxUserinfo);
        }
    }

    @Override
    public List<Object[]> countUserByDept_Age(String s_age,String e_age, String deptName) {
        List<Object[]>  list = dxUserinfoDao.countByHqlWhere(s_age,e_age,deptName);
        return list;
    }

    @Override
    public List<Object[]> computeUserLev(String pk) {
        return dxUserinfoDao.computeUserLev(pk);
    }

}