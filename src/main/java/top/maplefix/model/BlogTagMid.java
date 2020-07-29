package top.maplefix.model;

import lombok.Builder;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
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
    @Id
    @KeySql(genId = UuIdGenId.class)
    private String btId;
    /**
     * 博客id
     */
    private String blogId;
    /**
     * 标签id
     */
    private String tagId;
}
