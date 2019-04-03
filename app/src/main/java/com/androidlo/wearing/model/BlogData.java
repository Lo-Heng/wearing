package com.androidlo.wearing.model;


import android.net.Uri;

public class BlogData {
    //图片资源文件
    private String uri;
    //标题
    private String title;
    //摘要
    private String summarize;
    //作者名称
    private String author;
    //是否收藏
    private boolean isCollect;


    public BlogData(){

    }
    public BlogData(String uri, String title, String summarize, String author, boolean isCollect){
        this.title = title;
        this.summarize = summarize;
        this.uri = uri;
        this.author = author;
        this.isCollect = isCollect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummarize() {
        return summarize;
    }

    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public String getDecodeUriString(){
        String decodeUri ;
        decodeUri = uri.replace("x0027x","/");
        decodeUri = decodeUri.replace("x160x"," ");
        decodeUri = decodeUri.replace("x003ax",":");
        return decodeUri;
    }
}
