package top.maplefix.controller.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.Constant;
import top.maplefix.enums.ResultCode;
import top.maplefix.vo.server.Server;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @author : Maple
 * @description : 首页图标数据填充
 * @date : Created in 2019/9/15 17:46
 * @version : v1.0
 */
@RestController
@RequestMapping("/api/admin/dashboard")
@Slf4j
public class DashboardController {

    @GetMapping
    public BaseResult memJvmCpuData() throws UnknownHostException {
        log.info("系统信息查询开始...");
        BaseResult baseResult = new BaseResult();
        Server server = new Server();
        List<Double> data = server.getDashBoardData();
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(data);
        log.info("系统信息查询成功...");
        return baseResult;
    }
}
