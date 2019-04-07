package com.androidlo.wearing.model;


import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlo.wearing.R;

import java.util.List;


public class SubscribeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> mUserList;

    public SubscribeAdapter(List<User> list) {
        mUserList = list;
    }

    public void setData(List<User> newHallList) {
        this.mUserList = newHallList;
    }

    @Override
    public int getItemCount() {
        // 返回数据集合大小
        return mUserList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d("MyAdapter", "position" + position);
        //如果是属于ItemViewHolder类

        ItemViewHolder itemViewHolder = ((ItemViewHolder) holder);

        itemViewHolder.bind(mUserList.get(position), position);//显示

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false));

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvName,mTvSign;
        private final ImageView mIvIcon;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mTvName = itemView.findViewById(R.id.tv_name);
            mTvSign = itemView.findViewById(R.id.tv_sign);
            mIvIcon = itemView.findViewById(R.id.iv_icon);

        }

        /**
         * 显示绑定的对应数据
         */
        public void bind(final User user, final int position) {

            mTvName.setText(user.getName());
            mTvSign.setText(user.getSign());
            mIvIcon.setImageURI(Uri.parse(user.getUriStr()));

        }


    }


}