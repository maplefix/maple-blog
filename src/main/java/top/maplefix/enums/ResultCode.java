package top.maplefix.enums;

/**
 * @author : Maple
 * @description : 接口返回码
 * @date : Created in 2019/7/29 22:31
 * @version : v2.1
 */
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS_CODE(0),

    /**
     * 失败
     */
    FAIL_CODE(1),

    /**
     * 参数缺失
     */
    LACK_PARAM_CODE(2),

    /**
     * 请求超时
     */
    TIME_OUT_CODE(3),

    /**
     * 系统内部错误
     */
    SYSTEM_ERROR_CODE(4);

    private Integer code;

    ResultCode(int code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
