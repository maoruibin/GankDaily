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

package com.gudong.gankio.data;

import com.gudong.gankio.data.entity.Gank;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GuDong on 10/9/15 22:20.
 * Contact with gudong.name@gmail.com.
 */
public class GankData extends BaseData {

    public List<String> category;
    public Result results;

    public class Result {
        @SerializedName("Android")
        public List<Gank> androidList;
        @SerializedName("休息视频")
        public List<Gank> 休息视频List;
        @SerializedName("iOS")
        public List<Gank> iOSList;
        @SerializedName("福利")
        public List<Gank> 妹纸List;
        @SerializedName("拓展资源")
        public List<Gank> 拓展资源List;
        @SerializedName("瞎推荐")
        public List<Gank> 瞎推荐List;
        @SerializedName("App")
        public List<Gank> appList;
    }
}
