package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.dao.ISysConfigDao;
import com.pinhuba.core.pojo.SysConfig;

@Repository
public class SysConfigDaoImpl extends BaseHapiDaoimpl<SysConfig, Long> implements ISysConfigDao {

	public SysConfigDaoImpl() {
		super(SysConfig.class);
	}
}