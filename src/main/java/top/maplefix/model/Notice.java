package top.maplefix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Maple
 * @description 通知公告实体类
 * @date 2020/1/22 20:35
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Notice extends BaseEntity implements Serializable {

    /**
     * 公告ID
     */
    private Long id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告类型（1通知 2公告）
     */
    private Integer type;

    /**
     * 公告内容
     */
    private String content;
    /**
     * Html内容
     */
    private String htmlContent;

    /**
     * 公告状态（1正常 0关闭）
     */
    private String status;
}
