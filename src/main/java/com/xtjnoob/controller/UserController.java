package com.xtjnoob.controller;

import com.xtjnoob.bean.UserDO;
import com.xtjnoob.bean.view.UserView;
import com.xtjnoob.error.BusinessException;
import com.xtjnoob.error.EnumBusinessException;
import com.xtjnoob.response.CommonReturnType;
import com.xtjnoob.service.UserService;
import com.xtjnoob.service.model.UserModel;
import org.springframework.beans.BeanUtils;
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
    public CommonReturnType getUser(@RequestParam("id") Integer id) throws BusinessException {

        if (StringUtils.isEmpty(id)) {
            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATED_ERROR);
        }

        UserModel user = userService.getUserById(id);
        if (user == null) {
            throw new BusinessException(EnumBusinessException.USER_NOT_EXISTS, "用户不存在");
        }

        UserView userView = convertFromModel(user);
        return CommonReturnType.create(userView);
    }

    private UserView convertFromModel(UserModel user) {
        if (user == null) {
            return null;
        }
        UserView userView = new UserView();
        BeanUtils.copyProperties(user, userView);
        return userView;
    }
}
