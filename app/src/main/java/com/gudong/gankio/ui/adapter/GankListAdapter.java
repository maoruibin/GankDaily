package com.gudong.gankio.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gudong.gankio.R;
import com.gudong.gankio.data.entity.Gank;
import com.gudong.gankio.ui.activity.WebActivity;
import com.gudong.gankio.util.StringStyleUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by GuDong on 10/9/15 22:53.
 * Contact with 1252768410@qq.com.
 */
public class GankListAdapter extends RecyclerView.Adapter<GankListAdapter.ViewHolder> {

    private List<Gank> mGankList;

    public GankListAdapter() {
        mGankList = new ArrayList<>();
    }

    /**
     * before add data , it will remove history data
     * @param data
     */
    public void updateWithClear(List<Gank> data){
        mGankList.clear();
        mGankList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Gank mGank = mGankList.get(position);
        if (position == 0) {
            showCategory(holder);
        }
        else {
            // 上一个与这一个的目录是否一样
            boolean doesLastAndThis =
                    mGankList.get(position - 1).type.equals(mGankList.get(position).type);
            if (!doesLastAndThis) {
                showCategory(holder);
            }
            else if (holder.mTvCategory.isShown()) holder.mTvCategory.setVisibility(View.GONE);
        }
        holder.mTvCategory.setText(mGank.type);
        if (holder.mTvTitle.getTag() == null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(mGank.desc).append(
                    StringStyleUtils.format(holder.mTvTitle.getContext(), " (via. " + mGank.who + ")",
                            R.style.ViaTextAppearance));
            CharSequence mTvTitleText = builder.subSequence(0, builder.length());
            holder.mTvTitle.setTag(mTvTitleText);
        }
        CharSequence text = (CharSequence) holder.mTvTitle.getTag();
        holder.mTvTitle.setText(text);

        holder.mLlGankParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebActivity.gotoWebActivity(holder.mLlGankParent.getContext(),mGank.url,mGank.desc);
            }
        });
    }

    private void showCategory(ViewHolder holder) {
        if (!holder.mTvCategory.isShown()) holder.mTvCategory.setVisibility(View.VISIBLE);
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
    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_category)
        TextView mTvCategory;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.ll_gank_parent)
        LinearLayout mLlGankParent;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
