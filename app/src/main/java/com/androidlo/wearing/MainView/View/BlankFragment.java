package com.androidlo.wearing.MainView.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlo.wearing.R;

public class BlankFragment extends Fragment {



    public static BlankFragment sFragment;

    public static BlankFragment getInstance(){
        if(sFragment == null){
            sFragment = new BlankFragment();
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

        View root = inflater.inflate(R.layout.fragment_blank, container, false);


        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }




}
