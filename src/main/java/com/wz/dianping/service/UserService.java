package com.wz.dianping.service;

import com.wz.dianping.common.BusinessException;
import com.wz.dianping.model.UserModel;

/**
 * 作者：王哲
 * 时间：2023045/4/2023
 * 包名：com.wz.dianping.service
 * 备注：
 **/
public interface UserService {

    UserModel getUser(Integer id);

    UserModel register(UserModel registerUserModel) throws BusinessException;

    UserModel loginByTelphoneAndPassword(String telphone,String password) throws BusinessException;

    Integer getAllUser();

}
