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
import android.widget.ImageView;

import com.gudong.gankio.R;
import com.gudong.gankio.presenter.GirlFacePresenter;
import com.gudong.gankio.presenter.view.IGirlFaceView;
import com.gudong.gankio.ui.BaseActivity;
import com.gudong.gankio.util.AndroidUtils;
import com.gudong.gankio.util.ToastUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

public class GirlFaceActivity extends BaseActivity<GirlFacePresenter> implements IGirlFaceView {

    private static final String EXTRA_BUNDLE_URL = "BUNDLE_URL";
    private static final String EXTRA_BUNDLE_TITLE = "BUNDLE_TITLE";

    private static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    private static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";

    @Bind(R.id.iv_girl_detail)
    ImageView mIvGirlDetail;

    private String mUrl;
    PhotoViewAttacher mAttacher;


    public static void gotoWatchGirlDetail(BaseActivity context,String url,String title,final View viewImage,final View viewText){
        Intent intent = new Intent(context,GirlFaceActivity.class);
        intent.putExtra(EXTRA_BUNDLE_URL,url);
        intent.putExtra(EXTRA_BUNDLE_TITLE,title);

        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context,new Pair<View, String>(viewImage,
                        VIEW_NAME_HEADER_IMAGE),
                new Pair<View, String>(viewText,
                        VIEW_NAME_HEADER_TITLE));

        ActivityCompat.startActivity(context, intent, activityOptions.toBundle());
    }



    @Override
    protected int getLayout() {
        return R.layout.activity_girl_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAttacher = new PhotoViewAttacher(mIvGirlDetail);

        mUrl = getIntent().getStringExtra(EXTRA_BUNDLE_URL);
        setTitle(getIntent().getStringExtra(EXTRA_BUNDLE_TITLE), true);

        ViewCompat.setTransitionName(mIvGirlDetail, VIEW_NAME_HEADER_IMAGE);
        ViewCompat.setTransitionName(AndroidUtils.getTitleViewInToolbar(mToolbar), VIEW_NAME_HEADER_TITLE);

        loadItem();
    }

    private void loadItem() {
        if (AndroidUtils.isAndroidL() && addTransitionListener()) {
            loadThumbnail();
        } else {
            loadFullSizeImage();
        }
    }

    /**
     * Load the item's thumbnail image into our {@link ImageView}.
     */
    private void loadThumbnail() {
        Picasso.with(this)
                .load(mUrl)
                .noFade()
                .into(mIvGirlDetail);
    }

    private void loadFullSizeImage() {
        Picasso.with(this)
                .load(mUrl)
                .noFade()
                .noPlaceholder()
                .into(mIvGirlDetail);
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

    /**
     * Try and add a {@link Transition.TransitionListener} to the entering shared element
     * {@link Transition}. We do this so that we can load the full-size image after the transition
     * has completed.
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
                    loadFullSizeImage();

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
}
