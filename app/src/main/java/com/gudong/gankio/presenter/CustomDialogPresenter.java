package com.gudong.gankio.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.gudong.gankio.R;
import com.gudong.gankio.presenter.view.ICustomDialog;
import com.gudong.gankio.ui.fragment.CustomWebViewDialog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by GuDong on 11/6/15 16:00.
 * Contact with 1252768410@qq.com.
 */
public class CustomDialogPresenter extends BasePresenter<ICustomDialog> {

    private static final String EXTRA_DIALOG_TITLE = "DIALOG_TITLE";
    private static final String EXTRA_HTML_FILE_NAME = "HTML_FILE_NAME";
    private static final String EXTRA_ACCENT_COLOR = "ACCENT_COLOR";

    private static final String KEY_UTF_8 = "UTF_8";

    public CustomDialogPresenter(Activity context, ICustomDialog view) {
        super(context, view);
    }

    /**
     * create a custom dialog use web view load layout by html file
     * @param dialogTitle  dialog title
     * @param htmlFileName html file name
     * @param accentColor  accent color
     * @return a instance of CustomWebViewDialog
     */
    public static CustomWebViewDialog create(String dialogTitle, String htmlFileName, int accentColor) {
        CustomWebViewDialog dialog = new CustomWebViewDialog();
        Bundle args = new Bundle();
        args.putString(EXTRA_DIALOG_TITLE, dialogTitle);
        args.putString(EXTRA_HTML_FILE_NAME, htmlFileName);
        args.putInt(EXTRA_ACCENT_COLOR, accentColor);
        dialog.setArguments(args);
        return dialog;
    }


    public AlertDialog makeDialog(Fragment fragment,View customView) {
        String dialogTitle = fragment.getArguments().getString(EXTRA_DIALOG_TITLE);
        String htmlFileName = fragment.getArguments().getString(EXTRA_HTML_FILE_NAME);
        int accentColor = fragment.getArguments().getInt(EXTRA_ACCENT_COLOR);

        final WebView webView = (WebView) customView.findViewById(R.id.webview);
        setWebView(webView);
        loadData(webView, htmlFileName, accentColor);

        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(dialogTitle)
                .setView(customView)
                .setPositiveButton(android.R.string.ok, null)
                .show();

        return dialog;
    }

    private void setWebView(WebView webView){
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName(KEY_UTF_8);
        settings.setJavaScriptEnabled(true);
    }

    private void loadData(WebView webView,String htmlFileName,int accentColor){
        try {
            StringBuilder buf = new StringBuilder();
            InputStream json = mContext.getAssets().open(htmlFileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(json,KEY_UTF_8));
            String str;
            while ((str = in.readLine()) != null)
                buf.append(str);
            in.close();

            String formatLodString = buf.toString()
                    .replace("{style-placeholder}", "body { background-color: #ffffff; color: #000; }")
                    .replace("{link-color}", colorToHex(shiftColor(accentColor, true)))
                    .replace("{link-color-active}", colorToHex(accentColor));
            webView.loadDataWithBaseURL(null, formatLodString, "text/html", KEY_UTF_8, null);
        } catch (Throwable e) {
            webView.loadData("<h1>Unable to load</h1><p>" + e.getLocalizedMessage() + "</p>", "text/html", KEY_UTF_8);
        }
    }

    private String colorToHex(int color) {
        return Integer.toHexString(color).substring(2);
    }

    private int shiftColor(int color, boolean up) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= (up ? 1.1f : 0.9f); // value component
        return Color.HSVToColor(hsv);
    }
}
