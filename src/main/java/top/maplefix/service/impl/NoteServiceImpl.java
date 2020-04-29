package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.maplefix.enums.TagType;
import top.maplefix.mapper.NoteMapper;
import top.maplefix.model.Note;
import top.maplefix.model.Tag;
import top.maplefix.service.NoteService;
import top.maplefix.service.TagService;
import top.maplefix.utils.ConvertUtils;
import top.maplefix.utils.DateUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Maple
 * @description 图书笔记service实现
 * @date 2020/3/20 11:11
 */
@Service
public class NoteServiceImpl implements NoteService {
    
    @Autowired
    NoteMapper noteMapper;
    @Autowired
    TagService tagService;

    @Override
    public Note selectNoteById(String id) {
        Note note = noteMapper.selectNoteById(id);
        note.setTagTitleList(getTagTitleListByNoteId((id)));
        return note;
    }

    private List<String> getTagTitleListByNoteId(String noteId) {
        List<Tag> tagList = tagService.selectTagListByTypeAndId(TagType.NOTE.getType(), noteId);
        return tagList.stream().map(Tag::getTagName).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertNote(Note note) {
        note.setCreateDate(DateUtils.getTime());
        int count = noteMapper.insertNote(note);
        tagService.updateTagMid(TagType.NOTE.getType(), note.getNoteId(), note.getTagTitleList());
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateNote(Note note) {
        note.setUpdateDate(DateUtils.getTime());
        int count = noteMapper.updateNote(note);
        tagService.updateTagMid(TagType.NOTE.getType(), note.getNoteId(), note.getTagTitleList());
        return count;
    }

    @Override
    public List<Note> selectNoteList(Note note) {
        return noteMapper.selectNoteList(note);
    }

    @Override
    public int deleteNoteByIds(String ids) {
        return noteMapper.deleteNoteByIds(ConvertUtils.toStrArray(ids));
    }

    @Override
    public List<String> selectNoteTagList(String query) {
        Tag tag = new Tag();
        tag.setTagName(query);
        tag.setType(TagType.NOTE.getType());
        List<Tag> tagList = tagService.selectTagList(tag);
        return tagList.stream().map(Tag::getTagName).collect(Collectors.toList());
    }
}
