package cn.sharenotes.core.enums;

/**
 * @auther kiwi
 * @Date 2019/10/5 1:09
 */
public enum  HTMLParseType {

    activity("html","1"),

    barrage("markdown","2");

    private String typeName;
    private String typeCode;

    private HTMLParseType(String typeName, String typeCode) {
        this.typeName = typeName;
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
