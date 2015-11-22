package com.gudong.gankio.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.gudong.gankio.R;
import com.gudong.gankio.presenter.CustomDialogPresenter;
import com.gudong.gankio.presenter.view.ICustomDialog;
import com.gudong.gankio.util.DialogUtil;

/**
 * CustomWebViewDialog
 * @author mao
 */
public class CustomWebViewDialog extends DialogFragment implements ICustomDialog {
    private CustomDialogPresenter mPresenter;
    private WebView mWebView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CustomDialogPresenter(getActivity(),this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View customView;
        try {
            customView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_webview, null);
            mWebView = (WebView) customView.findViewById(R.id.webview);
            mWebView.addJavascriptInterface(new WebAppInterface(customView.getContext()),"Android");
        } catch (InflateException e) {
            throw new IllegalStateException("This device does not support Web Views.");
        }
        return mPresenter.makeDialog(this,customView);
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        /** Show a dialog about app **/
        @JavascriptInterface
        public void showAbout(){
            DialogUtil.showCustomDialog(getActivity(), getFragmentManager(), getString(R.string.action_about), "about_gank_app.html", "app");
        }

        /** Show a dialog about gank site **/
        @JavascriptInterface
        public void showAboutGank(){
            DialogUtil.showCustomDialog(getActivity(), getFragmentManager(), getString(R.string.action_about_gank), "about_gank_site.html", "site");
        }
    }

}
