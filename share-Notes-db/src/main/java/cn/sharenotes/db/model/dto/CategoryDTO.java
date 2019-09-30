package cn.sharenotes.db.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * Category output dto.
 *
 * @author kiwi
 */
@Data
@ToString
@EqualsAndHashCode
public class CategoryDTO{

    private String name;

    private Date createTime;

    private Date updateTime;

}
