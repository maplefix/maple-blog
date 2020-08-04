package top.maplefix.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.maplefix.mapper.CommentMapper;
import top.maplefix.mapper.LoginLogMapper;
import top.maplefix.mapper.OperateLogMapper;
import top.maplefix.mapper.VisitLogMapper;
import top.maplefix.model.Comment;
import top.maplefix.model.LoginLog;
import top.maplefix.model.OperateLog;
import top.maplefix.model.VisitLog;
import top.maplefix.utils.AddressUtils;
import top.maplefix.utils.StringUtils;

import java.util.List;

/**
 * @author Maple
 * @description update address in comment,visitLog,operateLog,loginLog
 * @date 2020/3/18 15:06
 */
@Slf4j
@Component
public class AddressUpdateTask {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    OperateLogMapper operateLogMapper;
    @Autowired
    LoginLogMapper loginLogMapper;
    @Autowired
    VisitLogMapper visitLogMapper;

    /**
     * update <code>tableName</code>address
     *
     * @param tableName only support comment/visitLog/operateLog/loginLog
     * @return result message
     */
    public String updateAddress(String tableName) {
        String result;
        switch (tableName) {
            case "comment":
                result = updateCommentAddress();
                break;
            case "visitLog":
                result = updateVisitLogAddress();
                break;
            case "operateLog":
                result = updateOperateLogAddress();
                break;
            case "loginLog":
                result = updateLoginLogAddress();
                break;
            default:
                result = "不支持的table: " + tableName;
        }
        return result;
    }

    private String updateLoginLogAddress() {
        List<LoginLog> loginLogList = loginLogMapper.selectLoginLogList(new LoginLog());
        Integer count = 0;
        for (LoginLog loginLog : loginLogList) {
            String ip = loginLog.getIp();
            if (StringUtils.isNotEmpty(ip)) {
                count++;
                String cityInfoByIp = AddressUtils.getCityInfoByIp(ip);
                loginLog.setLocation(cityInfoByIp);
                loginLogMapper.updateLoginLog(loginLog);
            }
        }
        return "LoginLog总数:" + loginLogList.size() + ";更新总数:" + count;
    }

    private String updateOperateLogAddress() {
        List<OperateLog> operateLogList = operateLogMapper.selectOperateLogList(new OperateLog());
        Integer count = 0;
        for (OperateLog operateLog : operateLogList) {
            String ip = operateLog.getIp();
            if (StringUtils.isNotEmpty(ip)) {
                count++;
                String cityInfoByIp = AddressUtils.getCityInfoByIp(ip);
                operateLog.setLocation(cityInfoByIp);
                operateLogMapper.updateOperateLog(operateLog);
            }
        }
        return "OperateLog总数:" + operateLogList.size() + ";更新总数:" + count;
    }

    private String updateVisitLogAddress() {
        List<VisitLog> visitLogList = visitLogMapper.selectVisitLogList(new VisitLog());
        Integer count = 0;
        for (VisitLog visitLog : visitLogList) {
            String ip = visitLog.getIp();
            if (StringUtils.isNotEmpty(ip)) {
                count++;
                String cityInfoByIp = AddressUtils.getCityInfoByIp(ip);
                visitLog.setLocation(cityInfoByIp);
                visitLogMapper.updateVisitLog(visitLog);
            }
        }
        return "VisitLog总数:" + visitLogList.size() + ";更新总数:" + count;
    }

    private String updateCommentAddress() {
        List<Comment> commentList = commentMapper.selectCommentList(new Comment());
        Integer count = 0;
        for (Comment comment : commentList) {
            String ip = comment.getIp();
            if (StringUtils.isNotEmpty(ip)) {
                count++;
                String cityInfoByIp = AddressUtils.getCityInfoByIp(ip);
                comment.setLocation(cityInfoByIp);
                commentMapper.updateComment(comment);
            }
        }
        return "Comment总数:" + commentList.size() + ";更新总数:" + count;
    }
}
