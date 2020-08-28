package top.maplefix.config.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : Maple
 * @description : 日志message
 * @date : 2020/1/18 16:35
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoggerMessage {
    private String body;
    private String timestamp;
    private String threadName;
    private String className;
    private String level;
    private String exception;
    private String cause;
}
