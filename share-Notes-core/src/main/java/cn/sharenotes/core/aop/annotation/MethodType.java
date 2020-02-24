package cn.sharenotes.core.aop.annotation;

public enum MethodType {
    SAVE("save"),
    UPDATE("update"),
    DELETES("deletes"),
    DELETE("delete");

    private String name;

    MethodType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
