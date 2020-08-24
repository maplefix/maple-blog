package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import top.maplefix.model.LocalStorage;

import java.util.List;

/**
 * @author Maple
 * @description 本地存储mapper
 * @date 2020/3/20 11:00
 */
public interface LocalStorageMapper {

    /**
     * 添加本地存储信息
     *
     * @param localStorage 本地存储
     * @return 受影响的行数
     */
    int insertLocalStorage(LocalStorage localStorage);

    /**
     * 更新本地存储的信息
     *
     * @param localStorage 本地存储信息
     * @return 受影响的行数
     */
    int updateLocalStorage(LocalStorage localStorage);

    /**
     * 删除本地存储信息
     *
     * @param id       需要删除的id
     * @return
     */
    int deleteLocalStorageById(@Param("id") Long id );

    /**
     * 列举本地存储信息
     *
     * @param localStorage 查询条件
     * @return list
     */
    List<LocalStorage> selectLocalStorageList(LocalStorage localStorage);

    /**
     * 根据ID获取信息
     *
     * @return local storage
     */
    LocalStorage selectLocalStorageById(Long  id);
}
