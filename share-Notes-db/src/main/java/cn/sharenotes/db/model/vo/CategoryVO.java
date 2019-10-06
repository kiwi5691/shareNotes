package cn.sharenotes.db.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 76905
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {

    private String slugName;

    private Integer parentId;

    private String name;

    private String description;

}
