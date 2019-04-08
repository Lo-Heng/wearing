package com.androidlo.wearing;


import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidlo.wearing.model.Constant;
import com.androidlo.wearing.pubUtil.BaseActivity;
import com.androidlo.wearing.pubUtil.SharedPreferencesUtil;

import org.w3c.dom.Text;

public class DetailActivity extends BaseActivity {

    public static String EXTRA_TITLE = "title";//文章标题
    public static String EXTRA_SUMMARIZE = "summarize";//摘要（其实是内容）
    public static String EXTRA_AUTHOR = "author";//作者
    public static String EXTRA_PHOTO = "photo";//图片
    private String mTitle;
    private String mSummarize;
    private String mAuthor;
    private String mPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        setCustomTitle("文章详情");

        getIntentValue();

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvSummarize = findViewById(R.id.tv_summarize);
        ImageView ivPhoto = findViewById(R.id.iv_photo);
        TextView tvAuthor = findViewById(R.id.tv_author);

        tvTitle.setText(mTitle);
        tvSummarize.setText(mSummarize);
        tvAuthor.setText("作者：" + mAuthor);
        ivPhoto.setImageURI(Uri.parse(mPhotoUri));

    }

    private void getIntentValue() {
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        mSummarize = getIntent().getStringExtra(EXTRA_SUMMARIZE);
        mAuthor = getIntent().getStringExtra(EXTRA_AUTHOR);
        mPhotoUri = getIntent().getStringExtra(EXTRA_PHOTO);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}

