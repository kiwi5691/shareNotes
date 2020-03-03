package cn.sharenotes.core.notify;

public enum NotifyType {
    ISSUE("issue"),
    RESOURCE("resource");

    NotifyType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return this.type;
    }
}
