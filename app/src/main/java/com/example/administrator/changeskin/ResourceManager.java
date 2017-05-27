package com.example.administrator.changeskin;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.example.administrator.changeskin.attr.SkinConstant;
import com.example.administrator.changeskin.utils.HashMapCache;


/**
 * Created by zhy on 15/9/22.
 */
public class ResourceManager {

    private HashMapCache<String, Integer> mColorCache
            = new HashMapCache<String, Integer>(true);
    private Resources mResources;
    private String mPluginPackageName;
    private String mSuffix;


    public ResourceManager(Resources res, String pluginPackageName, String suffix) {
        mResources = res;
        mPluginPackageName = pluginPackageName;

        if (suffix == null) {
            suffix = "";
        }
        mSuffix = suffix;
    }

    public int getColor(int resId) {
        String resName = mResources.getResourceEntryName(resId);
        return getColor(resName);
    }

    public int getColor(String resName) {
        String trueResName = appendSuffix(resName); //如果是应用内换肤，资源名加上后缀
        String resKey = getResKey(mPluginPackageName, trueResName);

        Integer color = mColorCache.getCache(resKey);
        if (null != color) {
            return color;
        }

        int trueResId = mResources.getIdentifier(
                trueResName, SkinConstant.ResTypes.RES_TYPE_NAME_COLOR, mPluginPackageName);
        int trueColor = mResources.getColor(trueResId);
        mColorCache.addCache(resKey, trueColor);
        return trueColor;
    }

    public Drawable getDrawable(int resId) {
        String resName = mResources.getResourceEntryName(resId);
        return getDrawable(resName);
    }

    public Drawable getDrawable(String resName) {
        String trueResName = appendSuffix(resName); //如果是应用内换肤，资源名加上后缀
        int trueResId = mResources.getIdentifier(trueResName,
                SkinConstant.ResTypes.RES_TYPE_NAME_DRAWABLE, mPluginPackageName);

        if (0 == trueResId) {
            trueResId = mResources.getIdentifier(trueResName,
                    SkinConstant.ResTypes.RES_TYPE_NAME_MIPMAP, mPluginPackageName);
            if (0 == trueResId) {
                throw new Resources.NotFoundException(trueResName);
            }
        }

        Drawable trueDrawable;
        if (android.os.Build.VERSION.SDK_INT < 22) {
            trueDrawable = mResources.getDrawable(trueResId);
        } else {
            trueDrawable = mResources.getDrawable(trueResId, null);
        }

        return trueDrawable;
    }

    public ColorStateList getColorStateList(int resId) {
        String resName = mResources.getResourceEntryName(resId);
        return getColorStateList(resName);
    }

    public ColorStateList getColorStateList(String resName) {
        return getColorStateList(SkinConstant.ResTypes.RES_TYPE_NAME_COLOR, resName);
    }

    public ColorStateList getColorStateList(String typeName, String resName) {
        int trueResId = mResources.getIdentifier(
                resName, typeName, mPluginPackageName);
        ColorStateList colorList = mResources.getColorStateList(trueResId);
        return colorList;
    }

    private String appendSuffix(String name) {
        if (!TextUtils.isEmpty(mSuffix))
            return name += "_" + mSuffix;
        return name;
    }

    private String getResKey(String skinPackageName, String resName) {
        return (null == skinPackageName ? "" : skinPackageName) + "_" + resName;
    }

}
