package com.example.administrator.changeskin.attr;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.changeskin.R;
import com.example.administrator.changeskin.attrhandler.SkinAttrFactory;
import com.example.administrator.changeskin.constant.SkinConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhy on 15/9/23.
 */
public class SkinAttrSupport {

    /**
     * 传入activity，找到content元素，递归遍历所有的子View，根据tag命名，记录需要换肤的View
     *
     * @param activity
     */
    public static List<SkinView> getSkinViews(Activity activity) {
        List<SkinView> skinViews = new ArrayList<SkinView>();
        ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
        addSkinViews(content, skinViews);
        return skinViews;
    }

    public static void addSkinViews(View view, List<SkinView> skinViews) {
        SkinView skinView = getSkinView(view);
        if (skinView != null) skinViews.add(skinView);

        if (view instanceof ViewGroup) {
            ViewGroup container = (ViewGroup) view;

            for (int i = 0, n = container.getChildCount(); i < n; i++) {
                View child = container.getChildAt(i);
                addSkinViews(child, skinViews);
            }
        }

    }

    public static SkinView getSkinView(View view) {
        Object tag = view.getTag(R.id.skin_tag_id);
        if (tag == null) {
            tag = view.getTag();
        }
        if (tag == null) return null;
        if (!(tag instanceof String)) return null;
        String tagStr = (String) tag;

        List<SkinAttr> skinAttrs = parseTag(tagStr);
        if (!skinAttrs.isEmpty()) {
            changeViewTag(view);
            return new SkinView(view, skinAttrs);
        }
        return null;
    }


    /*
        将view的tag改为键值对的形式，去除之前的存上的tag
        如：原来的tag是：skin: :color_red:textColor 改过之后是：id : skin:color:color_red:textColor
        作用：一方面可以用来判断是否已经被加入到集合，另一方面可以通过key值获取对应的tag
     */
    private static void changeViewTag(View view) {
        Object tag = view.getTag(R.id.skin_tag_id);
        if (tag == null) {
            tag = view.getTag();
            view.setTag(R.id.skin_tag_id, tag);
            view.setTag(null);
        }
    }

    //skin:mipmap:left_menu_icon:src|skin:color:color_red:textColor 可以通过这种方式为一个view添加多个换肤属性
    private static List<SkinAttr> parseTag(String tagStr) {
        List<SkinAttr> skinAttrs = new ArrayList<SkinAttr>();
        if (TextUtils.isEmpty(tagStr)) return skinAttrs;

        String[] items = tagStr.split("[|]");
        for (String item : items) {
            if (!item.startsWith(SkinConfig.SKIN_PREFIX))
                continue;
            String[] resItems = item.split(":");
            if (resItems.length != 4)
                continue;

            String resType = resItems[1];
            String resName = resItems[2];
            String resTypeName = resItems[3];

            boolean isSupport = SkinAttrFactory.isSupportType(resTypeName);
            if (isSupport == false) continue;
            SkinAttr attr = new SkinAttr(resType, resName, resTypeName);
            skinAttrs.add(attr);
        }
        return skinAttrs;
    }
}
