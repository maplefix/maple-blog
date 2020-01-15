package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author wangjg
 * @description 任务日志实体类
 * @date 2020/1/15 15:39
 */
@Data
@Table(name = "t_job_log")
public class JobLog implements Serializable {

    /**
     * 主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String jobLogId;
    /**
     *  任务名
     */
    @Excel(name = "任务名")
    private String jobName;
    /**
     * bean名称
     */
    @Excel(name = "bean名称")
    private String beanName;
    /**
     * 方法名
     */
    @Excel(name = "方法名")
    private String methodName;
    /**
     * 参数
     */
    @Excel(name = "参数")
    private String params;
    /**
     * cron表达式
     */
    @Excel(name = "cron表达式")
    private String cronExpression;
    /**
     * 异常信息
     */
    @Excel(name = "异常信息")
    private String exceptionMsg;
    /**
     * 运行结果
     */
    @Excel(name = "运行结果")
    private String result;
    /**
     * 运行状态
     */
    @Excel(name = "运行状态",readConverterExp = "1=成功,0=失败")
    private Integer status;
    /**
     * 耗时
     */
    @Excel(name = "耗时")
    private long cost;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private String createDate;


}
