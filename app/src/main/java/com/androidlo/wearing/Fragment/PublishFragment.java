package com.androidlo.wearing.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.androidlo.wearing.MainActivity;
import com.androidlo.wearing.model.BlogData;
import com.androidlo.wearing.model.Constant;
import com.androidlo.wearing.R;
import com.androidlo.wearing.model.ListManager;
import com.androidlo.wearing.pubUtil.SharedPreferencesUtil;
import com.androidlo.wearing.pubUtil.UriUtils;
import com.androidlo.wearing.view.IMainActivityView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PublishFragment extends Fragment {


    public static PublishFragment sFragment;
    private Button mBtnPublish;
    private EditText mEtPublishTitle;
    private EditText mEtPublishSummarize;
    private ImageView mIvPublishPhoto;
    private Uri mUri;

    private ListManager mListManager;

    public static PublishFragment getInstance() {
        if (sFragment == null) {
            sFragment = new PublishFragment();
        }
        return sFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListManager = new ListManager(getContext());

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
                List<BlogData> blogDataList,publishList;
                BlogData blogData;
                title = mEtPublishTitle.getText().toString();
                summarize = mEtPublishSummarize.getText().toString();
                Drawable.ConstantState drawable = mIvPublishPhoto.getDrawable().getConstantState();
                if (!title.isEmpty() && !summarize.isEmpty() && !getResources().getDrawable(R.drawable.plus_bg).getConstantState().equals(drawable)) {
                    String uriStr;
                    Uri fileUri = UriUtils.getFileUri(getContext(),mUri);
                    uriStr = fileUri.toString();
                    uriStr = uriStr.replace("/","x0027x");
                    uriStr = uriStr.replace(" ","x160x");
                    uriStr = uriStr.replace(":","x003ax");
                    blogData = new BlogData(uriStr, title, summarize, getFileName(), false,true);
                    blogDataList = mListManager.getObjectList(Constant.KEY_MAIN_FRAGMENT_LIST);
                    blogDataList.add(blogData);
                    mListManager.setObjectList(blogDataList,Constant.KEY_MAIN_FRAGMENT_LIST);
                    ((MainActivity)getActivity()).showToast("发布成功");
                    clearAll();
//                    publishList = mListManager.getObjectList(Constant.KEY_MY_PUBLISH_LIST);
//                    if(publishList.isEmpty()){
//                        publishList = new ArrayList<>();
//                    }
//                    publishList.add(blogData);
//                    mListManager.setObjectList(publishList,Constant.KEY_MY_PUBLISH_LIST);
//                    SharedPreferencesUtil.setDataList(getContext(),getFileName(),Constant.KEY_MAIN_FRAGMENT_LIST,blogDataList);

//                    Gson gson = new GsonBuilder()
//                            .registerTypeAdapter(Uri.class, new UriSerializer())
//                            .create();

//                    SharedPreferencesUtil.save(getContext(), account, Constant.KEY_PUBLISH_BLOG, gson.toJson(blogData));

                }

            }
        });
    }

    private void clearAll() {
        mEtPublishTitle.setText("");
        mEtPublishSummarize.setText("");
        mIvPublishPhoto.setImageResource(R.drawable.plus_bg);
    }

    private String getFileName() {
        String account = "";
        account = (String) SharedPreferencesUtil.get(getContext(), getString(R.string.app_name), Constant.KEY_CURRENT_USER, account);
        return account;
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


    public class UriDeserializer implements JsonDeserializer<Uri> {
        @Override
        public Uri deserialize(final JsonElement src, final Type srcType,
                               final JsonDeserializationContext context) throws JsonParseException {
            return Uri.parse(src.getAsString());
        }
    }
    //解析器
    public class UriSerializer implements JsonSerializer<Uri> {
        public JsonElement serialize(Uri src, Type typeOfSrc,
                                     JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }
}
