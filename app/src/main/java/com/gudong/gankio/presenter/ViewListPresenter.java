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

package com.gudong.gankio.presenter;

import android.app.Activity;

import com.gudong.gankio.data.PrettyGirlData;
import com.gudong.gankio.data.entity.Girl;
import com.gudong.gankio.data.休息视频Data;
import com.gudong.gankio.ui.view.IViewListView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * 首页视图控制器
 * Created by GuDong on 10/29/15 14:07.
 * Contact with gudong.name@gmail.com.
 */
public class ViewListPresenter extends BasePresenter<IViewListView> {

    private int mCurrentPage = 1;

    /**
     * the count of the size of one request
     */
    private static final int PAGE_SIZE = 10;

    public ViewListPresenter(Activity context, IViewListView view) {
        super(context, view);
    }

    public void resetCurrentPage() {
        mCurrentPage = 1;
    }

    /**
     * 只有当前只加载了第一页 那么下拉刷新才应该去执行数据请求，如果加载的页数超过两页，
     * 则不去执行重新加载的数据请求，此时的刷新为假刷新，不去请求数据。这是一种良好的用户体验。愚以为~
     *
     * @return
     */
    public boolean shouldRefillGirls() {
        return mCurrentPage <= 2;
    }

    /**
     * reload girls data it will clear history girls  so bad !
     */
    public void refillGirls() {
        Observable.zip(
                mGuDong.getPrettyGirlData(PAGE_SIZE, mCurrentPage),
                mGuDong.get休息视频Data(PAGE_SIZE, mCurrentPage),
                new Func2<PrettyGirlData, 休息视频Data, PrettyGirlData>() {
                    @Override
                    public PrettyGirlData call(PrettyGirlData prettyGirlData, 休息视频Data 休息视频Data) {
                        return createGirlInfoWith休息视频(prettyGirlData, 休息视频Data);
                    }
                })
                .map(new Func1<PrettyGirlData, List<Girl>>() {
                    @Override
                    public List<Girl> call(PrettyGirlData prettyGirlData) {
                        return prettyGirlData.results;
                    }
                })
                .flatMap(new Func1<List<Girl>, Observable<Girl>>() {
                    @Override
                    public Observable<Girl> call(List<Girl> girls) {
                        return Observable.from(girls);
                    }
                })
                .toSortedList(new Func2<Girl, Girl, Integer>() {
                    @Override
                    public Integer call(Girl girl, Girl girl2) {
                        return girl2.publishedAt.compareTo(girl.publishedAt);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Girl>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErrorView(e);
                        mView.getDataFinish();
                    }

                    @Override
                    public void onNext(List<Girl> girls) {
                        if (girls.isEmpty()) {
                            mView.showEmptyView();
                        } else if (girls.size() < PAGE_SIZE) {
                            mView.fillData(girls);
                            mView.hasNoMoreData();
                        } else if (girls.size() == PAGE_SIZE) {
                            mView.fillData(girls);
                            mCurrentPage++;
                        }
                        mView.getDataFinish();
                    }
                });
    }

    public void getDataMore() {
        Observable.zip(
                mGuDong.getPrettyGirlData(PAGE_SIZE, mCurrentPage),
                mGuDong.get休息视频Data(PAGE_SIZE, mCurrentPage),
                new Func2<PrettyGirlData, 休息视频Data, PrettyGirlData>() {
                    @Override
                    public PrettyGirlData call(PrettyGirlData prettyGirlData, 休息视频Data 休息视频Data) {
                        return createGirlInfoWith休息视频(prettyGirlData, 休息视频Data);
                    }
                })
                .map(new Func1<PrettyGirlData, List<Girl>>() {
                    @Override
                    public List<Girl> call(PrettyGirlData prettyGirlData) {
                        return prettyGirlData.results;
                    }
                })
                .flatMap(new Func1<List<Girl>, Observable<Girl>>() {
                    @Override
                    public Observable<Girl> call(List<Girl> girls) {
                        return Observable.from(girls);
                    }
                })
                .toSortedList(new Func2<Girl, Girl, Integer>() {
                    @Override
                    public Integer call(Girl girl, Girl girl2) {
                        return girl2.publishedAt.compareTo(girl.publishedAt);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Girl>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErrorView(e);
                        mView.getDataFinish();
                    }

                    @Override
                    public void onNext(List<Girl> girls) {
                        if (girls.isEmpty()) {
                            mView.hasNoMoreData();
                        } else if (girls.size() < PAGE_SIZE) {
                            mView.appendMoreDataToView(girls);
                            mView.hasNoMoreData();
                        } else if (girls.size() == PAGE_SIZE) {
                            mView.appendMoreDataToView(girls);
                            mCurrentPage++;
                        }
                        mView.getDataFinish();
                    }
                });
    }

    private PrettyGirlData createGirlInfoWith休息视频(PrettyGirlData girlData, 休息视频Data data) {
        int restSize = data.results.size();
        for (int i = 0; i < girlData.results.size(); i++) {
            if (i <= restSize - 1) {
                Girl girl = girlData.results.get(i);
                girl.desc += " " + data.results.get(i).desc;
            } else {
                break;
            }
        }
        return girlData;
    }
}
