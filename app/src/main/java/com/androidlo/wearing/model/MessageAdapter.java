package com.androidlo.wearing.model;


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


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> mMessageList;



    public MessageAdapter(List<Message> list) {
        mMessageList = list;
    }

    public void setData(List<Message> newHallList) {
        this.mMessageList = newHallList;
    }

    @Override
    public int getItemCount() {
        // 返回数据集合大小
        return mMessageList.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d("MyAdapter", "position" + position);
        //如果是属于ItemViewHolder类

        ItemViewHolder itemViewHolder = ((ItemViewHolder) holder);

        itemViewHolder.bind(mMessageList.get(position), position);//显示

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false));

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {


        private final TextView mTvName;
        private final TextView mTvContent;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mTvName = itemView.findViewById(R.id.tv_name);
            mTvContent = itemView.findViewById(R.id.tv_content);

        }



        /**
         * 显示绑定的对应数据
         */
        public void bind(final Message message, final int position) {

            mTvName.setText(message.getName());
            mTvContent.setText(message.getContent());

        }


    }


}