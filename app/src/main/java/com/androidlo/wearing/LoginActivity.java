package com.androidlo.wearing;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;


import android.os.Bundle;

import android.support.design.widget.TextInputEditText;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.androidlo.wearing.model.Constant;
import com.androidlo.wearing.pubUtil.BaseActivity;
import com.androidlo.wearing.pubUtil.SharedPreferencesUtil;

public class LoginActivity extends BaseActivity {

    private TextInputEditText mTiEtPsw, mTiEtAcc;
    private EditText mEtPassword;
    private EditText mEtAccount;
    private LinearLayout mLlParent;
    private Button mBtnLogin;
    private Button mSignUp;
    public static LoginActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        setCustomTitle(getString(R.string.title_activity_login));
        instance = this;

        mLlParent = findViewById(R.id.parent);
        mEtPassword = findViewById(R.id.et_password);
        mEtAccount = findViewById(R.id.et_account);
        mBtnLogin = findViewById(R.id.btn_log_in);
        mSignUp = findViewById(R.id.btn_sign_up);

        mSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewPage(RegisterActivity.class);
            }
        });

        mBtnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAllInput()) {
                    showProgressDialog("加载中...");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            skip2Main();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                finishAfterTransition();
                            } else {
                                finish();
                            }
                        }
                    }, 1000);
                }
            }
        });

    }

    /**
     * 检查输入 不能为空 不能超出20位
     */
    private boolean checkAllInput() {
        boolean accFlag = false;
        String pwd = mEtPassword.getText().toString();
        String acc = mEtAccount.getText().toString();

        //检查账号框
        if (pwd.isEmpty()) {
            showSnack("输入不能为空");
        } else if (acc.isEmpty()) {
            showSnack("输入不能为空");
        } else if (!SharedPreferencesUtil.contains(this, getString(R.string.app_name),acc)) {
            showSnack("用户不存在");
        } else {
            String savedPwd = "";
            savedPwd = (String) SharedPreferencesUtil.get(this,getString(R.string.app_name) ,acc, savedPwd);
            SharedPreferencesUtil.save(this,getString(R.string.app_name),Constant.KEY_CURRENT_USER,acc);
            if (savedPwd != null && savedPwd.equals(pwd)) {
                accFlag = true;
            }else{
                showSnack("密码不正确");
            }
        }
        return accFlag;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //当前焦点
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.toggleSoftInput(0, InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    lostFocus(mLlParent);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //底边弹窗
    public void showSnack(String msg) {
        Snackbar.make(mLlParent, msg, Snackbar.LENGTH_SHORT).show();
    }

}

