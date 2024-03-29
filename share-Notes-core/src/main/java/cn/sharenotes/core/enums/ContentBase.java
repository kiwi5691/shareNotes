package cn.sharenotes.core.enums;

/**
 * Comment status.
 *
 * @author kiwi
 */
public enum ContentBase implements ValueEnum<Integer> {

    /**
     * 已发布
     */
    PUBLISHED(0),

    LIMITWORDS(5),

    LIMITTIMES(10),

    LIMITTIMESINFO(3),

    PRIVATEID(2),

    PUBLICID(1),

    ALLOWACCESS(0),

    NOACCESS(1),
    /**
     * Auditing status.
     */
    AUDITING(1),

    Annoymous(0),
    NotAnnoymous(1),
    /**
     * 回收站
     */
    RECYCLE(2);

    private final Integer value;

    ContentBase(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
