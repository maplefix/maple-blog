package top.maplefix.controller.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.common.BaseController;
import top.maplefix.service.ServerService;

/**
 * @author : Maple
 * @description : 服务器监控相关信息
 * @date : 2020/2/15 16:20
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {
    @Autowired
    private ServerService serverService;
    /**
     * 服务器相关信息
     * @return
     * @throws Exception
     */
    @PreAuthorize("@permissionService.hasPermission('monitor:server:list')")
    @GetMapping()
    public BaseResult getInfo() throws Exception {
        return BaseResult.success(serverService.getServers());
    }
}
