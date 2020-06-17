package top.maplefix.mapper;

import org.apache.ibatis.annotations.Param;
import top.maplefix.model.JobLog;
import top.maplefix.model.LoginLog;
import top.maplefix.model.OperateLog;
import top.maplefix.model.VisitLog;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 访问日志mapper
 * @date : 2020/1/15 0:34
 */
public interface DashboardMapper{

    /**
     * 获取访问数量
     *
     * @return 访问数量
     */
    Long getVisitorCount();

    /**
     * 获取博客数量
     *
     * @return 博客数量
     */
    Long getBlogCount();

    /**
     * 获取图书数量
     *
     * @return 图书数量
     */
    Long getBookCount();

    /**
     * 获取笔记数量
     *
     * @return 笔记数量
     */
    Long getNoteCount();

    /**
     * 根据创建时间获取访问日志
     *
     * @param date current day string. eg:2019-08-08
     * @return count
     */
    Long getVisitorCountByCreateDate(@Param("date") String date);

    /**
     * get note count by createTime
     *
     * @param day current day string. eg:2019-08-08
     * @return count
     */
    Long getNoteCountByCreateDate(String day);

    /**
     * get blog count by createTime
     *
     * @param day current day string. eg:2019-08-08
     * @return count
     */
    Long getBlogCountByCreateDate(String day);

    /**
     * get book count by createTime
     *
     * @param day current day string. eg:2019-08-08
     * @return count
     */
    Long getBookCountByCreateDate(String day);

    List<Map<String, Long>> getSpiderData();

    /**
     * 获取visitLog
     *
     * @return visitLog
     */
    List<VisitLog> getVisitLog();

    /**
     * 根据Id获取blog的title
     *
     * @param pageId blog的id
     * @return blog的title
     */
    String getBlogNameByPageId(String pageId);

    /**
     * 获取登录日志
     *
     * @return 登录日志
     */
    List<LoginLog> getLoginLogList();

    /**
     * 获取操作日志
     *
     * @return 操作日志
     */
    List<OperateLog> getOperateLogList();

    /**
     * 获取任务日志
     *
     * @return 任务日志
     */
    List<JobLog> getJobLogList();
}
