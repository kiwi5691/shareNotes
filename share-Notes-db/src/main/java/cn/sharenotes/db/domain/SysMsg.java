package cn.sharenotes.db.domain;

import java.sql.Timestamp;
import java.util.Date;

public class SysMsg {
    private Long id;

    private Integer recId;

    private Integer sendId;

    private String email;

    private String title;

    private Integer type;

    private Date postTime;
public SysMsg(){

}

    public SysMsg(Integer recId, Integer sendId, String email, String title, Integer type, Date postTime) {
        this.recId = recId;
        this.sendId = sendId;
        this.email = email;
        this.title = title;
        this.type = type;
        this.postTime = postTime;
    }

    public SysMsg(int recId, Integer sendId, String title, Integer type, Date postTime) {
        this.recId = recId;
        this.sendId = sendId;

        this.title = title;
        this.type = type;
        this.postTime = postTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRecId() {
        return recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
}