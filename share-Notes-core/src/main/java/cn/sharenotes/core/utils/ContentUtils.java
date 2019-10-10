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

    public static String getDescription(String iconSelected){
        if (iconSelected.equals("activity")) {
            return   "活动";
        } else if (iconSelected.equals("barrage")) {
            return   "手记";
        } else {
            return   "内容";
        }
    }
}
