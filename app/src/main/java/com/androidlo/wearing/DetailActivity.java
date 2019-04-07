package com.androidlo.wearing;


import android.content.Context;
import android.media.Image;
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


    public static DetailActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        setCustomTitle("如何穿搭");

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvSummarize = findViewById(R.id.tv_summarize);
        ImageView ivPhoto = findViewById(R.id.iv_photo);

        tvTitle.setText(R.string.title);
        tvSummarize.setText(R.string.article);
        ivPhoto.setImageResource(R.drawable.main1);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



}

