package cn.sharenotes.db.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode
public class PostCommentDto {
    private String title;
    private Date createTime;
    private Date updateTime;
    private Integer type;
    private String originalContent;
    private Long visits;
    private Integer disallowComment;
    List<CommentDto> commentDtoList;
}
