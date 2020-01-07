package top.maplefix.aspect;

import com.alibaba.fastjson.JSONObject;
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
import top.maplefix.annotation.OLog;
import top.maplefix.constant.Constant;
import top.maplefix.enums.BusinessType;
import top.maplefix.model.OperationLog;
import top.maplefix.model.User;
import top.maplefix.service.IOperationLogService;
import top.maplefix.utils.AddressUtils;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.IpUtils;
import top.maplefix.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Maple
 * @description : 操作日志切面
 * @date : Created in 2019/9/12 23:42
 * @version : v1.0
 */
@Aspect
@Component
@Slf4j
public class OperationLogAspect {

    @Autowired
    private IOperationLogService operationLogService;
    /**
     * 配置切入点
     */
    @Pointcut("@annotation(top.maplefix.annotation.OLog)")
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
        // *========数据库日志=========*//
        OperationLog operLog = new OperationLog();
        try {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
            // 获得注解
            OLog controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 获取当前的用户
            User loginUser = (User)request.getSession().getAttribute(Constant.LOGIN_USER);

            String operIp = IpUtils.getIpAddr(request);
            //访问地区
            String operLocation = AddressUtils.getRealAddressByIp(operIp);
            // 获取客户端操作系统
            String operOs = userAgent.getOperatingSystem().getName();
            // 获取客户端浏览器
            String operBrowser = userAgent.getBrowser().getName();
            //访问地址
            String operUrl = request.getRequestURI();
            operLog.setStatus(Constant.SUCCESS);
            operLog.setOperIp(operIp);
            operLog.setOperLocation(operLocation);
            operLog.setOperOs(operOs);
            operLog.setOperBrowser(operBrowser);
            operLog.setOperUrl(operUrl);
            if (loginUser != null) {
                operLog.setOperName(loginUser.getLoginName());
            }
            if (e != null) {
                e.printStackTrace();
                operLog.setStatus(Constant.FAIL);
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            operLog.setOperDate(DateUtils.getCurrDate());
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog);
            if(StringUtils.isEmpty(operLog.getOperName())){
                operLog.setOperName("admin");
            }
            // 保存数据库
            operationLogService.saveOperationLog(operLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("操作日志保存异常，异常信息:{},异常堆栈:{}", exp.getMessage(),exp);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(OLog log, OperationLog operLog) throws Exception {
        // 设置action动作
        operLog.setBusinessType(BusinessType.getValue(log.businessType().toString()));
        // 设置标题
        operLog.setModule(log.module());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operationLog
     */
    private void setRequestValue(OperationLog operationLog) {
        Map map = new HashMap(16);
        Enumeration enumeration = ServletUtils.getRequest().getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = (String) enumeration.nextElement();
            String paramValue = ServletUtils.getRequest().getParameter(paramName);
            //形成键值对应的map
            map.put(paramName, paramValue);
        }
        String params = JSONObject.toJSONString(map);
        operationLog.setOperParam(params.length()>2000 ? params.substring(0,2000) : params);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private OLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(OLog.class);
        }
        return null;
    }
}
