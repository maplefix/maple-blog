package top.maplefix.controller.backend;

import com.google.code.kaptcha.Constants;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.maplefix.annotation.OLog;
import top.maplefix.constant.Constant;
import top.maplefix.constant.LoginConstant;
import top.maplefix.constant.UserConstant;
import top.maplefix.enums.OperationType;
import top.maplefix.model.LoginLog;
import top.maplefix.model.User;
import top.maplefix.service.*;
import top.maplefix.utils.AddressUtils;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.IpUtils;
import top.maplefix.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * @author : Maple
 * @description : 登录操作控制类
 * @date : Created in 2019/7/28 16:37
 * @editor:
 * @version: v2.1
 */
@Controller
@RequestMapping("/api/admin")
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ILabelService labelService;

    @Autowired
    private ILinksService linksService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ILoginLogService loginLogService;

    @Autowired
    private IDashboardService dashboardService;
    /**
     * 后台首页地址
     */
    private final static String INDEX = "admin/index";
    /**
     * 登录地址
     */
    private final static String LOGIN = "admin/login";
    /**
     * 重定向到首页地址
     */
    private final static String REDIRECT_INDEX = "redirect:/api/admin/index";

    /**
     * 登录页面跳转
     * @return
     */
    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }

    /**
     * 管理系统首页加载出博客总数，分类总数，标签总数，友链总数，配置型总数
     * @param request
     * @return
     */
    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        String loginUserId = (String) request.getSession().getAttribute(Constant.LOGIN_USER_ID);
        User user = new User();
        user.setUserId(loginUserId);
        User loginUser = userService.getUserDetail(user);
        request.setAttribute("path", "index");
        //当前登录用户
        request.setAttribute(Constant.LOGIN_USER, loginUser);
        //分类总数
        request.setAttribute("categoryCount", categoryService.getTotalCategory());
        //博客总数
        request.setAttribute("blogCount", blogService.getTotalBlog());
        //友链总数
        request.setAttribute("linksCount", linksService.getTotalLinks());
        //标签总数
        request.setAttribute("labelCount", labelService.getTotalLabel());
        //字典总数
        request.setAttribute("dictCount", dictService.getTotalDict());
        //放置Dashboard最新消息
        request.setAttribute("logMessages", dashboardService.selectLogMessage());
        request.setAttribute("path", "index");
        return INDEX;
    }

    /**
     * 登录操作
     * @param loginName 登录名
     * @param password 密码
     * @param verifyCode 验证码
     * @param session
     * @return
     */
    @PostMapping(value = "/login")
    @OLog(module = "登录操作", businessType = OperationType.LOGIN)
    public String login(HttpSession session,HttpServletRequest request,
                        @RequestParam("loginName") String loginName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode ) {
        log.info("登录操作开始...");
        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", LoginConstant.VERIFY_CODE_NOT_EMPTY);
            insertLoginLog(request,loginName, Constant.FAIL,LoginConstant.VERIFY_CODE_NOT_EMPTY);
            log.error("登录操作失败，验证码为空...");
            return LOGIN;
        }
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", LoginConstant.NAME_OR_PWD_NOT_EMPTY);
            insertLoginLog(request,loginName, Constant.FAIL,LoginConstant.VERIFY_CODE_NOT_EMPTY);
            log.error("登录操作失败，用户名或密码为空...");
            return LOGIN;
        }
        //从session中获取验证码计算后的值
        Object object  = session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String captchaCode = String.valueOf(object != null ? object : "");
        if (StringUtils.isEmpty(captchaCode) || !verifyCode.equalsIgnoreCase(captchaCode)) {
            session.setAttribute("errorMsg", LoginConstant.VERIFY_CODE_ERROR);
            insertLoginLog(request,loginName, Constant.FAIL,LoginConstant.VERIFY_CODE_ERROR);
            log.error("登录操作失败，验证码错误...");
            return LOGIN;
        }
        User user = userService.login(loginName, password);
        if (user != null) {
            if(UserConstant.USER_DISABLE.equals(user.getStatus())){
                session.setAttribute("errorMsg", LoginConstant.ACCOUNT_DISABLED);
                insertLoginLog(request,loginName, Constant.FAIL,LoginConstant.VERIFY_CODE_ERROR);
                log.error("登录操作失败，用户账号已停用...");
                return LOGIN;
            }
            session.setAttribute(Constant.LOGIN_USER, user);
            session.setAttribute(Constant.LOGIN_USER_ID, user.getUserId());
            //设置redis-session有效期60分钟
            redisTemplate.opsForValue().set(Constant.LOGIN_USER + ":" + user.getUserId(), session.getId(),60*60, TimeUnit.SECONDS);
            //插入登录日志
            insertLoginLog(request,loginName, Constant.SUCCESS, LoginConstant.LOGIN_SUCCESS);
            //更新用户表最后登录日期和最后登录IP
            user.setLoginDate(DateUtils.getCurrDate());
            user.setLoginIp(IpUtils.getIpAddr(request));
            userService.updateUser(user);
            log.info("登录操作成功...");
            return REDIRECT_INDEX;
        } else {
            session.setAttribute("errorMsg", LoginConstant.LOGIN_FAIL);
            insertLoginLog(request,loginName, Constant.FAIL, LoginConstant.LOGIN_FAIL);
            log.error("登录操作失败，用户信息为空");
            return LOGIN;
        }
    }

    /**
     * 插入该次登录的登录日志信息
     * @param request
     * @param loginName 登录名
     * @param status 登录状态
     * @param loginMsg 登录信息
     */
    public void insertLoginLog(HttpServletRequest request,String loginName, String status,String loginMsg){
        log.info("插入登录日志开始...");
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        LoginLog loginLog = new LoginLog();
        //登录IP
        String loginIp = IpUtils.getIpAddr(request);
        //登录地区
        String loginLocation = AddressUtils.getRealAddressByIp(loginIp);
        // 获取客户端操作系统
        String loginOs = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String loginBrowser = userAgent.getBrowser().getName();
        loginLog.setLoginName(loginName);
        loginLog.setLoginIp(loginIp);
        loginLog.setLoginLocation(loginLocation);
        loginLog.setLoginBrowser(loginBrowser);
        loginLog.setLoginOs(loginOs);
        loginLog.setLoginMsg(loginMsg);
        loginLog.setStatus(status);
        loginLog.setLoginDate(DateUtils.getCurrDate());
        loginLogService.saveLoginLog(loginLog);
        log.info("插入登录日志成功...");
    }
    /**
     * 登出操作
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("登出操作开始。。。");
        request.getSession().removeAttribute(Constant.LOGIN_USER_ID);
        request.getSession().removeAttribute(Constant.LOGIN_USER);
        request.getSession().removeAttribute("errorMsg");
        redisTemplate.delete(Constant.LOGIN_USER + ":" + request.getSession().getAttribute(Constant.LOGIN_USER_ID));
        log.info("登出操作成功...");
        return "admin/login";
    }
}
