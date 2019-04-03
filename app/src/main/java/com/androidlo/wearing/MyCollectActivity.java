package com.androidlo.wearing;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidlo.wearing.Fragment.CollocationFragment;
import com.androidlo.wearing.Fragment.MainFragment;
import com.androidlo.wearing.Fragment.MineFragment;
import com.androidlo.wearing.Fragment.PublishFragment;
import com.androidlo.wearing.model.BlogData;
import com.androidlo.wearing.model.ListManager;
import com.androidlo.wearing.model.MyAdapter;
import com.androidlo.wearing.pubUtil.BaseActivity;
import com.androidlo.wearing.pubUtil.BottomNavigationViewHelper;
import com.androidlo.wearing.pubUtil.RecyclerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static android.widget.ListPopupWindow.MATCH_PARENT;

public class MyCollectActivity extends BaseActivity   {


    long firstTime = 0L;

    private ListManager mListManager;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private List<BlogData> mBlogDataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);

        //标题默认首页
        setCustomTitle(getString(R.string.title_my_collect));

        mRecyclerView = findViewById(R.id.rv_collect_list);

        mListManager = new ListManager(this);
        mBlogDataList = mListManager.getCollectList();

        mAdapter = new MyAdapter(mBlogDataList);
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
