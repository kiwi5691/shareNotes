package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.service.LogsService;
import cn.sharenotes.db.domain.Logs;
import cn.sharenotes.db.domain.LogsExample;
import cn.sharenotes.db.mapper.LogsMapper;
import cn.sharenotes.db.model.dto.LogsDTO;
import cn.sharenotes.db.utils.DateUtil;
import cn.sharenotes.db.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 76905
 */
@Service
public class LogsServiceImpl implements LogsService {

    @Autowired
    LogsMapper logsMapper;

    @Override
    public List<LogsDTO> getAllLogsByUserId(Integer userId) {
        LogsExample logsExample = new LogsExample();
        LogsExample.Criteria criteria = logsExample.createCriteria();
        criteria.andUserIdEqualTo(userId.toString());
        List<Logs> logs = logsMapper.selectByExample(logsExample);
        return DtoUtils.convertList2List(logs,LogsDTO.class);
    }

    @Override
    public Integer deleteAllLogs(){
        return logsMapper.deleteAll(DateUtil.dateBeforeThreeDays());
    }

}
