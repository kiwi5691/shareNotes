package cn.sharenotes.db.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    private String createTime;

    private String updateTime;

}
