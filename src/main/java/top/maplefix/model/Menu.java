package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import top.maplefix.annotation.Excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maple
 * @description 菜单表实体类
 * @date 2020/1/15 16:03
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu extends BaseEntity implements Serializable {
    /**
     * 主键ID(每4位进行编号,父子关系采用拼接,例如00010001)
     */
    @Excel(name = "主键")
    private Long id;
    /**
     * 菜单名称
     */
    @Excel(name = "菜单名称")
    private String menuName;
    /**
     * 父菜单ID
     */
    @Excel(name = "父菜单ID")
    private Long parentId;
    /**
     * 组件地址
     */
    @Excel(name = "组件地址")
    private String component;
    /**
     * 路由地址
     */
    @Excel(name = "路由地址")
    private String path;

    /**
     * 是否为外链(1是,0否)
     */
    @Excel(name = "是否为外链",readConverterExp = "1=是外链,0=不是外链")
    private Integer isFrame;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @Excel(name = "菜单类型",readConverterExp = "M=目录,C=菜单,F=按钮")
    private Integer menuType;
    /**
     *  菜单状态(1显示,0隐藏)
     */
    @Excel(name = "菜单状态",readConverterExp = "0=隐藏,1=显示")
    private Integer status;
    /**
     * 权限标识
     */
    @Excel(name = "权限标识")
    private String perms;
    /**
     * 菜单图标
     */
    @Excel(name = "菜单图标")
    private String icon;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
    /**
     * 序号
     */
    @Excel(name = "序号")
    private Integer seq;
    /**
     * 子菜单
     */
    private List<Menu> children = new ArrayList<>();
}
