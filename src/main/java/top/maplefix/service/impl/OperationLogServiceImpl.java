package top.maplefix.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.constant.PageConstant;
import top.maplefix.mapper.OperationLogMapper;
import top.maplefix.model.OperationLog;
import top.maplefix.service.IOperationLogService;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.UuidUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 操作日志接口实现类
 * @date : Created in 2019/7/24 22:59
 * @version : v2.1
 */
@Service
public class OperationLogServiceImpl implements IOperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public List<OperationLog> getOperationLogPage(Map<String, Object> params) {
        int currPage = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGENUM));
        int pageSize = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGESIZE));
        //模块名
        String module = top.maplefix.utils.StringUtils.getObjStr(params.get("module"));
        //操作人
        String operName = top.maplefix.utils.StringUtils.getObjStr(params.get("operName"));
        String status = top.maplefix.utils.StringUtils.getObjStr(params.get("status"));
        //业务类型
        String businessType = top.maplefix.utils.StringUtils.getObjStr(params.get("businessType"));
        String beginDate = top.maplefix.utils.StringUtils.getObjStr(params.get("beginDate"));
        String endDate = top.maplefix.utils.StringUtils.getObjStr(params.get("endDate"));
        Example example = new Example(OperationLog.class);
        //根据时间排序
        example.setOrderByClause("operDate desc");
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(module)){
            criteria.andEqualTo("module", module);
        }
        if(!StringUtils.isEmpty(operName)){
            criteria.andEqualTo("operName", operName);
        }
        if(!StringUtils.isEmpty(businessType)){
            criteria.andEqualTo("businessType", businessType);
        }
        if(!StringUtils.isEmpty(beginDate)){
            criteria.andGreaterThan("operDate", beginDate + " 00:00:00");
        }
        if(!StringUtils.isEmpty(endDate)){
            criteria.andLessThan("operDate", endDate + " 23:59:59");
        }
        PageHelper.startPage(currPage, pageSize);
        return operationLogMapper.selectByExample(example);
    }

    @Override
    public OperationLog selectById(String operationLogId) {
        OperationLog operationLog = new OperationLog();
        operationLog.setOperId(operationLogId);
        return operationLogMapper.selectByPrimaryKey(operationLog);
    }

    @Override
    public void saveOperationLog(OperationLog operationLog) {
        operationLog.setOperId(UuidUtils.getRandomUuidWithoutSeparator());
        operationLogMapper.insert(operationLog);
    }

    @Override
    public void updateOperationLog(OperationLog operationLog) {
        operationLogMapper.updateByPrimaryKeySelective(operationLog);
    }

    @Override
    public void deleteBatch(String[] operationLogIds) {
        for(String operationLogId: operationLogIds){
            OperationLog operationLog = new OperationLog();
            operationLog.setOperId(operationLogId);
            operationLogMapper.delete(operationLog);
        }
    }

    @Override
    public List<OperationLog> selectOperationLogByIds(String[] ids) {
        //如果前端没选中列表数据则全部导出
        if(null == ids || ids.length == 0){
            Example example = new Example(OperationLog.class);
            example.setOrderByClause("operDate desc");
            return operationLogMapper.selectByExample(example);
        }
        //将数组转成字符串，用逗号隔开
        String idsStr = StringUtils.join(ids,",");
        return operationLogMapper.selectByIds(idsStr);
    }
}
