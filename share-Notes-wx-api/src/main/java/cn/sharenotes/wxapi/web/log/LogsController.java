package cn.sharenotes.wxapi.web.log;

import cn.sharenotes.core.service.LogsService;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.model.dto.LogsDTO;
import cn.sharenotes.wxapi.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<LogsDTO> logsDTOS = logsService.getAllLogsByUserId(userId);
        if(CollectionUtils.isEmpty(logsDTOS)){
            return ResponseUtil.fail(501,"没有操作日志");
        }
        Calendar c = Calendar.getInstance();
        result.put("logs",logsDTOS);
        return ResponseUtil.ok(result);
    }

}
