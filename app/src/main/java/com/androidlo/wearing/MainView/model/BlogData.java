package com.androidlo.wearing.MainView.model;



public class BlogData {
    //图片资源文件
    private int resId;
    //标题
    private String title;
    //摘要
    private String summarize;
    //作者名称
    private String author;
    //是否收藏
    private boolean isCollect;


    public BlogData(int resId,String title,String summarize,String author,boolean isCollect){
        this.title = title;
        this.summarize = summarize;
        this.resId = resId;
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

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
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
}
