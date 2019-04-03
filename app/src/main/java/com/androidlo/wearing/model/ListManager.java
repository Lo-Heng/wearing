package com.androidlo.wearing.model;

import android.content.Context;

import com.androidlo.wearing.R;
import com.androidlo.wearing.pubUtil.SharedPreferencesUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ListManager {
    Context context;
    public ListManager() {

    }

    public ListManager(Context context){
        this.context = context;
    }
    private String getFileName(){
        String account ="";
        account = (String) SharedPreferencesUtil.get(context, context.getString(R.string.app_name), Constant.KEY_CURRENT_USER, account);
        return account;
    }
    //定制json解析器
    public List<BlogData> getObjectList() {
        String jsonString = SharedPreferencesUtil.getDataList(context, getFileName(), Constant.KEY_MAIN_FRAGMENT_LIST).toString().trim();
        ;
        List<BlogData> blogDataList = new ArrayList<>();
        if (jsonString != null && jsonString.isEmpty()) {
            return null;
        } else {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    BlogData blogData = new BlogData();
                    blogData.setAuthor(jsonArray.getJSONObject(i).getString("author"));
                    blogData.setCollect(jsonArray.getJSONObject(i).getBoolean("isCollect"));
                    blogData.setSummarize(jsonArray.getJSONObject(i).getString("summarize"));
                    blogData.setTitle(jsonArray.getJSONObject(i).getString("title"));
                    String uriStr = jsonArray.getJSONObject(i).getString("uri");

                    blogData.setUri(uriStr);

                    blogDataList.add(blogData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return blogDataList;
    }



}
