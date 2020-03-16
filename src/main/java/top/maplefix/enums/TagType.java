package top.maplefix.enums;

/**
 * @author Maple
 * @description 标签类型
 * @date 2020/3/13 11:07
 */
public enum TagType {
    /**
     * tag of blog
     */
    BLOG(1),
    /**
     * tag of note
     */
    NOTE(2),
    /**
     * tag of book
     */
    BOOK(3);

    private Integer type;

    TagType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
