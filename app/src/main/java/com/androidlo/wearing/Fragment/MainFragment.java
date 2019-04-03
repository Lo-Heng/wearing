package com.androidlo.wearing.Fragment;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlo.wearing.model.ListManager;
import com.androidlo.wearing.model.MyAdapter;
import com.androidlo.wearing.model.BlogData;
import com.androidlo.wearing.model.Constant;
import com.androidlo.wearing.R;
import com.androidlo.wearing.pubUtil.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {


    public static MainFragment sMainFragment;
    private List<BlogData> mBlogDataList;
    private MyAdapter mAdapter;
    private ListManager mListManager;

    public static MainFragment getInstance() {
        if (sMainFragment == null) {
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
        mBlogDataList = new ArrayList<>();
        initData();
        mListManager = new ListManager(getContext());
        mAdapter = new MyAdapter(mBlogDataList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new RecyclerItemDecoration(6, 2));


        String account = "", jsonBlogData = "";
        List<BlogData> blogDataList;
        //Fragment隐藏时调用
        blogDataList = mListManager.getObjectList();
        if(blogDataList != null){
            mBlogDataList.clear();
            mBlogDataList.addAll(blogDataList);
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setItemEvent(new MyAdapter.ItemEvent() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onHeartClick(int position) {
                mBlogDataList.get(position).setCollect(!mBlogDataList.get(position).isCollect());
                mAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    private void initData() {
//        BlogData blogData1 = new BlogData(getResources().getDrawable(R.drawable.main1), "潮流穿搭1", "简介简介简介", "用户1", true);
//        BlogData blogData2 = new BlogData(getResources().getDrawable(R.drawable.main2), "潮流穿搭2", "简介简介简介", "用户2", true);
//        BlogData blogData3 = new BlogData(getResources().getDrawable(R.drawable.main3), "潮流穿搭3", "简介简介简介", "用户3", true);
//        mBlogDataList.add(blogData1);
//        mBlogDataList.add(blogData2);
//        mBlogDataList.add(blogData3);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Fragment显示时调用    


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

    private String getFileName(){
        String account ="";
        account = (String) SharedPreferencesUtil.get(getContext(), getString(R.string.app_name), Constant.KEY_CURRENT_USER, account);
        return account;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {

        } else {
            //Fragment显示时调用    
            String account = "", jsonBlogData = "";
            List<BlogData> blogDataList;
            //Fragment隐藏时调用
            blogDataList = mListManager.getObjectList();
            if(blogDataList != null){
                mBlogDataList.clear();
                mBlogDataList.addAll(blogDataList);
                mAdapter.notifyDataSetChanged();
            }
//            jsonBlogData = (String) SharedPreferencesUtil.get(getContext(), account, Constant.KEY_PUBLISH_BLOG, jsonBlogData);
//
//            if (jsonBlogData != null && !jsonBlogData.isEmpty()) {
//                Gson gson = new GsonBuilder()
//                        .registerTypeAdapter(Uri.class, new UriDeserializer())
//                        .create();
//                blogData = gson.fromJson(jsonBlogData, BlogData.class);
//
//                mBlogDataList.add(blogData);
//                SharedPreferencesUtil.remove(getContext(),account,Constant.KEY_PUBLISH_BLOG);//删除
//                mAdapter.notifyDataSetChanged();
//
//            }
        }
    }



    public class UriSerializer implements JsonSerializer<Uri> {
        public JsonElement serialize(Uri src, Type typeOfSrc,
                                     JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }

    public class UriDeserializer implements JsonDeserializer<Uri> {
        @Override
        public Uri deserialize(final JsonElement src, final Type srcType,
                               final JsonDeserializationContext context) throws JsonParseException {
            return Uri.parse(src.getAsString());
        }
    }


}
