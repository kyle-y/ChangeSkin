package com.example.administrator.changeskin.attrhandler;


import android.text.TextUtils;

import com.example.administrator.changeskin.attr.SkinAttr;
import com.example.administrator.changeskin.attr.SkinConstant;
import com.example.administrator.changeskin.attrhandler.impl.BackgroundAttrHandler;
import com.example.administrator.changeskin.attrhandler.impl.DividerAttrHandler;
import com.example.administrator.changeskin.attrhandler.impl.DrawableLeftAttrHandler;
import com.example.administrator.changeskin.attrhandler.impl.ListSelectorAttrHandler;
import com.example.administrator.changeskin.attrhandler.impl.SrcAttrHandler;
import com.example.administrator.changeskin.attrhandler.impl.TextColorAttrHandler;
import com.example.administrator.changeskin.attrhandler.impl.TextColorHintAttrHandler;

import java.util.HashMap;
import java.util.Map;


/***
 * 获取支持的属性处理器工厂
 */
public class SkinAttrFactory {

    //存放支持的换肤属性和对应的处理器
    private static Map<String, ISkinAttrHandler> mSupportAttrHandler = new HashMap<String, ISkinAttrHandler>();

    //静态注册支持的属性和处理器
    static {
        registerSkinAttrHandler(SkinConstant.SupportTypes.BACKGROUND, new BackgroundAttrHandler());
        registerSkinAttrHandler(SkinConstant.SupportTypes.SRC, new SrcAttrHandler());
        registerSkinAttrHandler(SkinConstant.SupportTypes.TEXT_COLOR, new TextColorAttrHandler());
        registerSkinAttrHandler(SkinConstant.SupportTypes.TEXT_COLOR_HINT, new TextColorHintAttrHandler());
        registerSkinAttrHandler(SkinConstant.SupportTypes.LIST_SELECTOR, new ListSelectorAttrHandler());
        registerSkinAttrHandler(SkinConstant.SupportTypes.DIVIDER, new DividerAttrHandler());
        registerSkinAttrHandler(SkinConstant.SupportTypes.DRAWABLE_LEFT, new DrawableLeftAttrHandler());
    }

    /***
     * 创建一个新的皮肤对象
     * @param attrName
     * @param attrValueRefId
     * @param attrValueRefName
     * @param typeName
     * @return
     */
    public static SkinAttr newSkinAttr(
            String attrName, int attrValueRefId,
            String attrValueRefName, String typeName) {
        SkinAttr skinAttr = new SkinAttr();

        skinAttr.mAttrName = attrName;
        skinAttr.mAttrValueRefId = attrValueRefId;
        skinAttr.mAttrValueRefName = attrValueRefName;
        skinAttr.mAttrValueTypeName = typeName;
        return skinAttr;
    }

    /**
     * 判断是否是支持的换肤类型
     * @param attrName
     * @return
     */
    public static boolean isSupportType(String attrName) {
        for (String supportType : SkinConstant.SupportTypes.supportTypeList) {
            if (supportType.equals(attrName)) {
                return true;
            }
        }
        return false;
    }

    /***
     * 获取特定属性的换肤处理器
     *
     * @param attrName
     * @return
     */
    public static ISkinAttrHandler getSkinAttrHandler(String attrName) {
        return mSupportAttrHandler.get(attrName);
    }

    /****
     * 注册对某个属性的换肤支持
     *
     * @param attrName
     */
    public static void registerSkinAttrHandler(String attrName, ISkinAttrHandler skinAttrHandler) {
        if (TextUtils.isEmpty(attrName) || null == skinAttrHandler) {
            return;
        }

        mSupportAttrHandler.put(attrName, skinAttrHandler);
    }

    /***
     * 移除对某个属性的换肤支持
     *
     * @param attrName
     */
    public static void unRegisterSkinAttrHandler(String attrName) {
        if (TextUtils.isEmpty(attrName)) {
            return;
        }
        mSupportAttrHandler.remove(attrName);
    }
}
