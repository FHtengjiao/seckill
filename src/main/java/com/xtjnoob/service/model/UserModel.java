package com.xtjnoob.service.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserModel {
    private Integer id;

    @NotBlank(message = "名字不能为空")
    private String name;

    @NotNull(message = "性别不能不填")
    @Size(min = 0, max = 2, message = "性别只能是男/女/保密之一")
    private Byte gender;

    @NotNull(message = "性别不能为空")
    @Size(min = 0, max = 150, message = "年龄只能在0-150之间")
    private Byte age;

    @NotNull(message = "手机号码不能为空")
    @Length(min = 11, max = 11, message = "手机号位数不正确")
    private String telephone;

    private String registerMode;

    private String thirdPartyId;

    @NotNull(message = "密码不能为空")
    private String encrptPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword;
    }
}
