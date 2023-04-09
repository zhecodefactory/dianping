package com.wz.dianping.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wz.dianping.common.*;
import com.wz.dianping.model.SellerModel;
import com.wz.dianping.request.PageQuery;
import com.wz.dianping.request.SellerReq;
import com.wz.dianping.service.SellerService;
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
@Controller("/admin/seller")
@RequestMapping("/admin/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    /*查询商户列表*/
    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(PageQuery pageQuery){

        PageHelper.startPage(pageQuery.getPage(),pageQuery.getSize());

        List<SellerModel> sellerModels = sellerService.selectAll();

        PageInfo<SellerModel> sellerModelPageInfo = new PageInfo<>(sellerModels);

        ModelAndView modelAndView = new ModelAndView("/admin/seller/index.html");
        modelAndView.addObject("data",sellerModelPageInfo);
        modelAndView.addObject("CONTROLLER_NAME","seller");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }

    /*添加商家*/
    @RequestMapping("/createpage")
    @AdminPermission
    public ModelAndView createpage(){
        ModelAndView modelAndView = new ModelAndView("/admin/seller/create.html");
        modelAndView.addObject("CONTROLLER_NAME","seller");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }

    /*添加商家*/
    @RequestMapping("/create")
    @AdminPermission
    public String create(@Valid SellerReq sellerReq, BindingResult bindingResult) throws BusinessException {

        if (bindingResult.hasErrors()){
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }

        SellerModel sellerModel = new SellerModel();
        sellerModel.setName(sellerReq.getName());
        sellerService.create(sellerModel);

        return "redirect:/admin/seller/index";
    }

    /*禁用商家*/
    @RequestMapping("/down")
    @AdminPermission
    @ResponseBody
    public ResultDao down(@RequestParam Integer id) throws BusinessException {

        SellerModel sellerModel = sellerService.changeStatus(id, 1);

        return ResultDao.successResultDao(sellerModel);
    }

    /*禁用商家*/
    @RequestMapping("/up")
    @AdminPermission
    @ResponseBody
    public ResultDao up(@RequestParam Integer id) throws BusinessException {

        SellerModel sellerModel = sellerService.changeStatus(id, 0);

        return ResultDao.successResultDao(sellerModel);
    }

}
