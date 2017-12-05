package com.amotion.amotion_2017.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amotion.amotion_2017.R;

public class FragmentHome extends Fragment {
    private View rootView;
    private TextView text;

    public FragmentHome() {
    }

    @Nullable
    //내부화면 관리
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_home, null);
        text=rootView.findViewById(R.id.home_text_main);
        return rootView;
    }

    public void setText(String input) {
        TextView textd=rootView.findViewById(R.id.home_text_main);
        text.setText(input);
    }
}