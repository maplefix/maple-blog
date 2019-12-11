package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.vo.FileItem;
import top.maplefix.vo.FileItemVo;

import java.util.List;

/**
 * @author : Maple
 * @description :
 * @date : Created in 2019/8/22 22:35
 * @editor:
 * @version: v2.1
 */
@CacheNamespace
public interface FileItemMapper extends Mapper<FileItem> {

    /**
     * 查询列表
     * @param FileItem
     * @return
     */
    List<FileItem> getFileItemList(FileItemVo FileItem);

    /**
     * 根据serverType删除数据库中的数据
     *
     * @param serverType 图片服务器类型
     * @return 受影响的行数
     */
    int deleteByServerType(String serverType);

    /**
     * 插入文件
     * @param FileItem
     * @return
     */
    int insertFileItem(FileItem FileItem);

    /**
     * 根据文件名和存储类型删除文件
     * @param name
     * @param serverType
     * @return
     */
    int deleteByNameAndServerType(@Param("name") String name, @Param("serverType") String serverType);
}
