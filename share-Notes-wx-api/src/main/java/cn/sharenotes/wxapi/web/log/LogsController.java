package cn.sharenotes.wxapi.web.log;

import cn.sharenotes.core.service.LogsService;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.LogsDTO;
import cn.sharenotes.db.model.dto.OpLogsDTO;
import cn.sharenotes.wxapi.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 76905
 */
@RestController
@RequestMapping("/wx/logs")
public class LogsController {

    @Autowired
    LogsService logsService;

    @GetMapping("/getAll")
    public Object getAllLogs(@LoginUser Integer userId){
        Map<String,Object> result = new HashMap<>();
        List<LogsDTO> today = new ArrayList<>();
        List<LogsDTO> yesterday = new ArrayList<>();
        List<LogsDTO> tdbYesterday = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        List<LogsDTO> logsDTOS = logsService.getAllLogsByUserId(userId);
        if(CollectionUtils.isEmpty(logsDTOS)){
            return ResponseUtil.fail(501,"没有操作日志");
        }
        for(LogsDTO logsDTO : logsDTOS){
            if(sdf.format(date).equals(sdf.format(logsDTO.getCreateTime()))) {
                today.add(logsDTO);
            }else if(sdf.format(new Date(date.getTime()- 24*3600*1000L)).equals(sdf.format(logsDTO.getCreateTime()))){
                yesterday.add(logsDTO);
            }else if(sdf.format(new Date(date.getTime()- 2*24*3600*1000L)).equals(sdf.format(logsDTO.getCreateTime()))){
                tdbYesterday.add(logsDTO);
            }
        }

        OpLogsDTO todays = new OpLogsDTO(new Date(),today);
        OpLogsDTO yesterdays = new OpLogsDTO(new Date(date.getTime()- 24*3600*1000L),yesterday);
        OpLogsDTO tdbYesterdays = new OpLogsDTO(new Date(date.getTime()- 2*24*3600*1000L),tdbYesterday);

        result.put("today",todays);
        result.put("yesterday",yesterdays);
        result.put("tdbYesterday",tdbYesterdays);
        return ResponseUtil.ok(result);
    }

}
