package com.wz.dianping.controller;

import com.wz.dianping.common.*;
import com.wz.dianping.model.UserModel;
import com.wz.dianping.request.LoginReq;
import com.wz.dianping.request.RegisterReq;
import com.wz.dianping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 作者：王哲
 * 时间：2023045/4/2023
 * 包名：com.wz.dianping.controller
 * 备注：
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    private static String CURRENT_USER_SESSION = "CURRENT_USER_SESSION";

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @RequestMapping("/getUser")
    @ResponseBody
    public ResultDao user(@RequestParam Integer id) throws BusinessException {
        UserModel user = userService.getUser(id);

        if (user==null){
            throw new BusinessException(BusinessError.NO_OBJECT_FOUND);
        }

        return ResultDao.successResultDao(user);
    }


    @RequestMapping("/register")
    @ResponseBody
    public ResultDao registerUser(@Valid @RequestBody RegisterReq registerReq, BindingResult bindingResult) throws BusinessException {

        System.out.println("registerReq = " + registerReq);

        if (bindingResult.hasErrors()){
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }

        UserModel userModel = new UserModel();

        userModel.setPassword(registerReq.getPassword());
        userModel.setGender(registerReq.getGender());
        userModel.setNickName(registerReq.getNickName());
        userModel.setTelphone(registerReq.getTelphone());

        UserModel register = userService.register(userModel);

        return ResultDao.successResultDao(userModel);
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResultDao login(@Valid @RequestBody LoginReq loginReq,BindingResult bindingResult) throws BusinessException {

        if (bindingResult.hasErrors()){
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR,CommonUtil.processErrorString(bindingResult));
        }

        UserModel userModel = userService.loginByTelphoneAndPassword(loginReq.getTelphone(), loginReq.getPassword());

        httpServletRequest.getSession().setAttribute(CURRENT_USER_SESSION,userModel);

        return ResultDao.successResultDao(userModel);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public ResultDao logout(){

        httpServletRequest.getSession().invalidate();

        return ResultDao.successResultDao(null);
    }

    /*获取当前用户信息*/
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public ResultDao getUserInfo(){
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute(CURRENT_USER_SESSION);
        return ResultDao.successResultDao(userModel);
    }

}
