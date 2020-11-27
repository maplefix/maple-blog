package top.maplefix.controller.system;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.common.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.DictData;
import top.maplefix.service.DictDataService;
import top.maplefix.utils.DateUtils;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author : Maple
 * @description :字典数据接口
 * @date : 2020/3/27 18:33
 */
@RestController
@RequestMapping("/system/dict/data")
public class DictDataController extends BaseController {

    private final DictDataService dictDataService;

    public DictDataController(DictDataService dictDataService) {
        this.dictDataService = dictDataService;
    }

    @PreAuthorize("@permissionService.hasPermission('system:dict:list')")
    @GetMapping("/list")
    public TableDataInfo list(DictData dictData) {
        startPage();
        List<DictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    /**
     * 查询字典数据详细
     */
    @PreAuthorize("@permissionService.hasPermission('system:dict:query')")
    @GetMapping(value = "/{dictCode}")
    public BaseResult getInfo(@PathVariable Long dictCode) {
        return BaseResult.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @PreAuthorize("@permissionService.hasPermission('system:dict:query')")
    @GetMapping(value = "/dictType/{dictType}")
    public BaseResult dictType(@PathVariable String dictType) {
        return BaseResult.success(dictDataService.selectDictDataByType(dictType));
    }

    /**
     * 新增字典类型
     */
    @PreAuthorize("@permissionService.hasPermission('system:dict:add')")
    @OLog(module = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public BaseResult add(@RequestBody DictData dict) {
        dict.setCreateDate(DateUtils.getTime());
        return toResult(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @PreAuthorize("@permissionService.hasPermission('system:dict:edit')")
    @OLog(module = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public BaseResult edit(@RequestBody DictData dict) {
        dict.setUpdateDate(DateUtils.getTime());
        return toResult(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @PreAuthorize("@permissionService.hasPermission('system:dict:remove')")
    @OLog(module = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCode}")
    public BaseResult remove(@PathVariable Long dictCode) {
        return toResult(dictDataService.deleteDictDataById(dictCode));
    }
}
