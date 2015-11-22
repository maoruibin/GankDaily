package com.gudong.gankio.util;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.gudong.gankio.R;
import com.gudong.gankio.presenter.CustomDialogPresenter;


/**
 * tool for dialog
 * Created by mao on 7/19/15.
 */
public class DialogUtil {
    /**
     * show a dialog which it contain one point message only
     * @param context context
     */
    public static void showSinglePointDialog(Context context, String message){
        new AlertDialog.Builder(context)
                .setTitle(R.string.title_point)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_confirm, null)
                .show();
    }


    /**
     * show a custom dialog use a local html file
     * @param context
     * @param fragmentManager
     * @param dialogTitle title
     * @param htmlFileName file name
     * @param tag
     */
    public static void showCustomDialog(Context context, FragmentManager fragmentManager, String dialogTitle, String htmlFileName, String tag) {
        int accentColor = AndroidUtils.getAccentColor(context);
        CustomDialogPresenter.create(dialogTitle, htmlFileName, accentColor)
                .show(fragmentManager, tag);
    }
}