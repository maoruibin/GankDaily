package com.gudong.gankio.ui.activity;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.gudong.gankio.R;
import com.gudong.gankio.data.entity.Gank;
import com.gudong.gankio.data.entity.Girl;
import com.gudong.gankio.presenter.ViewListPresenter;
import com.gudong.gankio.presenter.view.IViewListView;
import com.gudong.gankio.ui.BaseSwipeRefreshActivity;
import com.gudong.gankio.ui.adapter.ViewListAdapter;

import java.util.List;

import butterknife.Bind;


/**
 * show all meizis on this activity ,enjoy it
 * Created by GuDong on 9/27/15.
 * Contact with 1252768410@qq.com
 */
public class ViewListActivity extends BaseSwipeRefreshActivity<ViewListPresenter> implements ViewListAdapter.IClickItem,IViewListView<Girl> {

    @Bind(R.id.rcv_index_content)
    RecyclerView mRcvIndexContent;

    private ViewListAdapter mAdapter;
    private boolean mHasMoreData = true;

    @Override
    protected int getLayout() {
        return R.layout.activity_view_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_activity_view_list),true);
        initRecycleView();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new ViewListPresenter(this,this);
    }

    private void initRecycleView(){
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRcvIndexContent.setLayoutManager(layoutManager);
        mAdapter = new ViewListAdapter(this);
        mAdapter.setIClickItem(this);
        mRcvIndexContent.setAdapter(mAdapter);

        mRcvIndexContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1]
                                >= mAdapter.getItemCount() - 4;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData) {
                    showRefresh();
                    mPresenter.getDataMore();
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mPresenter.refillGirls();
    }

    @Override @CheckResult
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
    public void onClickPhoto(int position, View viewImage,View viewText) {
        Girl clickGirl = mAdapter.getGirl(position);
        if(clickGirl!=null){
            Gank gank = new Gank();
            gank.type = clickGirl.type;
            gank.url = clickGirl.url;
            gank.publishedAt = clickGirl.publishedAt;
            MainActivity.gotoGankActivity(this,gank,false,viewImage,viewText);
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
