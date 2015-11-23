package com.gudong.gankio.ui.adapter;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.gudong.gankio.R;
import com.gudong.gankio.data.entity.Girl;
import com.gudong.gankio.ui.widget.RatioImageView;
import com.gudong.gankio.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主页数据显示Adapter
 * Created by GuDong on 9/28/15.
 * Contact with 1252768410@qq.com
 */
public class ViewListAdapter extends RecyclerView.Adapter<ViewListAdapter.ViewHolder> {
    private List<Girl> mListData;
    private Context mContext;
    private IClickItem mIClickItem;
    //blur meizi
    private static ColorFilter mColorFilter;
    public ViewListAdapter(Context context) {
        mContext = context;
        mListData = new ArrayList<>();

        float[]array = new float[]{
                1,0,0,0,-70,
                0,1,0,0,-70,
                0,0,1,0,-70,
                0,0,0,1,0,
        };
        mColorFilter = new ColorMatrixColorFilter(new ColorMatrix(array));
    }

    public void setIClickItem(IClickItem IClickItem) {
        mIClickItem = IClickItem;
    }

    /**
     * before add data , it will remove history data
     * @param data
     */
    public void updateWithClear(List<Girl> data){
        mListData.clear();
        mListData.addAll(data);
        notifyDataSetChanged();
    }

    /**
     *  add data append to history data
     * @param data new data
     */
    public void update(List<Girl> data){
        mListData.addAll(data);
        notifyDataSetChanged();
    }

    public Girl getGirl(int position){
        return mListData.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        Girl entity = mListData.get(position);

        Glide.with(mContext)
                .load(entity.url)
                .centerCrop()
                .into(holder.mIvIndexPhoto)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        holder.mIvIndexPhoto.setColorFilter(mColorFilter);
                    }
                });
        holder.mTvTime.setText(DateUtil.toDate(entity.publishedAt));
        if(mIClickItem!=null){
            holder.mIvIndexPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIClickItem.onClickPhoto(position, holder.mIvIndexPhoto,holder.mTvTime);
                }
            });
        }
    }

    public interface IClickItem{
        void onClickPhoto(int position,View view,View textView);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'index_listitem.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_index_photo)
        RatioImageView mIvIndexPhoto;
        @Bind(R.id.tv_time)
        TextView mTvTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            mIvIndexPhoto.setOriginalSize(50,50);
        }
    }

}
