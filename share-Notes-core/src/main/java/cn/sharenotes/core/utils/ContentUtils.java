package cn.sharenotes.core.utils;

/**
 * @auther kiwi
 * @Date 2019/10/7 19:12
 */
public class ContentUtils {

    public static String getType(Integer type){
        if (type.equals(1)) {
            return   "html";
        } else {
            return   "markdown";
        }
    }

    public static Boolean getTypeInBoolean(Integer type){
        if (type.equals(1)) {
            return   true;
        } else {
            return   false;
        }
    }
}
