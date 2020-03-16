package top.maplefix.enums;

/**
 * @author : Maple
 * @description: 书单阅读状态
 * @date : Created on 2019/12/2 16:16
 * @version v1.0
 */
public enum  BookListStatus {
    /**
     * 未开始阅读
     */
    NOT_READ(0),
    /**
     * 阅读中
     */
    READING(1),
    /**
     * 已阅读完
     */
    END_READ(2);

    private int value;

    BookListStatus(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
