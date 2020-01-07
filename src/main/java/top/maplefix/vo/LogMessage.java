package top.maplefix.vo;

import lombok.Data;

/**
 * @author : Maple
 * @description : dashboard展示的最新新消息
 * @date : Created in 2019/9/15 18:03
 * @version : v1.0
 */
@Data
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
