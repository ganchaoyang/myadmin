package cn.gan.framework.constans;

/**
 * 系统通用错误码
 * @author ganchaoyang
 */
public enum ErrorCode {

    // 参数不能为空
    NULL_PARAMS(10001, "参数不能为空！"),
    // 系统异常了
    SYSTEM_ERROR(10002, "系统异常！"),
    // 文件上传异常
    UPLOAD_ERROR(10003, "上传文件异常！"),
    // 参数错误
    PARAMS_ERROR(10004, "参数错误！"),
    // 非法操作
    ILLEGAL_OPERATION(10005, "非法操作！"),
    // 认证过期了。
    AUTHENTICATION_EXPIRED(40001, "认证过期！"),
    LOGIN_FAILED(40002, "登录错误，用户名/密码错误！");

    private Integer code;

    private String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
