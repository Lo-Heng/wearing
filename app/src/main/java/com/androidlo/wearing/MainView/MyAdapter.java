package com.androidlo.wearing.MainView;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidlo.wearing.MainView.model.BlogData;
import com.androidlo.wearing.R;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BlogData> mDataList;
    private ItemEvent mItemEvent;


    public MyAdapter(List<BlogData> list) {
        mDataList = list;
    }

    public void setData(List<BlogData> newHallList) {
        this.mDataList = newHallList;
    }

    @Override
    public int getItemCount() {
        // 返回数据集合大小
        return mDataList.size();
    }

    public void setItemEvent(ItemEvent itemEvent) {
        this.mItemEvent = itemEvent;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d("MyAdapter", "position" + position);
        //如果是属于ItemViewHolder类

        ItemViewHolder itemViewHolder = ((ItemViewHolder) holder);

        itemViewHolder.setClick(mItemEvent, position);//设置某一条item的点击事件
        itemViewHolder.bind(mDataList.get(position));//显示

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal, parent, false));

    }

    //回调接口,当item被点击的时候使用
    public interface ItemEvent {
        void onItemCLick(int position);//点击事件
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        /**
         * itemViewHolder内部类
         * 还要增加一个换位的方法
         * 考虑优化速度,如果需要元素换位,是否把位置写入固有属性
         */
        private TextView mTvTitle;
        private TextView mTvHallName, mTvTime;
        private LinearLayout mWarnLine;
        private FrameLayout mFrameLayout;


        public ItemViewHolder(View itemView) {
            super(itemView);

            mTvTitle = itemView.findViewById(R.id.tv_title);


        }

        public void setClick(final MyAdapter.ItemEvent mItemEvent, final int position) {
//            mFrameLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mItemEvent != null)
//                        mItemEvent.onItemCLick(position);
//                }
//            });
        }

        /**
         * 显示绑定的对应数据
         *
         * @param hall
         */
        public void bind(BlogData hall) {
            //
//            mHall1 = hall;
//            mTvHallName.setText(hall.getHall_name());
//            mTvID.setText(String.valueOf(hall.getHall_id()));

//            Log.d("ItemViewHolder","name"+hall.getHall_name()+"isoffline?"+hall.isOffline());

        }


    }


}