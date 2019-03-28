package com.androidlo.wearing.pubUtil;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlo.wearing.MainView.MainActivity;
import com.androidlo.wearing.R;

public class BaseActivity extends AppCompatActivity {

    public ProgressDialog mProgressDialog;
    private View mActionBarView;
    private TextView mTvTitle;
    private TextView mTvLeftText;
    private TextView mTvRightText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横屏
        setStatusBar();
        setCustomActionBar();
        mProgressDialog = new ProgressDialog(BaseActivity.this);//1.创建一个ProgressDialog的实例
        bindView();

    }

    /**
     * 绑定view
     */
    private void bindView() {
        mTvTitle = mActionBarView.findViewById(R.id.tv_title);
        mTvLeftText = mActionBarView.findViewById(R.id.tv_left_text);
        mTvRightText = mActionBarView.findViewById(R.id.tv_right_text);
    }

    private void setCustomActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setCustomView(mActionBarView, lp);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }


    }

    private void setStatusBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public void setCustomTitle(String title) {
        mTvTitle.setText(title);
    }

    protected void setRightText(String text) {
        mTvRightText.setText(text);
    }

    protected void setTvLeftText(String text) {
        mTvLeftText.setText(text);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideInput(v, ev)) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    assert v != null;
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    /** 失去焦点 */
//
//                }
//            }
//            return super.dispatchTouchEvent(ev);
//        }
//        // 必不可少，否则所有的组件都不会有TouchEvent了
//        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
//    }

    //失去焦点
    //传入一个无关紧要的控件,转移焦点
    protected void lostFocus(View parent) {
        parent.setClickable(true);
        parent.setFocusable(true);
        parent.setFocusableInTouchMode(true);
        parent.requestFocusFromTouch();
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

    //显示Toast消息
    public void showToast(final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();

                    }
                });
            }
        }).start();
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

    @Override
    protected void onPause() {
        if (mProgressDialog != null){
            hideProgressDialog();
        }
        hideProgressDialog();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
