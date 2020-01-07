package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 博客标签关联表
 * @date : Created in 2019/7/25 17:03
 * @version : v1.0
 */
@Data
@Table(name = "t_blog_label")
@NameStyle
public class BlogLabel implements Serializable {

    /**
     * 主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    private String blId;
    /**
     * 博客id
     */
    private String blogId;
    /**
     * 标签id
     */
    private String labelId;
    /**
     * 创建时间
     */
    private String createDate;
}
