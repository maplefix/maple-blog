package top.maplefix.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.constant.PageConstant;
import top.maplefix.mapper.VisitLogMapper;
import top.maplefix.model.VisitLog;
import top.maplefix.service.IVisitLogService;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.UuidUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 访问日志接口实现类
 * @date : Created in 2019/7/24 23:01
 * @version : v2.1
 */
@Service
public class VisitLogServiceImpl implements IVisitLogService {

    @Autowired
    private VisitLogMapper visitLogMapper;

    @Override
    public List<VisitLog> getVisitLogPage(Map<String, Object> params) {
        int currPage = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGENUM));
        int pageSize = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGESIZE));
        //访问模块名
        String module = top.maplefix.utils.StringUtils.getObjStr(params.get("module"));
        //访问人IP地址
        String visitIp = top.maplefix.utils.StringUtils.getObjStr(params.get("visitIp"));
        String status = top.maplefix.utils.StringUtils.getObjStr(params.get("status"));
        String beginDate = top.maplefix.utils.StringUtils.getObjStr(params.get("beginDate"));
        String endDate = top.maplefix.utils.StringUtils.getObjStr(params.get("endDate"));
        Example example = new Example(VisitLog.class);
        //根据时间排序
        example.setOrderByClause("visitDate desc");
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(module)){
            criteria.andEqualTo("module", module);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andEqualTo("status", status);
        }
        if(!StringUtils.isEmpty(visitIp)){
            criteria.andEqualTo("visitIp", visitIp);
        }
        if(!StringUtils.isEmpty(beginDate)){
            criteria.andGreaterThan("visitDate", beginDate + " 00:00:00");
        }
        if(!StringUtils.isEmpty(endDate)){
            criteria.andLessThan("visitDate", endDate + " 23:59:59");
        }
        PageHelper.startPage(currPage, pageSize);
        return visitLogMapper.selectByExample(example);
    }

    @Override
    public VisitLog selectById(String visitLogId) {
        VisitLog visitLog = new VisitLog();
        visitLog.setVisitLogId(visitLogId);
        return visitLogMapper.selectByPrimaryKey(visitLog);
    }

    @Override
    public void saveVisitLog(VisitLog visitLog) {
        visitLog.setVisitLogId(UuidUtils.getRandomUuidWithoutSeparator());
        visitLogMapper.insert(visitLog);
    }

    @Override
    public void updateVisitLog(VisitLog visitLog) {
        visitLogMapper.updateByPrimaryKeySelective(visitLog);
    }

    @Override
    public void deleteBatch(String[] visitLogIds) {
        for (String visitLogId : visitLogIds){
            VisitLog visitLog = new VisitLog();
            visitLog.setVisitLogId(visitLogId);
            visitLogMapper.delete(visitLog);
        }
    }

    @Override
    public List<VisitLog> selectVisitLogByIds(String[] ids) {
        //如果前端没选中列表数据则全部导出
        if(null == ids || ids.length == 0){
            Example example = new Example(VisitLog.class);
            example.setOrderByClause("visitDate desc");
            return visitLogMapper.selectByExample(example);
        }
        //将数组转成字符串，用逗号隔开
        String idsStr = StringUtils.join(ids,",");
        return visitLogMapper.selectByIds(idsStr);
    }
}
