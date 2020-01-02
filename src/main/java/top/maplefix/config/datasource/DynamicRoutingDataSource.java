package top.maplefix.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author : Maple
 * @description : 该类继承自 AbstractRoutingDataSource 类，
 *                在访问数据库时会调用该类的 determineCurrentLookupKey() 方法获取数据库实例的 key
 * @date : Created in 2019/7/24 13：10
           Edited in 2019/1120 14:17
 * @version : v2.1
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("current datasource is : {}",DynamicDataSourceContextHolder.getDataSourceKey());
        return DynamicDataSourceContextHolder.getDataSourceKey();
    }
}
