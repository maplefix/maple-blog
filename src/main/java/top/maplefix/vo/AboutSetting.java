package top.maplefix.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Maple
 * @description 关于我设置
 * @date 2020/1/22 20:27
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AboutSetting {
    private String content;
    private String htmlContent;
}
