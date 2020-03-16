package top.maplefix.enums;

/**
 * @author Maple
 * @description 用户状态
 * @date 2020/1/18 15:05
 */
public enum  UserStatus {
    /**
     * 用户状态，正常
     */
    OK(1,"正常"),
    /**
     * 用户状态，停用
     */
    DISABLE(0,"停用");

    private Integer code;
    private String info;

    UserStatus(Integer code,String info){
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
