package com.pinhuba.core.service;

import com.pinhuba.core.dao.IDxArchivementTempDao;
import com.pinhuba.core.pojo.DxArchivement;
import com.pinhuba.core.dao.IDxArchivementDao;
import com.pinhuba.core.iservice.IDxArchivementService;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pack.DxArchivementPack;

import java.util.List;
import javax.annotation.Resource;

import com.pinhuba.core.pojo.DxArchivementTemp;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DxArchivementService implements IDxArchivementService {

    @Resource
    private IDxArchivementDao dxArchivementDao;
    @Resource
    private IDxArchivementTempDao dxArchivementTempDao;

    public int listDxArchivementCount(DxArchivement dxArchivement) {
        int count = dxArchivementDao.findByHqlWhereCount(DxArchivementPack.packDxArchivementQuery(dxArchivement));
        return count;
    }

    public List<DxArchivement> listDxArchivement(DxArchivement dxArchivement, Pager pager) {
        List<DxArchivement> list = dxArchivementDao.findByHqlWherePage(DxArchivementPack.packDxArchivementQuery(dxArchivement), pager);
        return list;
    }

    public List<DxArchivement> listDxArchivement(DxArchivement dxArchivement) {
        List<DxArchivement> list = dxArchivementDao.findByHqlWhere(DxArchivementPack.packDxArchivementQuery(dxArchivement));
        return list;
    }

    public DxArchivement saveDxArchivement(DxArchivement dxArchivement) {
        DxArchivement temp = (DxArchivement) dxArchivementDao.save(dxArchivement);
        return temp;
    }

    public DxArchivement getDxArchivementByPk(String pk) {
        DxArchivement dxArchivement = (DxArchivement) dxArchivementDao.getByPK(pk);
        return dxArchivement;
    }

    public DxArchivement getModDxArchivementByPk(String pk) {
        DxArchivement dxArchivement ;
        DxArchivementTemp dxArchivementTemp = (DxArchivementTemp) dxArchivementTempDao.getByPK(pk);
        if (dxArchivementTemp != null) {
            dxArchivement = new DxArchivement();
            dxArchivement.setEmpid(dxArchivementTemp.getEmpid());
            dxArchivement.setAbility(dxArchivementTemp.getAbility());
            dxArchivement.setArchieve(dxArchivementTemp.getArchieve());
            dxArchivement.setCompet(dxArchivementTemp.getCompet());
            dxArchivement.setName(dxArchivementTemp.getName());
            dxArchivement.setPatent(dxArchivementTemp.getPatent());
            dxArchivement.setPerformance(dxArchivementTemp.getPerformance());
            dxArchivement.setResult(dxArchivementTemp.getResult());
            dxArchivement.setType(dxArchivementTemp.getType());
        } else {
            dxArchivement = (DxArchivement) dxArchivementDao.getByPK(pk);
        }
        return dxArchivement;
    }

    public void deleteDxArchivementByPks(String[] pks) {
        for (String pk : pks) {
            DxArchivement dxArchivement = dxArchivementDao.getByPK(pk);
            dxArchivementDao.remove(dxArchivement);
        }
    }


}