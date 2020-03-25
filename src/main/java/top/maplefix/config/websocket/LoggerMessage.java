package top.maplefix.config.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Maple
 * @description : 日志message
 * @date : 2020/1/18 16:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggerMessage {
    private String body;
    private String timestamp;
    private String threadName;
    private String className;
    private String level;
    private String exception;
    private String cause;
}
