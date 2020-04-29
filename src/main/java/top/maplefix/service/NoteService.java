package top.maplefix.service;


import top.maplefix.model.Note;

import java.util.List;


/**
 * @author : Maple
 * @description: 读书笔记
 * @date : 2020/1/15 14:36
 */
public interface NoteService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Note selectNoteById(String id);

    /**
     * 新增数据
     *
     * @param note 实例对象
     * @return 受影响的行数
     */
    int insertNote(Note note);

    /**
     * 修改数据
     *
     * @param note 实例对象
     * @return 受影响的行数
     */
    int updateNote(Note note);

    /**
     * 查询所有数据
     *
     * @param note 带有查询条件的note实体
     * @return note list
     */
    List<Note> selectNoteList(Note note);

    /**
     * 批量删除note
     *
     * @param ids id
     * @return 受影响的行数
     */
    int deleteNoteByIds(String ids);

    /**
     * 根据查询参数获取符合条件的Tag Title
     *
     * @param query 查询
     * @return 符合条件的list
     */
    List<String> selectNoteTagList(String query);

}
