package com.xtjnoob.response;

public class CommonReturnType {

    // 状态码，success或者fail
    private String status;

    // 若状态为success则返回json格式的内容
    // 若状态为fail则返回错误状态信息
    private Object data;

    public static CommonReturnType create(String status, Object data) {
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setData(data);
        commonReturnType.setStatus(status);
        return commonReturnType;
    }

    public static CommonReturnType create(Object data) {
        return create("success", data);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
