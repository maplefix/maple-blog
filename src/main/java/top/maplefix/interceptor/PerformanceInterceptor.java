package top.maplefix.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author : Maple
 * @description : 性能拦截器，用于输出每条 SQL 语句及其执行时间
 * @date : 2020/12/18 15:34
 */
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
        }
)
@Slf4j
public class PerformanceInterceptor implements Interceptor {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Executor executor = (Executor) invocation.getTarget();
        Object parameter = null;
        if (args.length > 1) {
            parameter = args[1];
        }
        BoundSql boundSql = ms.getBoundSql(parameter);;
        CacheKey cacheKey;
        //RowBounds rowBounds = (RowBounds) args[2];
        //由于逻辑关系，只会进入一次
        /*if(args.length == 4){
            //4 个参数时
            boundSql = ms.getBoundSql(parameter);
            //cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            //6 个参数时
            //cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }*/
        String statementId = ms.getId();
        Configuration configuration = ms.getConfiguration();
        String sql = getSql(boundSql, parameter, configuration);

        long start = System.currentTimeMillis();

        Object result = invocation.proceed();

        long end = System.currentTimeMillis();
        long timing = end - start;
        log.debug("耗时：" + timing + " ms" + " - id:" + statementId + " - Sql:" + sql);
        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private String getSql(BoundSql boundSql, Object parameter, Configuration configuration) {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameter == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameter.getClass())) {
                        value = parameter;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameter);
                        value = metaObject.getValue(propertyName);
                    }
                    sql = replacePlaceholder(sql, value);
                }
            }
        }
        return sql;
    }

    private String replacePlaceholder(String sql, Object propertyValue) {
        String result;
        if (propertyValue != null) {
            if (propertyValue instanceof String) {
                result = "'" + propertyValue + "'";
            } else if (propertyValue instanceof Date) {
                result = "'" + DATE_FORMAT.format(propertyValue) + "'";
            } else {
                result = propertyValue.toString();
            }
        } else {
            result = "null";
        }
        try {
            return sql.replaceFirst("\\?", result);
        }catch (IllegalArgumentException e){
            // 如果result中存在特殊字符串 $ 进行转义为 $字符
            return sql.replaceFirst("\\?", result.replaceAll("\\$", "\\\\\\$"));
        }
    }
}
