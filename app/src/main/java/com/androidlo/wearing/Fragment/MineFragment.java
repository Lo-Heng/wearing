package com.androidlo.wearing.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidlo.wearing.MainActivity;
import com.androidlo.wearing.MyCollectActivity;
import com.androidlo.wearing.MyPublishActivity;
import com.androidlo.wearing.MySubscribeActivity;
import com.androidlo.wearing.R;
import com.androidlo.wearing.model.Constant;
import com.androidlo.wearing.pubUtil.SharedPreferencesUtil;
import com.androidlo.wearing.pubUtil.UriUtils;

import static android.app.Activity.RESULT_OK;

public class MineFragment extends Fragment implements View.OnClickListener {


    public static MineFragment sMineFragment;
    private TextView mTvSign;
    private ImageView mIvIcon;

    public static MineFragment getInstance(){
        if(sMineFragment == null){
            sMineFragment = new MineFragment();
        }
        return sMineFragment;
    }
    private String getFileName() {
        String account = "";
        account = (String) SharedPreferencesUtil.get(getContext(), getString(R.string.app_name), Constant.KEY_CURRENT_USER, account);
        account = "Account" + account;
        return account;


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_mine, container, false);
        RelativeLayout rlMyCollect = root.findViewById(R.id.rl_my_collect);
        RelativeLayout rlMyPublish = root.findViewById(R.id.rl_my_publish);
        RelativeLayout rlMySubscribe = root.findViewById(R.id.rl_my_subscribe);
        TextView tvName = root.findViewById(R.id.tv_my_name);
        mTvSign = root.findViewById(R.id.tv_my_sign);
        mIvIcon = root.findViewById(R.id.iv_icon);
        rlMyCollect.setOnClickListener(this);
        rlMyPublish.setOnClickListener(this);
        rlMySubscribe.setOnClickListener(this);
        mIvIcon.setOnClickListener(this);
        mTvSign.setOnClickListener(this);
        String name;
        name = getFileName().replace("Account","");
        tvName.setText(name);
        setSign();

        String uriStr = (String)SharedPreferencesUtil.get(getActivity().getApplicationContext(),getFileName(),Constant.KEY_MY_ICON,"");
        if(uriStr == null || uriStr.isEmpty() ){
            mIvIcon.setImageResource(R.drawable.default_icon);
        }
        else{
            mIvIcon.setImageURI(Uri.parse(uriStr));
        }


        return root;
    }
    /**
     * 这是兼容的 AlertDialog
     */
    private void showDialog() {
        final EditText etSign = new EditText(getContext());
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setTitle("更改个性签名");
//        builder.setMessage("");
        builder.setView(etSign);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferencesUtil.save(getActivity().getApplicationContext(),getFileName(),Constant.KEY_MY_SIGN,etSign.getText().toString());

                setSign();

            }
        });
        builder.show();
    }

    private void setSign() {
        String signStr = "";
        signStr = (String) SharedPreferencesUtil.get(getActivity().getApplicationContext(),getFileName(),Constant.KEY_MY_SIGN,"个性签名");
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getFileName(), Context.MODE_PRIVATE);
//        signStr = sharedPreferences.getString(Constant.KEY_MY_SIGN,"个性签名");
        mTvSign.setText(signStr);

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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            //调用相册后返回
            case Constant.ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    Uri fileUri = UriUtils.getFileUri(getContext(),uri);
                    mIvIcon.setImageURI(fileUri);
                    SharedPreferencesUtil.save(getActivity().getApplicationContext(),getFileName(),Constant.KEY_MY_ICON,fileUri.toString());
                }
                break;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_my_collect:
                ((MainActivity)getActivity()).startNewPage(MyCollectActivity.class);
                break;
            case R.id.rl_my_subscribe:
                ((MainActivity)getActivity()).startNewPage(MySubscribeActivity.class);
                break;
            case R.id.rl_my_publish:
                ((MainActivity)getActivity()).startNewPage(MyPublishActivity.class);
                break;
            case R.id.tv_my_sign:
                showDialog();
                break;
            case R.id.iv_icon:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    //授权先
                    getPicFromAlbm();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.ALBUM_REQUEST_CODE);
                }
                break;

        }
    }
}
