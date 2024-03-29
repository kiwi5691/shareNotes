package cn.sharenotes.db.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author 76905
 */
@Data
public class LogsDTO {

    @JsonFormat(pattern = "HH:mm:ss")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private String content;
}
