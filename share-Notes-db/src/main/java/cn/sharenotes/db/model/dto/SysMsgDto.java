package cn.sharenotes.db.model.dto;

import cn.sharenotes.db.domain.SysMsg;

public class SysMsgDto  {
    private String avatar;
    private  SysMsg sysMsg;
    public SysMsgDto(){

    }
    public SysMsg getSysMsg() {
        return sysMsg;
    }

    public void setSysMsg(SysMsg sysMsg) {
        this.sysMsg = sysMsg;
    }

    public SysMsgDto(String avatar, SysMsg sysMsg) {
        this.avatar = avatar;
        this.sysMsg = sysMsg;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
