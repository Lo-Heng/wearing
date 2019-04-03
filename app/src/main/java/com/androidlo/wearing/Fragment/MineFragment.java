package com.androidlo.wearing.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.androidlo.wearing.MyCollectActivity;
import com.androidlo.wearing.R;

public class MineFragment extends Fragment implements View.OnClickListener {

    public static MineFragment sMineFragment;

    public static MineFragment getInstance(){
        if(sMineFragment == null){
            sMineFragment = new MineFragment();
        }
        return sMineFragment;
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
        rlMyCollect.setOnClickListener(this);
        rlMyPublish.setOnClickListener(this);
        rlMySubscribe.setOnClickListener(this);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_my_collect:
                startActivity(new Intent(getActivity(), MyCollectActivity.class));
                break;
            case R.id.rl_my_subscribe:
                break;
            case R.id.rl_my_publish:
                break;
        }
    }
}
