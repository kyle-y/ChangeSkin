package com.example.administrator.changeskin.attr;

import java.util.Arrays;
import java.util.List;

/**
 * 皮肤框架的常量定义
 */
public class SkinConstant {

    public static class ResTypes{
        /**属性值对应的类型是color*/
        public static final String RES_TYPE_NAME_COLOR = "color";

        /**属性值对应的类型是drawable*/
        public static final String RES_TYPE_NAME_DRAWABLE = "drawable";

        /**属性值对应的类型是mipmap*/
        public static final String RES_TYPE_NAME_MIPMAP = "mipmap";
    }

    public static class SupportTypes{
        public static final String BACKGROUND = "background";
        public static final String SRC = "src";
        public static final String DRAWABLE_LEFT = "drawableLeft";
        public static final String TEXT_COLOR = "textColor";
        public static final String TEXT_COLOR_HINT = "textColorHint";
        public static final String LIST_SELECTOR = "listSelector";
        public static final String DIVIDER = "divider";


        public static final String[] types = {BACKGROUND, SRC, DRAWABLE_LEFT, TEXT_COLOR, TEXT_COLOR_HINT, LIST_SELECTOR, DIVIDER};
        public static final List<String> supportTypeList = Arrays.asList(types);
    }




}
