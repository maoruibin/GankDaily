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
 * Created by GuDong on 11/2/15 14:39.
 * Contact with gudong.name@gmail.com.
 */
public interface IMainView<T extends Soul> extends ISwipeRefreshView {
    /**
     * load data successfully
     *
     * @param data
     */
    void fillData(List<T> data);

    /**
     * append data to history list(load more)
     *
     * @param data
     */
    void appendMoreDataToView(List<T> data);

    /**
     * no more data for show and this condition is hard to appear,it need you scroll main view
     * long time
     * I think it has no body do it like this ,even though，I deal this condition also, In case
     * someone does it.
     */
    void hasNoMoreData();

    /**
     * show change log info in a dialog
     *
     * @param assetFileName the name of local html file like "changelog.html"
     */
    void showChangeLogInfo(String assetFileName);
}
