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

    public static Integer checkMenu_id(String menu_id){
        if (menu_id.equals("tab1")) {
            return  1;
        } else {
            return  2;
        }
    }

    public static Boolean getMenuBoolean(String isPcOrPr){
        return isPcOrPr.equals("1");
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
