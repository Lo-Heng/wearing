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
import com.androidlo.wearing.pubUtil.RecyclerItemDecoration;
import com.androidlo.wearing.pubUtil.SharedPreferencesUtil;
import com.androidlo.wearing.pubUtil.UriUtils;
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

        mListManager = new ListManager(getContext());
        initData();
        mAdapter = new MyAdapter(mBlogDataList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new RecyclerItemDecoration(6, 2));



        List<BlogData> blogDataList;
        //Fragment隐藏时调用
//        blogDataList = mListManager.getObjectList();
//        if (blogDataList != null) {
//            mBlogDataList.clear();
//            mBlogDataList.addAll(blogDataList);
//            mAdapter.notifyDataSetChanged();
//        }
        mAdapter.setItemEvent(new MyAdapter.ItemEvent() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onHeartClick(int position) {
                mBlogDataList.get(position).setCollect(!mBlogDataList.get(position).isCollect());
                SharedPreferencesUtil.setDataList(getContext(), getFileName(), Constant.KEY_MAIN_FRAGMENT_LIST, mBlogDataList);

                mAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    private void initData() {
        List<BlogData> blogDataList = new ArrayList<>();
        BlogData blogData1 = new BlogData(UriUtils.resourceIdToUri(getContext(), R.drawable.cap1).toString(), "潮流穿搭1", "简介简介简介", "用户1", true);
        BlogData blogData2 = new BlogData(UriUtils.resourceIdToUri(getContext(), R.drawable.cap1).toString(), "潮流穿搭2", "简介简介简介", "用户2", true);
        BlogData blogData3 = new BlogData(UriUtils.resourceIdToUri(getContext(), R.drawable.cap1).toString(), "潮流穿搭3", "简介简介简介", "用户3", true);
        blogDataList.add(blogData1);
        blogDataList.add(blogData2);
        blogDataList.add(blogData3);

        mBlogDataList = mListManager.getObjectList();
        if(!mBlogDataList.isEmpty()) {
            mBlogDataList.addAll(blogDataList);

        }
        mListManager.setObjectList(mBlogDataList);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Fragment显示时调用    


    }

    private String getFileName() {
        String account = "";
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
            if (blogDataList != null) {
                mBlogDataList.clear();
                mBlogDataList.addAll(blogDataList);
                mAdapter.notifyDataSetChanged();
            }

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
