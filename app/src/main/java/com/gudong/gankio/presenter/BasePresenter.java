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

import com.gudong.gankio.core.GuDong;
import com.gudong.gankio.core.MainFactory;
import com.gudong.gankio.ui.view.IBaseView;

/**
 * Created by GuDong on 10/29/15 14:08.
 * Contact with gudong.name@gmail.com.
 */
public class BasePresenter<GV extends IBaseView> {

    protected GV mView;
    /**
     * TODO 这里用是否用Activity待考证
     */
    protected Activity mContext;

    public static final GuDong mGuDong = MainFactory.getGuDongInstance();

    public BasePresenter(Activity context, GV view) {
        mContext = context;
        mView = view;
    }
}
