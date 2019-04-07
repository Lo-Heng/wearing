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
    public List<BlogData> getObjectList(String key) {
        String jsonString = SharedPreferencesUtil.getDataList(context, getFileName(), key).toString().trim();
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
                    blogData.setMyPublish(jsonArray.getJSONObject(i).getBoolean("isMyPublish"));
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

    //获取我的收藏
    public List<BlogData> getCollectList(){
        List<BlogData> blogDataList = new ArrayList<>();
        List<BlogData> collectDataList = new ArrayList<>();
        blogDataList.addAll(getObjectList(Constant.KEY_MAIN_FRAGMENT_LIST));

        for(int i=0;i<blogDataList.size();i++){
            if(blogDataList.get(i).isCollect()){
                collectDataList.add(blogDataList.get(i));
            }
        }
        return collectDataList;
    }
    //获取我的收藏
    public List<BlogData> getPublishList(){
        List<BlogData> blogDataList = new ArrayList<>();
        List<BlogData> publishList = new ArrayList<>();
        blogDataList.addAll(getObjectList(Constant.KEY_MAIN_FRAGMENT_LIST));

        for(int i=0;i<blogDataList.size();i++){
            if(blogDataList.get(i).isMyPublish()){
                publishList.add(blogDataList.get(i));
            }
        }
        return publishList;
    }

    public void setObjectList(List<BlogData> blogDataList,String key) {
        for(BlogData blogData:blogDataList){
            if(blogData.getUri().contains("/") || blogData.getUri().contains(" ") ||blogData.getUri().contains(":") ){
                String uriStr = blogData.getUri();
                uriStr = uriStr.replace("/","x0027x");
                uriStr = uriStr.replace(" ","x160x");
                uriStr = uriStr.replace(":","x003ax");
                blogData.setUri(uriStr);
            }
        }
        SharedPreferencesUtil.setDataList(context,getFileName(),key,blogDataList);

    }
}
