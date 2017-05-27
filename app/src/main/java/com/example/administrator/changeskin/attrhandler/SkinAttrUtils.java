package com.example.administrator.changeskin.attrhandler;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.example.administrator.changeskin.ResourceManager;
import com.example.administrator.changeskin.attr.SkinConstant;


/**
 * 皮肤属性的辅助帮助类
 * qqliu
 * 2016/9/25.
 */
public class SkinAttrUtils {

    /***
     * 获取指定资源的drawable，支持的resId为int型颜色和drawable
     *
     * @param resourceManager
     * @param resId
     * @param resTypeName 如drawable
     * @param resName     如icon_home
     * @return
     */
    public static Drawable getDrawable(ResourceManager resourceManager,
                                       int resId,
                                       String resTypeName,
                                       String resName) {
        if (SkinConstant.ResTypes.RES_TYPE_NAME_COLOR.equals(resTypeName)) {
            int bgColor = resourceManager.getColor(resName);
            return new ColorDrawable(bgColor);

        } else if (SkinConstant.ResTypes.RES_TYPE_NAME_DRAWABLE.equals(resTypeName)) {
            Drawable drawable = resourceManager.getDrawable(resName);
            return drawable;
        } else if (SkinConstant.ResTypes.RES_TYPE_NAME_MIPMAP.equals(resTypeName)) {
            Drawable drawable = resourceManager.getDrawable(resName);
            return drawable;
        }

        return null;
    }

}
