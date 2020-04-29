package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Link;

import java.util.List;

/**
 * @author : Maple
 * @description : 友链mapper
 * @date : 2020/1/25 0:32
 */
public interface LinkMapper extends Mapper<Link>{

    /**
     * 查询友链
     *
     * @param id 友链ID
     * @return 友链
     */
    Link selectLinkById(String id);

    /**
     * 查询友链列表
     *
     * @param link 友链
     * @return 友链集合
     */
    List<Link> selectLinkList(Link link);

    /**
     * 新增友链
     *
     * @param link 友链
     * @return 结果
     */
    int insertLink(Link link);

    /**
     * 修改友链
     *
     * @param link 友链
     * @return 结果
     */
    int updateLink(Link link);

    /**
     * 删除友链
     *
     * @param id 友链ID
     * @return 结果
     */
    int deleteLinkById(@Param("id") String id);

    /**
     * 批量删除友链
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteLinkByIds(@Param("ids") String[] ids);
}
