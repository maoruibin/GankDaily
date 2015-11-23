package com.gudong.gankio.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gudong.gankio.R;
import com.gudong.gankio.data.entity.Gank;
import com.gudong.gankio.presenter.MainPresenter;
import com.gudong.gankio.presenter.view.IMainView;
import com.gudong.gankio.ui.BaseActivity;
import com.gudong.gankio.ui.BaseSwipeRefreshActivity;
import com.gudong.gankio.ui.adapter.MainListAdapter;
import com.gudong.gankio.ui.widget.RatioImageView;
import com.gudong.gankio.util.DateUtil;
import com.gudong.gankio.util.DialogUtil;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * this Activity is use to MainActivity and when scroll bottom , it will load more data.
 * at the same time, it is also use to show the gank info of one day ( I don't know is it good like this and design it)
 * if getIntent() contains bundle of EXTRA_BUNDLE_GANK ,it indicate this Activity is MainActivity ,
 * otherwise this Activity is a GankActivity used to show Gank info of one day
 */
public class MainActivity extends BaseSwipeRefreshActivity<MainPresenter> implements IMainView<Gank>,MainListAdapter.IClickMainItem {

    private static final String EXTRA_BUNDLE_GANK = "BUNDLE_GANK";
    private static final String EXTRA_BUNDLE_LOAD_MORE = "BUNDLE_LOAD_MORE";

    private static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    private static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";

    @Bind(R.id.rv_gank)
    RecyclerView mRvGank;
    MainListAdapter mAdapter;

    /**
     * the flag of has more data or not
     */
    private boolean mHasMoreData = true;

    /**
     * the flag to district whether scroll bottom load more data or not
     * default is load more data
     */
    boolean isLoadMore = true;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.checkAutoUpdateByUmeng();
        initRecycleView();
        setTitle(getString(R.string.app_name), false);
        isLoadMore = getIntent().getBooleanExtra(EXTRA_BUNDLE_LOAD_MORE,true);
        //check update info by Umeng
        mPresenter.checkVersionInfo();
        prepareShowGankDetailView();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // make swipeRefreshLayout visible manually
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showRefresh();
            }
        }, 568);
        getData();
    }

    @Override
    protected void onRefreshStarted() {
        getData();
    }

    @Override
    protected boolean prepareRefresh() {
        return mPresenter.shouldRefillData();
    }

    @Override
    protected int getMenuRes() {
        return isLoadMore ?R.menu.menu_main:R.menu.menu_gank;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_view_list:
                startActivity(new Intent(this,ViewListActivity.class));
                break;
            case R.id.action_github_tending:
                String url = getString(R.string.url_github_trending);
                String title = getString(R.string.action_github_trending);
                WebActivity.gotoWebActivity(this,url,title);
                break;
            case R.id.action_about:
                DialogUtil.showCustomDialog(this, getSupportFragmentManager(), getString(R.string.action_about), "about_gank_app.html", "about");
                break;
            case R.id.action_opinion:
                String urlOpinion = getString(R.string.url_github_issue);
                String titleOpinion = getString(R.string.action_github_issue);
                WebActivity.gotoWebActivity(this,urlOpinion,titleOpinion);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void fillData(List<Gank> data) {
        mAdapter.updateWithClear(data);
    }

    @Override
    public void appendMoreDataToView(List<Gank> data) {
        mAdapter.update(data);
    }

    @Override
    public void hasNoMoreData() {
        mHasMoreData = false;
        Snackbar.make(mRvGank, R.string.no_more_gank, Snackbar.LENGTH_LONG)
                .setAction(R.string.action_to_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (mRvGank.getLayoutManager()).smoothScrollToPosition(mRvGank,null,0);
                    }
                })
                .show();
    }

    @Override
    public void showChangeLogInfo(String assetFileName) {
        DialogUtil.showCustomDialog(this, getSupportFragmentManager(), getString(R.string.change_log), assetFileName, "");
    }

    @Override
    public void showEmptyView() {
        //our meizi will not empty and we can new it
    }

    @Override
    public void showErrorView(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onClickGankItemGirl(Gank gank, View viewImage, View viewText) {
        GirlFaceActivity.gotoWatchGirlDetail(this, gank.url, DateUtil.toDate(gank.publishedAt), viewImage, viewText);
    }

    @Override
    public void onClickGankItemNormal(Gank gank, View view) {
        WebActivity.gotoWebActivity(this, gank.url, gank.desc);
    }

    public static void gotoGankActivity(BaseActivity activity, Gank gank,Boolean load_more,final View viewImage,final View viewText) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(EXTRA_BUNDLE_GANK, gank);
        intent.putExtra(EXTRA_BUNDLE_LOAD_MORE, load_more);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,new Pair<View, String>(viewImage,
                        VIEW_NAME_HEADER_IMAGE),
                new Pair<View, String>(viewText,
                        VIEW_NAME_HEADER_TITLE));
        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
    }

    private void getData() {
        Gank gank = (Gank) getIntent().getSerializableExtra(EXTRA_BUNDLE_GANK);
        if(gank==null){
            mPresenter.getData(new Date(System.currentTimeMillis()));
        }else{
            mPresenter.getData(gank.publishedAt);
        }
    }

    /**
     *  if the getIntent() contains Gank entity, it indicates this activity is used to show one Day gank info
     */
    private void prepareShowGankDetailView(){
        Gank gank = (Gank) getIntent().getSerializableExtra(EXTRA_BUNDLE_GANK);
        if(gank != null){
            setTitle(DateUtil.toDate(gank.publishedAt), true);
            mRvGank.post(new Runnable() {
                @Override
                public void run() {
                    View gankGrilView = mRvGank.getChildAt(0);
                    RatioImageView mImageView = ButterKnife.findById(gankGrilView, R.id.iv_index_photo);
                    TextView mTvTime = ButterKnife.findById(gankGrilView, R.id.tv_video_name);

                    ViewCompat.setTransitionName(mImageView, VIEW_NAME_HEADER_IMAGE);
                    ViewCompat.setTransitionName(mTvTime, VIEW_NAME_HEADER_TITLE);
                }
            });
        }
    }


    private void initRecycleView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvGank.setLayoutManager(layoutManager);
        mAdapter = new MainListAdapter(this);
        mAdapter.setIClickItem(this);
        mRvGank.setAdapter(mAdapter);

        mRvGank.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= mAdapter.getItemCount() - 4;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData && isLoadMore) {
                    showRefresh();
                    mPresenter.getDataMore();
                } else if (!mHasMoreData) {
                    hasNoMoreData();
                }
            }
        });
    }

}
