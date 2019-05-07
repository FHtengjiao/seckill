package com.xtjnoob.service.impl;

import com.xtjnoob.bean.UserDO;
import com.xtjnoob.dao.UserDOMapper;
import com.xtjnoob.service.UserService;
import com.xtjnoob.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO user = userDOMapper.selectByPrimaryKey(id);
        return user;
    }
}
