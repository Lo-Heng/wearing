package com.androidlo.wearing.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatCallback;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidlo.wearing.MainActivity;
import com.androidlo.wearing.MyCollectActivity;
import com.androidlo.wearing.MyPublishActivity;
import com.androidlo.wearing.MySubscribeActivity;
import com.androidlo.wearing.R;
import com.androidlo.wearing.model.Constant;
import com.androidlo.wearing.pubUtil.SharedPreferencesUtil;
import com.androidlo.wearing.view.IMainActivityView;

public class MineFragment extends Fragment implements View.OnClickListener {


    public static MineFragment sMineFragment;
    private TextView mTvSign;

    public static MineFragment getInstance(){
        if(sMineFragment == null){
            sMineFragment = new MineFragment();
        }
        return sMineFragment;
    }
    private String getFileName() {
        String account = "";
        account = (String) SharedPreferencesUtil.get(getContext(), getString(R.string.app_name), Constant.KEY_CURRENT_USER, account);
//        account = getString(R.string.app_name);
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
        rlMyCollect.setOnClickListener(this);
        rlMyPublish.setOnClickListener(this);
        rlMySubscribe.setOnClickListener(this);
        mTvSign.setOnClickListener(this);
        String name;
        name = getFileName().replace("Account","");
        tvName.setText(name);
        setSign();


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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        }
    }
}
