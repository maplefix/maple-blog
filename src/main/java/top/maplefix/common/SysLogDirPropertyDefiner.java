package top.maplefix.common;

import ch.qos.logback.core.PropertyDefinerBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import top.maplefix.config.MapleBlogConfig;

import java.nio.file.Paths;

/**
 * @author : Maple
 * @description : 定义域logback的转换器，用来动态配置系统日志目录
 * @date : 2020/12/22 17:30
 */
@Slf4j
@Configuration
public class SysLogDirPropertyDefiner extends PropertyDefinerBase {

    @Autowired
    private MapleBlogConfig mapleBlogConfig;
    @Override
    public String getPropertyValue() {
        //利用Paths.get方式将路径返回标准化的字符串
        return getLogPath();
    }

    public String getLogPath() {
        String logPath = "";
        try {
            String path = mapleBlogConfig.getLogPath();
            logPath = Paths.get(path).normalize().toUri().toASCIIString();
        }catch (Exception e){
            log.error("获取日志路径异常,{},{}",e.getMessage(),e);
        }
        return logPath;
    }
}
