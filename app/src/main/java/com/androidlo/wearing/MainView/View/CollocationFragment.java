package com.androidlo.wearing.MainView.View;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlo.wearing.MainView.MyAdapter;
import com.androidlo.wearing.MainView.model.BlogData;
import com.androidlo.wearing.R;

import java.util.ArrayList;
import java.util.List;

public class CollocationFragment extends Fragment {



    public static CollocationFragment sMainFragment;

    public static CollocationFragment getInstance(){
        if(sMainFragment == null){
            sMainFragment = new CollocationFragment();
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
        List<BlogData> blogData = new ArrayList<>();
        BlogData blogData1 = new BlogData();
        blogData.add(blogData1);
        blogData.add(blogData1);

        MyAdapter myAdapter = new MyAdapter(blogData);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(myAdapter);
        recyclerView.addItemDecoration(new RecyclerItemDecoration(6,3));

        return root;
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
