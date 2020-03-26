package top.maplefix.enums;

/**
 * @author : Maple
 * @description : 业务类型
 * @date : 2020/1/16 9:34
 */
public enum BusinessType {
    /**
     * 其它
     */
    OTHER("OTHER","其他"),

    /**
     * 生成
     */
    GENERATE("GENERATE","生成"),
    /**
     * 新增
     */
    INSERT("INSERT","新增"),

    /**
     * 修改
     */
    UPDATE("UPDATE","修改"),

    /**
     * 删除
     */
    DELETE("DELETE","删除"),

    /**
     * 列表查询
     */
    LIST("LIST","列表查询"),

    /**
     * 导出
     */
    EXPORT("EXPORT","导出"),

    /**
     * 导入
     */
    IMPORT("IMPORT","导入"),

    /**
     * 强退
     */
    FORCE("FORCE","强退"),

    /**
     * 上传
     */
    UPLOAD("UPLOAD","上传"),
    /**
     * 下载
     */
    DOWNLOAD("UPLOAD","下载"),
    /**
     * 登出
     */
    LOGOUT("LOGOUT","登出"),
    /**
     * 登录
     */
    LOGIN("LOGIN","登录");

    private String key;
    private String value;

    BusinessType(String  key, String value) {
        this.key = key;
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }

    public String getKey() {
        return key;
    }
    public static String getKey(String value) {
        BusinessType[] businessTypes = values();
        for (BusinessType businessType : businessTypes) {
            if (businessType.getValue().equals(value)) {
                return businessType.getKey();
            }
        }
        return null;
    }

    public static String getValue(String key) {
        BusinessType[] businessTypes = values();
        for (BusinessType businessType : businessTypes) {
            if (businessType.getKey().equals(key)) {
                return businessType.getValue();
            }
        }
        return null;
    }
}
