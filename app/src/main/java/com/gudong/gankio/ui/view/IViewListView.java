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

package com.gudong.gankio.ui.view;

import com.gudong.gankio.data.entity.Soul;

import java.util.List;

/**
 * index view
 * Created by GuDong on 10/29/15 14:10.
 * Contact with 1252768410@qq.com.
 */
public interface IViewListView<T extends Soul> extends ISwipeRefreshView{
    /**
     * load data successfully
     * @param data
     */
    void fillData(List<T> data);

    /**
     * append data to history list(load more)
     * @param data
     */
    void appendMoreDataToView(List<T> data);

    /**
     * no more data
     */
    void hasNoMoreData();

}
