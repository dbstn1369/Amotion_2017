package com.amotion.amotion_2017.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.amotion.amotion_2017.R;
import com.amotion.amotion_2017.asynctask.LoginAsyncTask;
import com.amotion.amotion_2017.asynctask.ScheduleAsyncTask;
import com.amotion.amotion_2017.asynctask.SubjectAsyncTask;
import com.amotion.amotion_2017.asynctask.SubjectSubmenuAsyncTask;
import com.amotion.amotion_2017.asynctask.TableAsyncTask;
import com.amotion.amotion_2017.data.AsyncData;
import com.amotion.amotion_2017.data.Schedule;
import com.amotion.amotion_2017.View.ScheduleView;
import com.amotion.amotion_2017.data.Subject;
import com.amotion.amotion_2017.data.TableAsyncData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FragmentHome extends Fragment {
    private View rootView;
    static ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
    SingerAdapter adapter;
    ListView listView;
    public FragmentHome() {
    }

    @Nullable
    //내부화면 관리
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_home, null);
        listView=rootView.findViewById(R.id.subjectList);

        if(getAssignmentList()){
            adapter = new SingerAdapter();
            for(int i=0;i<scheduleArrayList.size();i++){
                adapter.addItem(scheduleArrayList.get(i));
            }
            listView.setAdapter(adapter);
        }else{
            System.out.print("로드 실패");
        }





        return rootView;
    }

    boolean getAssignmentList(){

        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> loginCookie = null;
        ArrayList<Subject> subjects = null;
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
        ArrayList<Schedule> items = new ArrayList<Schedule>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Schedule item) {
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
            ScheduleView view = new ScheduleView(getContext());
            Schedule item = items.get(position);
            view.setCourse(item.getCourse());
            view.setContent(item.getTitle());
            view.setStartDate(item.getStart());
            view.setEndDate(item.getEnd());
            return view;
        }
    }
}