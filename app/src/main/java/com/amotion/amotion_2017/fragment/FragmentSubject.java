package com.amotion.amotion_2017.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amotion.amotion_2017.R;

/**
 * Created by YunDongHyeon on 2017-12-05.
 */


public  class FragmentSubject extends Fragment {
    public FragmentSubject() {
    }

    @Nullable
    //내부화면 관리
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subject, null);
    }
}
