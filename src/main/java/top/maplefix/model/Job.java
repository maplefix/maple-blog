package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Maple
 * @description 定时任务实体类
 * @date 2020/1/15 15:31
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Job extends BaseEntity implements Serializable {

    public static final String JOB_KEY = "JOB_KEY";
    /**
     * 主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String jobId;
    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    private String jobName;
    /**
     * bean名称
     */
    @Excel(name = "bean名称")
    private String beanName;
    /**
     * 方法名称
     */
    @Excel(name = "方法名称")
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
     * 任务状态
     */
    @Excel(name = "任务状态",readConverterExp = "1=运行,2=暂停")
    private Integer status;
    /**
     * 备注信息
     */
    @Excel(name = "备注信息")
    private String remark;
}
