package cn.sharenotes.db.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kiwi
 * @Date 2019/10/10 15:36
 */
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PostTypeDTO implements Serializable {

    private Integer id;

    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String title;

    private String formatContent;

}
