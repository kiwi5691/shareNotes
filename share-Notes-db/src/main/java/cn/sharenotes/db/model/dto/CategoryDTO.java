package cn.sharenotes.db.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Category output dto.
 *
 * @author 76905
 */
@Data
@EqualsAndHashCode
public class CategoryDTO  implements Serializable {
    private Integer id;

    private String name;

    private String description;

    private Date createTime;

    private Date updateTime;

}
