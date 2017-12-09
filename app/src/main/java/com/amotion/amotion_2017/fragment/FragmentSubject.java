package com.amotion.amotion_2017.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.amotion.amotion_2017.R;
import com.amotion.amotion_2017.asynctask.LoginAsyncTask;
import com.amotion.amotion_2017.asynctask.ResetScheduleAsyncTask;
import com.amotion.amotion_2017.asynctask.ScheduleAsyncTask;
import com.amotion.amotion_2017.asynctask.SubjectAsyncTask;
import com.amotion.amotion_2017.data.AsyncData;
import com.amotion.amotion_2017.data.Schedule;
import com.amotion.amotion_2017.data.SingerItem;
import com.amotion.amotion_2017.data.SingerItemView;
import com.amotion.amotion_2017.data.Subject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YunDongHyeon on 2017-12-05.
 */


public class FragmentSubject extends Fragment {
    Button dateButton;
    ListView listView;
    SingerAdapter adapter;
    View view;


    public FragmentSubject() {
    }


    @Nullable
    //내부화면 관리
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_subject, null);
        listView = (ListView) view.findViewById(R.id.listView);
        dateButton = (Button) view.findViewById(R.id.date);
        Log.d("listView", "listView");


        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), dateSetListener, year, month, day);
                datePickerDialog.show();
    }

});


        adapter = new SingerAdapter();
        adapter.addItem(new SingerItem("1", "string", "string"));
        adapter.addItem(new SingerItem("2", "string", "string"));
        adapter.addItem(new SingerItem("3", "string", "string"));
        adapter.addItem(new SingerItem("4", "string", "string"));
        Log.d("listView", "listView2");
        listView.setAdapter(adapter);


        return view;
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

    public DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ArrayList<Schedule> scheduleArrayList = new ArrayList<>();


            Map<String, String> map = new HashMap<String, String>();
            Map<String, String> loginCookie = null;
            ArrayList<Subject> subjects = null;
            AsyncData asyncData;
            map.put("id", "pw");

            int day =  20170704;


            dateButton.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
            ResetScheduleAsyncTask resetschedule = new ResetScheduleAsyncTask();
            resetschedule.returnday(day);


            try {
                loginCookie = new LoginAsyncTask(getActivity()).execute(map).get();
                subjects = new SubjectAsyncTask().execute(loginCookie).get();

                asyncData = new AsyncData(loginCookie, subjects);

                //TODO 스케쥴 들임
                scheduleArrayList = new ResetScheduleAsyncTask().execute(asyncData).get();
                System.out.println(scheduleArrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

}
