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

package com.gudong.gankio.data.entity;

import com.gudong.gankio.core.GankCategory;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by GuDong on 15/10/8.
 * Contact with gudong.name@gmail.com
 */
public class Gank extends Soul implements Cloneable,Serializable{

    /**
     * createdAt : 2015-10-06T08:23:35.565Z
     * publishedAt : 2015-10-08T01:29:48.811Z
     * used : true
     * type : 休息视频
     * url : http://v.youku.com/v_show/id_XMTY4OTE3OTQ4.html
     * who : lxxself
     * desc : 耐克还有这广告，我良辰服气
     * updatedAt : 2015-10-08T01:29:49.219Z
     */

    public boolean used;
    public String type;
    public String url;
    public String who;
    public String desc;
    public Date updatedAt;
    public Date createdAt;
    public Date publishedAt;

    /**
     * this item is header type of gank or not,if true, this item will show category name
     */
    public boolean isHeader;

    public boolean is妹子(){
        return type.equals(GankCategory.福利.name());
    }

    @Override
    public Gank clone() {
        Gank gank = null;
        try{
            gank = (Gank)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return gank;
    }
}
