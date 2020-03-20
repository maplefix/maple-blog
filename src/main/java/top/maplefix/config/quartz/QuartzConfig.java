package top.maplefix.config.quartz;

import org.quartz.Scheduler;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author Maple
 * @description quartz配置
 * @date 2020/1/18 15:41
 */
@Configuration
public class QuartzConfig {

    /**
     * Solve the problem of null spring bean injection in job
     */
    @Component
    public static class QuartzJobFactory extends AdaptableJobFactory {

        @Autowired
        AutowireCapableBeanFactory capableBeanFactory;

        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
            Object jobInstance = super.createJobInstance(bundle);
            capableBeanFactory.autowireBean(jobInstance);
            return jobInstance;
        }
    }

    @Bean
    public Scheduler scheduler(QuartzJobFactory quartzJobFactory) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(quartzJobFactory);
        schedulerFactoryBean.afterPropertiesSet();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
        return scheduler;
    }
}
