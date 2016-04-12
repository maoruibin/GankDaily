/*
 *
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 * Copyright (C) 2015 GuDong <maoruibin9035@gmail.com>
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

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
     *
     * @param context context
     */
    public static void showSinglePointDialog(Context context, String message) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.title_point)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_confirm, null)
                .show();
    }


    /**
     * show a custom dialog use a local html file
     *
     * @param context
     * @param fragmentManager
     * @param dialogTitle     title
     * @param htmlFileName    file name
     * @param tag
     */
    public static void showCustomDialog(Context context, FragmentManager fragmentManager, String
            dialogTitle, String htmlFileName, String tag) {
        int accentColor = AndroidUtils.getAccentColor(context);
        CustomDialogPresenter.create(dialogTitle, htmlFileName, accentColor)
                .show(fragmentManager, tag);
    }
}
