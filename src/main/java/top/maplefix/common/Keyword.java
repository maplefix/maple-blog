package top.maplefix.common;

/**
 * @author : Maple
 * @description : 关键字
 * @date : 2020/1/16 9:54
 */
public enum Keyword {


    /**
     * 系统配置
     */
    SYSTEM_CONFIG("systemConfig"),
    /**
     * 主题配置
     */
    THEME_CONFIG("themeConfig");

    private final String value;

    Keyword(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
