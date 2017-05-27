package com.example.administrator.changeskin.attr;

import android.view.View;

import com.example.administrator.changeskin.ResourceManager;
import com.example.administrator.changeskin.SkinManager;
import com.example.administrator.changeskin.attrhandler.SkinAttrFactory;

import java.util.List;

/**
 * Created by zhy on 15/9/22.
 */
public class SkinView {
    public View view;
    public List<SkinAttr> attrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.view = view;
        this.attrs = skinAttrs;
    }

    public void apply() {
        // View view = viewRef.get();
        if (view == null) return;

        for (SkinAttr attr : attrs) {
            SkinAttrFactory.getSkinAttrHandler(attr.mAttrName)
                    .apply(view, attr, getResourceManager());
        }
    }

    public ResourceManager getResourceManager() {
        return SkinManager.getInstance().getResourceManager();
    }
}
