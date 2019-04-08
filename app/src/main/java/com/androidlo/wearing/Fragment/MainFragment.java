package com.androidlo.wearing.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlo.wearing.DetailActivity;
import com.androidlo.wearing.MainActivity;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainFragment extends Fragment {


    public static MainFragment sMainFragment;
    private List<BlogData> mBlogDataList;
    private MyAdapter mAdapter;
    private ListManager mListManager;
    private SearchView mSearchView;

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
        mSearchView = ((MainActivity) getActivity()).findViewById(R.id.search_main);
        mSearchView.setQueryHint("输入关键字搜索");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<BlogData> blogDataList = new ArrayList<>();
                blogDataList = mListManager.getObjectList(Constant.KEY_MAIN_FRAGMENT_LIST);
                Iterator<BlogData> it = blogDataList.iterator();
                while (it.hasNext()) {
                    BlogData blogData = it.next();
                    if (blogData.getTitle().contains(query)) {
                        continue;
                    } else {
                        it.remove();
                    }
                }
                mBlogDataList.clear();
                mBlogDataList.addAll(blogDataList);
                mAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
//        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
////                mBlogDataList.clear();
//                mBlogDataList = mListManager.getObjectList(Constant.KEY_MAIN_FRAGMENT_LIST);
//                mAdapter.notifyDataSetChanged();
//                return false;
//            }
//
//        });
        mSearchView.setVisibility(View.VISIBLE);
        mBlogDataList = new ArrayList<>();

        mListManager = new ListManager(getContext());
        initData();
        mAdapter = new MyAdapter(mBlogDataList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new RecyclerItemDecoration(6, 2));


        final List<BlogData> blogDataList;


        mAdapter.setItemEvent(new MyAdapter.ItemEvent() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(),DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_TITLE,mBlogDataList.get(position).getTitle());
                intent.putExtra(DetailActivity.EXTRA_SUMMARIZE,BlogData.getDecodeString(mBlogDataList.get(position).getSummarize()));
                intent.putExtra(DetailActivity.EXTRA_AUTHOR,mBlogDataList.get(position).getAuthor());
                intent.putExtra(DetailActivity.EXTRA_PHOTO,BlogData.getDecodeString(mBlogDataList.get(position).getUri()));
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.my_anim_in, android.R.anim.fade_out);
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
        BlogData blogData1 = new BlogData(UriUtils.resourceIdToUri(getContext(), R.drawable.article1_photo).toString(), getString(R.string.title1), getString(R.string.article1), "小龙女", true, false);
        BlogData blogData2 = new BlogData(UriUtils.resourceIdToUri(getContext(), R.drawable.article2_photo).toString(), getString(R.string.title2), getString(R.string.article2), "小龙女", true, false);
        BlogData blogData3 = new BlogData(UriUtils.resourceIdToUri(getContext(), R.drawable.article3_photo).toString(), getString(R.string.title3), getString(R.string.article3), "小龙女", true, false);
        BlogData blogData4 = new BlogData(UriUtils.resourceIdToUri(getContext(), R.drawable.article4_photo).toString(), getString(R.string.title4), getString(R.string.article4), "Yee", true, false);
        BlogData blogData5 = new BlogData(UriUtils.resourceIdToUri(getContext(), R.drawable.article5_photo).toString(),  getString(R.string.title5), getString(R.string.article5), "Yee", true, false);
        BlogData blogData6 = new BlogData(UriUtils.resourceIdToUri(getContext(), R.drawable.article6_photo).toString(),  getString(R.string.title6), getString(R.string.article6), "Yee", true, false);

        blogDataList.add(blogData1);
        blogDataList.add(blogData2);
        blogDataList.add(blogData3);
        blogDataList.add(blogData4);
        blogDataList.add(blogData5);
        blogDataList.add(blogData6);
        mBlogDataList = mListManager.getObjectList(Constant.KEY_MAIN_FRAGMENT_LIST);
        if (mBlogDataList.isEmpty()) {
            mBlogDataList.addAll(blogDataList);
        }
        mListManager.setObjectList(mBlogDataList, Constant.KEY_MAIN_FRAGMENT_LIST);
    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
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
            mSearchView.setVisibility(View.GONE);
        } else {
            //Fragment显示时调用    
            mSearchView.setVisibility(View.VISIBLE);
            String account = "", jsonBlogData = "";
            List<BlogData> blogDataList;
            //Fragment隐藏时调用
            blogDataList = mListManager.getObjectList(Constant.KEY_MAIN_FRAGMENT_LIST);
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
