package com.wz.dianping.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wz.dianping.common.AdminPermission;
import com.wz.dianping.common.BusinessError;
import com.wz.dianping.common.BusinessException;
import com.wz.dianping.common.CommonUtil;
import com.wz.dianping.model.CategoryModel;
import com.wz.dianping.model.SellerModel;
import com.wz.dianping.request.CategoryReq;
import com.wz.dianping.request.PageQuery;
import com.wz.dianping.request.SellerReq;
import com.wz.dianping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * 作者：王哲
 * 时间：2023049/4/2023
 * 包名：com.wz.dianping.controller.admin
 * 备注：
 **/
@Controller("/admin/category")
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /*查询品类列表*/
    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(PageQuery pageQuery){

        PageHelper.startPage(pageQuery.getPage(),pageQuery.getSize());

        List<CategoryModel> categoryModel = categoryService.selectAll();

        PageInfo<CategoryModel> sellerModelPageInfo = new PageInfo<>(categoryModel);

        ModelAndView modelAndView = new ModelAndView("/admin/category/index.html");
        modelAndView.addObject("data",sellerModelPageInfo);
        modelAndView.addObject("CONTROLLER_NAME","category");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }

    /*添加类别*/
    @RequestMapping("/createpage")
    @AdminPermission
    public ModelAndView createpage(){
        ModelAndView modelAndView = new ModelAndView("/admin/category/create.html");
        modelAndView.addObject("CONTROLLER_NAME","seller");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }

    /*添加商家*/
    @RequestMapping("/create")
    @AdminPermission
    public String create(@Valid CategoryReq categoryReq, BindingResult bindingResult) throws BusinessException {

        if (bindingResult.hasErrors()){
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName(categoryReq.getName());
        categoryModel.setIconUrl(categoryReq.getIconUrl());
        categoryModel.setSort(categoryReq.getSort());
        categoryService.create(categoryModel);

        return "redirect:/admin/category/index";
    }

}
