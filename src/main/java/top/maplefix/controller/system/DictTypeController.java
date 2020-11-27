package top.maplefix.controller.system;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.Constant;
import top.maplefix.controller.common.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.DictType;
import top.maplefix.service.DictTypeService;
import top.maplefix.utils.DateUtils;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description :字典类型接口
 * @date : 2020/3/27 16:33
 */
@RestController
@RequestMapping("/system/dict/type")
public class DictTypeController extends BaseController {
    private final DictTypeService dictTypeService;

    public DictTypeController(DictTypeService dictTypeService) {
        this.dictTypeService = dictTypeService;
    }

    @PreAuthorize("@permissionService.hasPermission('system:dict:list')")
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(DictType dictType) {
        startPage();
        List<DictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    /**
     * 查询字典类型详细
     */
    @PreAuthorize("@permissionService.hasPermission('system:dict:query')")
    @GetMapping(value = "/{id}")
    public BaseResult getInfo(@PathVariable Long id) {
        return BaseResult.success(dictTypeService.selectDictTypeById(id));
    }

    /**
     * 新增字典类型
     */
    @PreAuthorize("@permissionService.hasPermission('system:dict:add')")
    @OLog(module = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@RequestBody DictType dict) {
        if (Constant.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return BaseResult.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateDate(DateUtils.getTime());
        return toResult(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @PreAuthorize("@permissionService.hasPermission('system:dict:edit')")
    @OLog(module = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@RequestBody DictType dict) {
        if (Constant.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return BaseResult.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateDate(DateUtils.getTime());
        return toResult(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @PreAuthorize("@permissionService.hasPermission('system:dict:remove')")
    @OLog(module = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public BaseResult remove(@PathVariable Long id) {
        return toResult(dictTypeService.deleteDictTypeById(id));
    }
}
