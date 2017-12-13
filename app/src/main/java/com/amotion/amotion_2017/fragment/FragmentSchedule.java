package com.amotion.amotion_2017.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.amotion.amotion_2017.MainActivity;
import com.amotion.amotion_2017.R;
import com.amotion.amotion_2017.asynctask.ResetScheduleAsyncTask;
import com.amotion.amotion_2017.asynctask.ScheduleAsyncTask;
import com.amotion.amotion_2017.asynctask.SubjectAsyncTask;
import com.amotion.amotion_2017.data.AsyncData;
import com.amotion.amotion_2017.data.Schedule;
import com.amotion.amotion_2017.View.ScheduleView;
import com.amotion.amotion_2017.data.Subject;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentSchedule extends Fragment {
    private View rootView;
    private ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
    private ScheduleAdapter adapter;
    private ListView listView;
    private Button calendarButton;

    public FragmentSchedule() {
    }

    @Nullable
    //내부화면 관리
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_schedule, null);
        listView=rootView.findViewById(R.id.scheduleList);
        calendarButton=(Button)rootView.findViewById(R.id.buttonCalendar);


        calendarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
                datePickerDialog.show();
            }

        });

        listView.setAdapter(adapter);
        if(adapter.getCount()==0){
            for(int i=0;i<scheduleArrayList.size();i++){
                adapter.addItem(scheduleArrayList.get(i));
            }
        }
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!getAssignmentList()){
            System.out.print("로드 실패");
        }
        adapter = new ScheduleAdapter();
    }

    boolean getAssignmentList(){
        ArrayList<Subject> subjects = null;
        AsyncData asyncData;
        try {
            subjects = new SubjectAsyncTask().execute(MainActivity.loginCookie).get();
            asyncData = new AsyncData(MainActivity.loginCookie, subjects);
            //System.out.println(subjects);
            //TODO 스케쥴 들임
            scheduleArrayList = new ScheduleAsyncTask().execute(asyncData).get();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    class ScheduleAdapter extends BaseAdapter {
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

    public DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ArrayList<Schedule> scheduleArrayList = new ArrayList<>();


            ArrayList<Subject> subjects = null;
            AsyncData asyncData;

            int day =  20170704;


            calendarButton.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
            ResetScheduleAsyncTask resetschedule = new ResetScheduleAsyncTask();
            resetschedule.returnday(day);


            try {
                subjects = new SubjectAsyncTask().execute(MainActivity.loginCookie).get();

                asyncData = new AsyncData(MainActivity.loginCookie, subjects);

                //TODO 스케쥴 들임
                scheduleArrayList = new ResetScheduleAsyncTask().execute(asyncData).get();
                System.out.println(scheduleArrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
}