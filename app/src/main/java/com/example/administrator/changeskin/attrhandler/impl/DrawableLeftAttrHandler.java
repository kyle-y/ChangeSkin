package com.example.administrator.changeskin.attrhandler.impl;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.changeskin.ResourceManager;
import com.example.administrator.changeskin.attr.SkinAttr;
import com.example.administrator.changeskin.attr.SkinConstant;
import com.example.administrator.changeskin.attrhandler.ISkinAttrHandler;
import com.example.administrator.changeskin.attrhandler.SkinAttrUtils;


/**
 * TextView的drawableLeft属性处理
 * qqliu
 * 2016/9/27.
 */
public class DrawableLeftAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, ResourceManager resourceManager) {
        if(null == view
                || null == skinAttr
                || !(SkinConstant.SupportTypes.DRAWABLE_LEFT.equals(skinAttr.mAttrName))) {
            return;
        }

        if (!(view instanceof TextView)) {
            return;
        }

        Drawable drawable = SkinAttrUtils.getDrawable(resourceManager,
                skinAttr.mAttrValueRefId,
                skinAttr.mAttrValueTypeName,
                skinAttr.mAttrValueRefName);

        if(null != drawable) {
            ((TextView)view).setCompoundDrawablesWithIntrinsicBounds(
                    drawable, null, null, null);
        }
    }
}
