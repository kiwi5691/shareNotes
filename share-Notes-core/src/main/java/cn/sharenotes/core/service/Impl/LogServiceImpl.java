package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.service.LogService;
import cn.sharenotes.db.domain.Logs;
import cn.sharenotes.db.mapper.LogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 76905
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogsMapper logsMapper;

    @Override
    public Integer addLog(Logs logs) {
        return logsMapper.insert(logs);
    }
}
