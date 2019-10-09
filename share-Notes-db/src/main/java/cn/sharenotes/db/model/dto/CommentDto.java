package cn.sharenotes.db.model.dto;

import cn.sharenotes.db.domain.Comments;
import cn.sharenotes.db.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
@Data
@ToString
@EqualsAndHashCode
@Slf4j
public class CommentDto {
    private long id;
    private String avatar;
    private  String author;
    private  String content;
    private Date createTime;
    private boolean isAnonymous;

    public CommentDto(Comments comments, User user){
        this.id = comments.getId();

        this.avatar = user.getAvatar();
        this.author = user.getNickname();
        this.content =comments.getContent();
        this.createTime =comments.getCreateTime() ;
        if(comments.getIsAnonymous()==0){
            this.isAnonymous = true;
        }else {
            this.isAnonymous = false;
        }

    }

    public CommentDto(){
    }
}
