package top.maplefix.controller.oms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.service.LocalStorageService;
import top.maplefix.model.LocalStorage;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author Maple
 * @description 本地存储数据接口
 * @date 2020/3/20 10:58
 */
@RestController
@RequestMapping("/localStorage")
public class LocalStorageController extends BaseController {
    
    @Autowired
    private LocalStorageService localStorageService;

    @PreAuthorize("@permissionService.hasPermission('tool:localStorage:list')")
    @GetMapping("/list")
    public TableDataInfo list(LocalStorage localStorage) {
        startPage();
        List<LocalStorage> list = localStorageService.selectLocalStorageList(localStorage);
        return getDataTable(list);
    }

    @OLog(module = "本地存储", businessType = BusinessType.UPLOAD)
    @PreAuthorize("@permissionService.hasPermission('tool:localStorage:upload')")
    @PostMapping
    public BaseResult upload(@RequestParam String name, @RequestParam MultipartFile file) {
        return toResult(localStorageService.upload(name, file));
    }

    @OLog(module = "本地存储", businessType = BusinessType.UPDATE)
    @PreAuthorize("@permissionService.hasPermission('tool:localStorage:edit')")
    @PutMapping
    public BaseResult upload(@RequestBody LocalStorage localStorage) {
        return toResult(localStorageService.updateLocalStorage(localStorage));
    }


    @OLog(module = "本地存储", businessType = BusinessType.DELETE)
    @PreAuthorize("@permissionService.hasPermission('tool:localStorage:remove')")
    @DeleteMapping("/{id}")
    public BaseResult delete(@PathVariable String id) {
        return toResult(localStorageService.deleteLocalStorage(id));
    }
}
