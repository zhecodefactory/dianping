package com.wz.dianping.service;

import com.wz.dianping.common.BusinessException;
import com.wz.dianping.model.CategoryModel;

import java.util.List;

/**
 * 作者：王哲
 * 时间：2023049/4/2023
 * 包名：com.wz.dianping.service
 * 备注：
 **/
public interface CategoryService {

    CategoryModel create(CategoryModel categoryModel) throws BusinessException;

    CategoryModel selectById(Integer id);

    List<CategoryModel> selectAll();

    Integer selectAllCount();

}
