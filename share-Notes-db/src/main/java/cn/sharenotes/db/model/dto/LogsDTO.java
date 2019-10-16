package cn.sharenotes.db.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author 76905
 */
@Data
public class LogsDTO {

    private Date createTime;

    private String content;
}
