package com.wz.dianping.controller.admin;

import cn.hutool.crypto.SecureUtil;
import com.wz.dianping.common.AdminPermission;
import com.wz.dianping.common.BusinessError;
import com.wz.dianping.common.BusinessException;
import com.wz.dianping.service.CategoryService;
import com.wz.dianping.service.SellerService;
import com.wz.dianping.service.ShopService;
import com.wz.dianping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者：王哲
 * 时间：2023048/4/2023
 * 包名：com.wz.dianping.controller.admin
 * 备注：
 **/
@Controller("/admin/admin")
@RequestMapping("/admin/admin")
public class AdminController {

    @Value("${admin.email}")
    private String adminEamil;

    @Value("${admin.password}")
    private String adminPassword;

    public static final String CURRENT_ADMIN_SESSION = "CURRENT_ADMIN_SESSION";

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    UserService userService;
    @Autowired
    SellerService sellerService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ShopService shopService;

    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("/admin/admin/index");

        modelAndView.addObject("userCount",userService.getAllUser());
        modelAndView.addObject("sellerCount",sellerService.countAllSeller());
        modelAndView.addObject("categoryCount",categoryService.selectAllCount());
        modelAndView.addObject("shopCount",shopService.selectAllCount());


        modelAndView.addObject("CONTROLLER_NAME","admin");
        modelAndView.addObject("ACTION_NAME","index");

        return modelAndView;
    }
    @RequestMapping("/loginpage")
    public ModelAndView loginpage(){
        ModelAndView modelAndView = new ModelAndView("/admin/admin/login");
        return modelAndView;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam String email,@RequestParam String password) throws BusinessException {
        if (StringUtils.isEmpty(email)||StringUtils.isEmpty(password)){
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR,"账号密码不能为空");
        }
        if (adminEamil.equals(email)&&adminPassword.equals(encodingMd5(password))){

            httpServletRequest.getSession().setAttribute(CURRENT_ADMIN_SESSION,email);
            return "redirect:/admin/admin/index";
        }else{
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR,"账号密码错误");
        }
    }

    private String encodingMd5(String password){
        return SecureUtil.md5().digestHex(password);
    }

}
