package com.gudong.gankio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuDong on 9/28/15.
 * Contact with 1252768410@qq.com
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<T> mDataList = new ArrayList<>();

    public BaseListAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void addAll(List<T> dataList){
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
