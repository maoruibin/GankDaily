package com.gudong.gankio.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.gudong.gankio.R;
import com.gudong.gankio.data.entity.Gank;
import com.gudong.gankio.presenter.GankDetailPresenter;
import com.gudong.gankio.presenter.view.IGankDetailView;
import com.gudong.gankio.ui.BaseSwipeRefreshActivity;
import com.gudong.gankio.ui.adapter.GankListAdapter;
import com.gudong.gankio.util.DateUtil;

import java.util.Date;
import java.util.List;

import butterknife.Bind;

public class GankDetailActivity extends BaseSwipeRefreshActivity<GankDetailPresenter> implements IGankDetailView<Gank>{
    private static final String EXTRA_BUNDLE_DATE = "BUNDLE_DATE";

    @Bind(R.id.rv_gank)
    RecyclerView mRvGank;

    GankListAdapter mAdapter;

    Date mDate;

    public static void gotoGankActivity(Context context, Date date) {
        Intent intent = new Intent(context, GankDetailActivity.class);
        intent.putExtra(EXTRA_BUNDLE_DATE, date);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_gank;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_gank;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDate = (Date) getIntent().getSerializableExtra(EXTRA_BUNDLE_DATE);
        setTitle(DateUtil.toDate(mDate), true);

        initRecycleView();
        initData();
    }

    private void initData(){
        mPresenter.getData(mDate);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GankDetailPresenter(this,this);
    }

    @Override
    protected void onRefreshStarted() {
        initData();
    }

    @Override
    public void fillData(List<Gank> data) {
        mAdapter.updateWithClear(data);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvGank.setLayoutManager(layoutManager);
        mAdapter = new GankListAdapter();
        mRvGank.setAdapter(mAdapter);
    }
}
