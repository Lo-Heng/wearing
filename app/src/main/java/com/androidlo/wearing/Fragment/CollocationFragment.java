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
    private boolean[] chooseTag = new boolean[4];

    private ImageView mIvBody;
    private TextView mTvRightText;

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

    private void refreshView() {
        int count = 0;
        for (int i = 0; i < chooseTag.length; i++) {
            if (chooseTag[i]) {
                count++;
            }
        }
        String[] stringArray;
        int resId = -1;
        switch (count) {
            case 0:
                mIvBody.setImageResource(R.drawable.lady);
                break;
            case 1:
                stringArray = getResources().getStringArray(R.array.suit1_choose_one);
                for (int i = 0; i < chooseTag.length; i++) {
                    if (chooseTag[i]) {
                        resId = getResources().getIdentifier(stringArray[i], "drawable", getContext().getPackageName());
                        mIvBody.setImageResource(resId);
                    }
                }
                break;
            case 2:
                stringArray = getResources().getStringArray(R.array.suit1_choose_two);
                int index = 0;
                for (int i = 0; i < chooseTag.length; i++) {
                    for (int j = i + 1; j < chooseTag.length; j++) {
                        if (chooseTag[i] && chooseTag[j]) {
                            resId = getResources().getIdentifier(stringArray[index], "drawable", getContext().getPackageName());
                        }
                        index++;
                    }
                }
                mIvBody.setImageResource(resId);
                break;
            case 3:
                stringArray = getResources().getStringArray(R.array.suit1_choose_three);
                index = 0;
                for (int i = 0; i < chooseTag.length; i++) {
                    for (int j = i + 1; j < chooseTag.length; j++) {
                        for (int k = j + 1; k < chooseTag.length; k++) {
                            if (chooseTag[i] && chooseTag[j] && chooseTag[k]) {
                                resId = getResources().getIdentifier(stringArray[index], "drawable", getContext().getPackageName());
                            }
                            index++;
                        }
                    }
                }
                mIvBody.setImageResource(resId);
                break;
            case 4:
                mIvBody.setImageResource(R.drawable.all1);
                break;
        }
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
        ImageView ivCap = root.findViewById(R.id.iv_cap);
        ImageView ivShirt = root.findViewById(R.id.iv_shirt);
        ImageView ivTrousers = root.findViewById(R.id.iv_trousers);
        ImageView ivShoes = root.findViewById(R.id.iv_shoes);
        mTvRightText = ((MainActivity)getActivity()).findViewById(R.id.tv_right_text);
        mTvRightText.setOnClickListener(this);
        mTvRightText.setText("保存");
        ivCap.setOnClickListener(this);
        ivShirt.setOnClickListener(this);
        ivTrousers.setOnClickListener(this);
        ivShoes.setOnClickListener(this);
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
            case R.id.iv_cap:
                chooseTag[0] = !chooseTag[0];
                refreshView();
                break;
            case R.id.iv_shirt:
                chooseTag[1] = !chooseTag[1];
                refreshView();
                break;
            case R.id.iv_trousers:
                chooseTag[2] = !chooseTag[2];
                refreshView();
                break;
            case R.id.iv_shoes:
                chooseTag[3] = !chooseTag[3];
                refreshView();
                break;
            case R.id.tv_right_text:
                viewSaveToImage(mIvBody);
                break;

        }

    }


}
