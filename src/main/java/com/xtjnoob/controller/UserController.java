package com.xtjnoob.controller;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/register")
    public CommonReturnType userRegister(@RequestParam("optCode") String optCode,
                                         @RequestParam("name") String name,
                                         @RequestParam("age") Integer age,
                                         @RequestParam("gender")Integer gender,
                                         @RequestParam("telephone") String telephone,
                                         @RequestParam("password") String password) throws BusinessException {
        // 验证手机号和optCode是否一致
        String sessionCode = (String) request.getSession().getAttribute(telephone);

        if (!com.alibaba.druid.util.StringUtils.equals(sessionCode, optCode)) {
            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATED_ERROR, "短信验证码错误");
        }

    }

    @PostMapping("/getopt")
    public CommonReturnType getOpt(@RequestParam("telephone") String telephone) throws BusinessException {
        if (StringUtils.isEmpty(telephone)) {
            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATED_ERROR, "手机号码不能为空");
        }

        if (telephone.length() != 11) {
            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATED_ERROR, "非法手机号");
        }

        // 生成验证码
        Random random = new Random();
        int optCode = random.nextInt(899999) + 100000;

        // 保存验证码，关联用户手机号
        request.getSession().setAttribute(telephone, telephone);
        System.out.println("telephone = " + telephone + "& optCode = " + optCode);

        // 发送验证码
        return CommonReturnType.create(null);
    }

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
