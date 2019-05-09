package com.xtjnoob.controller;

import com.xtjnoob.bean.view.UserView;
import com.xtjnoob.error.BusinessException;
import com.xtjnoob.error.EnumBusinessException;
import com.xtjnoob.response.CommonReturnType;
import com.xtjnoob.service.UserService;
import com.xtjnoob.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@RestController("userController")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = {"*"})
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 用户登录API
     * */
    @PostMapping("/login")
    public CommonReturnType login(@RequestParam("telephone") String telephone,
                                  @RequestParam("password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 判空处理
        if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATED_ERROR, "非法参数");
        }
        UserModel userModel = userService.validateLogin(telephone, EncodeByMD5(password));

        request.getSession().setAttribute("IS_LOGIN", true);
        request.getSession().setAttribute("LOGIN_USER", userModel);

        return CommonReturnType.create(null);
    }

    /**
     * 用户注册API
     * */
    @PostMapping("/register")
    public CommonReturnType userRegister(@RequestParam("optCode") String optCode,
                                         @RequestParam("name") String name,
                                         @RequestParam("age") Byte age,
                                         @RequestParam("gender") Byte gender,
                                         @RequestParam("telephone") String telephone,
                                         @RequestParam("password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 验证手机号和optCode是否一致
        int sessionCode = (int) request.getSession().getAttribute(telephone);

        if (!com.alibaba.druid.util.StringUtils.equals(String.valueOf(sessionCode), optCode)) {
            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATED_ERROR, "短信验证码错误");
        }

        // 用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(gender);
        userModel.setTelephone(telephone);
        userModel.setRegisterMode("by telephone");
        userModel.setEncrptPassword(EncodeByMD5(password));

        userService.register(userModel);

        return CommonReturnType.create(null);
    }

    /**
     * 密码加密
     * */
    public String EncodeByMD5(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        // 加密字符串
        String encode = base64Encoder.encode(md5.digest(string.getBytes("utf8")));

        return encode;
    }

    /**
     * 用户获取opt短信API
     * */
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
        request.getSession().setAttribute(telephone, optCode);
        System.out.println("telephone = " + telephone + "& optCode = " + optCode);

        // 发送验证码
        return CommonReturnType.create(null);
    }

    /**
     * 获取用户API
     * */
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
