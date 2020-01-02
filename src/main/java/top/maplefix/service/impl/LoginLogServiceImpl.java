package top.maplefix.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.constant.PageConstant;
import top.maplefix.mapper.LoginLogMapper;
import top.maplefix.model.LoginLog;
import top.maplefix.service.ILoginLogService;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.UuidUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 登录日志操作接口实现类
 * @date : Created in 2019/7/24 22:57
 * @version : v2.1
 */
@Service
public class LoginLogServiceImpl implements ILoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public List<LoginLog> getLoginLogPage(Map<String, Object> params) {
        int currPage = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGENUM));
        int pageSize = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGESIZE));
        //登录名
        String loginName = top.maplefix.utils.StringUtils.getObjStr(params.get("loginName"));
        //登录IP
        String loginIp = top.maplefix.utils.StringUtils.getObjStr(params.get("loginIp"));
        //登录状态
        String status = top.maplefix.utils.StringUtils.getObjStr(params.get("status"));
        String beginDate = top.maplefix.utils.StringUtils.getObjStr(params.get("beginDate"));
        String endDate = top.maplefix.utils.StringUtils.getObjStr(params.get("endDate"));
        Example example = new Example(LoginLog.class);
        //根据时间排序
        example.setOrderByClause("loginDate desc");
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(loginName)){
            criteria.andEqualTo("loginName", loginName);
        }
        if(!StringUtils.isEmpty(loginIp)){
            criteria.andEqualTo("loginIp", loginIp);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andEqualTo("status", status);
        }
        if(!StringUtils.isEmpty(beginDate)){
            criteria.andGreaterThan("loginDate", beginDate + " 00:00:00");
        }
        if(!StringUtils.isEmpty(endDate)){
            criteria.andLessThan("loginDate", endDate + " 23:59:59");
        }
        PageHelper.startPage(currPage, pageSize);
        return loginLogMapper.selectByExample(example);
    }


    @Override
    public LoginLog selectById(String loginLogId) {
        LoginLog loginLog = new LoginLog();
        loginLog.setLoginLogId(loginLogId);
        return loginLogMapper.selectByPrimaryKey(loginLog);
    }

    @Override
    public void saveLoginLog(LoginLog loginLog) {
        loginLog.setLoginLogId(UuidUtils.getRandomUuidWithoutSeparator());
        loginLogMapper.insert(loginLog);
    }

    @Override
    public void updateLoginLog(LoginLog loginLog) {
        loginLogMapper.updateByPrimaryKeySelective(loginLog);
    }

    @Override
    public void deleteBatch(String[] loginLogIds) {
        for(String loginLogId: loginLogIds){
            LoginLog loginLog = new LoginLog();
            loginLog.setLoginLogId(loginLogId);
            loginLogMapper.delete(loginLog);
        }
    }

    @Override
    public List<LoginLog> selectLoginLogByIds(String[] ids) {
        //如果前端没选中列表数据则全部导出
        if(null == ids || ids.length == 0){
            Example example = new Example(LoginLog.class);
            example.setOrderByClause("loginDate desc");
            return loginLogMapper.selectByExample(example);
        }
        //将数组转成字符串，用逗号隔开
        String idsStr = StringUtils.join(ids,",");
        return loginLogMapper.selectByIds(idsStr);
    }
}
