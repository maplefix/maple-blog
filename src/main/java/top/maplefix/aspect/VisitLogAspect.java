package top.maplefix.aspect;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.StringUtils;
import top.maplefix.annotation.VLog;
import top.maplefix.constant.Constant;
import top.maplefix.model.VisitLog;
import top.maplefix.service.IVisitLogService;
import top.maplefix.utils.AddressUtils;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.IpUtils;
import top.maplefix.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author : Maple
 * @description : 访客日志处理
 * @date : Created in 2019/7/24 23:13
 * @version : v1.0
 */

@Aspect
@Component
@Slf4j
public class VisitLogAspect {

    @Autowired
    private IVisitLogService visitLogService;
    /**
     *  配置织入点
     */
    @Pointcut("@annotation(top.maplefix.annotation.VLog)")
    public void logPointCut() { }

/**
     * 前置通知 用于拦截操作
     *
     * @param joinPoint 切点
     */

    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

/**
     * 拦截异常操作
     *
     * @param joinPoint
     * @param e
     */

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
            // 获得注解
            VLog vLog = getAnnotationLog(joinPoint);
            if (vLog == null) {
                return;
            }
            String visitIp = IpUtils.getIpAddr(request);
            //访问地区
            String visitLocation = AddressUtils.getRealAddressByIp(visitIp);
            // 获取客户端操作系统
            String visitOs = userAgent.getOperatingSystem().getName();
            // 获取客户端浏览器
            String visitBrowser = userAgent.getBrowser().getName();
            //访问地址
            String requestUrl = request.getRequestURI();
            VisitLog visitLog = new VisitLog();
            visitLog.setVisitIp(visitIp);
            visitLog.setVisitLocation(visitLocation);
            visitLog.setVisitOs(visitOs);
            visitLog.setVisitBrowser(visitBrowser);
            visitLog.setVisitDate(DateUtils.getCurrDate());
            visitLog.setRequestUrl(requestUrl);
            if (e != null) {
                visitLog.setStatus(Constant.FAIL);
                visitLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            } else {
                visitLog.setStatus(Constant.SUCCESS);
            }
            // 处理设置注解上的参数
            getControllerMethodDescription(vLog, visitLog);
            // 保存数据库
            visitLogService.saveVisitLog(visitLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("访问日志保存异常，异常信息:{},异常堆栈:{}", exp.getMessage(),exp);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log      日志
     * @param visitLog 操作日志
     * @throws Exception
     */

    public void getControllerMethodDescription(VLog log, VisitLog visitLog) throws Exception {
        visitLog.setModule(log.module());
    }


    /**
     * 是否存在注解，如果存在就获取
     */

    private VLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(VLog.class);
        }
        return null;
    }
}
