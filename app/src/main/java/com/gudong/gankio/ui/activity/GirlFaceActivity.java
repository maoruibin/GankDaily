package com.gudong.gankio.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.gudong.gankio.R;
import com.gudong.gankio.presenter.GirlFacePresenter;
import com.gudong.gankio.presenter.view.IGirlFaceView;
import com.gudong.gankio.ui.BaseActivity;
import com.gudong.gankio.util.ToastUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

public class GirlFaceActivity extends BaseActivity<GirlFacePresenter> implements IGirlFaceView {
    private static final String EXTRA_BUNDLE_URL = "BUNDLE_URL";
    private static final String EXTRA_BUNDLE_TITLE = "BUNDLE_TITLE";


    @Bind(R.id.iv_girl_detail)
    ImageView mIvGirlDetail;

    PhotoViewAttacher mAttacher;

    public static void gotoWatchGirlDetail(Context context,String url,String title){
        Intent intent = new Intent(context,GirlFaceActivity.class);
        intent.putExtra(EXTRA_BUNDLE_URL,url);
        intent.putExtra(EXTRA_BUNDLE_TITLE,title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_girl_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAttacher = new PhotoViewAttacher(mIvGirlDetail);

        String title = getIntent().getStringExtra(EXTRA_BUNDLE_TITLE);
        setTitle(title, true);

        String url = getIntent().getStringExtra(EXTRA_BUNDLE_URL);
        mPresenter.loadGirl(url, mIvGirlDetail);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GirlFacePresenter(this,this);
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_girl_detail;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            mPresenter.saveFace(getIntent().getStringExtra(EXTRA_BUNDLE_URL));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void saveSuccess(String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void showFailInfo(String error) {
        ToastUtils.showShort(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.with(this).cancelRequest(mIvGirlDetail);
    }

}
