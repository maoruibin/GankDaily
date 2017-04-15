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

package com.gudong.gankio.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;
import com.gudong.gankio.R;
import com.gudong.gankio.presenter.GirlFacePresenter;
import com.gudong.gankio.ui.view.IGirlFaceView;
import com.gudong.gankio.util.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class GirlFaceActivity extends BaseActivity<GirlFacePresenter> implements IGirlFaceView {

    private static final String EXTRA_BUNDLE_URL = "BUNDLE_URL";
    private static final String EXTRA_BUNDLE_TITLE = "BUNDLE_TITLE";

    private static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    private static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";

    @Bind(R.id.iv_girl_detail)
    PhotoView mIvGirlDetail;

    private String mUrl;


    public static void gotoWatchGirlDetail(BaseActivity context, String url, String title, final View viewImage, final View viewText) {
        Intent intent = new Intent(context, GirlFaceActivity.class);
        intent.putExtra(EXTRA_BUNDLE_URL, url);
        intent.putExtra(EXTRA_BUNDLE_TITLE, title);

        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context, new Pair<View, String>(viewImage,
                        VIEW_NAME_HEADER_IMAGE));

        ActivityCompat.startActivity(context, intent, activityOptions.toBundle());
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_girl_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUrl = getIntent().getStringExtra(EXTRA_BUNDLE_URL);
        setTitle(getIntent().getStringExtra(EXTRA_BUNDLE_TITLE), true);
        ViewCompat.setTransitionName(mIvGirlDetail, VIEW_NAME_HEADER_IMAGE);

        loadItem();
    }

    private void loadItem() {
        Glide.with(this)
                .load(mUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(mIvGirlDetail);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GirlFacePresenter(this, this);
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_girl_detail;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            mPresenter.saveFace(getIntent().getStringExtra(EXTRA_BUNDLE_URL), mIvGirlDetail);
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

    /**
     * Try and add a {@link Transition.TransitionListener} to the entering shared element
     * {@link Transition}. We do this so that we can load the full-size image after the transition
     * has completed.
     *
     * @return true if we were successful in adding a listener to the enter transition
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener() {
        final Transition transition = getWindow().getSharedElementEnterTransition();

        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    // As the transition has ended, we can now load the full-size image
                    loadItem();

                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    // No-op
                }
            });
            return true;
        }
        // If we reach here then we have not added a listener
        return false;
    }

    @OnClick(R.id.iv_girl_detail)
    public void onViewClicked() {
        if(getSupportActionBar().isShowing()){
            getSupportActionBar().hide();
        }else{
            getSupportActionBar().show();
        }
    }
}
