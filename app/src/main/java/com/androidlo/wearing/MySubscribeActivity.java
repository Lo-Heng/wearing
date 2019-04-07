package com.androidlo.wearing;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidlo.wearing.model.BlogData;
import com.androidlo.wearing.model.ListManager;
import com.androidlo.wearing.model.MyAdapter;
import com.androidlo.wearing.model.SubscribeAdapter;
import com.androidlo.wearing.model.User;
import com.androidlo.wearing.pubUtil.BaseActivity;
import com.androidlo.wearing.pubUtil.RecyclerItemDecoration;
import com.androidlo.wearing.pubUtil.UriUtils;

import java.util.ArrayList;
import java.util.List;

public class MySubscribeActivity extends BaseActivity {


    long firstTime = 0L;

    private ListManager mListManager;
    private RecyclerView mRecyclerView;
    private SubscribeAdapter mAdapter;
    private List<BlogData> mBlogDataList = new ArrayList<>();
    private List<User> mUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);

        //标题默认首页
        setCustomTitle(getString(R.string.title_my_subscribe));

        mRecyclerView = findViewById(R.id.rv_collect_list);

        mListManager = new ListManager(this);

        initData();
        mAdapter = new SubscribeAdapter(mUserList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void initData() {
        mBlogDataList = mListManager.getCollectList();
        if(mBlogDataList.isEmpty()){
            showToast("暂无数据");
            return;
        }
        for (int i = 0; i < mBlogDataList.size(); i++) {
            mUserList.add(new User(UriUtils.resourceIdToUri(MySubscribeActivity.this, R.drawable.cap1).toString(),
                    mBlogDataList.get(i).getAuthor(),"他很懒，没有个性签名"));
        }
    }


}
