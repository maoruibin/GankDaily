package com.gudong.gankio.presenter.view;

import com.gudong.gankio.data.entity.Soul;

import java.util.List;

/**
 * Created by GuDong on 11/2/15 14:39.
 * Contact with 1252768410@qq.com.
 */
public interface IGankDetailView<T extends Soul>  extends ISwipeRefreshView {
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
}
