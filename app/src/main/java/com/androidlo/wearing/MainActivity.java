package com.androidlo.wearing;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.androidlo.wearing.Fragment.CollocationFragment;
import com.androidlo.wearing.Fragment.MainFragment;
import com.androidlo.wearing.Fragment.MineFragment;
import com.androidlo.wearing.Fragment.PublishFragment;
import com.androidlo.wearing.pubUtil.BaseActivity;
import com.androidlo.wearing.pubUtil.BottomNavigationViewHelper;

import static android.widget.ListPopupWindow.MATCH_PARENT;

public class MainActivity extends BaseActivity   {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment currentFragment ;
    private FragmentManager mMFragmentManager;
    private FrameLayout mfl_container;
    private MainFragment mainFragment;
    private View mViewCover;
    private CardView mCvContainPublish;
    private BottomNavigationView mNavigation;
    long firstTime = 0L;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    setCustomTitle(getString(R.string.title_home));
                    switchFragment(MainFragment.getInstance()).commit();
                    return true;
                case R.id.navigation_collocation:
//                    mTextMessage.setText(R.string.title_dashboard);
                    setCustomTitle(getString(R.string.title_collocation));
                    switchFragment(CollocationFragment.getInstance()).commit();
                    return true;
                case R.id.navigation_publish:

//                    setCustomTitle(getString(R.string.title_publish));
                    switchFragment(PublishFragment.getInstance()).commit();
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_message:
                    setCustomTitle(getString(R.string.title_message));
                    switchFragment(CollocationFragment.getInstance()).commit();
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_mine:
                    setCustomTitle(getString(R.string.title_mine));
                    switchFragment(MineFragment.getInstance()).commit();
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //键盘弹出不挤压
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mNavigation = findViewById(R.id.navigation);
        mfl_container = findViewById(R.id.f_container);

        //标题默认首页
        setCustomTitle(getString(R.string.title_home));
        //初始化navigation底边栏
        initNavigation();

//         FragmentManager
        mFragmentManager = getSupportFragmentManager();
//        通过begin开启事务
        mFragmentTransaction = mFragmentManager.beginTransaction();
//        使用replace向容器内添加碎片
        mFragmentTransaction.replace(R.id.f_container, MainFragment.getInstance());
//        将事务添加到返回栈中
        mFragmentTransaction.addToBackStack(null);
//        拿到FrameLayout以便在设置其大小

//        重新设置右碎片的布局
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);//工具类哦
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.navigation);

        mfl_container.setLayoutParams(layoutParams);
//        提交事务
        mFragmentTransaction.commit();
        currentFragment  = MainFragment.getInstance();

    }

    private void initNavigation() {
        BottomNavigationViewHelper.disableShiftMode(mNavigation);//防止切换

        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color.TextPrimary),
                getResources().getColor(R.color.colorPrimary)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        mNavigation.setItemTextColor(csl);
        mNavigation.setItemIconTintList(csl);
//        重新设置右碎片的布局
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * 双击返回退出app
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private FragmentTransaction switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下        
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.f_container, targetFragment, targetFragment.getClass().getName());
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }




}
