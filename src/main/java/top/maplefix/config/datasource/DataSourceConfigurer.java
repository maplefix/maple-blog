package top.maplefix.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Maple
 * @description : druid多数据源配置,在该类中生成多个数据源实例并将其注入到 ApplicationContext 中
 * @date : Created in 2019/7/25 18：01
           Edited in 2019/1120 14:17
 * @version : v2.1
 */
@Configuration
public class DataSourceConfigurer {

    @Value("${mybatis.typeAliasesPackage}")
    private String typeAliasesPackage;

    /**
     * 配置数据源一
     * @return DataSource
     */
    @Bean(name = "master")
    @Primary
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource master(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 配置数据源二
     * @return DataSource
     */
    @Bean(name = "slave")
    @ConfigurationProperties("spring.datasource.druid.slave")
    public DataSource slave(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态数据源加载
     * @return DynamicRoutingDataSource
     */
    @Bean(name = "dynamicDataSource")
    public DynamicRoutingDataSource dynamicDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<Object, Object>(2);

        dataSourceMap.put(DataSourceKey.MASTER.getName(),master());
        dataSourceMap.put(DataSourceKey.SLAVE.getName(),slave());

        dynamicRoutingDataSource.setDefaultTargetDataSource(master());
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        DynamicDataSourceContextHolder.dataSourceKey.addAll(dataSourceMap.keySet());

        return dynamicRoutingDataSource;

    }

    /**
     * SqlSessionFactoryBean配置,由于配置文件已经配置了mybatis相关属性，此处该bean可以注释
     * 如果不注释，该bean会覆盖配置文件的值
     * @return SqlSessionFactoryBean
     */
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactoryBean sessionFactoryBean()throws IOException {
       SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        //下划线转驼峰
        configuration.setMapUnderscoreToCamelCase(true);
        //#全局映射器启用缓存
        configuration.setCacheEnabled(true);
        //扫描相关的mapper配置xml文件
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/mapper/*Mapper.xml";
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packageSearchPath));

        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        return sqlSessionFactoryBean;
    }

    /**
     * 事务
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    /**
     * druid监控配置
     * @return ServletRegistrationBean
     */
    //@Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<>(16);
        //禁用HTML页面上的“Rest All”功能
        initParameters.put("resetEnable", "false");
        //ip白名单（没有配置或者为空，则允许所有访问）
        initParameters.put("allow", "");
        //++监控页面登录用户名
        initParameters.put("loginUsername", "admin");
        //++监控页面登录用户密码
        initParameters.put("loginPassword", "admin");
        //ip黑名单
        initParameters.put("deny", "");
        initParameters.put("filters", "stat,wall,log4j");
        //如果某个ip同时存在，deny优先于allow
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     *
     * @return filter registration bean
     */
    //@Bean
    public FilterRegistrationBean druidStatFilter() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean( new WebStatFilter());

        // 添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");

        // 添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
