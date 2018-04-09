package com.pinhuba.core.dao;

import com.pinhuba.core.pojo.*;

import java.util.List;
import java.util.Map;

/**
 * 表：dx_userinfo 对应dao
 */
public interface IDxUserinfoDao extends BaseDao<DxUserinfo,String>{
    public List<Object[]> countByHqlWhere(String s_age,String e_age,String deptName);
    public List<Object[]> computeUserLev(String pk);
}