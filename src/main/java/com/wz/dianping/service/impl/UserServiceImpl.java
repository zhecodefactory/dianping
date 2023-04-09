package com.wz.dianping.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.wz.dianping.common.BusinessError;
import com.wz.dianping.common.BusinessException;
import com.wz.dianping.dao.UserModelMapper;
import com.wz.dianping.model.UserModel;
import com.wz.dianping.service.UserService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 作者：王哲
 * 时间：2023045/4/2023
 * 包名：com.wz.dianping.service.impl
 * 备注：
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserModelMapper userModelMapper;

    @Override
    public UserModel getUser(Integer id) {
        return userModelMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public UserModel register(UserModel registerUserModel) throws BusinessException {

        registerUserModel.setPassword(encodingMd5(registerUserModel.getPassword()));
        registerUserModel.setCreatedAt(new Date());
        registerUserModel.setUpdatedAt(new Date());


        try {
            userModelMapper.insertSelective(registerUserModel);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessError.DUP_KEY_EXCEPTION);
        }

        return userModelMapper.selectByPrimaryKey(registerUserModel.getId());
    }

    @Override
    public UserModel loginByTelphoneAndPassword(String telphone, String password) throws BusinessException {

        password = encodingMd5(password);

        UserModel loginUser = userModelMapper.selectByTelphoneAndPassword(telphone,password);

        if (loginUser==null){
            throw new BusinessException(BusinessError.LOGIN_FAIL);
        }

        return loginUser;
    }

    @Override
    public Integer getAllUser() {
        return userModelMapper.countAllUser();
    }


    private String encodingMd5(String password){
        return SecureUtil.md5().digestHex(password);
    }

}
