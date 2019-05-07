package com.xtjnoob.controller;

import com.xtjnoob.bean.UserDO;
import com.xtjnoob.error.BusinessException;
import com.xtjnoob.error.EnumBusinessException;
import com.xtjnoob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    public UserDO getUser(@RequestParam("id") Integer id) throws BusinessException {

        if (StringUtils.isEmpty(id)) {
            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATED_ERROR);
        }

        UserDO user = userService.getUserById(id);
        if (user == null) {
            throw new BusinessException(EnumBusinessException.USER_NOT_EXISTS, "用户不存在");
        }
        return user;
    }
}
