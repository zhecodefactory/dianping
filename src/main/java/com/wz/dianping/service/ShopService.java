package com.wz.dianping.service;

import com.wz.dianping.common.BusinessException;
import com.wz.dianping.model.ShopModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 作者：王哲
 * 时间：2023049/4/2023
 * 包名：com.wz.dianping.service
 * 备注：
 **/
public interface ShopService {
    ShopModel create(ShopModel shopModel) throws BusinessException;
    ShopModel selectById(Integer id);
    List<ShopModel> selectAll();
    Integer selectAllCount();

    List<ShopModel> recommend(BigDecimal longitude, BigDecimal latitude);

    List<ShopModel> search(BigDecimal longitude, BigDecimal latitude, String keyword,Integer orderby,Integer categoryId,String tags);

    List<Map<String, Object>> searchTags(String keyword, Integer categoryId, String tags);
}
