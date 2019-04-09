package com.androidlo.wearing.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlo.wearing.MainActivity;
import com.androidlo.wearing.model.Constant;
import com.androidlo.wearing.model.MyAdapter;
import com.androidlo.wearing.model.BlogData;
import com.androidlo.wearing.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CollocationFragment extends Fragment implements View.OnClickListener {


    public static CollocationFragment sMainFragment;
    private boolean[] chooseTag = new boolean[8];

    private ImageView mIvBody;
    private TextView mTvRightText;
    private ImageView mIvCap;
    private ImageView mIvShirt;
    private ImageView mIvTrousers;
    private ImageView mIvShoes;
    private FrameLayout mFlModalRoot;

    public static CollocationFragment getInstance() {
        if (sMainFragment == null) {
            sMainFragment = new CollocationFragment();

        }
        return sMainFragment;
    }

    private void viewSaveToImage(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

// 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);

        FileOutputStream fos;
        String imagePath = "";
        try {
// 判断手机设备是否有SD卡
            boolean isHasSDCard = Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED);
            if (isHasSDCard) {
// SD卡根目录
                File sdRoot = Environment.getExternalStorageDirectory();
                File file = new File(sdRoot, Calendar.getInstance().getTimeInMillis() + ".png");
                fos = new FileOutputStream(file);
                imagePath = file.getAbsolutePath();
            } else
                throw new Exception("创建文件失败!");

            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        ((MainActivity)getActivity()).showToast("图片已保存到"+imagePath);
        view.destroyDrawingCache();
    }


    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
/** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_collocation, container, false);

        mIvBody = root.findViewById(R.id.iv_body);
        mFlModalRoot = root.findViewById(R.id.fl_modal_root);
        //左边人物身上的
        mIvCap = root.findViewById(R.id.iv_cap);
        mIvShirt = root.findViewById(R.id.iv_shirt);
        mIvTrousers = root.findViewById(R.id.iv_trousers);
        mIvShoes = root.findViewById(R.id.iv_shoes);

        //右边衣服选择
        ImageView ivCap1 = root.findViewById(R.id.iv_cap1);
        ImageView ivCap2 = root.findViewById(R.id.iv_cap2);
        ImageView ivShirt1 = root.findViewById(R.id.iv_shirt1);
        ImageView ivShirt2 = root.findViewById(R.id.iv_shirt2);
        ImageView ivTrousers1 = root.findViewById(R.id.iv_trousers1);
        ImageView ivTrousers2 = root.findViewById(R.id.iv_trousers2);
        ImageView ivShoes1 = root.findViewById(R.id.iv_shoes1);
        ImageView ivShoes2 = root.findViewById(R.id.iv_shoes2);

        //设置点击事件
        ivCap1.setOnClickListener(this);
        ivCap2.setOnClickListener(this);
        ivShirt1.setOnClickListener(this);
        ivShirt2.setOnClickListener(this);
        ivTrousers1.setOnClickListener(this);
        ivTrousers2.setOnClickListener(this);
        ivShoes1.setOnClickListener(this);
        ivShoes2.setOnClickListener(this);

        mTvRightText = ((MainActivity)getActivity()).findViewById(R.id.tv_right_text);
        mTvRightText.setOnClickListener(this);
        mTvRightText.setText("保存");


        return root;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            mTvRightText.setVisibility(View.GONE);
        } else {
            //Fragment显示时调用    
            mTvRightText.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cap1:
//                refreshView();
                mIvCap.setImageResource(R.drawable.dress_cap1);
                chooseTag[0] = !chooseTag[0];
                break;
            case R.id.iv_cap2:
                mIvCap.setImageResource(R.drawable.dress_cap2);
                chooseTag[1] = !chooseTag[1];
//                refreshView();
                break;
            case R.id.iv_shirt1:
                mIvShirt.setImageResource(R.drawable.dress_shirt1);
                chooseTag[2] = !chooseTag[2];
//                refreshView();
                break;
            case R.id.iv_shirt2:
                mIvShirt.setImageResource(R.drawable.dress_shirt2);
                chooseTag[3] = !chooseTag[3];
//                refreshView();
                break;
            case R.id.iv_trousers1:
                mIvTrousers.setImageResource(R.drawable.dress_trousers1);
                chooseTag[4] = !chooseTag[4];
//                refreshView();
                break;
            case R.id.iv_trousers2:
                mIvTrousers.setImageResource(R.drawable.dress_trousers2);
                chooseTag[5] = !chooseTag[5];
//                refreshView();
                break;
            case R.id.iv_shoes1:
                mIvShoes.setImageResource(R.drawable.dress_shoes1);
                chooseTag[6] = !chooseTag[6];
//                refreshView();
                break;
            case R.id.iv_shoes2:
                mIvShoes.setImageResource(R.drawable.dress_shoes2);
                chooseTag[7] = !chooseTag[7];
//                refreshView();
                break;

            case R.id.tv_right_text:
                viewSaveToImage(mFlModalRoot);
                break;

        }
//        refreshView();

    }

//    private void refreshView(int position) {
//        if(chooseTag[position] && chooseTag[position%4] == false){
//
//        }
//    }


}
