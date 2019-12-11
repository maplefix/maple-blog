package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 博客字典实体
 * @date : Created in 2019/7/24 0:04
 * @editor:
 * @version: v2.1
 */
@Data
@Table(name = "t_dict")
@NameStyle
public class Dict implements Serializable {

    private static final long serialVersionUID = -7458813809933292186L;
    /**
     * 字典表主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    private String dictId;
    /**
     * 关键字
     */
    private String keyWord;
    /**
     * 字典名
     */
    private String dictName;
    /**
     * 字典值
     */
    private String dictValue;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 删除标识（1：删除，0正常）
     */
    private String delFlag;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * 备注
     */
    private String remark;
}
