package top.maplefix.controller.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.maplefix.controller.BaseController;
import top.maplefix.vo.server.Server;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : Maple
 * @description : 服务器监控相关信息
 * @date : Created in 2019/9/15 16:20
 * @version : v2.1
 */
@Controller
@RequestMapping("/api/admin/server")
public class ServerController extends BaseController {

    /**
     * 服务器相关信息
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping()
    public String server(HttpServletRequest request) throws Exception {
        Server server = new Server();
        server.copyTo();
        request.setAttribute("server", server);
        request.setAttribute("path", "server");
        return "admin/server";
    }
}
