package cn.sharenotes.core.enums;

import org.springframework.lang.Nullable;

/**
 * Input type enum.
 *
 * @author kiwi
 */
public enum InputType {

    TEXT,

    NUMBER,

    RADIO,

    SELECT,

    TEXTAREA,

    COLOR,

    ATTACHMENT;

    /**
     * Convert type to input type.
     *
     * @param type input type
     * @return corresponding input type or TEXT if the given type is missing or mismatch
     */
    public static InputType typeOf(@Nullable Object type) {
        if (type != null) {
            for (InputType inputType : values()) {
                if (inputType.name().equalsIgnoreCase(type.toString())) {
                    return inputType;
                }
            }
        }
        return TEXT;
    }

}
