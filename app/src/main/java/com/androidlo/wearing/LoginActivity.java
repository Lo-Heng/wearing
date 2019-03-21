package com.androidlo.wearing;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;


import android.database.Cursor;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;

import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidlo.wearing.pubUtil.BaseActivity;

public class LoginActivity extends BaseActivity {

    private static final int REQUEST_READ_CONTACTS = 0;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private LinearLayout mLlParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("登录");
        setContentView(R.layout.activity_login);


        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mLlParent = findViewById(R.id.parent);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);

//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
//                    return true;
//                }
//                return false;
//            }
//        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                }
            }
        });

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
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    imm.toggleSoftInput(0, InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    lostFocus(mLlParent);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }


}

