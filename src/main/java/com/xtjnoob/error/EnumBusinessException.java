package com.xtjnoob.error;

public enum EnumBusinessException implements CommonError {

    // 用户不存在
    USER_NOT_EXISTS(20001, "用户不存在"),

    // 参数错误
    PARAMETER_VALIDATED_ERROR(10001, "非法参数"),

    // 未知错误
    UNKNOWN_ERROR(30001, "未知错误");

    private int errCode;
    private String errMsg;

    EnumBusinessException(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public CommonError setErrMsg(String msg) {
        this.errMsg = msg;
        return this;
    }
}
