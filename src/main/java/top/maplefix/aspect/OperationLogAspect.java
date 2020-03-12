package top.maplefix.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.thymeleaf.util.StringUtils;
import top.maplefix.annotation.OLog;
import top.maplefix.config.factory.AsyncFactory;
import top.maplefix.config.factory.AsyncManager;
import top.maplefix.constant.Constant;
import top.maplefix.model.OperateLog;
import top.maplefix.secrrity.LoginUser;
import top.maplefix.secrrity.service.TokenService;
import top.maplefix.utils.IpUtils;
import top.maplefix.utils.ServletUtils;
import top.maplefix.utils.SpringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @author : Maple
 * @description : 操作日志同步工厂
 * @date : 2020/1/15 16:30
 */
@Aspect
@Component
@Slf4j
public class OperationLogAspect {

    private long startTime = 0L;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(top.maplefix.annotation.OLog)")
    public void logPointCut() { }

    /**
     * 目标方法出现异常将被拦截
     *
     * @param joinPoint 切点
     */
    @AfterThrowing(value = "logPointCut()",throwing = "e")
    public void doBefore(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e,null,System.currentTimeMillis() - startTime);
    }

    /**
     * 环绕方法
     *
     * @param joinPoint 切点
     * @return object 目标方法返回结果
     * @throws Throwable exception
     */
    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        startTime = System.currentTimeMillis();
        result = joinPoint.proceed();
        handleLog(joinPoint, null, result, System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * 日志记录
     *
     * @param joinPoint  join point
     * @param e          exception
     * @param jsonResult result
     * @param cost       the time of this method cost
     */
    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult, long cost) {
        try {
            // get annotation
            OLog controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // get current user from servlet
            LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());

            OperateLog operateLog = new OperateLog();
            operateLog.setStatus(Constant.SUCCESS);
            // get the IP of this request
            operateLog.setIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
            operateLog.setResult(JSON.toJSONString(jsonResult));
            operateLog.setCost(cost);
            operateLog.setUrl(ServletUtils.getRequest().getRequestURI());

            if (e != null) {
                operateLog.setStatus(Constant.FAILED);
                operateLog.setExceptionMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // get the class name
            String className = joinPoint.getTarget().getClass().getName();
            // get method name
            String methodName = joinPoint.getSignature().getName();
            operateLog.setMethod(MessageFormat.format("{}.{}()", className, methodName));
            // get request method
            operateLog.setMethod(ServletUtils.getRequest().getMethod());
            // set method args
            getControllerMethodDescription(joinPoint, controllerLog, operateLog);
            // save log
            AsyncManager.me().execute(AsyncFactory.recordOperateLog(operateLog));
        } catch (Exception exception) {
            log.error("get exception in handleLog,{} ", exception.getMessage(), exception);
        }
    }

    /**
     * 设置操作日志值
     *
     * @param joinPoint  join point
     * @param log        the annotation
     * @param operateLog the operateLog
     */
    private void getControllerMethodDescription(JoinPoint joinPoint, OLog log, OperateLog operateLog) {
        // set businessType
        operateLog.setFunction(log.businessType().getValue());
        // set title
        operateLog.setModule(log.module());
        // save request data if saveRequestData is true
        if (log.isSaveRequestData()) {
            // set request value
            setRequestValue(joinPoint, operateLog);
        }
    }

    /**
     * get the data from request.
     * If this request method is PUT/POST get data from body(the method args)
     * else ,get data from attribute
     *
     * @param joinPoint  join point
     * @param operateLog operate log
     */
    private void setRequestValue(JoinPoint joinPoint, OperateLog operateLog) {
        String requestMethod = operateLog.getMethod();
        if (HttpMethod.PUT.matches(requestMethod) || HttpMethod.POST.matches(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operateLog.setParams(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operateLog.setParams(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 获取注解
     *
     * @param joinPoint join point
     * @return
     */
    private OLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(OLog.class);
        }
        return null;
    }

    /**
     * 参数链接
     *
     * @param paramsArray data
     * @return params data
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params.append(jsonObj.toString() + " ");
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断数据是否为文件或者数据响应
     *
     * @param o the data
     * @return 是返回true，否则返回false
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}
