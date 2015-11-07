package com.gudong.gankio.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.gudong.gankio.R;

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
     * 获取主题强调色
     * @param context
     * @return
     */
    public static int getAccentColor(Context context){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
