package com.gudong.gankio.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gudong.gankio.R;
import com.gudong.gankio.core.GankType;
import com.gudong.gankio.data.entity.Gank;
import com.gudong.gankio.ui.activity.WebActivity;
import com.gudong.gankio.ui.widget.RatioImageView;
import com.gudong.gankio.util.DateUtil;
import com.gudong.gankio.util.StringStyleUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by GuDong on 10/9/15 22:53.
 * Contact with 1252768410@qq.com.
 */
public class GankListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Gank> mGankList;
    private Context mContext;
    public GankListAdapter(Context context) {
        mContext = context;
        mGankList = new ArrayList<>();
    }

    /**
     * before add data , it will remove history data
     *
     * @param data
     */
    public void updateWithClear(List<Gank> data) {
        mGankList.clear();
        mGankList.addAll(data);
        notifyDataSetChanged();
    }


    /**
     *  add data append to history data
     * @param data new data
     */
    public void update(List<Gank> data){
        mGankList.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //目前只是用两种类型 ==0 是妹子福利图片类型
        if(viewType == GankType.福利.ordinal()){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_item_head, null);
            return new ViewHolderHeader福利(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_item, null);
            return new ViewHolderNormalItem(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderHeader福利){
            final ViewHolderHeader福利 viewHolderHeader福利 = (ViewHolderHeader福利) holder;
            final Gank mGank = mGankList.get(position);

            Picasso.with(mContext).load(mGank.url).into(viewHolderHeader福利.mImageView);
            viewHolderHeader福利.mTvTime.setText(DateUtil.toDate(mGank.publishedAt));

        }else if(holder instanceof ViewHolderNormalItem){
            final ViewHolderNormalItem viewHolderNormalItem = (ViewHolderNormalItem) holder;
            final Gank mGank = mGankList.get(position);

            if (position == 0) {
//                showCategory(viewHolderNormalItem);
            } else {
                // 上一个与这一个的目录是否一样
                boolean doesLastAndThis =
                        mGankList.get(position - 1).type.equals(mGankList.get(position).type);
                if (!doesLastAndThis) {
                    showCategory(viewHolderNormalItem);
                } else if (viewHolderNormalItem.mTvCategory.isShown())
                    viewHolderNormalItem.mTvCategory.setVisibility(View.GONE);
            }
            viewHolderNormalItem.mTvCategory.setText(mGank.type);
            if (viewHolderNormalItem.mTvTitle.getTag() == null) {
                SpannableStringBuilder builder = new SpannableStringBuilder(mGank.desc).append(
                        StringStyleUtils.format(viewHolderNormalItem.mTvTitle.getContext(), " (via. " + mGank.who + ")",
                                R.style.ViaTextAppearance));
                CharSequence mTvTitleText = builder.subSequence(0, builder.length());
                viewHolderNormalItem.mTvTitle.setTag(mTvTitleText);
            }
            CharSequence text = (CharSequence) viewHolderNormalItem.mTvTitle.getTag();
            viewHolderNormalItem.mTvTitle.setText(text);

            viewHolderNormalItem.mLlGankParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebActivity.gotoWebActivity(viewHolderNormalItem.mLlGankParent.getContext(), mGank.url, mGank.desc);
                }
            });

        }

    }

    private void showCategory(ViewHolderNormalItem holder) {
        if (!holder.mTvCategory.isShown()) holder.mTvCategory.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemViewType(int position) {
        String type = mGankList.get(position).type;
        int itemType = 0;
        switch (type) {
            case "福利":
                itemType = GankType.福利.ordinal();
                break;
            case "iOS":
                itemType = GankType.iOS.ordinal();
                break;
            case "Android":
                itemType = GankType.Android.ordinal();
                break;
            case "App":
                itemType = GankType.App.ordinal();
                break;
            case "拓展资源":
                itemType = GankType.拓展资源.ordinal();
                break;
            case "瞎推荐":
                itemType = GankType.瞎推荐.ordinal();
                break;
            case "休息视频":
                itemType = GankType.休息视频.ordinal();
                break;
        }
        return itemType;
    }
    @Override
    public int getItemCount() {
        return mGankList.size();
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'mTvTitle_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolderNormalItem extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_category)
        TextView mTvCategory;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.ll_gank_parent)
        LinearLayout mLlGankParent;

        ViewHolderNormalItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 头部图片holder
     */
    static class ViewHolderHeader福利 extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_time)
        TextView mTvTime;
        @Bind(R.id.iv_index_photo)
        RatioImageView mImageView;

        ViewHolderHeader福利(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mImageView.setOriginalSize(200, 100);
        }
    }
}
