package top.maplefix.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.maplefix.model.Blog;

/**
 * @author : Maple
 * @description : blog扩展实体
 * @date : Created in 2019/7/25 16:59
 * @editor:
 * @version: v2.1
 */
@Data
@EqualsAndHashCode
public class BlogVO extends Blog {

    /**
     * 搜索关键字
     */
    private String search;
    /**
     * 分页信息-当前页数
     */
    private Integer currPage;
    /**
     * 分页信息-每页大小
     */
    private Integer pageSize;

}
