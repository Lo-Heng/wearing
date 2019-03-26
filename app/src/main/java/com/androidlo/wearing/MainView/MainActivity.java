package com.androidlo.wearing.MainView;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlo.wearing.MainView.View.MainFragment;
import com.androidlo.wearing.R;

import static android.widget.ListPopupWindow.MATCH_PARENT;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private MainFragment mainFragment;
    private TextView mTextMessage;
    long firstTime = 0L;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };
    private FragmentManager mMFragmentManager;
    private FrameLayout mLlContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
//        mFrameLayout = findViewById(R.id.fl_container);
        BottomNavigationView navigation = findViewById(R.id.navigation);
//        重新设置右碎片的布局

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        if (mainFragment == null) {
//            mainFragment = new MainFragment();
//        }
//        mMFragmentManager = getSupportFragmentManager();
//        mFragmentTransaction = mMFragmentManager.beginTransaction();
//        //实例化要管理的fragment
////                    rightFragment = new RightFragment();
//        //通过添加（事务处理的方式）将fragment加到对应的布局中
//        mFragmentTransaction.add(R.id.home,mainFragment);
//        //事务处理完需要提交
//        mFragmentTransaction.commit();

//         FragmentManager
        mFragmentManager = getSupportFragmentManager();
//        通过begin开启事务
        mFragmentTransaction = mFragmentManager.beginTransaction();
//        使用replace向容器内添加碎片
        mFragmentTransaction.replace(R.id.ll_Container,new MainFragment());
//        将事务添加到返回栈中
        mFragmentTransaction.addToBackStack(null);
//        拿到FrameLayout以便在设置其大小
        mLlContainer = findViewById(R.id.ll_Container);
//        重新设置右碎片的布局
        mLlContainer.setLayoutParams(new FrameLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT,Gravity.LEFT));
//        提交事务
        mFragmentTransaction.commit();



    }
//    //    左边碎片中的按钮绑定的事件
//    public void anotherRight(View v){
////         FragmentManager
//        fragmentManager = getSupportFragmentManager();
////        通过begin开启事务
//        fragmentTransaction = fragmentManager.beginTransaction();
////        使用replace向容器内添加碎片
//        fragmentTransaction.replace(R.id.right_layout,new AnotherRightFragment());
////        将事务添加到返回栈中
//        fragmentTransaction.addToBackStack(null);
////        拿到FrameLayout以便在设置其大小
//        frameLayout = (FrameLayout)findViewById(R.id.right_layout);
////        重新设置右碎片的布局
//        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,MATCH_PARENT, 2.0f));
////        提交事务
//        fragmentTransaction.commit();
////        土司一下，证明你点击有效
//        Toast.makeText(MainActivity.this,"你点击了按钮",Toast.LENGTH_SHORT).show();
//    }
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
}
