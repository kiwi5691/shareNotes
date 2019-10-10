package cn.sharenotes.db.domain;

import java.sql.Timestamp;
import java.util.Date;

public class Comments {

    private Integer type;

    private Long id;

    private Timestamp createTime;

    private Timestamp updateTime;

    private String author;

    private String content;

    private Integer isAnonymous;

    private Integer postId;

    private Integer status;

    private Integer topPriority;

    private Integer userId;

    public Comments(Integer type, Long id, Timestamp createTime, Timestamp updateTime, String author, String content, Integer isAnonymous, Integer postId, Integer status, Integer topPriority, Integer userId) {
        this.type = type;
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.author = author;
        this.content = content;
        this.isAnonymous = isAnonymous;
        this.postId = postId;
        this.status = status;
        this.topPriority = topPriority;
        this.userId = userId;
    }

    public Comments(Integer type, Timestamp createTime, Timestamp updateTime, String author, String content, Integer isAnonymous, Integer postId, Integer status, Integer topPriority, Integer userId) {
        this.type = type;

        this.createTime = createTime;
        this.updateTime = updateTime;
        this.author = author;
        this.content = content;
        this.isAnonymous = isAnonymous;
        this.postId = postId;
        this.status = status;
        this.topPriority = topPriority;
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTopPriority() {
        return topPriority;
    }

    public void setTopPriority(Integer topPriority) {
        this.topPriority = topPriority;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }





    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", isAnonymous=" + isAnonymous +
                ", postId=" + postId +
                ", status=" + status +
                ", topPriority=" + topPriority +
                ", userId=" + userId +
                '}';
    }
}