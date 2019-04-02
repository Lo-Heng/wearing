package com.androidlo.wearing.MainView.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlo.wearing.MainView.MyAdapter;
import com.androidlo.wearing.MainView.model.BlogData;
import com.androidlo.wearing.R;

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends Fragment {

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




        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }




}