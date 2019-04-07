package com.androidlo.wearing;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidlo.wearing.model.BlogData;
import com.androidlo.wearing.model.Constant;
import com.androidlo.wearing.model.ListManager;
import com.androidlo.wearing.model.MyAdapter;
import com.androidlo.wearing.pubUtil.BaseActivity;
import com.androidlo.wearing.pubUtil.RecyclerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MyPublishActivity extends BaseActivity   {


    long firstTime = 0L;

    private ListManager mListManager;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private List<BlogData> mMyPublishList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);

        //标题默认首页
        setCustomTitle(getString(R.string.title_my_publish));

        mRecyclerView = findViewById(R.id.rv_collect_list);

        mListManager = new ListManager(this);
        mMyPublishList = mListManager.getPublishList();

        mAdapter = new MyAdapter(mMyPublishList);
        mAdapter.setItemEvent(new MyAdapter.ItemEvent() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onHeartClick(int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.addItemDecoration(new RecyclerItemDecoration(6, 2));





    }





}
