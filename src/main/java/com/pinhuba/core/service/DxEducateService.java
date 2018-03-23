package com.pinhuba.core.service;

import com.pinhuba.core.dao.IDxEducateTempDao;
import com.pinhuba.core.pojo.DxEducate;
import com.pinhuba.core.dao.IDxEducateDao;
import com.pinhuba.core.iservice.IDxEducateService;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pack.DxEducatePack;

import java.util.List;
import javax.annotation.Resource;

import com.pinhuba.core.pojo.DxEducateTemp;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DxEducateService implements IDxEducateService {

    @Resource
    private IDxEducateDao dxEducateDao;
    @Resource
    private IDxEducateTempDao dxEducateTempDao;

    public int listDxEducateCount(DxEducate dxEducate) {
        int count = dxEducateDao.findByHqlWhereCount(DxEducatePack.packDxEducateQuery(dxEducate));
        return count;
    }

    public List<DxEducate> listDxEducate(DxEducate dxEducate, Pager pager) {
        List<DxEducate> list = dxEducateDao.findByHqlWherePage(DxEducatePack.packDxEducateQuery(dxEducate), pager);
        return list;
    }

    public List<DxEducate> listDxEducate(DxEducate dxEducate) {
        List<DxEducate> list = dxEducateDao.findByHqlWhere(DxEducatePack.packDxEducateQuery(dxEducate));
        return list;
    }

    public DxEducate saveDxEducate(DxEducate dxEducate) {
        DxEducate temp = (DxEducate) dxEducateDao.save(dxEducate);
        return temp;
    }

    public DxEducate getDxEducateByPk(String pk) {
        DxEducate dxEducate = (DxEducate) dxEducateDao.getByPK(pk);
        return dxEducate;
    }

    public DxEducate getModDxEducateByPk(String pk) {
        DxEducate dxEducate;
        DxEducateTemp dxEducateTemp = (DxEducateTemp) dxEducateTempDao.getByPK(pk);
        if (dxEducateTemp != null) {
            dxEducate = new DxEducate();
            dxEducate.setName(dxEducateTemp.getName());
            dxEducate.setExperience(dxEducateTemp.getExperience());
            dxEducate.setCertificate(dxEducateTemp.getCertificate());
            dxEducate.setSpecialty(dxEducateTemp.getSpecialty());
            dxEducate.setDate(dxEducateTemp.getDate());
            dxEducate.setYear(dxEducateTemp.getYear());
            dxEducate.setPastspecialty(dxEducateTemp.getPastspecialty());
            dxEducate.setPastdate(dxEducateTemp.getPastdate());
            dxEducate.setPstyear(dxEducateTemp.getPstyear());
            dxEducate.setEmpid(dxEducateTemp.getEmpid());
        } else {
            dxEducate = (DxEducate) dxEducateDao.getByPK(pk);
        }
        return dxEducate;
    }

    public void deleteDxEducateByPks(String[] pks) {
        for (String pk : pks) {
            DxEducate dxEducate = dxEducateDao.getByPK(pk);
            dxEducateDao.remove(dxEducate);
        }
    }
}