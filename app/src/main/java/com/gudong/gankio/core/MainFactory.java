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

/**
 * Created by GuDong on 15/10/8.
 * Contact with gudong.name@gmail.com
 */
public class MainFactory {
    /**
     * 数据主机地址
     */
    public static final String HOST = "http://gank.io/api";

    private static GuDong mGuDong;

    protected static final Object monitor = new Object();

    public static GuDong getGuDongInstance(){
        synchronized (monitor){
            if(mGuDong==null){
                mGuDong = new MainRetrofit().getService();
            }
            return mGuDong;
        }
    }
}
