package cn.sharenotes.core.utils;

/**
 * @author kiwi
 * @date 2019/10/12 19:44
 */
public class MsgUtils {

    public static String getType(Integer type){
        if (type.equals(1)) {
            return   "collection";
        } else if (type.equals(2)) {
            return   "remind";
        } else {
            return   "addressbook";
        }
    }

}
