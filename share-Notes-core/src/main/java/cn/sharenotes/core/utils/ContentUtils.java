package cn.sharenotes.core.utils;

import cn.sharenotes.core.enums.ContentBase;

/**
 * @auther kiwi
 * @Date 2019/10/7 19:12
 */
public class ContentUtils {

    public static Boolean returnTypeInBoolean(Integer type){
        if (type.equals(0)) {
            return  true;
        } else {
            return  false;
        }
    }

    public static String getType(Integer type){
        if (type.equals(1)) {
            return   "html";
        } else {
            return   "markdown";
        }
    }

    public static Boolean getTypeInBoolean(Integer type){
        if (type.equals(0)) {
            return   true;
        } else {
            return   false;
        }
    }
}
