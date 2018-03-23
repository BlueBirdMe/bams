package com.pinhuba.core.service;

import com.pinhuba.core.pojo.DxCorporation;
import com.pinhuba.core.dao.IDxCorporationDao;
import com.pinhuba.core.iservice.IDxCorporationService;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pack.DxCorporationPack;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class DxCorporationService implements IDxCorporationService{

    @Resource
    private IDxCorporationDao dxCorporationDao;

    public int listDxCorporationCount(DxCorporation dxCorporation){
        int count = dxCorporationDao.findByHqlWhereCount(DxCorporationPack.packDxCorporationQuery(dxCorporation));
        return count;
    }

    public List<DxCorporation> listDxCorporation(DxCorporation dxCorporation, Pager pager){
        List<DxCorporation> list = dxCorporationDao.findByHqlWherePage(DxCorporationPack.packDxCorporationQuery(dxCorporation), pager);
        return list;
    }

    public List<DxCorporation> listDxCorporation(DxCorporation dxCorporation){
        List<DxCorporation> list = dxCorporationDao.findByHqlWhere(DxCorporationPack.packDxCorporationQuery(dxCorporation));
        return list;
    }

    public DxCorporation saveDxCorporation(DxCorporation dxCorporation){
        DxCorporation temp = (DxCorporation)dxCorporationDao.save(dxCorporation);
        return temp;
    }

    public DxCorporation getDxCorporationByPk(String pk){
        DxCorporation dxCorporation = (DxCorporation)dxCorporationDao.getByPK(pk);
        return dxCorporation;
    }

    public void deleteDxCorporationByPks(String[] pks){
        for (String pk : pks) {
            DxCorporation dxCorporation = dxCorporationDao.getByPK(pk);
            dxCorporationDao.remove(dxCorporation);
        }
    }
}