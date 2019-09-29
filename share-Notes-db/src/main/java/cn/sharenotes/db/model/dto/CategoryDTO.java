package cn.sharenotes.db.model.dto;

import cn.sharenotes.db.domain.Categories;
import cn.sharenotes.db.model.dto.base.OutputConverter;
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
public class CategoryDTO implements OutputConverter<CategoryDTO, Categories> {

    private Integer id;

    private String name;

    private String slugName;

    private String description;

    private Integer parentId;

    private String createTime;

    private String updateTime;

}
