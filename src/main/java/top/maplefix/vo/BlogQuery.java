package top.maplefix.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Maple
 * @description 前端查询
 * @date 2020/2/5 18:05
 */
@Data
public class BlogQuery implements Serializable {
    /**
     * 分类Id
     */
    private Long categoryId;
    /**
     * 是否推荐
     */
    private Boolean support;
    /**
     * 查询开始时间
     */
    private String beginTime;
    /**
     * 查询结束时间
     */
    private String endTime;
}
