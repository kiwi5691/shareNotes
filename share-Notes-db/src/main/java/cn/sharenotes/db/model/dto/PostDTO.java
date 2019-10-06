package cn.sharenotes.db.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author kiwi
 * @Date 2019/10/6 12:02
 */
@Data
@ToString
@EqualsAndHashCode
public class PostDTO {
    private Integer id;

    private Integer type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String title;

    private String formatContent;

}
