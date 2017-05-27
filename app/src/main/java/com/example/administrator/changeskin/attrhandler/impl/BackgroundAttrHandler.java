package com.example.administrator.changeskin.attrhandler.impl;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.administrator.changeskin.ResourceManager;
import com.example.administrator.changeskin.attr.SkinAttr;
import com.example.administrator.changeskin.attr.SkinConstant;
import com.example.administrator.changeskin.attrhandler.ISkinAttrHandler;
import com.example.administrator.changeskin.attrhandler.SkinAttrUtils;


/***
 * 背景属性的换肤支持（android:background）
 */
public class BackgroundAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, ResourceManager resourceManager) {
        if(null == view
                || null == skinAttr
                || !(SkinConstant.SupportTypes.BACKGROUND.equals(skinAttr.mAttrName))) {
            return;
        }

        Drawable drawable = SkinAttrUtils.getDrawable(
                resourceManager, skinAttr.mAttrValueRefId,
                skinAttr.mAttrValueTypeName, skinAttr.mAttrValueRefName);

        if(null != drawable) {
            view.setBackgroundDrawable(drawable);
        }
    }
}
