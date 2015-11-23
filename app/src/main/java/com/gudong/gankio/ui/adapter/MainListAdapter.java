package com.gudong.gankio.ui.adapter;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gudong.gankio.R;
import com.gudong.gankio.core.GankCategory;
import com.gudong.gankio.data.entity.Gank;
import com.gudong.gankio.ui.BaseActivity;
import com.gudong.gankio.ui.widget.RatioImageView;
import com.gudong.gankio.util.DateUtil;
import com.gudong.gankio.util.StringStyleUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by GuDong on 10/9/15 22:53.
 * Contact with 1252768410@qq.com.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolderItem> {
    private List<Gank> mGankList;
    private BaseActivity mContext;

    private static IClickMainItem mIClickItem;
    //blur meizi
    private static ColorFilter mColorFilter;

    public MainListAdapter(BaseActivity context) {
        mContext = context;
        mGankList = new ArrayList<>();
        mGankList.add(getDefGankGirl());

        float[]array = new float[]{
                1,0,0,0,-70,
                0,1,0,0,-70,
                0,0,1,0,-70,
                0,0,0,1,0,
        };
        mColorFilter = new ColorMatrixColorFilter(new ColorMatrix(array));
    }
    /**
     * before add data , it will remove history data
     * @param data
     */
    public void updateWithClear(List<Gank> data) {
        mGankList.clear();
        update(data);
    }

    /**
     * add data append to history data*
     * @param data new data
     */
    public void update(List<Gank> data) {
        formatGankData(data);
        notifyDataSetChanged();
    }

    /**
     * the type of RecycleView item
     */
    private enum EItemType{
        ITEM_TYPE_GIRL,
        ITEM_TYPE_NORMAL,
        ITEM_TYPE_CATEGOTY;
    }

    @Override
    public int getItemViewType(int position) {
        Gank gank = mGankList.get(position);
        if(gank.is妹子()){
            return EItemType.ITEM_TYPE_GIRL.ordinal();
        }else if(gank.isHeader){
            return EItemType.ITEM_TYPE_CATEGOTY.ordinal();
        }else{
            return EItemType.ITEM_TYPE_NORMAL.ordinal();
        }
    }

    @Override
    public MainListAdapter.ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == EItemType.ITEM_TYPE_GIRL.ordinal()) {
            view = LayoutInflater.from(mContext).inflate(R.layout.gank_item_girl, null);
            return new ViewHolderItemGirl(view);
        }else if(viewType == EItemType.ITEM_TYPE_CATEGOTY.ordinal()){
            view = LayoutInflater.from(mContext).inflate(R.layout.gank_item_category, null);
            return new ViewHolderItemCategory(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.gank_item_normal, null);
            return new ViewHolderItemNormal(view);
        }
    }

    @Override
    public void onBindViewHolder(final MainListAdapter.ViewHolderItem holder, int position) {
        holder.bindItem(mContext, mGankList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }

    /**
     * ViewHolderItem is a parent class for different item
     */
    abstract static class ViewHolderItem extends RecyclerView.ViewHolder {
        public ViewHolderItem(View itemView) {
            super(itemView);
        }
        abstract void bindItem(Context context,Gank gank);
    }

    static class ViewHolderItemNormal extends ViewHolderItem {
        @Bind(R.id.tv_gank_title)
        TextView mTvTitle;
        @Bind(R.id.ll_gank_parent)
        RelativeLayout mRlGankParent;

        ViewHolderItemNormal(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(final Context context,final Gank gank){
            mTvTitle.setText(StringStyleUtils.getGankInfoSequence(context, gank));
            mRlGankParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIClickItem.onClickGankItemNormal(gank, view);
                }
            });
        }
    }

   static class ViewHolderItemCategory extends ViewHolderItem {
        @Bind(R.id.tv_category)
        TextView mTvCategory;

       ViewHolderItemCategory(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

       @Override
       void bindItem(Context context,Gank gank) {
           mTvCategory.setText(gank.type);
       }
   }

    static class ViewHolderItemGirl extends ViewHolderItem {
        @Bind(R.id.tv_video_name)
        TextView mTvTime;
        @Bind(R.id.iv_index_photo)
        RatioImageView mImageView;

        ViewHolderItemGirl(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mImageView.setOriginalSize(200, 100);
        }

        @Override
        void bindItem(Context context,final Gank gank) {
            mTvTime.setText(DateUtil.toDate(gank.publishedAt));
            Picasso.with(context)
                    .load(gank.url)
                    .into(mImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            mImageView.setColorFilter(mColorFilter);
                        }
                        @Override
                        public void onError() {

                        }
                    });

            mTvTime.setText(DateUtil.toDate(gank.publishedAt));
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIClickItem.onClickGankItemGirl(gank, mImageView, mTvTime);
                }
            });
        }
    }

    public void setIClickItem(IClickMainItem IClickItem) {
        mIClickItem = IClickItem;
    }

    public interface IClickMainItem{
        /**
         * click gank girl info item
         * @param gank
         * @param viewImage
         * @param viewText
         */
        void onClickGankItemGirl(Gank gank,View viewImage,View viewText);

        /**
         * click gank normal info item
         * @param gank
         * @param view
         */
        void onClickGankItemNormal(Gank gank,View view);
    }

    /**
     * filter list and add category entity into list
     * @param data source data
     */
    private void formatGankData(List<Gank> data) {
        //Insert headers into list of items.
        String lastHeader = "";
        for (int i = 0; i < data.size(); i++) {
            Gank gank = data.get(i);
            String header = gank.type;
            if (!gank.is妹子() && !TextUtils.equals(lastHeader, header)) {
                // Insert new header view.
                Gank gankHeader = gank.clone();
                lastHeader = header;
                gankHeader.isHeader = true;
                mGankList.add(gankHeader);
            }
            gank.isHeader = false;
            mGankList.add(gank);
        }
    }

    /**
     * get a init Gank entity
     * @return gank entity
     */
    private Gank getDefGankGirl(){
        Gank gank = new Gank();
        gank.publishedAt = new Date(System.currentTimeMillis());
        gank.url = "empty";
        gank.type = GankCategory.福利.name();
        return gank;
    }

}

