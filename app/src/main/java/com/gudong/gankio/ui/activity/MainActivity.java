package com.gudong.gankio.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CheckResult;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.gudong.gankio.R;
import com.gudong.gankio.data.entity.Girl;
import com.gudong.gankio.presenter.MainPresenter;
import com.gudong.gankio.presenter.view.IMainView;
import com.gudong.gankio.ui.BaseSwipeRefreshActivity;
import com.gudong.gankio.ui.adapter.MainListAdapter;
import com.gudong.gankio.util.DialogUtil;

import java.util.List;

import butterknife.Bind;


/**
 * the index view of app
 * Created by GuDong on 9/27/15.
 * Contact with 1252768410@qq.com
 */
public class MainActivity extends BaseSwipeRefreshActivity<MainPresenter> implements MainListAdapter.IClickItem,IMainView<Girl> {

    @Bind(R.id.rcv_index_content)
    RecyclerView mRcvIndexContent;

    private MainListAdapter mAdapter;
    private boolean mIsFirstTimeTouchBottom = true;
    private boolean mHasMoreData = true;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecycleView();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(this,this);
    }

    private void initRecycleView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRcvIndexContent.setLayoutManager(layoutManager);
        mAdapter = new MainListAdapter(this);
        mAdapter.setIClickItem(this);
        mRcvIndexContent.setAdapter(mAdapter);

        mRcvIndexContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= mAdapter.getItemCount() - 4;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData) {
                    if (!mIsFirstTimeTouchBottom) {
                        showRefresh();
                        mPresenter.getDataMore();
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        });
    }

    @Override protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // make swipeRefreshLayout visible manually
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showRefresh();
            }
        }, 558);

        mPresenter.refillGirls();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_github_tending:
                String url = getString(R.string.url_github_trending);
                String title = getString(R.string.action_github_trending);
                WebActivity.gotoWebActivity(this,url,title);
                break;
            case R.id.action_about:
                DialogUtil.showCustomDialog(this, getSupportFragmentManager(), getString(R.string.action_about), "about.html", "about");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    @CheckResult
    protected boolean prepareRefresh() {
        if(mPresenter.shouldRefillGirls()){
            mPresenter.resetCurrentPage();
            if(!isRefreshing()){
                showRefresh();
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onRefreshStarted() {
        mPresenter.refillGirls();
    }

    @Override
    public void onClickPhoto(int position, View view) {
        Girl clickGirl = mAdapter.getGirl(position);
        if(clickGirl!=null){
            String[]array = clickGirl.desc.split(" ");
            String title = getString(R.string.app_name);
            if(array.length>=2){
                title = array[1];
            }
            GirlFaceActivity.gotoWatchGirlDetail(this, clickGirl.url, title);
        }
    }

    @Override
    public void onClickDesc(int position, View view) {
        Girl clickGirl = mAdapter.getGirl(position);
        if(clickGirl!=null){
            GankDetailActivity.gotoGankActivity(this, clickGirl.publishedAt);
        }
    }

    @Override
    public void appendMoreDataToView(List<Girl> data) {
        mAdapter.update(data);
    }

    @Override
    public void fillData(List<Girl> data) {
        mAdapter.updateWithClear(data);
    }

    @Override
    public void showEmptyView() {
        Snackbar.make(mRcvIndexContent, R.string.empty_data_of_girls,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorView(Throwable throwable){
        throwable.printStackTrace();

        final Snackbar errorSnack = Snackbar.make(mRcvIndexContent, R.string.error_index_load,Snackbar.LENGTH_INDEFINITE);
        errorSnack.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorSnack.dismiss();
                onRefreshStarted();
            }
        });
        errorSnack.show();
    }

    @Override
    public void hasNoMoreData() {
        mHasMoreData = false;
        Snackbar.make(mRcvIndexContent, R.string.no_more_girls,Snackbar.LENGTH_SHORT)
                .setAction(R.string.action_to_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (mRcvIndexContent.getLayoutManager()).smoothScrollToPosition(mRcvIndexContent,null,0);
                    }
                })
                .show();
    }

}
