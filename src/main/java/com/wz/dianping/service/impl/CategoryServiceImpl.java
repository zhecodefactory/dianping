package com.wz.dianping.service.impl;

import com.wz.dianping.common.BusinessError;
import com.wz.dianping.common.BusinessException;
import com.wz.dianping.dao.CategoryModelMapper;
import com.wz.dianping.model.CategoryModel;
import com.wz.dianping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 作者：王哲
 * 时间：2023049/4/2023
 * 包名：com.wz.dianping.service.impl
 * 备注：
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryModelMapper categoryModelMapper;

    @Override
    @Transactional
    public CategoryModel create(CategoryModel categoryModel) throws BusinessException {
        categoryModel.setCreatedAt(new Date());
        categoryModel.setUpdatedAt(new Date());
        try {
            categoryModelMapper.insertSelective(categoryModel);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessError.CREATE_CATEGORY_KEY_EXCEPTION);
        }
        return selectById(categoryModel.getId());
    }

    @Override
    public CategoryModel selectById(Integer id) {
        return categoryModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CategoryModel> selectAll() {

        List<CategoryModel> categoryModelList = categoryModelMapper.selectAll();

        return categoryModelList;
    }

    @Override
    public Integer selectAllCount() {
        return categoryModelMapper.selectCount();
    }
}
