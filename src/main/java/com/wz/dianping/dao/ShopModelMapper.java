package com.wz.dianping.dao;

import com.wz.dianping.model.ShopModel;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShopModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbg.generated Sun Apr 09 11:43:33 HKT 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbg.generated Sun Apr 09 11:43:33 HKT 2023
     */
    int insert(ShopModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbg.generated Sun Apr 09 11:43:33 HKT 2023
     */
    int insertSelective(ShopModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbg.generated Sun Apr 09 11:43:33 HKT 2023
     */
    ShopModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbg.generated Sun Apr 09 11:43:33 HKT 2023
     */
    int updateByPrimaryKeySelective(ShopModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shop
     *
     * @mbg.generated Sun Apr 09 11:43:33 HKT 2023
     */
    int updateByPrimaryKey(ShopModel record);

    List<ShopModel> selectAll();

    Integer selectCount();

    List<ShopModel> recommend(@Param("longitude") BigDecimal longitude, @Param("latitude") BigDecimal latitude);

    List<ShopModel> search(@Param("longitude") BigDecimal longitude, @Param("latitude") BigDecimal latitude
            , @Param("keyword") String keyword, @Param("orderby") Integer orderby,
                           @Param("categoryId") Integer categoryId,
                           @Param("tags") String tags);

    List<Map<String, Object>> searchTags(@Param("keyword") String keyword,
                                         @Param("tags") String tags,
                                         @Param("categoryId") Integer categoryId);
}