package com.androidlo.wearing.MainView.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlo.wearing.MainView.model.BlogData;
import com.androidlo.wearing.MainView.model.Constant;
import com.androidlo.wearing.R;
import com.androidlo.wearing.pubUtil.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import static android.app.Activity.RESULT_OK;

public class PublishFragment extends Fragment {


    public static PublishFragment sFragment;
    private Button mBtnPublish;
    private EditText mEtPublishTitle;
    private EditText mEtPublishSummarize;
    private ImageView mIvPublishPhoto;
    private Uri mUri;

    public static PublishFragment getInstance() {
        if (sFragment == null) {
            sFragment = new PublishFragment();
        }
        return sFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_publish, container, false);


        mBtnPublish = root.findViewById(R.id.btn_publish);
        mEtPublishTitle = root.findViewById(R.id.et_publish_title);
        mEtPublishSummarize = root.findViewById(R.id.et_publish_summarize);
        mIvPublishPhoto = root.findViewById(R.id.iv_publish_photo);

        setListener();

        return root;
    }

    private void setListener() {

        mIvPublishPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    //授权先
                    getPicFromAlbm();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.ALBUM_REQUEST_CODE);
                }

            }
        });


        mBtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = "", summarize = "", account = "";

                BlogData blogData;
                title = mBtnPublish.getText().toString();
                summarize = mEtPublishSummarize.getText().toString();
                account = (String) SharedPreferencesUtil.get(getContext(), getString(R.string.app_name), "currentAccount", account);
                Drawable.ConstantState drawable = mIvPublishPhoto.getDrawable().getConstantState();
                if (!title.isEmpty() && !summarize.isEmpty() && !getResources().getDrawable(R.drawable.plus_bg).getConstantState().equals(drawable) ) {
                    blogData = new BlogData(mUri, title, summarize, account, false);
//                    Gson gson = new Gson();
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Uri.class, new UriSerializer())
                            .create();
                    SharedPreferencesUtil.save(getContext(), account, Constant.KEY_PUBLISH_BLOG, gson.toJson(blogData));
                }
            }
        });
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, Constant.ALBUM_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            //调用相册后返回
            case Constant.ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    mUri = intent.getData();
                    mIvPublishPhoto.setImageURI(mUri);

                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    //解析器
    public class UriSerializer implements JsonSerializer<Uri> {
        public JsonElement serialize(Uri src, Type typeOfSrc,
                                     JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }
}
