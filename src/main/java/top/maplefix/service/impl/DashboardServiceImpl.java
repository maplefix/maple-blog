package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.mapper.DashboardMapper;
import top.maplefix.model.LoginLog;
import top.maplefix.model.OperationLog;
import top.maplefix.model.VisitLog;
import top.maplefix.service.IDashboardService;
import top.maplefix.utils.DateUtils;
import top.maplefix.vo.LogMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : Maple
 * @description :
 * @date : Created in 2019/9/15 18:08
 * @version : v2.1
 */
@Service
public class DashboardServiceImpl implements IDashboardService {

    @Autowired
    private DashboardMapper dashboardMapper;

    @Override
    public List<LogMessage> selectLogMessage() {
        List<LogMessage> messages = new ArrayList<>();
        //登录日志
        List<LoginLog> loginLogList = dashboardMapper.getLoginLogData();
        //操作日志
        List<OperationLog> operationLogList = dashboardMapper.getOperationLogData();
        //访问日志
        List<VisitLog> visitLogList = dashboardMapper.getVisitLogData();

        if (loginLogList != null && loginLogList.size() != 0) {
            for (LoginLog loginLog : loginLogList) {
                LogMessage temp = new LogMessage();
                temp.setDateStr(DateUtils.showTime(DateUtils.parseDate(loginLog.getLoginDate())));
                temp.setDate(loginLog.getLoginDate());
                temp.setMessage(loginLog.getLoginName() + " 登录了系统");
                messages.add(temp);
            }
        }

        if (operationLogList != null && operationLogList.size() != 0) {
            for (OperationLog operationLog : operationLogList) {
                LogMessage temp = new LogMessage();
                temp.setDateStr(DateUtils.showTime(DateUtils.parseDate(operationLog.getOperDate())));
                temp.setDate(operationLog.getOperDate());
                temp.setMessage(operationLog.getOperName() + " 对 " + operationLog.getModule() + " 进行了 " + operationLog.getBusinessType() + " 操作");
                messages.add(temp);
            }
        }
        //访问日志
        if (visitLogList != null && visitLogList.size() != 0) {
            for (VisitLog visitLog : visitLogList) {
                LogMessage temp = new LogMessage();
                temp.setDate(visitLog.getVisitDate());
                temp.setDateStr(DateUtils.showTime(DateUtils.parseDate(visitLog.getVisitDate())));
                temp.setMessage(visitLog.getVisitIp() + " 访问了 " + visitLog.getModule() + " 模块");
                messages.add(temp);
            }
        }

        Collections.sort(messages, (logMessage1, logMessage2) -> {
            long time = DateUtils.parseDate(logMessage1.getDate()).getTime() - DateUtils.parseDate(logMessage2.getDate()).getTime();
            if (time < 0) {
                return 1;
            } else if (time > 0) {
                return -1;
            } else {
                return 0;
            }
        });
        return messages;
    }

}
