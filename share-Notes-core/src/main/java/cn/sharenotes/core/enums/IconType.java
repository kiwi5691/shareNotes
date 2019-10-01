package cn.sharenotes.core.enums;

/**
 * @auther kiwi
 * @Date 2019/10/1 15:22
 */
public enum IconType {

    activity("activity","1"),

    brush("brush","3"),

    barrage("barrage","2");

    private String typeName;
    private String typeCode;

    private IconType(String typeName, String typeCode) {
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
