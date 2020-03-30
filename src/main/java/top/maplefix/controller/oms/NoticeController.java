package top.maplefix.controller.oms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Notice;
import top.maplefix.service.NoticeService;
import top.maplefix.utils.DateUtils;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author Maple
 * @description 通知公告数据接口
 * @date 2020/3/20 10:46
 */
@RestController
@RequestMapping("/system/notice")
public class NoticeController extends BaseController {
    @Autowired
    private NoticeService noticeService;


    /**
     * 获取通知公告列表
     */
    @PreAuthorize("@permissionService.hasPermission('system:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(Notice notice) {
        startPage();
        List<Notice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @PreAuthorize("@permissionService.hasPermission('system:notice:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable String id) {
        return BaseResult.success(noticeService.selectNoticeById(id));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@permissionService.hasPermission('system:notice:add')")
    @OLog(module = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@RequestBody Notice notice) {
        notice.setCreateDate(DateUtils.getTime());
        return toResult(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@permissionService.hasPermission('system:notice:edit')")
    @OLog(module = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@RequestBody Notice notice) {
        notice.setUpdateDate(DateUtils.getTime());
        return toResult(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@permissionService.hasPermission('system:notice:remove')")
    @OLog(module = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public BaseResult remove(@PathVariable String ids) {
        return toResult(noticeService.deleteNoticeByIds(ids));
    }
}
