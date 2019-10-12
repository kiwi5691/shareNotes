package cn.sharenotes.db.model.dto;

import lombok.Data;

/**
 * @author kiwi
 * @date 2019/10/12 19:52
 */
@Data
public class MsgDetailDTO {
    private Integer post_id;
    //根据type获取消息 1 您收到评论了  2 你的好友{name} 把你删除了//  添加 name 成功 3 系统通知！
    private String Content;
    //笔记1：写得酷哦
    private String Comment;

}
