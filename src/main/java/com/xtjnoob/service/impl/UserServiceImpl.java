package com.xtjnoob.service.impl;

import com.xtjnoob.bean.UserDO;
import com.xtjnoob.bean.UserPasswordDO;
import com.xtjnoob.dao.UserDOMapper;
import com.xtjnoob.dao.UserPasswordDOMapper;
import com.xtjnoob.error.BusinessException;
import com.xtjnoob.error.EnumBusinessException;
import com.xtjnoob.service.UserService;
import com.xtjnoob.service.model.UserModel;
import com.xtjnoob.validator.UserModelValidator;
import com.xtjnoob.validator.ValidatorResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private UserModelValidator validator;

    @Override
    public UserModel getUserById(Integer id) {
        // 查询数据库
        UserDO user = userDOMapper.selectByPrimaryKey(id);
        UserPasswordDO userPassword = userPasswordDOMapper.selectByUserId(id);

        // 创建userModel对象
        UserModel userModel = convertFromDO(user, userPassword);
        return userModel;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATED_ERROR);
        }
        // 入参校验
//        if (StringUtils.isEmpty(userModel.getName())
//                || userModel.getAge() == null
//                || userModel.getGender() == null
//                || StringUtils.isEmpty(userModel.getTelephone())
//                || StringUtils.isEmpty(userModel.getEncrptPassword())) {
//            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATED_ERROR);
//        }
        ValidatorResult result = validator.validate(userModel);
        if (result.isHasErrs()) {
            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATED_ERROR, result.getErrMsg());
        }

        UserDO userDO = convertDOFromModel(userModel);
        userDOMapper.insertSelective(userDO);
        UserPasswordDO userPasswordDO = convertPasswordDOFromModel(userModel);
        userPasswordDO.setUserId(userDO.getId());
        userPasswordDOMapper.insertSelective(userPasswordDO);
        return;
    }

    @Override
    public UserModel validateLogin(String telephone, String encrptPassword) throws BusinessException {
        // 入参校验
        if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(encrptPassword)) {
            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATED_ERROR, "参数不合法");
        }

        // 根据手机号查询出userDO
        UserDO userDO = userDOMapper.selectByTelephone(telephone);
        if (userDO == null) {
            throw new BusinessException(EnumBusinessException.USER_LOGIN_FAIL, "用户手机号或者密码不正确");
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        if (userPasswordDO == null) {
            throw new BusinessException(EnumBusinessException.USER_LOGIN_FAIL, "用户手机号或者密码不正确");
        }

        // 验证密码是否正确
        if (!StringUtils.equals(encrptPassword, userPasswordDO.getEncrptPassword())) {
            throw new BusinessException(EnumBusinessException.USER_LOGIN_FAIL, "用户手机号或者密码不正确");
        }

        UserModel userModel = convertFromDO(userDO, userPasswordDO);

        return userModel;
    }

    // userModel转化成UserDo
    private UserDO convertDOFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    // userModel转化成UserPasswordDO
    private UserPasswordDO convertPasswordDOFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        BeanUtils.copyProperties(userModel, userPasswordDO);
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    /**
     * 将UserDO对象和UserPasswordDO对象转化成UserModel对象
     * */
    private UserModel convertFromDO(UserDO userDO, UserPasswordDO userPasswordDO) {
        if (userDO == null) {
            return null;
        }

        if (userPasswordDOMapper == null) {
            return null;
        }

        // 新建返回的userModel
        UserModel userModel = new UserModel();

        BeanUtils.copyProperties(userDO, userModel);
        userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());

        return userModel;
    }
}
