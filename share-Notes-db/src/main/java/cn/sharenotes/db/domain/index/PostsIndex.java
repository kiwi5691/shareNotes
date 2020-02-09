package cn.sharenotes.db.domain.index;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Document(indexName = "posts",type = "docs", shards = 1, replicas = 0)
public class PostsIndex implements Serializable {
    private static final long serialVersionUID = 6320548148250372651L;

    @Id
    private Integer id;

    @Field(type = FieldType.Text)
    private String formatContent;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String originalContent;


    @Field(type = FieldType.Keyword)
    private Integer type;

    @Field(type = FieldType.Keyword)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    @Field(type = FieldType.Keyword)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private Integer createFrom;

    @Field(index = false, type = FieldType.Keyword)
    private Integer disallowComment;

    @Field(type = FieldType.Keyword)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editTime;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @Field(index = false, type = FieldType.Keyword)
    private Integer topPriority;

    @Field(index = false, type = FieldType.Keyword)
    private Long visits;


    public String getFormatContent() {
        return formatContent;
    }

    public void setFormatContent(String formatContent) {
        this.formatContent = formatContent;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

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
        this.title = title;
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
