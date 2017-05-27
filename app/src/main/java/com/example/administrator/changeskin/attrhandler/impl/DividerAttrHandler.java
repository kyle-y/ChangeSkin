package com.example.administrator.changeskin.attrhandler.impl;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.changeskin.ResourceManager;
import com.example.administrator.changeskin.attr.SkinAttr;
import com.example.administrator.changeskin.attr.SkinConstant;
import com.example.administrator.changeskin.attrhandler.ISkinAttrHandler;
import com.example.administrator.changeskin.attrhandler.SkinAttrUtils;


/***
 * ListView divider属性的换肤支持（android:divider）
 */
public class DividerAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, ResourceManager resourceManager) {
        if (null == view
                || null == skinAttr
                || !(SkinConstant.SupportTypes.DIVIDER.equals(skinAttr.mAttrName))) {
            return;
        }

        if (!(view instanceof ListView)) {
            return;
        }

        ListView tv = (ListView) view;
        Drawable drawable = SkinAttrUtils.getDrawable(
                resourceManager, skinAttr.mAttrValueRefId,
                skinAttr.mAttrValueTypeName, skinAttr.mAttrValueRefName);

        if (null != drawable) {
            tv.setDivider(drawable);
        }
    }
}
