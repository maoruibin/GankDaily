package com.gudong.gankio.presenter.view;

/**
 * if you implement ISwipeRefreshView , you view should be a list view
 * Created by GuDong on 10/29/15 18:08.
 * Contact with 1252768410@qq.com.
 */
public interface ISwipeRefreshView extends IBaseView {


    void getDataFinish();

    void showEmptyView();

    void showErrorView(Throwable throwable);

    void showRefresh();

    void hideRefresh();
}
