package cn.sharenotes.core.service;

import cn.sharenotes.db.domain.Logs;
import org.springframework.stereotype.Service;

/**
 * @author 76905
 */
@Service
public interface LogService {

    Integer addLog(Logs logs);
}
