package com.gudong.gankio.core;

import com.gudong.gankio.data.GankData;
import com.gudong.gankio.data.PrettyGirlData;
import com.gudong.gankio.data.休息视频Data;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by GuDong on 9/27/15.
 * Contact with 1252768410@qq.com
 */
public interface GuDong {

    @GET("/data/福利/{pagesize}/{page}")
    Observable<PrettyGirlData> getPrettyGirlData(@Path("pagesize") int pagesize,@Path("page") int page);

    @GET("/data/休息视频/{pagesize}/{page}")
    Observable<休息视频Data> get休息视频Data(@Path("pagesize") int pagesize,@Path("page")int page);

    @GET("/day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year")int year,@Path("month")int month,@Path("day")int day);
}
