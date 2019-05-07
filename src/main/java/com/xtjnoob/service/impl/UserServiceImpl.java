package com.xtjnoob.service.impl;

import com.xtjnoob.bean.UserDO;
import com.xtjnoob.bean.UserPasswordDO;
import com.xtjnoob.dao.UserDOMapper;
import com.xtjnoob.dao.UserPasswordDOMapper;
import com.xtjnoob.service.UserService;
import com.xtjnoob.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
        userModel.setEncrtyPassword(userPasswordDO.getEncrtyPassword());

        return userModel;
    }
}
