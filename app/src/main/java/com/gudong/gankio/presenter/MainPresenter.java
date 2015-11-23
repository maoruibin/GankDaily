package com.gudong.gankio.presenter;

import android.app.Activity;

import com.gudong.gankio.BuildConfig;
import com.gudong.gankio.data.GankData;
import com.gudong.gankio.data.entity.Gank;
import com.gudong.gankio.presenter.view.IMainView;
import com.gudong.gankio.util.AndroidUtils;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * the Presenter of MainActivity
 * Created by GuDong on 11/2/15 14:38.
 * Contact with 1252768410@qq.com.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    private static final int DAY_OF_MILLISECOND = 24*60*60*1000;
    private Date mCurrentDate;
    List<Gank> mGankList = new ArrayList<>();
    private int mCountOfGetMoreDataEmpty = 0;

    public MainPresenter(Activity context, IMainView view) {
        super(context, view);
    }

    /**
     *  if execute getDataMore method more than once ,this flag will be true else false
     */
    private boolean hasLoadMoreData = false;

    public void checkAutoUpdateByUmeng() {
        if(mContext.getIntent().getSerializableExtra("BUNDLE_GANK") == null){
            UmengUpdateAgent.setUpdateCheckConfig(BuildConfig.DEBUG);
            //check update even in 2g/3g/4g condition
            UmengUpdateAgent.setUpdateOnlyWifi(false);
            UmengUpdateAgent.update(mContext);
        }
    }

    //check version info ,if the version info has changed,we need pop a dialog to show change log info
    public void checkVersionInfo() {
        String currentVersion = AndroidUtils.getAppVersion(mContext);
        String localVersionName = AndroidUtils.getLocalVersion(mContext);
        if (!localVersionName.equals(currentVersion)) {
            mView.showChangeLogInfo("changelog.html");
            AndroidUtils.setCurrentVersion(mContext, currentVersion);
        }
    }

    /**
     * @return
     */
    public boolean shouldRefillData(){
        return !hasLoadMoreData;
    }

    public void getData(final Date date) {
        mCurrentDate = date;
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
                        // after get data complete, need put off time one day
                        mCurrentDate = new Date(date.getTime()-DAY_OF_MILLISECOND);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Gank> list) {
                        // some day the data will be return empty like sunday, so we need get after day data
                        if (list.isEmpty()) {
                            getData(new Date(date.getTime()-DAY_OF_MILLISECOND));
                        } else {
                            mCountOfGetMoreDataEmpty = 0;
                            mView.fillData(list);
                        }
                        mView.getDataFinish();
                    }
                });
    }

    public void getDataMore() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCurrentDate);
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
                        // after get data complete, need put off time one day
                        mCurrentDate = new Date(mCurrentDate.getTime()-DAY_OF_MILLISECOND);
                        // now user has execute getMoreData so this flag will be set true
                        //and now when user pull down list we would not refill data
                        hasLoadMoreData = true;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Gank> list) {
                        //when this day is weekend , the list will return empty(weekend has not gank info,the editors need rest)
                        if (list.isEmpty()) {
                            //record count of empty day
                            mCountOfGetMoreDataEmpty += 1;
                            //if empty day is more than five,it indicate has no more data to show
                            if(mCountOfGetMoreDataEmpty>=5){
                                mView.hasNoMoreData();
                            }else{
                                // we need look forward data
                                getDataMore();
                            }
                        } else {
                            mCountOfGetMoreDataEmpty = 0;
                            mView.appendMoreDataToView(list);
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
        if (results.休息视频List != null) mGankList.addAll(results.休息视频List);
        // make meizi data is in first position
        if (results.妹纸List != null) mGankList.addAll(0, results.妹纸List);
        return mGankList;
    }


}
