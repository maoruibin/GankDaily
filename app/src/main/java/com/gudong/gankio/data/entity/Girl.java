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


import java.util.Date;

/**
 * Created by GuDong on 15/10/8.
 * Contact with 1252768410@qq.com
 */
public class Girl extends Soul {

    /**
     * createdAt : 2015-10-07T05:42:23.910Z
     * publishedAt : 2015-10-08T01:29:48.812Z
     * used : true
     * type : 福利
     * url : http://ww4.sinaimg.cn/large/7a8aed7bgw1ewsip9thfoj20hs0qo774.jpg
     * who : 张涵宇
     * desc : 10.8
     * updatedAt : 2015-10-08T01:29:49.400Z
     */

    public boolean used;
    public String type;
    public String url;
    public String who;
    public String desc;
    public Date createdAt;
    public Date publishedAt;
    public Date updatedAt;
}
