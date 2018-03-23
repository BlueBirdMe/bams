package com.pinhuba.core.iservice;

import com.pinhuba.core.pojo.HrmContract;
import com.pinhuba.core.pojo.HrmContractType;
import java.util.List;
import com.pinhuba.common.pages.Pager;

public interface IHrmContractService{

    public int listHrmContractCount(HrmContract hrmContract);
    public List<HrmContract> listHrmContract(HrmContract hrmContract, Pager pager);
    public HrmContract saveHrmContract(HrmContract hrmContract);
    public HrmContract getHrmContractByPk(String pk);
    public void deleteHrmContractByPks(String[] pks);
    public int listHrmContractCountByType(String pk);

    public int listHrmContractTypeCount(HrmContractType hrmContractType);
    public List<HrmContractType> listHrmContractType(HrmContractType hrmContractType, Pager pager);
    public HrmContractType saveHrmContractType(HrmContractType hrmContractType);
    public HrmContractType getHrmContractTypeByPk(String pk);
    public void deleteHrmContractTypeByPks(String[] pks);
	public List<HrmContractType> listHrmContractType(HrmContractType hrmContractType);
	
	

}