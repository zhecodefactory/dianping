package com.wz.dianping.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wz.dianping.common.BusinessError;
import com.wz.dianping.common.BusinessException;
import com.wz.dianping.dao.ShopModelMapper;
import com.wz.dianping.model.CategoryModel;
import com.wz.dianping.model.SellerModel;
import com.wz.dianping.model.ShopModel;
import com.wz.dianping.service.CategoryService;
import com.wz.dianping.service.SellerService;
import com.wz.dianping.service.ShopService;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 作者：王哲
 * 时间：2023049/4/2023
 * 包名：com.wz.dianping.service.impl
 * 备注：
 **/
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopModelMapper shopModelMapper;
    @Autowired
    SellerService sellerService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    @Transactional
    public ShopModel create(ShopModel shopModel) throws BusinessException {
        shopModel.setCreatedAt(new Date());
        shopModel.setUpdatedAt(new Date());
        /*校验商家是否存在*/
        SellerModel sellerModel = sellerService.select(shopModel.getSellerId());
        if (sellerModel==null){
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR,"商户不存在");
        }
        if (sellerModel.getDisabledFlag()==1){
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR,"商户已被禁用");
        }

        CategoryModel categoryModel = categoryService.selectById(shopModel.getCategoryId());
        if (categoryModel==null){
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR,"类目不存在");
        }

        shopModelMapper.insertSelective(shopModel);

        return selectById(shopModel.getId());
    }

    @Override
    public ShopModel selectById(Integer id) {
        ShopModel shopModel = shopModelMapper.selectByPrimaryKey(id);
        if (shopModel==null){
            return null;
        }

        shopModel.setCategoryModel(categoryService.selectById(shopModel.getCategoryId()));
        shopModel.setSellerModel(sellerService.select(shopModel.getSellerId()));
        return shopModel;
    }

    @Override
    public List<ShopModel> selectAll() {
        List<ShopModel> shopModels = shopModelMapper.selectAll();
        shopModels.forEach(shopModel -> {
            shopModel.setCategoryModel(categoryService.selectById(shopModel.getCategoryId()));
            shopModel.setSellerModel(sellerService.select(shopModel.getSellerId()));
        });
        return shopModels;
    }

    @Override
    public Integer selectAllCount() {
        return shopModelMapper.selectCount();
    }

    @Override
    public List<ShopModel> recommend(BigDecimal longitude, BigDecimal latitude) {
        List<ShopModel> shopModels = shopModelMapper.recommend(longitude,latitude);
        shopModels.forEach(shopModel -> {
            shopModel.setCategoryModel(categoryService.selectById(shopModel.getCategoryId()));
            shopModel.setSellerModel(sellerService.select(shopModel.getSellerId()));
        });
        return shopModels;
    }

    @Override
    public List<ShopModel> search(BigDecimal longitude, BigDecimal latitude, String keyword,Integer orderby
            ,Integer categoryId,String tags) {
        List<ShopModel> shopModels = shopModelMapper.search(longitude,latitude,keyword,orderby,categoryId,tags);
        shopModels.forEach(shopModel -> {
            shopModel.setCategoryModel(categoryService.selectById(shopModel.getCategoryId()));
            shopModel.setSellerModel(sellerService.select(shopModel.getSellerId()));
        });
        return shopModels;
    }

    @Override
    public List<Map<String, Object>> searchTags(String keyword, Integer categoryId, String tags) {
        return shopModelMapper.searchTags(keyword,tags,categoryId);
    }


    @Override
    public Map<String, Object> searchEs(BigDecimal longitude, BigDecimal latitude, String keyword, Integer orderby, Integer categoryId, String tags) throws IOException {

        Map<String, Object> map = new HashMap<>();

//        SearchRequest searchRequest = new SearchRequest("shop");
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchQuery("name",keyword));
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        searchRequest.source(searchSourceBuilder);
//
//        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//
//        ArrayList<Integer> shopIdList = new ArrayList<>();
//        SearchHit[] hits = search.getHits().getHits();
//        for (SearchHit hit : hits) {
//            shopIdList.add(Integer.valueOf(hit.getSourceAsMap().get("id").toString()));
//        }
//
//        List<ShopModel> ShopModelList = shopIdList.stream().map(shopId -> {
//            return selectById(shopId);
//        }).collect(Collectors.toList());
//
//        map.put("ShopModelList",ShopModelList);


        Request request = new Request("GET","/shop/_search");
        String reqJson = "{\"query\":{\n" +
                "    \"match\":{\"name\":\""+keyword+"\"}\n" +
                "  },\n" +
                "  \"_source\": \"*\", \n" +
                "  \"script_fields\": {\n" +
                "    \"distance\": {\n" +
                "      \"script\": {\n" +
                "        \"source\": \"haversin(lat,lon,doc['location'].lat,doc['location'].lon)\",\n" +
                "        \"lang\": \"expression\",\n" +
                "        \"params\": {\"lat\":"+latitude.toString()+",\"lon\":"+longitude.toString()+"}\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"sort\": [\n" +
                "    {\n" +
                "      \"_geo_distance\": {\n" +
                "        \"location\":{\n" +
                "          \"lat\":"+latitude.toString()+",\n" +
                "          \"lon\":"+longitude.toString()+"\n" +
                "        },\n" +
                "        \"order\": \"asc\",\n" +
                "        \"unit\": \"km\",\n" +
                "        \"distance_type\": \"arc\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]}";

        System.out.println("reqJson = " + reqJson);

        request.setJsonEntity(reqJson);

        Response response = restHighLevelClient.getLowLevelClient().performRequest(request);

        String responseStr = EntityUtils.toString(response.getEntity());

        System.out.println("responseStr = " + responseStr);

        JSONObject jsonObject = JSONObject.parseObject(responseStr);

        JSONArray jsonArray = jsonObject.getJSONObject("hits").getJSONArray("hits");

        List<ShopModel> ShopModelList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            Integer id = Integer.valueOf(jsonObject1.get("_id").toString());
            BigDecimal distance = new BigDecimal(jsonObject1.getJSONObject("fields").getJSONArray("distance")
                    .get(0).toString());
            ShopModel shopModel = selectById(id);
            shopModel.setDistance(distance.multiply(new BigDecimal(1000).setScale(0,BigDecimal.ROUND_CEILING)).intValue());
            ShopModelList.add(shopModel);
        }

        map.put("ShopModelList",ShopModelList);

        return map;
    }
}
