package com.wz.dianping.service;

import com.wz.dianping.common.BusinessException;
import com.wz.dianping.model.SellerModel;

import java.util.List;

/**
 * 作者：王哲
 * 时间：2023048/4/2023
 * 包名：com.wz.dianping.service
 * 备注：
 **/
public interface SellerService {

    SellerModel create(SellerModel sellerModel);

    SellerModel select(Integer id);

    List<SellerModel> selectAll();

    SellerModel changeStatus(Integer id,Integer disableFlag) throws BusinessException;

    Integer countAllSeller();

}
