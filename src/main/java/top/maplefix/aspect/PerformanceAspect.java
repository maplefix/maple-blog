package top.maplefix.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author : Maple
 * @description : 性能监控aop,打印出sql执行参数和执行耗时
 * @date : 2020/1/16 9:54
 */
@Slf4j
@Component
@Aspect
public class PerformanceAspect {

    /**
     * 记录返回值
     * @param joinPoint 切点
     */
    @AfterReturning("execution(* top.maplefix.mapper.*Mapper.*(..))")
    public void logServiceAccess(JoinPoint joinPoint) { }

    /**
     * 监控top.maplefix.mapper.*Mapper包及其子包的所有public方法
     */
    @Pointcut("execution(* top.maplefix.mapper.*Mapper.*(..))")
    private void pointCutMethod() { }

    /**
     * 声明环绕通知，打印sql参数和耗时
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object obj = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        String methodName = proceedingJoinPoint.getSignature().toString();
        log.info("耗时：{} 毫秒,方法：{}，参数：{}",(end - begin),
                methodName.substring(0,methodName.indexOf("(")), Arrays.toString(proceedingJoinPoint.getArgs()));
        return obj;
    }

}
