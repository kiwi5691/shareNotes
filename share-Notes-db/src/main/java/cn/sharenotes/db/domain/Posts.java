package cn.sharenotes.db.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
@Document(indexName = "posts",type = "docs", shards = 5, replicas = 1)
public class Posts {
    @Id
    private Integer id;

    @Field(type = FieldType.Keyword)
    private Integer type;

    @Field(type = FieldType.Date)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Field(type = FieldType.Date)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private Integer createFrom;

    @Field(index = false, type = FieldType.Keyword)
    private Integer disallowComment;

    @Field(type = FieldType.Date)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editTime;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @Field(index = false, type = FieldType.Keyword)
    private Integer topPriority;

    @Field(index = false, type = FieldType.Keyword)
    private Long visits;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateFrom() {
        return createFrom;
    }

    public void setCreateFrom(Integer createFrom) {
        this.createFrom = createFrom;
    }

    public Integer getDisallowComment() {
        return disallowComment;
    }

    public void setDisallowComment(Integer disallowComment) {
        this.disallowComment = disallowComment;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getTopPriority() {
        return topPriority;
    }

    public void setTopPriority(Integer topPriority) {
        this.topPriority = topPriority;
    }

    public Long getVisits() {
        return visits;
    }

    public void setVisits(Long visits) {
        this.visits = visits;
    }
}