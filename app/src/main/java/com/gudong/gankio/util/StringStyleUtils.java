package com.gudong.gankio.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;

import com.gudong.gankio.R;
import com.gudong.gankio.data.entity.Gank;

public class StringStyleUtils {

    public static SpannableString format(Context context, String text, int style) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new TextAppearanceSpan(context, style), 0, text.length(), 0);
        return spannableString;
    }

    public static CharSequence getGankInfoSequence(Context context,Gank mGank) {
        SpannableStringBuilder builder = new SpannableStringBuilder(mGank.desc).append(
                StringStyleUtils.format(context, " (via. " + mGank.who + ")", R.style.ViaTextAppearance));
        return builder.subSequence(0, builder.length());
    }
}
