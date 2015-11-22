package com.gudong.gankio.presenter.view;

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
