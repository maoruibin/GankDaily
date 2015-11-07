package com.gudong.gankio.presenter;

import android.content.Context;

import com.gudong.gankio.data.GankData;
import com.gudong.gankio.data.entity.Gank;
import com.gudong.gankio.presenter.view.IGankDetailView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by GuDong on 11/2/15 14:38.
 * Contact with 1252768410@qq.com.
 */
public class GankDetailPresenter extends BasePresenter<IGankDetailView> {

    List<Gank> mGankList = new ArrayList<>();

    public GankDetailPresenter(Context context, IGankDetailView view) {
        super(context, view);
    }

    public void getData(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mGuDong.getGankData(year, month, day)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GankData, GankData.Result>() {
                    @Override
                    public GankData.Result call(GankData gankData) {
                        return gankData.results;
                    }
                })
                .map(new Func1<GankData.Result, List<Gank>>() {
                    @Override
                    public List<Gank> call(GankData.Result result) {
                        return addAllResults(result);
                    }
                })
                .subscribe(new Subscriber<List<Gank>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Gank> list) {
                        if (list.isEmpty()) {
                            mView.showEmptyView();
                        } else {
                            mView.fillData(list);
                        }
                        mView.getDataFinish();
                    }
                });

    }

    private List<Gank> addAllResults(GankData.Result results) {
        mGankList.clear();
        if (results.androidList != null) mGankList.addAll(results.androidList);
        if (results.iOSList != null) mGankList.addAll(results.iOSList);
        if (results.appList != null) mGankList.addAll(results.appList);
        if (results.拓展资源List != null) mGankList.addAll(results.拓展资源List);
        if (results.瞎推荐List != null) mGankList.addAll(results.瞎推荐List);
        if (results.休息视频List != null) mGankList.addAll(0, results.休息视频List);
        return mGankList;
    }

}
