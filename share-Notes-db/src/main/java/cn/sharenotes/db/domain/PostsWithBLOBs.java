package cn.sharenotes.db.domain;

public class PostsWithBLOBs extends Posts {
    private String formatContent;

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