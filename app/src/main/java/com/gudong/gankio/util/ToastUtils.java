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
import android.widget.Toast;

import com.gudong.gankio.GankApp;

/**
 * Created by drakeet on 9/27/14.
 */
public class ToastUtils {

    Context mContext;

    private ToastUtils() {
    }

    private static void show(Context context, int resId, int duration) {
        Toast.makeText(context, resId, duration).show();
    }

    private static void show(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void showShort(int resId) {
        Toast.makeText(GankApp.sContext, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(String message) {
        Toast.makeText(GankApp.sContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int resId) {
        Toast.makeText(GankApp.sContext, resId, Toast.LENGTH_LONG).show();
    }

    public static void showLong(String message) {
        Toast.makeText(GankApp.sContext, message, Toast.LENGTH_LONG).show();
    }

    public static void showLongLong(String message) {
        showLong(message);
        showLong(message);
    }

    public static void showLongLong(int resId) {
        showLong(resId);
        showLong(resId);
    }

    public static void showLongLongLong(int resId) {
        showLong(resId);
        showLong(resId);
        showShort(resId);
    }

    public static void showLongLongLong(String message) {
        showLong(message);
        showLong(message);
        showShort(message);
    }
}
