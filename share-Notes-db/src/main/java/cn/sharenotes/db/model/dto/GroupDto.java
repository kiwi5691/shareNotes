package cn.sharenotes.db.model.dto;

import cn.sharenotes.db.domain.User;

import lombok.Data;

/**
 * Category group dto.
 *
 * @author hu
 */
@Data
public class GroupDto  {
    private String nickname;
    private String avatar;
    private int id;
    public GroupDto(){
    }
    public GroupDto(User user){
        nickname = user.getNickname();
        avatar = user.getAvatar();
        id = user.getId();
    }

    @Override
    public String toString() {
        return "{" +
                ", id=" + id +
                "nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +

                '}';
    }
}
