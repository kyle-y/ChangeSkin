package com.example.administrator.changeskin.attrhandler.impl;

/***
 * ListView selector属性的换肤支持（android:listSelector）
 */

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AbsListView;

import com.example.administrator.changeskin.ResourceManager;
import com.example.administrator.changeskin.attr.SkinAttr;
import com.example.administrator.changeskin.attr.SkinConstant;
import com.example.administrator.changeskin.attrhandler.ISkinAttrHandler;
import com.example.administrator.changeskin.attrhandler.SkinAttrUtils;


public class ListSelectorAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, ResourceManager resourceManager) {
        if (null == view
                || null == skinAttr
                || !(SkinConstant.SupportTypes.LIST_SELECTOR.equals(skinAttr.mAttrName))) {
            return;
        }

        if (!(view instanceof AbsListView)) {
            return;
        }

        Drawable drawable = SkinAttrUtils.getDrawable(
                resourceManager, skinAttr.mAttrValueRefId,
                skinAttr.mAttrValueTypeName, skinAttr.mAttrValueRefName);

        if (null != drawable) {
            ((AbsListView) view).setSelector(drawable);
        }
    }
}
