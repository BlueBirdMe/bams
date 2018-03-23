package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_NOTEBOOK 对应daoImpl
 */
@Repository
public class OaNotebookDaoImpl extends BaseHapiDaoimpl<OaNotebook, Long> implements IOaNotebookDao {

   public OaNotebookDaoImpl(){
      super(OaNotebook.class);
   }
}