package com.imooc.mall.exception;

public enum ImoocMallExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),
    PASSWORD_TOO_SHORT(10003, "密码长度不能小于8位"),
    NAME_EXISTED(10004,"不允许重名"),
    INSERT_FAILED(10005,"插入失敗"),
    WRONG_PASSWORD(10006,"密码错误"),
    NEED_LOGIN(10007,"需要登录"),
    UPDATE_FAILED(10008, "更新失败"),
    NEED_ADMIN(10009, "无管理员权限"),
    NAME_NOT_NULL(10010, "名字不能为空"),
    CREATE_FAILED(10011, "新增失败"),
    REQUEST_PARAMS_ERROR(10012, "參數錯誤"),
    DELETE_FAILED(10013, "删除失败"),
    MKDIR_FAILED(10014, "文件夹创建失败"),
    NOT_SELL(10015, "商品装填不可售"),
    NOT_ENOUGH(10016, "商品库存不足"),
    CART_EMPTY(10017, "购物车勾选的商品为空"),
    NO_ENUM(10019, "未找到对应的枚举类"),
    NO_ORDER(10020, "订单不存在"),
    NO_YOUR_ORDER(10022, "订单不属于你"),
    WRONG_WRONG_STATUS(10023, "订单状态不符"),
    WRONG_EMAIL(10024, "非法邮件地址"),
    EMAIL_ALREADY_BEEN_REGISTER(10025, "邮件已注册"),
    NEED_CODE(10026, "验证码不能为空"),
    NEED_EMAIL(10026, "邮箱不能为空"),
    TOKEN_EXPIRE(10027, "token失效"),
    TOKEN_WRONG(10026, "解码失败"),
    EMAIL_ALREADY_BEEN_SEND(10026, "email已发送，若无法收到，请稍后再试"),
    WRONG_CODE(10027, "验证码错误"),
    SYSTEM_ERROR(20000,"系統異常");

    Integer code;
    String msg;

    ImoocMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
