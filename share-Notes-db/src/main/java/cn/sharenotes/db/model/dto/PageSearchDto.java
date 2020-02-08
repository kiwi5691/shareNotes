package cn.sharenotes.db.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageSearchDto {
    private Integer page;
    private Integer pageSize;
    private String searchKeyword;
}
