package com.example.administrator.changeskin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;

import com.example.administrator.changeskin.attr.SkinAttrSupport;
import com.example.administrator.changeskin.attr.SkinView;
import com.example.administrator.changeskin.callback.ISkinChangingCallback;
import com.example.administrator.changeskin.utils.L;
import com.example.administrator.changeskin.utils.PrefUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhy on 15/9/22.
 */
public class SkinManager {
    private Context mContext;
    private Resources mResources;
    private ResourceManager mResourceManager;
    private PrefUtils mPrefUtils;

    private boolean usePlugin;

    private String mSuffix = "";
    private String mCurPluginPath;
    private String mCurPluginPkg;


    private List<Activity> mActivities = new ArrayList<Activity>();

    private SkinManager() {
    }

    private static class SingletonHolder {
        static SkinManager sInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return SingletonHolder.sInstance;   //单例SkinManager
    }


    /**
     * 初始化，先从本地拿皮肤路径，初始化资源加载器
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context.getApplicationContext();
        mPrefUtils = new PrefUtils(mContext);

        String skinPluginPath = mPrefUtils.getPluginPath();
        String skinPluginPkg = mPrefUtils.getPluginPkgName();
        mSuffix = mPrefUtils.getSuffix();

        if (!validPluginParams(skinPluginPath, skinPluginPkg))
            return;

        try {
            loadPlugin(skinPluginPath, skinPluginPkg, mSuffix);
            mCurPluginPath = skinPluginPath;
            mCurPluginPkg = skinPluginPkg;
        } catch (Exception e) {
            mPrefUtils.clear();
            e.printStackTrace();
        }
    }

    /**
     * 通过资源APK获取APK包的信息，用于验证皮肤包的正确性
     * 会出现这个问题：加载到packageInfo为null，由以下原因造成：
     * 1，读写权限；2，包不完整或错误；3，其他未知原因
     * 保险期间，建议直接使用资源包名进行验证，而不是动态获取
     *
     * @param skinPluginPath
     * @return
     */
    private PackageInfo getPackageInfo(String skinPluginPath) {
        PackageManager pm = mContext.getPackageManager();
        return pm.getPackageArchiveInfo(skinPluginPath, PackageManager.GET_ACTIVITIES);
    }


    //得到AssetManager --> Resource，用来解析资源包，得到后初始化ResourceManager进行调用；
    private void loadPlugin(String skinPath, String skinPkgName, String suffix) throws Exception {
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
        addAssetPath.invoke(assetManager, skinPath);

        Resources superRes = mContext.getResources();
        mResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        mResourceManager = new ResourceManager(mResources, skinPkgName, suffix);
        usePlugin = true;
    }

    private boolean validPluginParams(String skinPath, String skinPkgName) {
        if (TextUtils.isEmpty(skinPath) || TextUtils.isEmpty(skinPkgName)) {
            return false;
        }

        File file = new File(skinPath);
        if (!file.exists())
            return false;

        PackageInfo info = getPackageInfo(skinPath);
        if (!info.packageName.equals(skinPkgName))
            return false;
        return true;
    }

    private void checkPluginParamsThrow(String skinPath, String skinPkgName) {
        if (!validPluginParams(skinPath, skinPkgName)) {
            throw new IllegalArgumentException("skinPluginPath or skinPkgName not valid ! ");
        }
    }

    /**
     * 移除所有记录的插件信息，通知活着的页面恢复默认皮肤
     * isUsePlugin为false，会改变资源加载器，使新页面加载资源包时失败，造成恢复默认效果
     */
    public void removeAnySkin() {
        L.e("removeAnySkin");
        clearPluginInfo();
        notifyChangedListeners();
    }


    public boolean needChangeSkin() {
        return usePlugin || !TextUtils.isEmpty(mSuffix);
    }


    //分插件换肤和非插件换肤两种情况：插件换肤使用了外部资源包获取ResuouceManager，如果不使用插件，需要加载自身资源
    public ResourceManager getResourceManager() {
        if (!usePlugin) {
            mResourceManager = new ResourceManager(mContext.getResources(), mContext.getPackageName(), mSuffix);
        }
        return mResourceManager;
    }


