package com.androidlo.wearing;


import android.content.Context;
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
import android.widget.LinearLayout;

import com.androidlo.wearing.pubUtil.BaseActivity;
import com.androidlo.wearing.pubUtil.SharedPreferencesUtil;

public class RegisterActivity extends BaseActivity {

    private TextInputEditText mTiEtPsw, mTiEtAcc;
    private EditText mEtPassword;
    private EditText mEtAccount;
    private LinearLayout mLlParent;
    private Button mBtnReg;
    private EditText mEtConfirmPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.sign_up));
        setContentView(R.layout.activity_register);

        mLlParent = findViewById(R.id.parent);
        mEtPassword = findViewById(R.id.et_password);
        mEtAccount = findViewById(R.id.et_account);
        mEtConfirmPsw = findViewById(R.id.et_confirm_password);
        mBtnReg = findViewById(R.id.btn_sign_up);

        mBtnReg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkAllInput()) {
                    if (LoginActivity.instance != null){
                        LoginActivity.instance.finish();
                    }
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
//        boolean accFlag = false, pwdFlag = false;
        String pwd = mEtPassword.getText().toString();
        String acc = mEtAccount.getText().toString();
        String confirmPwd = mEtConfirmPsw.getText().toString();
        //检查账号框
        if (pwd.isEmpty()) {
            showSnack("输入不能为空");
            return false;
        } else if (acc.length() == 20) {
            showSnack("长度超出20个字符");
            return false;
        }
        //检查密码框
        if (acc.isEmpty()) {
            showSnack("输入不能为空");
            return false;
        } else if (acc.length() == 20) {
            showSnack("长度超出20个字符");
            return false;
        } else if (!confirmPwd.equals(pwd)) {
            showSnack("密码不一致");
            return false;
        }else if(confirmPwd.equals(pwd)){
            SharedPreferencesUtil.save(this, acc, pwd);
            return true;
        }else{
            showSnack("错误，原因不明，请反馈");
            return false;
        }

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

