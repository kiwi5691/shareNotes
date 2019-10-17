package cn.sharenotes.core.service;

import cn.sharenotes.db.model.dto.LogsDTO;

import java.util.List;

/**
 * @author 76905
 */
public interface LogsService {

    List<LogsDTO> getAllLogsByUserId(Integer userId);

    Integer deleteAllLogs();
}
