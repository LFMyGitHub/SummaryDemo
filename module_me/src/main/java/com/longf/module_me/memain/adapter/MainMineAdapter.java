package com.longf.module_me.memain.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.longf.lib_api.entity.MainMeSettingEntity;
import com.longf.module_me.R;

import java.util.List;

public class MainMineAdapter extends BaseQuickAdapter<MainMeSettingEntity.DataBean, BaseViewHolder> {
    private int mHeaderView = 1;
    private List<MainMeSettingEntity.DataBean> dataBeanList;

    public MainMineAdapter(int layoutResId, @Nullable List<MainMeSettingEntity.DataBean> data) {
        super(layoutResId, data);
        this.dataBeanList = data;
    }

    @Override
    public void onBindViewHolder(@Nullable BaseViewHolder holder, int position) {
        if (holder.getItemViewType() != HEADER_VIEW && holder.getItemViewType() != FOOTER_VIEW) {
            //获取分组ID
            int currentID = dataBeanList.get(position - mHeaderView).getGroup_id();
            //获得上一组ID
            int previewID = (position - 1 - mHeaderView) >= 0 ? (dataBeanList.get(position - 1 - mHeaderView).getGroup_id()) : 0;
            //获得下一组ID
            int nextID = (dataBeanList.size() > (position + 1 - mHeaderView)) ? dataBeanList.get(position + 1 - mHeaderView).getGroup_id() : -1;

            if (previewID != (currentID)) {
                holder.getView(R.id.tv_mypageline).setVisibility(View.VISIBLE);
                holder.getView(R.id.mypage_line).setVisibility(View.GONE);
                //判断下一个是不是同组
                if (currentID == nextID) {
                    holder.setBackgroundResource(R.id.rl_mypage_item, R.drawable.bg_top_radius);
                } else {
                    holder.setBackgroundResource(R.id.rl_mypage_item, R.drawable.bg_full_radius);
                }
            } else {
                holder.getView(R.id.tv_mypageline).setVisibility(View.GONE);
                holder.getView(R.id.mypage_line).setVisibility(View.VISIBLE);
                if (currentID == nextID) {
                    holder.setBackgroundResource(R.id.rl_mypage_item, R.drawable.bg_no_radius);
                } else {
                    holder.setBackgroundResource(R.id.rl_mypage_item, R.drawable.bg_bottom_radius);
                }
            }
            holder.setText(R.id.tv_mypage_nickname, dataBeanList.get(position - mHeaderView).getTitle());
            if(dataBeanList.get(position - mHeaderView).getIco().startsWith("http")){
                Glide.with(getContext()).load(dataBeanList.get(position - mHeaderView).getIco()).into((ImageView) holder.getView(R.id.iv_mypage_photo));
            }else {
                int imgID = getContext().getResources().getIdentifier(dataBeanList.get(position - mHeaderView).getIco(),
                        "drawable",getContext().getPackageName());
                holder.setImageResource(R.id.iv_mypage_photo, imgID);
            }
            super.onBindViewHolder(holder, position);
        } else {

        }
    }

    @Override
    protected void convert(@Nullable BaseViewHolder baseViewHolder, MainMeSettingEntity.DataBean dataBean) {

    }
}
