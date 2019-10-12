package cn.sharenotes.db.model.vo;

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

    private String formatContent;

    private String originalContent;

    private Integer disallowComment;

}
