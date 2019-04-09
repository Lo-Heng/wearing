package com.androidlo.wearing;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidlo.wearing.model.BlogData;
import com.androidlo.wearing.model.Constant;
import com.androidlo.wearing.model.ListManager;
import com.androidlo.wearing.model.MyAdapter;
import com.androidlo.wearing.model.SubscribeAdapter;
import com.androidlo.wearing.model.User;
import com.androidlo.wearing.pubUtil.BaseActivity;
import com.androidlo.wearing.pubUtil.RecyclerItemDecoration;
import com.androidlo.wearing.pubUtil.SharedPreferencesUtil;
import com.androidlo.wearing.pubUtil.UriUtils;

import java.util.ArrayList;
import java.util.Iterator;
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
        List<BlogData> blogDataList = new ArrayList<>();
        mBlogDataList = mListManager.getCollectList();

        Iterator<BlogData> it = mBlogDataList.iterator();
        while (it.hasNext()) {
            BlogData blogData = it.next();
            String author = blogData.getAuthor();
            if (author.equals(getFileName())){
                continue;
            }
            boolean isDiff = true;
            for(int i =0;i<blogDataList.size();i++){
                if(blogDataList.get(i).getAuthor().equals(author)){
                    isDiff = false;
                }
            }
            if(isDiff){
                blogDataList.add(blogData);
            }
        }
        mBlogDataList.clear();
        mBlogDataList.addAll(blogDataList);
        if(mBlogDataList.isEmpty()){
            showToast("暂无数据");
            return;
        }
        for (int i = 0; i < mBlogDataList.size(); i++) {
            mUserList.add(new User(UriUtils.resourceIdToUri(MySubscribeActivity.this, R.drawable.default_icon).toString(),
                    mBlogDataList.get(i).getAuthor(),"他很懒，没有个性签名"));
        }
    }
    private String getFileName() {
        String account = "";
        account = (String) SharedPreferencesUtil.get(this, getString(R.string.app_name), Constant.KEY_CURRENT_USER, account);
//        account = getString(R.string.app_name);

        return account;


    }

}
