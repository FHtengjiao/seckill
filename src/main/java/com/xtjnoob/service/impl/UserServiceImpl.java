package com.xtjnoob.service.impl;

import com.xtjnoob.bean.UserDO;
import com.xtjnoob.bean.UserPasswordDO;
import com.xtjnoob.dao.UserDOMapper;
import com.xtjnoob.dao.UserPasswordDOMapper;
import com.xtjnoob.error.BusinessException;
import com.xtjnoob.error.EnumBusinessException;
import com.xtjnoob.service.UserService;
import com.xtjnoob.service.model.UserModel;
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
        if (StringUtils.isEmpty(userModel.getName())
                || userModel.getAge() == null
                || userModel.getGender() == null
                || StringUtils.isEmpty(userModel.getTelephone())
                || StringUtils.isEmpty(userModel.getEncrtyPassword())) {
            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATED_ERROR);
        }

        UserDO userDO = convertDOFromModel(userModel);
        userDOMapper.insertSelective(userDO);
        UserPasswordDO userPasswordDO = convertPasswordDOFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
        return;
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
        userModel.setEncrtyPassword(userPasswordDO.getEncrptPassword());

        return userModel;
    }
}
