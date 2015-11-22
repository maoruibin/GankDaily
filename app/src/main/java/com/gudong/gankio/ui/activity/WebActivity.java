package com.gudong.gankio.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.gudong.gankio.R;
import com.gudong.gankio.presenter.WebPresenter;
import com.gudong.gankio.presenter.view.IWebView;
import com.gudong.gankio.ui.BaseActivity;
import com.gudong.gankio.ui.BaseSwipeRefreshActivity;
import com.gudong.gankio.util.AndroidUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebActivity extends BaseSwipeRefreshActivity<WebPresenter> implements IWebView{
    private static final String EXTRA_URL = "URL";
    private static final String EXTRA_TITLE = "TITLE";

    @Bind(R.id.wb_content)
    WebView mWbContent;

    public static void gotoWebActivity(BaseActivity context,String url,String title){
        Intent intent = new Intent(context,WebActivity.class);
        intent.putExtra(EXTRA_URL,url);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(EXTRA_URL);
        String title = getIntent().getStringExtra(EXTRA_TITLE);

        if(!TextUtils.isEmpty(title)){
            setTitle(title,true);
        }
        mPresenter.setUpWebView(mWbContent);
        mPresenter.loadUrl(mWbContent,url);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_web;
    }

    @Override
    protected void onRefreshStarted() {
        refresh();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new WebPresenter(this,this);
    }

    private void refresh(){
        mWbContent.reload();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_copy_url:
                String copyDone = getString(R.string.toast_copy_done);
                AndroidUtils.copyToClipBoard(this, mWbContent.getUrl(), copyDone);
                return true;
            case R.id.action_open_url:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(mWbContent.getUrl());
                intent.setData(uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(WebActivity.this, R.string.toast_open_fail, Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWbContent != null) mWbContent.destroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onPause() {
        if (mWbContent != null) mWbContent.onPause();
        super.onPause();
    }

    @Override
    public void showLoadErrorMessage(String message) {
        Snackbar.make(mWbContent,message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWbContent.canGoBack()) {
            mWbContent.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
