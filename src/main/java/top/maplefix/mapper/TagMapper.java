package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.BlogTagMid;
import top.maplefix.model.Tag;

import java.util.List;

/**
 * @author : Maple
 * @description : 博客标签mapper
 * @date : 2019/7/25 0:31
 * @version : v1.0
 */
public interface TagMapper extends Mapper<Tag>{

    /**
     * 根据条件查询所有的Tag
     *
     * @param tag tag
     * @return Tag List
     */
    List<Tag> selectTagList(Tag tag);

    /**
     * 根据tag的id获取Tag实体
     *
     * @param id tag的id
     * @return Tag
     */
    Tag selectTagById(String id);

    /**
     * 新增Tag
     *
     * @param tag tag实体
     * @return 受影响的行数
     */
    int insertTag(Tag tag);

    /**
     * 更新Tag
     *
     * @param tag tag实体
     * @return 受影响的行数
     */
    int updateTag(Tag tag);

    /**
     * 根据Id批量删除Tag
     *
     * @param ids      id
     * @param username 操作者账号
     * @return
     */
    int deleteTagByIds(@Param("ids") String[] ids, @Param("username") String username);

    /**
     * 删除TagMapping关联
     *
     * @param blogTagMid TagMapping关联
     * @return 受影响的行数
     */
    int deleteTagMid(BlogTagMid blogTagMid);

    /**
     * 根据Title查询Tag
     *
     * @param title title
     * @return Tag tag
     */
    Tag selectTagByTitle(@Param("title") String title);

    /**
     * 插入Tag关联
     *
     * @param blogTagMid 关联
     * @return 受影响的行数
     */
    int insertTagMid(BlogTagMid blogTagMid);

    /**
     * 根据Tag的id获取TagList
     *
     * @param id   对应类型的id
     * @return Tag list
     */
    List<Tag> selectTagListByType(@Param("id") String id);
}
