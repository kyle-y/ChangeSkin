package com.example.administrator.changeskin.attrhandler.impl;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.changeskin.ResourceManager;
import com.example.administrator.changeskin.attr.SkinAttr;
import com.example.administrator.changeskin.attr.SkinConstant;
import com.example.administrator.changeskin.attrhandler.ISkinAttrHandler;
import com.example.administrator.changeskin.attrhandler.SkinAttrUtils;


/***
 * src属性的换肤支持（android:src）
 */
public class SrcAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, ResourceManager resourceManager) {
        if (null == view
                || null == skinAttr
                || !(SkinConstant.SupportTypes.SRC.equals(skinAttr.mAttrName))) {
            return;
        }

        if (!(view instanceof ImageView)) {
            return;
        }

        boolean isAnimationDrawable = false;
        boolean isRunning = false;
        Drawable originalDrawable = ((ImageView) view).getDrawable();
        if (originalDrawable instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) originalDrawable;
            isAnimationDrawable = true;
            isRunning = animationDrawable.isRunning();
        }

        Drawable drawable = SkinAttrUtils.getDrawable(
                resourceManager, skinAttr.mAttrValueRefId,
                skinAttr.mAttrValueTypeName, skinAttr.mAttrValueRefName);

        if (null != drawable) {
            ((ImageView) view).setImageDrawable(drawable);

            if (isAnimationDrawable && drawable instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = ((AnimationDrawable) drawable);
                if (isRunning) {
                    animationDrawable.start();
                } else {
                    animationDrawable.stop();
                }
            }
        }
    }
}
