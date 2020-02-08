package cn.sharenotes.db.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 76905
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostContentVo implements Serializable {

    private Integer createFrom;

    private String title;

    private Integer type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String formatContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String originalContent;

    private Integer disallowComment;

}
