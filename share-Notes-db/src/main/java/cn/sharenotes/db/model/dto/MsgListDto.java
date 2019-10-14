package cn.sharenotes.db.model.dto;

public class MsgListDto {
    Long msgId;
    String type;
    String content;


    public MsgListDto(Long id, String type, String content) {
        this.msgId = id;
        this.type = type;
        this.content = content;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
