package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_FORMS 对应daoImpl
 */
@Repository
public class OaFormsDaoImpl extends BaseHapiDaoimpl<OaForms, Long> implements IOaFormsDao {

   public OaFormsDaoImpl(){
      super(OaForms.class);
   }
}