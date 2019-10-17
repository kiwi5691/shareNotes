package cn.sharenotes.db.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author kiwi
 * @date 2019/10/17 16:37
 */
@Data
@AllArgsConstructor
public class OpLogsDTO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private List<LogsDTO> logsDTOS;
}
