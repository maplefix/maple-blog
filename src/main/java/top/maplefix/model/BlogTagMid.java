package top.maplefix.model;

import lombok.Builder;
import lombok.Data;
import top.maplefix.annotation.Excel;

import java.io.Serializable;

/**
 * @author : Maple
 * @description : 博客标签关联表
 * @date : 2019/7/25 17:03
 */
@Data
@Builder
public class BlogTagMid implements Serializable {

    /**
     * 主键
     */
    @Excel(name = "主键")
    private Long id;
    /**
     * 博客id
     */
    private Long blogId;
    /**
     * 标签id
     */
    private Long tagId;
}
