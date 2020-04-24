package top.maplefix.enums;

/**
 * @author Maple
 * @description 目录类型
 * @date 2020/4/17 11:36
 */
public enum  CategoryType {
    /**
     * type of blog
     */
    BLOG(1),
    /**
     * type of note
     */
    NOTE(2),
    /**
     * type of book
     */
    BOOK(3);
    private Integer type;

    CategoryType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
