package com.gudong.gankio.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gudong.gankio.presenter.view.IWebView;

/**
 * Created by GuDong on 11/2/15 17:05.
 * Contact with 1252768410@qq.com.
 */
public class WebPresenter extends BasePresenter<IWebView> {
    public WebPresenter(Context context, IWebView view) {
        super(context, view);
    }
    
    public void setUpWebView(WebView webView){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebViewClient(new LoveClient());
    }

    public void loadUrl(WebView webView,String url){
        webView.loadUrl(url);
    }

    private class LoveClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mView.showRefresh();
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            mView.hideRefresh();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            mView.hideRefresh();
            mView.showLoadErrorMessage(description);
        }
    }
}
