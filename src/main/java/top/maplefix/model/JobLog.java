package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import top.maplefix.annotation.Excel;

import java.io.Serializable;

/**
 * @author Maple
 * @description 任务日志实体类
 * @date 2020/1/15 15:39
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobLog extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Excel(name = "主键")
    private Long id;
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
    private String param;
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
    @Excel(name = "运行状态",readConverterExp = "1=运行,2=暂停")
    private Integer status;
    /**
     * 耗时
     */
    @Excel(name = "耗时")
    private long cost;

}
