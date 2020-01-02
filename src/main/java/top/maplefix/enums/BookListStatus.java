package top.maplefix.enums;

/**
 * @author : Maple
 * @description: 书单阅读状态
 * @date : Created on 2019/12/2 16:16
 * @version v2.1
 */
public enum  BookListStatus {
    /**
     * 未开始阅读
     */
    NOT_READ("未开始"),
    /**
     * 阅读中
     */
    READING("阅读中"),
    /**
     * 已阅读完
     */
    END_READ("已结束");

    private String value;

    BookListStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
