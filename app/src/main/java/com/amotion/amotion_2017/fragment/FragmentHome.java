package com.amotion.amotion_2017.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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

public class FragmentHome extends Fragment {
    private View rootView;
    static ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
    ArrayList<Subject> subjects = null;

    public FragmentHome() {
    }

    @Nullable
    //내부화면 관리
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_home, null);
        if(getAssignmentList()){
            System.out.print("로드 성공");
            System.out.println(subjects);
        }else{
            System.out.print("로드 실패");
        }

        return rootView;
    }

    boolean getAssignmentList(){

        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> loginCookie = null;
        AsyncData asyncData;
        map.put("id", "putid");
        map.put("pw", "putpw");

        try {
            loginCookie = new LoginAsyncTask(getActivity()).execute(map).get();
            subjects = new SubjectAsyncTask().execute(loginCookie).get();

            asyncData = new AsyncData(loginCookie, subjects);

            subjects = new SubjectSubmenuAsyncTask().execute(asyncData).get();

            //System.out.println(subjects);
            //TODO 스케쥴 들임
            scheduleArrayList = new ScheduleAsyncTask().execute(asyncData).get();

            for (int subjectIndex = 0 ;subjectIndex<subjects.size();subjectIndex++){
                new TableAsyncTask().execute(new TableAsyncData(subjects.get(subjectIndex),loginCookie)).get();
            }

            for (Subject s : subjects) {
                Collections.sort(s.getTableDataArrayList());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SingerItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View converView, ViewGroup viewGroup) {
            SingerItemView view = new SingerItemView(getContext());
            SingerItem item = items.get(position);
            view.setNumber(item.getNumber());
            view.setContext(item.getContext());
            view.setDay(item.getDay());
            return view;
        }
    }
}