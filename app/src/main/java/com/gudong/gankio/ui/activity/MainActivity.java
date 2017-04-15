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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.gudong.gankio.R;
import com.gudong.gankio.data.entity.Gank;
import com.gudong.gankio.presenter.MainPresenter;
import com.gudong.gankio.ui.adapter.MainListAdapter;
import com.gudong.gankio.ui.view.IMainView;
import com.gudong.gankio.util.DateUtil;
import com.gudong.gankio.util.DialogUtil;

import java.util.Date;
import java.util.List;

import butterknife.Bind;

/**
 * this Activity is use to MainActivity and when scroll bottom , it will load more data.
 * at the same time, it is also use to show the gank info of one day ( I don't know is it good like this and design it)
 * if getIntent() contains bundle of EXTRA_BUNDLE_GANK ,it indicate this Activity is MainActivity ,
 * otherwise this Activity is a GankActivity used to show Gank info of one day
 */
public class MainActivity extends BaseSwipeRefreshActivity<MainPresenter> implements IMainView<Gank>,MainListAdapter.IClickMainItem {

    @Bind(R.id.rv_gank)
    RecyclerView mRvGank;
    MainListAdapter mAdapter;

    /**
     * the flag of has more data or not
     */
    private boolean mHasMoreData = true;


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
        //check update info by Umeng
        mPresenter.checkVersionInfo();
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
        return R.menu.menu_main;
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
                //DialogUtil.showCustomDialog(this, getSupportFragmentManager(), getString(R.string.action_about), "about_gank_app.html", "about");
                AboutActivity.gotoAboutActivity(this);
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

    private void getData() {
        mPresenter.getData(new Date(System.currentTimeMillis()));
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
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData) {
                    showRefresh();
                    mPresenter.getDataMore();
                } else if (!mHasMoreData) {
                    hasNoMoreData();
                }
            }
        });
    }
}
