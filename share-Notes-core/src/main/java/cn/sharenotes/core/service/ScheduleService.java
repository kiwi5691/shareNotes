package cn.sharenotes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author 76905
 */
@Service
public class ScheduleService {

    @Autowired
    LogsService logsService;

    @Scheduled(cron = "0 0 23 ? * 2,4,6")
    public void deleteAllLogs(){
        logsService.deleteAllLogs();
    }
}
