package com.gudong.gankio.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.TextView;

import com.gudong.gankio.R;

import java.lang.reflect.Field;

/**
 * Created by GuDong on 10/14/15 22:22.
 * Contact with 1252768410@qq.com.
 */
public class AndroidUtils {
    public static void copyToClipBoard(Context context, String text, String success) {
        ClipData clipData = ClipData.newPlainText("meizhi_copy", text);
        ClipboardManager manager =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        ToastUtils.showShort(success);
    }

    /**
     * get accent color
     * @param context
     * @return
     */
    public static int getAccentColor(Context context){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public static boolean isAndroidL(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * base on Toolbar get the TitleView by reflect way
     * @param obj Toolbar
     * @return the title text view in Toolbar
     */
    public static TextView getTitleViewInToolbar(Toolbar obj){
        TextView textView = null;
        try {
            Field title = obj.getClass().getDeclaredField("mTitleTextView");
            title.setAccessible(true);
            textView = (TextView) title.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return textView;
    }

    /**
     * get app version info
     * @param context context
     * @return app version info if occur exception return unknow
     */
    public static String getAppVersion(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return context.getString(R.string.unknow);
        }
    }

    public static void setCurrentVersion(Context context,String version){
        putStringPreference(context,"current_version",version);
    }

    public static String getLocalVersion(Context context){
        return getStringPreference(context, "current_version", "");
    }

    public static String getStringPreference(Context context,String key,String def){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, def);
    }

    public static void putStringPreference(Context context,String key,String value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(key, value).apply();
    }

}
