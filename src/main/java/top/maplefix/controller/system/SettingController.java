package top.maplefix.controller.system;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.ConfigKey;
import top.maplefix.controller.common.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.Config;
import top.maplefix.service.ConfigService;
import top.maplefix.vo.AboutSetting;
import top.maplefix.vo.EmailSetting;
import top.maplefix.vo.SiteSetting;

/**
 * @author wangjg
 * @description 系统设置接口
 * @date 2020/7/30 16:33
 */
@RestController
@RequestMapping("system/setting")
public class SettingController extends BaseController {
    @Autowired
    ConfigService configService;

    @GetMapping("/about")
    @PreAuthorize("@permissionService.hasPermission('system:setting:about:query')")
    public BaseResult about() {
        Config config = configService.selectConfigByKey(ConfigKey.CONFIG_KEY_ABOUT);
        if (config != null) {
            AboutSetting aboutSetting = JSON.parseObject(config.getConfigValue(), AboutSetting.class);
            return BaseResult.success("获取成功", aboutSetting.getContent());
        }
        return BaseResult.success(new AboutSetting());
    }

    @PutMapping("/about")
    @OLog(module = "系统设置-关于", businessType = BusinessType.UPDATE)
    @PreAuthorize("@permissionService.hasPermission('system:setting:about:edit')")
    public BaseResult editAbout(@RequestBody AboutSetting aboutSetting) {
        String jsonString = JSON.toJSONString(aboutSetting);
        Config config = new Config();
        config.setConfigKey(ConfigKey.CONFIG_KEY_ABOUT);
        config.setConfigValue(jsonString);
        return BaseResult.success(configService.updateConfigByConfigKey(config));
    }

    @GetMapping("/siteSetting")
    @PreAuthorize("@permissionService.hasPermission('system:setting:siteSetting:query')")
    public BaseResult siteSetting() {
        Config config = configService.selectConfigByKey(ConfigKey.CONFIG_KEY_SITE_SETTING);
        //convert to site setting
        if (config != null) {
            SiteSetting siteSetting = (SiteSetting) JSON.parse(config.getConfigValue());
            return BaseResult.success(siteSetting);
        }
        return BaseResult.success(new SiteSetting());
    }

    @PutMapping("siteSetting")
    @PreAuthorize("@permissionService.hasPermission('system:setting:siteSetting:edit')")
    @OLog(module = "系统设置-网站设置", businessType = BusinessType.UPDATE)
    public BaseResult editSiteSetting(@RequestBody SiteSetting siteSetting) {
        String jsonString = JSON.toJSONString(siteSetting);
        Config config = new Config();
        config.setConfigKey(ConfigKey.CONFIG_KEY_SITE_SETTING);
        config.setConfigValue(jsonString);
        return BaseResult.success(configService.updateConfigByConfigKey(config));
    }

    @GetMapping("/emailSetting")
    @PreAuthorize("@permissionService.hasPermission('system:setting:emailSetting:query')")
    public BaseResult emailSetting() {
        Config config = configService.selectConfigByKey(ConfigKey.CONFIG_KEY_EMAIL_SETTING);
        //convert to site setting
        if (config != null) {
            EmailSetting emailSetting = JSON.parseObject(config.getConfigValue(), EmailSetting.class);
            emailSetting.setPassword("*************************");
            return BaseResult.success(emailSetting);
        }
        return BaseResult.success(new EmailSetting());
    }

    @PutMapping("emailSetting")
    @OLog(module = "系统设置-邮件设置", businessType = BusinessType.UPDATE)
    @PreAuthorize("@permissionService.hasPermission('system:setting:emailSetting:edit')")
    public BaseResult editEmailSetting(@RequestBody EmailSetting emailSetting) {
        String jsonString = JSON.toJSONString(emailSetting);
        Config config = new Config();
        config.setConfigKey(ConfigKey.CONFIG_KEY_EMAIL_SETTING);
        config.setConfigValue(jsonString);
        return BaseResult.success(configService.updateConfigByConfigKey(config));
    }
}
