package cn.sharenotes.core.utils;

/**
 * @auther kiwi
 * @Date 2019/10/7 19:12
 */
public class CategoryUtils {
    public static Integer chekcIsPcOrPr(boolean isPcOrPr){
        if (isPcOrPr) {
            return  1;
        } else {
            return  2;
        }
    }

    public static String chekciconSelected(String iconSelected){
        if (iconSelected.equals("活动")) {
            return   "activity";
        } else if (iconSelected.equals("手记")) {
            return   "barrage";
        } else {
            return   "brush";
        }
    }
}
