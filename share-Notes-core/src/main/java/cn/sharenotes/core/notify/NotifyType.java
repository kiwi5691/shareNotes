package cn.sharenotes.core.notify;

public enum NotifyType {
    PAY_SUCCEED("mail"),
    SHIP("template");

    NotifyType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return this.type;
    }
}
