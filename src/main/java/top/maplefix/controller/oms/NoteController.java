package top.maplefix.controller.oms;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Note;
import top.maplefix.service.NoteService;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description : 读书笔记管理接口
 * @date : 2020/2/24 21:42
 */
@RestController
@RequestMapping("book/note")
public class NoteController extends BaseController {
    final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PreAuthorize("@permissionService.hasPermission('book:note:list')")
    @GetMapping("/list")
    public TableDataInfo list(Note note) {
        startPage();
        List<Note> list = noteService.selectNoteList(note);
        return getDataTable(list);
    }

    @PreAuthorize("@permissionService.hasPermission('book:note:add')")
    @OLog(module = "笔记管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public BaseResult add(@RequestBody Note note) {
        return toResult(noteService.insertNote(note));
    }

    @PreAuthorize("@permissionService.hasPermission('book:note:add')")
    @OLog(module = "笔记管理", businessType = BusinessType.INSERT)
    @PostMapping("draft")
    public BaseResult draft(@RequestBody Note note) {
        return toResult(noteService.insertNote(note));
    }

    @PreAuthorize("@permissionService.hasPermission('book:note:edit')")
    @OLog(module = "笔记管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public BaseResult edit(@RequestBody Note note) {
        return toResult(noteService.updateNote(note));
    }

    @PreAuthorize("@permissionService.hasPermission('book:note:edit')")
    @OLog(module = "笔记管理", businessType = BusinessType.UPDATE)
    @PutMapping("draft")
    public BaseResult editDraft(@RequestBody Note note) {
        return toResult(noteService.updateNote(note));
    }

    @PreAuthorize("@permissionService.hasPermission('book:note:edit')")
    @OLog(module = "笔记管理", businessType = BusinessType.UPDATE)
    @PutMapping("support/{id}/{support}")
    public BaseResult editSupport(@PathVariable String id, @PathVariable Boolean support) {
        Note note = new Note();
        note.setNoteId(id);
        note.setSupport(support ? 1:0);
        return toResult(noteService.updateNote(note));
    }

    @PreAuthorize("@permissionService.hasPermission('book:note:edit')")
    @OLog(module = "笔记管理", businessType = BusinessType.UPDATE)
    @PutMapping("comment/{id}/{comment}")
    public BaseResult editComment(@PathVariable String id, @PathVariable Boolean comment) {
        Note note = new Note();
        note.setNoteId(id);
        note.setComment(comment ? 1:0);
        return toResult(noteService.updateNote(note));
    }

    @PreAuthorize("@permissionService.hasPermission('book:note:query')")
    @GetMapping("/{id}")
    public BaseResult getInfo(@PathVariable String id) {
        return BaseResult.success(noteService.selectNoteById(id));
    }

    @PreAuthorize("@permissionService.hasPermission('book:note:remove')")
    @OLog(module = "笔记管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult remove(@PathVariable String ids) {
        return toResult(noteService.deleteNoteByIds(ids));
    }


    @PreAuthorize("@permissionService.hasPermission('book:note:edit')")
    @GetMapping("tag/{query}")
    public TableDataInfo getCommonTag(@PathVariable String query) {
        return getDataTable(noteService.selectNoteTagList(query));
    }

}
