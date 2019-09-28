package cn.sharenotes.core.enums;

/**
 * Log type.
 *
 * @author kiwi
 */
public enum LogType implements ValueEnum<Integer> {

    POST_PUBLISHED(5),
    POST_EDITED(15),
    POST_DELETED(20),
    PROFILE_UPDATED(25),
    SHEET_PUBLISHED(30),
    SHEET_EDITED(35),
    SHEET_DELETED(40);

    private final Integer value;

    LogType(Integer value) {
        this.value = value;
    }


    @Override
    public Integer getValue() {
        return value;
    }
}
