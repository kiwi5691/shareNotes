package cn.sharenotes.core.service;

import cn.sharenotes.db.domain.UserFormid;

import java.time.LocalDateTime;

public interface UserFormIdService {
     UserFormid queryByOpenId(String openId);
     void updateUserFormId(UserFormid userFormid) ;
     void addUserFormId(UserFormid userFormid);

}
