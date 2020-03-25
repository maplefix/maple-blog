package top.maplefix.config.factory;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import top.maplefix.model.LoginLog;
import top.maplefix.model.OperateLog;
import top.maplefix.model.VisitLog;
import top.maplefix.service.EmailService;
import top.maplefix.service.LoginLogService;
import top.maplefix.service.OperateLogService;
import top.maplefix.service.VisitLogService;
import top.maplefix.utils.*;
import top.maplefix.vo.ReplayEmail;

import java.util.TimerTask;

/**
 * @author Maple
 * @description 异步工厂,用来处理登陆日志/操作日志/访问日志和邮件发送
 * @date 2020/1/18 17:12
 */
@Slf4j
public class AsyncFactory {

    private AsyncFactory() { }

    /**
     * record login log
     *
     * @param username user name
     * @param statusStr   status
     * @param message  message
     * @param args     args
     * @return timeTask
     */
    public static TimerTask recordLoginLog(final String username, final Boolean statusStr, final String message,
                                           final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        int status;
        if(statusStr){
            status = 1;
        }else {
            status = 0;
        }
        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIp(ip);
                String os = userAgent.getOperatingSystem().getName();
                String browser = userAgent.getBrowser().getName();
                LoginLog loginLog = LoginLog.builder()
                        .loginName(username)
                        .ip(ip)
                        .browser(browser)
                        .os(os)
                        .loginMsg(message)
                        .location(address)
                        .status(status)
                        .build();
                log.info("insert login log {}", loginLog);
                SpringUtils.getBean(LoginLogService.class).insertLoginLog(loginLog);
            }
        };
    }

    /**
     * record operate log
     *
     * @param operateLog operate log
     * @return timeTask
     */
    public static TimerTask recordOperateLog(final OperateLog operateLog) {
        return new TimerTask() {
            @Override
            public void run() {
                operateLog.setLocation(AddressUtils.getRealAddressByIp(operateLog.getIp()));
                SpringUtils.getBean(OperateLogService.class).insertOperateLog(operateLog);
            }
        };
    }

    /**
     * record visit log
     *
     * @param visitLog visitLog
     * @return timeTask
     */
    public static TimerTask recordVisitLog(final VisitLog visitLog) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String spider = SpiderUtils.parseUserAgent(ServletUtils.getUserAgent());
        return new TimerTask() {
            @Override
            public void run() {
                visitLog.setOs(userAgent.getOperatingSystem().getName());
                visitLog.setSpider(spider);
                visitLog.setBrowser(userAgent.getBrowser().getName());
                visitLog.setLocation(AddressUtils.getRealAddressByIp(visitLog.getIp()));
                SpringUtils.getBean(VisitLogService.class).insertVisitLog(visitLog);
            }
        };
    }

    public static TimerTask sendReplyEmail(String url, String htmlContent, String nickName, String email, ReplayEmail replayEmail) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(EmailService.class).sendReplyEmail(url, htmlContent, nickName, email,replayEmail);
            }
        };
    }
}
