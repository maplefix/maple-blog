package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import top.maplefix.model.Note;

import java.util.List;

/**
 * @author : Maple
 * @description: 读书笔记
 * @date : 2020/4/18 10:57
 */
public interface NoteMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Note selectNoteById(String  id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param note 实例对象
     * @return 对象列表
     */
    List<Note> selectNoteList(Note note);

    /**
     * 新增数据
     *
     * @param note 实例对象
     * @return 影响行数
     */
    int insertNote(Note note);

    /**
     * 修改数据
     *
     * @param note 实例对象
     * @return 影响行数
     */
    int updateNote(Note note);

    /**
     * 通过主键删除数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteNoteByIds(@Param("ids") String[] ids);

}
