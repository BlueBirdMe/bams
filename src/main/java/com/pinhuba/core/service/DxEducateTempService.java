package com.pinhuba.core.service;

import com.pinhuba.core.pojo.DxEducateTemp;
import com.pinhuba.core.dao.IDxEducateTempDao;
import com.pinhuba.core.iservice.IDxEducateTempService;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pack.DxEducateTempPack;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DxEducateTempService implements IDxEducateTempService {

    @Resource
    private IDxEducateTempDao dxEducateTempDao;

    public int listDxEducateTempCount(DxEducateTemp dxEducateTemp) {
        int count = dxEducateTempDao.findByHqlWhereCount(DxEducateTempPack.packDxEducateTempQuery(dxEducateTemp));
        return count;
    }

    public List<DxEducateTemp> listDxEducateTemp(DxEducateTemp dxEducateTemp, Pager pager) {
        List<DxEducateTemp> list = dxEducateTempDao.findByHqlWherePage(DxEducateTempPack.packDxEducateTempQuery(dxEducateTemp), pager);
        return list;
    }

    public List<DxEducateTemp> listDxEducateTemp(DxEducateTemp dxEducateTemp) {
        List<DxEducateTemp> list = dxEducateTempDao.findByHqlWhere(DxEducateTempPack.packDxEducateTempQuery(dxEducateTemp));
        return list;
    }

    public DxEducateTemp saveDxEducateTemp(DxEducateTemp dxEducateTemp) {
        DxEducateTemp temp = (DxEducateTemp) dxEducateTempDao.save(dxEducateTemp);
        return temp;
    }

    public DxEducateTemp getDxEducateTempByPk(String pk) {
        DxEducateTemp dxEducateTemp = (DxEducateTemp) dxEducateTempDao.getByPK(pk);
        return dxEducateTemp;
    }

    public void deleteDxEducateTempByPks(String[] pks) {
        for (String pk : pks) {
            DxEducateTemp dxEducateTemp = dxEducateTempDao.getByPK(pk);
            if (dxEducateTemp != null) {
                dxEducateTempDao.remove(dxEducateTemp);
            }
        }
    }
}