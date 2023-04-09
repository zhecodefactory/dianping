package com.wz.dianping.service.impl;

import com.wz.dianping.common.BusinessError;
import com.wz.dianping.common.BusinessException;
import com.wz.dianping.dao.SellerModelMapper;
import com.wz.dianping.model.SellerModel;
import com.wz.dianping.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 作者：王哲
 * 时间：2023048/4/2023
 * 包名：com.wz.dianping.service.impl
 * 备注：
 **/
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerModelMapper sellerModelMapper;

    @Override
    @Transactional
    public SellerModel create(SellerModel sellerModel) {
        sellerModel.setCreatedAt(new Date());
        sellerModel.setUpdatedAt(new Date());
        sellerModel.setDisabledFlag(0);
        sellerModel.setRemarkScore(new BigDecimal(0));
        sellerModelMapper.insertSelective(sellerModel);
        return select(sellerModel.getId());
    }

    @Override
    public SellerModel select(Integer id) {
        return sellerModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SellerModel> selectAll() {

        return sellerModelMapper.selectAll();
    }

    @Override
    @Transactional
    public SellerModel changeStatus(Integer id, Integer disableFlag) throws BusinessException {
        SellerModel sellerModel = sellerModelMapper.selectByPrimaryKey(id);
        if (sellerModel==null){
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR,"商家不存在");
        }
        sellerModel.setDisabledFlag(disableFlag);
        sellerModelMapper.updateByPrimaryKeySelective(sellerModel);

        return sellerModel;
    }

    @Override
    public Integer countAllSeller() {
        return sellerModelMapper.selectCount();
    }
}
