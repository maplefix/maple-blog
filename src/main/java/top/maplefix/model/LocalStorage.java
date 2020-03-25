package top.maplefix.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Maple
 * @description 本地存储实体
 * @date 2020/3/18 17:21
 */
@Data
@NoArgsConstructor
public class LocalStorage extends BaseEntity implements Serializable {

    @Id
    @KeySql(genId = UuIdGenId.class)
    private String  fileId;
    /**
     * 文件真实名称
     */
    private String realName;
    /**
     * 文件名
     */
    private String name;
    /**
     * 后缀
     */
    private String suffix;
    /**
     * 路径
     */
    private String path;
    /**
     * 类型
     */
    private String type;
    /**
     * 大小
     */
    private String size;

    public LocalStorage(String realName, String name, String suffix, String path, String type, String size) {
        this.realName = realName;
        this.name = name;
        this.suffix = suffix;
        this.path = path;
        this.type = type;
        this.size = size;
    }
}
