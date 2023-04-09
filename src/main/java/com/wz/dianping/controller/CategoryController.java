package com.wz.dianping.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wz.dianping.common.*;
import com.wz.dianping.model.CategoryModel;
import com.wz.dianping.request.CategoryReq;
import com.wz.dianping.request.PageQuery;
import com.wz.dianping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * 作者：王哲
 * 时间：2023049/4/2023
 * 包名：com.wz.dianping.controller.admin
 * 备注：
 **/
@Controller("/category")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /*查询品类列表*/
    @RequestMapping("/list")
    @ResponseBody
    public ResultDao list(PageQuery pageQuery){

        PageHelper.startPage(pageQuery.getPage(),pageQuery.getSize());

        List<CategoryModel> categoryModel = categoryService.selectAll();

        PageInfo<CategoryModel> sellerModelPageInfo = new PageInfo<>(categoryModel);

        return ResultDao.successResultDao(categoryModel);
    }

}
