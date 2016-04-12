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

package com.gudong.gankio.core;

import com.gudong.gankio.data.GankData;
import com.gudong.gankio.data.PrettyGirlData;
import com.gudong.gankio.data.休息视频Data;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by GuDong on 9/27/15.
 * Contact with gudong.name@gmail.com
 */
public interface GuDong {

    @GET("/data/福利/{pagesize}/{page}")
    Observable<PrettyGirlData> getPrettyGirlData(@Path("pagesize") int pagesize, @Path("page")
    int page);

    @GET("/data/休息视频/{pagesize}/{page}")
    Observable<休息视频Data> get休息视频Data(@Path("pagesize") int pagesize, @Path("page") int page);

    @GET("/day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path
            ("day") int day);
}
