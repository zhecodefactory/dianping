package com.wz.dianping.controller;

import com.wz.dianping.common.BusinessError;
import com.wz.dianping.common.BusinessException;
import com.wz.dianping.common.ResultDao;
import com.wz.dianping.model.CategoryModel;
import com.wz.dianping.model.ShopModel;
import com.wz.dianping.service.CategoryService;
import com.wz.dianping.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：王哲
 * 时间：2023049/4/2023
 * 包名：com.wz.dianping.controller
 * 备注：
 **/
@Controller("/shop")
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    ShopService shopService;

    @Autowired
    CategoryService categoryService;

    /*推荐服务 V1.0*/
    @RequestMapping("/recommend")
    @ResponseBody
    public ResultDao recommend(@RequestParam BigDecimal longitude, @RequestParam BigDecimal latitude) throws BusinessException {
        if (longitude == null || latitude == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "未获取到经纬度");
        }
        List<ShopModel> shopModelList = shopService.recommend(longitude, latitude);

        return ResultDao.successResultDao(shopModelList);
    }

    /*搜索服务 V1.0*/
    @RequestMapping("/search")
    @ResponseBody
    public ResultDao search(@RequestParam BigDecimal longitude, @RequestParam BigDecimal latitude,
                            @RequestParam String keyword,
                            @RequestParam(required = false) Integer categoryId,
                            @RequestParam(required = false) Integer orderby,
                            @RequestParam(required = false) String tags
                            ) throws BusinessException, IOException {
        if (StringUtils.isEmpty(keyword) || longitude == null || latitude == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "未获取到经纬度");
        }

        List<ShopModel> shopModelList = (List<ShopModel>) shopService.searchEs(longitude, latitude, keyword, orderby, categoryId, tags).get("ShopModelList");


//        List<ShopModel> shopModelList = shopService.search(longitude, latitude, keyword,orderby,categoryId,tags);
        List<CategoryModel> categoryModelList = categoryService.selectAll();
        List<Map<String,Object>> searchTags = shopService.searchTags(keyword,categoryId,tags);

        HashMap<String, Object> map = new HashMap<>();
        map.put("shop", shopModelList);
        map.put("category", categoryModelList);
        map.put("tags", searchTags);

        return ResultDao.successResultDao(map);
    }

}
