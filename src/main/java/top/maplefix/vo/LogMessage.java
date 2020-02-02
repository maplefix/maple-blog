package top.maplefix.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : Maple
 * @description : dashboard展示的最新新消息
 * @date : 2019/9/15 18:03
 * @version : v1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogMessage {
    /**
     * Date String ,如“刚刚”等
     */
    String dateStr;
    /**
     * 具体时间
     */
    String date;
    /**
     * 消息
     */
    String message;

}
