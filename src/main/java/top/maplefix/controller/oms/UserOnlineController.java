package top.maplefix.controller.oms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.Constant;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.BusinessType;
import top.maplefix.redis.RedisCacheService;
import top.maplefix.secrrity.LoginUser;
import top.maplefix.service.UserOnlineService;
import top.maplefix.utils.StringUtils;
import top.maplefix.vo.UserOnline;
import top.maplefix.vo.page.TableDataInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Maple
 * @description 在线用户监控
 * @date 2020/3/20 10:32
 */
@RestController
@RequestMapping("/online")
public class UserOnlineController extends BaseController {

    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private UserOnlineService userOnlineService;

    @PreAuthorize("@permissionService.hasPermission('monitor:online:list')")
    @GetMapping("/list")
    public TableDataInfo list(String ip, String userName) {
        Collection<String> keys = redisCacheService.keys(Constant.LOGIN_TOKEN_KEY + "*");
        List<UserOnline> userOnlineList = new ArrayList<>();
        for (String key : keys) {
            LoginUser user = redisCacheService.getCacheObject(key);
            if (StringUtils.isNotEmpty(ip) && StringUtils.isNotEmpty(userName)) {
                if (StringUtils.equals(ip, user.getIp()) && StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(userOnlineService.selectOnlineByInfo(ip, userName, user));
                }
            } else if (StringUtils.isNotEmpty(ip)) {
                if (StringUtils.equals(ip, user.getIp())) {
                    userOnlineList.add(userOnlineService.selectOnlineByIpAddr(ip, user));
                }
            } else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
                if (StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                }
            } else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return getDataTable(userOnlineList);
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@permissionService.hasPermission('monitor:online:forceLogout')")
    @OLog(module = "在线用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tokenId}")
    public BaseResult forceLogout(@PathVariable String tokenId) {
        redisCacheService.deleteObject(Constant.LOGIN_TOKEN_KEY + tokenId);
        return BaseResult.success();
    }
}