    /**
     * 应用内换肤，传入资源区别的后缀
     *
     * @param suffix
     */
    public void changeSkin(String suffix) {
        clearPluginInfo();//clear before
        mSuffix = suffix;
        mPrefUtils.putPluginSuffix(suffix);
        notifyChangedListeners();
    }

    private void clearPluginInfo() {
        mCurPluginPath = null;
        mCurPluginPkg = null;
        usePlugin = false;
        mSuffix = null;
        mPrefUtils.clear();
    }

    private void updatePluginInfo(String skinPluginPath, String pkgName, String suffix) {
        mPrefUtils.putPluginPath(skinPluginPath);
        mPrefUtils.putPluginPkg(pkgName);
        mPrefUtils.putPluginSuffix(suffix);

        mCurPluginPkg = pkgName;
        mCurPluginPath = skinPluginPath;
        mSuffix = suffix;
    }

    /**
     * 传入插件，进行换肤
     *
     * @param skinPluginPath 插件路径
     * @param skinPluginPkg  插件包名
     * @param callback       回调
     */
    public void changeSkin(final String skinPluginPath, final String skinPluginPkg, ISkinChangingCallback callback) {
        changeSkin(skinPluginPath, skinPluginPkg, null, callback);
    }


    /**
     * 根据suffix选择插件内某套皮肤，默认为""
     *
     * @param skinPluginPath
     * @param skinPluginPkg
     * @param suffix
     * @param callback
     */
    public void changeSkin(final String skinPluginPath, final String skinPluginPkg, final String suffix, ISkinChangingCallback callback) {
        L.e("changeSkin = " + skinPluginPath + " , " + skinPluginPkg);
        if (callback == null)
            callback = ISkinChangingCallback.DEFAULT_SKIN_CHANGING_CALLBACK;
        final ISkinChangingCallback skinChangingCallback = callback;

        skinChangingCallback.onStart();

        try {
            checkPluginParamsThrow(skinPluginPath, skinPluginPkg);
        } catch (IllegalArgumentException e) {
            skinChangingCallback.onError(new RuntimeException("checkPlugin occur error"));
            return;
        }

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    loadPlugin(skinPluginPath, skinPluginPkg, suffix);
                    return 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }

            }

            @Override
            protected void onPostExecute(Integer res) {
                if (res == 0) {
                    skinChangingCallback.onError(new RuntimeException("loadPlugin occur error"));
                    return;
                }
                try {
                    updatePluginInfo(skinPluginPath, skinPluginPkg, suffix);
                    notifyChangedListeners();
                    skinChangingCallback.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    skinChangingCallback.onError(e);
                }

            }
        }.execute();
    }

    //重新遍历内部的所有view，获取需要换肤的view，然后解析资源包，并完成换肤操作
    public void apply(Activity activity) {
        List<SkinView> skinViews = SkinAttrSupport.getSkinViews(activity);
        if (skinViews == null) return;
        for (SkinView skinView : skinViews) {
            skinView.apply();
        }
    }

    public void register(final Activity activity) {
        mActivities.add(activity);

        activity.findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                apply(activity);
            }
        });
    }

    public void unregister(Activity activity) {
        mActivities.remove(activity);
    }

    //通知已经注册过的activity进行换肤
    public void notifyChangedListeners() {

        for (Activity activity : mActivities) {
            apply(activity);
        }
    }

    /**
     * apply for dynamic construct view
     * 1，当出现某个view不在android.R.id.content 内部（如popupWindow等）
     * 2，或者这个view的加载时机比较晚时
     * 3，这是在java文件中动态new出来的view（在应用之前该view需要setTag）
     *
     * @param view
     */
    public void injectSkin(View view) {
        List<SkinView> skinViews = new ArrayList<SkinView>();
        SkinAttrSupport.addSkinViews(view, skinViews);
        for (SkinView skinView : skinViews) {
            skinView.apply();
        }
    }


}
