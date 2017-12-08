package com.amotion.amotion_2017.fragment;

/**
 * Created by YunDongHyeon on 2017-12-05.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amotion.amotion_2017.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amotion.amotion_2017.R;

public  class FragmentCnu extends Fragment {
    View rootView;
    public FragmentCnu() {
    }

    @Nullable
    //내부화면 관리
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cnu, null);
        return rootView;
    }
}
