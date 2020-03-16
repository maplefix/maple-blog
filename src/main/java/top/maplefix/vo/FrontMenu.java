package top.maplefix.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Maple
 * @description 前端菜单
 * @date 2020/3/13 11:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontMenu implements Serializable {
    /**
     * 显示名
     */
    private String title;
    /**
     * 显示顺序
     */
    private Integer order;
    /**
     * 是否当前窗口打开,true表示当前窗口打开
     */
    private Boolean target;
    /**
     * 路径
     */
    private String url;
}
