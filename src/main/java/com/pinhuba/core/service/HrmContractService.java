package com.pinhuba.core.service;

import com.pinhuba.core.pojo.HrmContract;
import com.pinhuba.core.pojo.HrmContractType;
import com.pinhuba.core.dao.IHrmContractDao;
import com.pinhuba.core.dao.IHrmContractTypeDao;
import com.pinhuba.core.iservice.IHrmContractService;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pack.HrmContractPack;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class HrmContractService implements IHrmContractService{
	@Resource
    private IHrmContractDao hrmContractDao;
	@Resource
    private IHrmContractTypeDao hrmContractTypeDao;

    public int listHrmContractCount(HrmContract hrmContract){
        int count = hrmContractDao.findByHqlWhereCount(HrmContractPack.packHrmContractQuery(hrmContract));
        return count;
    }

    public List<HrmContract> listHrmContract(HrmContract hrmContract, Pager pager){
        List<HrmContract> list = hrmContractDao.findByHqlWherePage(HrmContractPack.packHrmContractQuery(hrmContract), pager);
        return list;
    }
    
    public int listHrmContractCountByType(String value){
    	return hrmContractDao.findByPropertyCount("contractType.primaryKey", value);
    }

    public HrmContract saveHrmContract(HrmContract hrmContract){
        HrmContract temp = (HrmContract)hrmContractDao.save(hrmContract);
        return temp;
    }

    public HrmContract getHrmContractByPk(String pk){
        HrmContract hrmContract = (HrmContract)hrmContractDao.getByPK(pk);
        return hrmContract;
    }

    public void deleteHrmContractByPks(String[] pks){
        for (String l : pks) {
            HrmContract hrmContract = hrmContractDao.getByPK(l);
            hrmContractDao.remove(hrmContract);
        }
    }

    public int listHrmContractTypeCount(HrmContractType hrmContractType){
        int count = hrmContractTypeDao.findByHqlWhereCount(HrmContractPack.packHrmContractTypeQuery(hrmContractType));
        return count;
    }

    public List<HrmContractType> listHrmContractType(HrmContractType hrmContractType, Pager pager){
        List<HrmContractType> list = hrmContractTypeDao.findByHqlWherePage(HrmContractPack.packHrmContractTypeQuery(hrmContractType), pager);
        return list;
    }
    
    public List<HrmContractType> listHrmContractType(HrmContractType hrmContractType){
    	List<HrmContractType> list = hrmContractTypeDao.findByHqlWhere(HrmContractPack.packHrmContractTypeQuery(hrmContractType));
    	return list;
    }

    public HrmContractType saveHrmContractType(HrmContractType hrmContractType){
        HrmContractType temp = (HrmContractType)hrmContractTypeDao.save(hrmContractType);
        return temp;
    }

    public HrmContractType getHrmContractTypeByPk(String pk){
        HrmContractType hrmContractType = (HrmContractType)hrmContractTypeDao.getByPK(pk);
        return hrmContractType;
    }

    public void deleteHrmContractTypeByPks(String[] pks){
        for (String l : pks) {
            HrmContractType hrmContractType = hrmContractTypeDao.getByPK(l);
            hrmContractTypeDao.remove(hrmContractType);
        }
    }

}