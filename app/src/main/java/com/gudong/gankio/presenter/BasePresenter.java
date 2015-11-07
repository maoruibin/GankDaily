package com.gudong.gankio.presenter;

import android.content.Context;

import com.gudong.gankio.core.GuDong;
import com.gudong.gankio.core.MainFactory;
import com.gudong.gankio.presenter.view.IBaseView;

/**
 * Created by GuDong on 10/29/15 14:08.
 * Contact with 1252768410@qq.com.
 */
public class BasePresenter<GV extends IBaseView> {

    protected GV mView;
    /**
     * TODO 这里用是否用Activity待考证
     */
    protected Context mContext;

    public static final GuDong mGuDong = MainFactory.getGuDongInstance();

    public BasePresenter(Context context, GV view) {
        mContext = context;
        mView = view;
    }
}
