package com.xtjnoob.service;

import com.xtjnoob.bean.UserDO;
import com.xtjnoob.error.BusinessException;
import com.xtjnoob.service.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusinessException;
}
