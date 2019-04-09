package com.androidlo.wearing;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;


import android.os.Bundle;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.androidlo.wearing.model.Constant;
import com.androidlo.wearing.pubUtil.BaseActivity;
import com.androidlo.wearing.pubUtil.SharedPreferencesUtil;

public class LoginActivity extends Activity {

    private TextInputEditText mTiEtPsw, mTiEtAcc;
    public ProgressDialog mProgressDialog;

    private EditText mEtPassword;
    private EditText mEtAccount;
    private FrameLayout mLlParent;
    private Button mBtnLogin;
    private Button mSignUp;
    public static LoginActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_login);
        mProgressDialog = new ProgressDialog(this);//1.创建一个ProgressDialog的实例


//        setCustomTitle(getString(R.string.title_activity_login));
        instance = this;

        mLlParent = findViewById(R.id.parent);
        mLlParent.setClickable(false);
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
    public void startNewPage(Class newPage) {
        startActivity(new Intent(this, newPage));
        overridePendingTransition(R.anim.my_anim_in, android.R.anim.fade_out);
    }


    public void skip2Main() {
        startNewPage(MainActivity.class);
        this.finish();
    }
    public synchronized void showProgressDialog(final String msg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mProgressDialog.setTitle("");//2.设置标题
                        mProgressDialog.setMessage(msg);//3.设置显示内容
                        mProgressDialog.setCancelable(true);//4.设置可否用back键关闭对话框
                        if (!isFinishing() && mProgressDialog!= null)
                            mProgressDialog.show();//5.将ProgessDialog显示出来
                    }
                });
            }
        }).start();
    }

    public void hideProgressDialog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mProgressDialog != null)
                            mProgressDialog.cancel();
                    }
                });
            }
        }).start();
    }
    /**
     * 判断点击的位置是否当前focus所在
     *
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
    //失去焦点
    //传入一个无关紧要的控件,转移焦点
    protected void lostFocus(View parent) {
//        parent.setClickable(true);
        parent.setFocusable(true);
        parent.setFocusableInTouchMode(true);
        parent.requestFocusFromTouch();
    }
}

