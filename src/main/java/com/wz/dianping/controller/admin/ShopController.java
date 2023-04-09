package com.wz.dianping.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wz.dianping.common.*;
import com.wz.dianping.model.SellerModel;
import com.wz.dianping.model.ShopModel;
import com.wz.dianping.request.PageQuery;
import com.wz.dianping.request.SellerReq;
import com.wz.dianping.request.ShopCreateReq;
import com.wz.dianping.service.SellerService;
import com.wz.dianping.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 作者：王哲
 * 时间：2023048/4/2023
 * 包名：com.wz.dianping.controller.admin
 * 备注：
 **/
@Controller("/admin/shop")
@RequestMapping("/admin/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    /*查询商户列表*/
    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(PageQuery pageQuery){

        PageHelper.startPage(pageQuery.getPage(),pageQuery.getSize());

        List<ShopModel> shopModels = shopService.selectAll();

        PageInfo<ShopModel> sellerModelPageInfo = new PageInfo<>(shopModels);

        ModelAndView modelAndView = new ModelAndView("/admin/shop/index.html");
        modelAndView.addObject("data",sellerModelPageInfo);
        modelAndView.addObject("CONTROLLER_NAME","shop");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }

    /*添加商家*/
    @RequestMapping("/createpage")
    @AdminPermission
    public ModelAndView createpage(){
        ModelAndView modelAndView = new ModelAndView("/admin/shop/create.html");
        modelAndView.addObject("CONTROLLER_NAME","shop");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }

    /*添加商家*/
    @RequestMapping("/create")
    @AdminPermission
    public String create(@Valid ShopCreateReq shopCreateReq, BindingResult bindingResult) throws BusinessException {

        if (bindingResult.hasErrors()){
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }

        ShopModel shopModel = new ShopModel();
        shopModel.setIconUrl(shopCreateReq.getIconUrl());
        shopModel.setAddress(shopCreateReq.getAddress());
        shopModel.setCategoryId(shopCreateReq.getCategoryId());
        shopModel.setEndTime(shopCreateReq.getEndTime());
        shopModel.setStartTime(shopCreateReq.getStartTime());
        shopModel.setLongitude(shopCreateReq.getLongitude());
        shopModel.setLatitude(shopCreateReq.getLatitude());
        shopModel.setName(shopCreateReq.getName());
        shopModel.setPricePerMan(shopCreateReq.getPricePerMan());
        shopModel.setSellerId(shopCreateReq.getSellerId());

        shopService.create(shopModel);

        return "redirect:/admin/shop/index";
    }


}
