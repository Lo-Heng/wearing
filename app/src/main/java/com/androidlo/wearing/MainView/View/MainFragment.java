package com.androidlo.wearing.MainView.View;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidlo.wearing.MainView.MainActivity;
import com.androidlo.wearing.MainView.MyAdapter;
import com.androidlo.wearing.MainView.model.BlogData;
import com.androidlo.wearing.R;
import com.androidlo.wearing.pubUtil.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import static android.widget.ListPopupWindow.MATCH_PARENT;

public class MainFragment extends Fragment {



    public static MainFragment sMainFragment;
    private List<BlogData> mBlogData;

    public static MainFragment getInstance(){
        if(sMainFragment == null){
            sMainFragment = new MainFragment();
        }
        return sMainFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.rv_main);
        mBlogData = new ArrayList<>();
initData();
        MyAdapter myAdapter = new MyAdapter(mBlogData);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(myAdapter);
        recyclerView.addItemDecoration(new RecyclerItemDecoration(6,2));

        return root;
    }

    private void initData() {
        BlogData blogData1 = new BlogData(R.drawable.main1,"潮流穿搭1","简介简介简介","用户1",true);
        BlogData blogData2 = new BlogData(R.drawable.main2,"潮流穿搭2","简介简介简介","用户2",true);
        BlogData blogData3 = new BlogData(R.drawable.main3,"潮流穿搭3","简介简介简介","用户3",true);
        mBlogData.add(blogData1);
        mBlogData.add(blogData1);
        mBlogData.add(blogData1);
        mBlogData.add(blogData2);
        mBlogData.add(blogData3);
        mBlogData.add(blogData3);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {
        private int itemSpace;
        private int itemNum;

        /**** @param itemSpace item间隔 * @param itemNum 每行item的个数 */
        public RecyclerItemDecoration(int itemSpace, int itemNum) {
            this.itemSpace = itemSpace;
            this.itemNum = itemNum;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = itemSpace;
            if (parent.getChildLayoutPosition(view) % itemNum == 0) { // parent.getChildLayoutPosition(view);//获取view的下标
                outRect.left = 0;
            } else {
                outRect.left = itemSpace;
            }
        }
    }


}
