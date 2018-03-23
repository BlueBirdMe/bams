package com.pinhuba.core.service;

import com.pinhuba.core.pojo.DxArchivementTemp;
import com.pinhuba.core.dao.IDxArchivementTempDao;
import com.pinhuba.core.iservice.IDxArchivementTempService;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pack.DxArchivementTempPack;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DxArchivementTempService implements IDxArchivementTempService {

    @Resource
    private IDxArchivementTempDao dxArchivementTempDao;

    public int listDxArchivementTempCount(DxArchivementTemp dxArchivementTemp) {
        int count = dxArchivementTempDao.findByHqlWhereCount(DxArchivementTempPack.packDxArchivementTempQuery(dxArchivementTemp));
        return count;
    }

    public List<DxArchivementTemp> listDxArchivementTemp(DxArchivementTemp dxArchivementTemp, Pager pager) {
        List<DxArchivementTemp> list = dxArchivementTempDao.findByHqlWherePage(DxArchivementTempPack.packDxArchivementTempQuery(dxArchivementTemp), pager);
        return list;
    }

    public List<DxArchivementTemp> listDxArchivementTemp(DxArchivementTemp dxArchivementTemp) {
        List<DxArchivementTemp> list = dxArchivementTempDao.findByHqlWhere(DxArchivementTempPack.packDxArchivementTempQuery(dxArchivementTemp));
        return list;
    }

    public DxArchivementTemp saveDxArchivementTemp(DxArchivementTemp dxArchivementTemp) {
        DxArchivementTemp temp = (DxArchivementTemp) dxArchivementTempDao.save(dxArchivementTemp);
        return temp;
    }

    public DxArchivementTemp getDxArchivementTempByPk(String pk) {
        DxArchivementTemp dxArchivementTemp = (DxArchivementTemp) dxArchivementTempDao.getByPK(pk);
        return dxArchivementTemp;
    }

    public void deleteDxArchivementTempByPks(String[] pks) {
        for (String pk : pks) {
            DxArchivementTemp dxArchivementTemp = dxArchivementTempDao.getByPK(pk);
            if (dxArchivementTemp != null) {
                dxArchivementTempDao.remove(dxArchivementTemp);
            }
        }
    }
}