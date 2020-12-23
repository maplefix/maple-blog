package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.mapper.NoticeMapper;
import top.maplefix.model.Notice;
import top.maplefix.service.NoticeService;
import top.maplefix.utils.ConvertUtils;

import java.util.List;

/**
 * @author Maple
 * @description 通知公告service实现类
 * @date 2020/3/20 10:49
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Notice selectNoticeById(Long  noticeId) {
        return noticeMapper.selectNoticeById(noticeId);
    }

    @Override
    public List<Notice> selectNoticeList(Notice notice) {
        return noticeMapper.selectNoticeList(notice);
    }

    @Override
    public int insertNotice(Notice notice) {
        return noticeMapper.insertNotice(notice);
    }

    @Override
    public int updateNotice(Notice notice) {
        return noticeMapper.updateNotice(notice);
    }

    @Override
    public int deleteNoticeByIds(String ids) {
        return noticeMapper.deleteNoticeByIds(ConvertUtils.toStrArray(ids));
    }
}
