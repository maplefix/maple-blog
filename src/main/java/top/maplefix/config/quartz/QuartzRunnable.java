package top.maplefix.config.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;
import top.maplefix.utils.SpringUtils;
import top.maplefix.utils.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author Maple
 * @description 任务线程
 * @date 2020/2/1 13:27
 */
@Slf4j
public class QuartzRunnable implements Callable {
    private Object target;
    private Method method;
    private String param;

    public QuartzRunnable(String beanName, String methodName, String methodParams) throws NoSuchMethodException {
        this.target = SpringUtils.getBean(beanName);
        this.param = methodParams;
        if (StringUtils.isNotEmpty(param)) {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    @Override
    public Object call() throws Exception {
        ReflectionUtils.makeAccessible(method);
        Object result;
        if (StringUtils.isNotEmpty(param)) {
            result = method.invoke(target, param);
        } else {
            result = method.invoke(target);
        }
        return result;
    }
}
