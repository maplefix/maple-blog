package top.maplefix.service;

import top.maplefix.vo.LogMessage;

import java.util.List;

/**
 * @author : Maple
 * @description : 后台Dashboard页面消息查询
 * @date : Created in 2019/9/15 18:06
 * @version : v1.0
 */
public interface IDashboardService {

    /**
     * 后台Dashboard页面消息查询
     * @return
     */
    List<LogMessage> selectLogMessage();
}
