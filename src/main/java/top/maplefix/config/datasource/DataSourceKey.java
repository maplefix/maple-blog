package top.maplefix.config.datasource;

/**
 * @author : Maple
 * @description : 多数据源key
 * @date : Created in 2020/1/16 9:55
 */
public enum DataSourceKey {
    /**
     * 数据源一
     */
    MASTER("master"),

    /**
     * 数据源二
     */
    SLAVE("slave");

    private String name;

    DataSourceKey (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
