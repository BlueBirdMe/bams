package com.pinhuba.core.service;

import com.pinhuba.core.pojo.DxPreSpec;
import com.pinhuba.core.dao.IDxPreSpecDao;
import com.pinhuba.core.iservice.IDxPreSpecService;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pack.DxPreSpecPack;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class DxPreSpecService implements IDxPreSpecService{

    @Resource
    private IDxPreSpecDao dxPreSpecDao;

    public int listDxPreSpecCount(DxPreSpec dxPreSpec){
        int count = dxPreSpecDao.findByHqlWhereCount(DxPreSpecPack.packDxPreSpecQuery(dxPreSpec));
        return count;
    }

    public List<DxPreSpec> listDxPreSpec(DxPreSpec dxPreSpec, Pager pager){
        List<DxPreSpec> list = dxPreSpecDao.findByHqlWherePage(DxPreSpecPack.packDxPreSpecQuery(dxPreSpec), pager);
        return list;
    }

    public List<DxPreSpec> listDxPreSpec(DxPreSpec dxPreSpec){
        List<DxPreSpec> list = dxPreSpecDao.findByHqlWhere(DxPreSpecPack.packDxPreSpecQuery(dxPreSpec));
        return list;
    }

    public DxPreSpec saveDxPreSpec(DxPreSpec dxPreSpec){
        DxPreSpec temp = (DxPreSpec)dxPreSpecDao.save(dxPreSpec);
        return temp;
    }

    public DxPreSpec getDxPreSpecByPk(String pk){
        DxPreSpec dxPreSpec = (DxPreSpec)dxPreSpecDao.getByPK(pk);
        return dxPreSpec;
    }

    public void deleteDxPreSpecByPks(String[] pks){
        for (String pk : pks) {
            DxPreSpec dxPreSpec = dxPreSpecDao.getByPK(pk);
            dxPreSpecDao.remove(dxPreSpec);
        }
    }
}