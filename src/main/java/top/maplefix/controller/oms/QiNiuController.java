package top.maplefix.controller.oms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.service.QiNiuService;
import top.maplefix.utils.StringUtils;
import top.maplefix.vo.QiNiuConfig;
import top.maplefix.vo.QiNiuContent;
import top.maplefix.vo.page.TableDataInfo;

import java.util.List;

/**
 * @author Maple
 * @description 七牛云存储数据接口
 * @date 2020/3/20 11:01
 */
@RestController
@RequestMapping("/qiNiu")
public class QiNiuController extends BaseController {

    @Autowired
    private QiNiuService qiNiuService;

    @GetMapping("/list")
    @PreAuthorize("@permissionService.hasPermission('tool:qiNiu:list')")
    public TableDataInfo list(QiNiuContent qiNiuContent) {
        startPage();
        List<QiNiuContent> qiNiuContentList = qiNiuService.selectContentList(qiNiuContent);
        return getDataTable(qiNiuContentList);
    }

    @GetMapping("/config")
    @PreAuthorize("@permissionService.hasPermission('tool:qiNiuConfig:query')")
    public BaseResult config() {
        QiNiuConfig qiNiuConfig = qiNiuService.getQiNiuConfig();
        if (StringUtils.isNotEmpty(qiNiuConfig.getSecretKey())) {
            //secretKey 打码
            qiNiuConfig.setSecretKey("**************************");
        }
        return BaseResult.success(qiNiuConfig);
    }

    @PutMapping("/config")
    @PreAuthorize("@permissionService.hasPermission('tool:qiNiuConfig:edit')")
    @OLog(module = "七牛云配置", businessType = BusinessType.UPDATE)
    public BaseResult edit(@RequestBody QiNiuConfig qiNiuConfig) {
        return toResult(qiNiuService.updateQiNiuConfig(qiNiuConfig));
    }

    @PostMapping
    @PreAuthorize("@permissionService.hasPermission('tool:qiNiu:upload')")
    @OLog(module = "七牛云存储", businessType = BusinessType.UPLOAD)
    public BaseResult upload(@RequestParam MultipartFile file) {
        QiNiuContent qiNiuContent = qiNiuService.upload(file);
        return BaseResult.success(qiNiuContent);
    }

    @PreAuthorize("@permissionService.hasPermission('system:qiNiu:synchronize')")
    @OLog(module = "七牛云存储", businessType = BusinessType.UPDATE)
    @PostMapping("/synchronize")
    public BaseResult synchronize() {
        return BaseResult.success(qiNiuService.synchronize());
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("@permissionService.hasPermission('tool:qiNiu:remove')")
    @OLog(module = "七牛云存储", businessType = BusinessType.DELETE)
    public BaseResult delete(@PathVariable String ids) {
        return toResult(qiNiuService.deleteQiNiuContent(ids));
    }

    @GetMapping("/download/{id}")
    @PreAuthorize("@permissionService.hasPermission('tool:qiNiu:download')")
    @OLog(module = "七牛云存储", businessType = BusinessType.DOWNLOAD)
    public BaseResult download(@PathVariable String  id) {
        return BaseResult.success(qiNiuService.getDownloadUrl(id));
    }
}