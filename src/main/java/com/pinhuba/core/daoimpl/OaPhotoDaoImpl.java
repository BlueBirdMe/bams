package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：OA_PHOTO 对应daoImpl
 */
@Repository
public class OaPhotoDaoImpl extends BaseHapiDaoimpl<OaPhoto, Long> implements IOaPhotoDao {

   public OaPhotoDaoImpl(){
      super(OaPhoto.class);
   }
}