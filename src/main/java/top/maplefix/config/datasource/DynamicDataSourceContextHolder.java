package top.maplefix.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Maple
 * @description : 数据源切换处理
 * @date : Created in 2019/7/25 13：10
           Edited in 2019/1120 14:17
 * @version : v1.0
 */
public class DynamicDataSourceContextHolder {

    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /**
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static ThreadLocal<Object> CONTEXT_HOLDER = ThreadLocal.withInitial(DataSourceKey.MASTER::getName);
    //private static ThreadLocal<Object> CONTEXT_HOLDER = ThreadLocal.withInitial(() -> DataSourceKey.MASTER.getName());

    public static List<Object> dataSourceKey = new ArrayList<Object>();
    /**
     * 设置数据源的变量
     */
    public static void setDataSourceKey(String key) {
        log.info("切换到{}数据源", key);
        CONTEXT_HOLDER.set(key);
    }

    /**
     * 获得数据源的变量
     */
    public static Object getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清空数据源变量
     */
    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * 判断是否包含该数据源
     * @param key
     * @return
     */
    public static Boolean containDataSourceKey(String key){
        return dataSourceKey.contains(key);
    }
}
