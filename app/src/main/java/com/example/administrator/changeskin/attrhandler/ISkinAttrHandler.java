package com.example.administrator.changeskin.attrhandler;

import android.view.View;

import com.example.administrator.changeskin.ResourceManager;
import com.example.administrator.changeskin.attr.SkinAttr;


/**
 * 皮肤属性处理器的接口
 *
 * an interface indicates how to apply special skin attributes for a view.
 *
 * qqliu
 * 2016/9/24.
 */
public interface ISkinAttrHandler {

    /***
     * 将属性应用到View上
     *
     * apply skin attribute to view
     *
     * @param view
     * @param skinAttr
     * @param resourceManager
     */
    void apply(View view,
               SkinAttr skinAttr,
               ResourceManager resourceManager);
}
