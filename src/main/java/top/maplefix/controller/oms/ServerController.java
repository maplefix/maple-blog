package top.maplefix.controller.oms;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.maplefix.common.BaseResult;
import top.maplefix.controller.BaseController;
import top.maplefix.vo.server.Server;

/**
 * @author : Maple
 * @description : 服务器监控相关信息
 * @date : 2020/2/15 16:20
 */
@Controller
@RequestMapping("/api/admin/server")
public class ServerController extends BaseController {

    /**
     * 服务器相关信息
     * @return
     * @throws Exception
     */
    @PreAuthorize("@permissionService.hasPermission('monitor:server:list')")
    @GetMapping()
    public BaseResult getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return BaseResult.success(server);
    }
}
