package com.amotion.amotion_2017.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amotion.amotion_2017.R;
import com.amotion.amotion_2017.asynctask.LoginAsyncTask;
import com.amotion.amotion_2017.asynctask.ScheduleAsyncTask;
import com.amotion.amotion_2017.asynctask.SubjectAsyncTask;
import com.amotion.amotion_2017.asynctask.SubjectSubmenuAsyncTask;
import com.amotion.amotion_2017.asynctask.TableAsyncTask;
import com.amotion.amotion_2017.data.AsyncData;
import com.amotion.amotion_2017.data.Schedule;
import com.amotion.amotion_2017.data.Subject;
import com.amotion.amotion_2017.data.TableAsyncData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YunDongHyeon on 2017-12-05.
 */


public class FragmentSubject extends Fragment {
    View rootView;


    static ArrayList<Schedule> scheduleArrayList = new ArrayList<>();

    public FragmentSubject() {
    }

    @Nullable
    //내부화면 관리
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_subject, null);
        return rootView;
    }

}
