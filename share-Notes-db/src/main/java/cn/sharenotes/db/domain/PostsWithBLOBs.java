package cn.sharenotes.db.domain;

import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@ToString
@Document(indexName = "posts",type = "docs", shards = 5, replicas = 1)
public class PostsWithBLOBs extends Posts {
    @Field(index = false, type = FieldType.Keyword)
    private String formatContent;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String originalContent;

    public String getFormatContent() {
        return formatContent;
    }

    public void setFormatContent(String formatContent) {
        this.formatContent = formatContent == null ? null : formatContent.trim();
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent == null ? null : originalContent.trim();
    }
}