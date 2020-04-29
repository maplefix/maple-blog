package top.maplefix.service;

import top.maplefix.model.Notice;

import java.util.List;

/**
 * @author Maple
 * @description 通知公告service
 * @date 2020/3/20 10:49
 */
public interface NoticeService {

    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    Notice selectNoticeById(String noticeId);

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    List<Notice> selectNoticeList(Notice notice);

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    int insertNotice(Notice notice);

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    int updateNotice(Notice notice);

    /**
     * 删除公告信息
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    int deleteNoticeByIds(String noticeId);
}
