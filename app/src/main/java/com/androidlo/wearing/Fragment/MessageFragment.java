package com.androidlo.wearing.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlo.wearing.R;
import com.androidlo.wearing.model.Message;
import com.androidlo.wearing.model.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {



    public static MessageFragment sMessageFragment;
    private List<Message> mMessageList;

    public static MessageFragment getInstance(){
        if(sMessageFragment == null){
            sMessageFragment = new MessageFragment();
        }
        return sMessageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_message, container, false);
        initData();
        RecyclerView rvMessageList = root.findViewById(R.id.rv_message_list);
        MessageAdapter adapter = new MessageAdapter(mMessageList);
        rvMessageList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMessageList.setAdapter(adapter);


        return root;
    }

    private void initData() {
        mMessageList = new ArrayList<>();
        mMessageList.add(new Message("系统","您好，恭喜您成功注册注册"));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }




}
