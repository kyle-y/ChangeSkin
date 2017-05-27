package com.example.administrator.changeskin.attrhandler.impl;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.changeskin.ResourceManager;
import com.example.administrator.changeskin.attr.SkinAttr;
import com.example.administrator.changeskin.attr.SkinConstant;
import com.example.administrator.changeskin.attrhandler.ISkinAttrHandler;


/***
 * 文字提示颜色属性的换肤支持（android:textColorHint）
 */
public class TextColorHintAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, ResourceManager resourceManager) {
        if (null == view
                || null == skinAttr
                || !(SkinConstant.SupportTypes.TEXT_COLOR_HINT.equals(skinAttr.mAttrName))) {
            return;
        }

        if (!(view instanceof TextView)) {
            return;
        }

        TextView tv = (TextView) view;
        if (SkinConstant.ResTypes.RES_TYPE_NAME_COLOR.equals(skinAttr.mAttrValueTypeName)) {
            //按照ColorStateList引用来解析；
            //context.getResources().getColor()方法可以取纯颜色，也可以取ColorStateList引用内的颜色，
            //如果取的是ColorStateList，则取其中默认颜色；
            //同时，context.getResources().getColorStateList()方法也可以取纯颜色生成一个ColorStateList
            ColorStateList textHintColor = resourceManager.getColorStateList(
                    skinAttr.mAttrValueRefName);
            tv.setHintTextColor(textHintColor);
        }
    }
}
