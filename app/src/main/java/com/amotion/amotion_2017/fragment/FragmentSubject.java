package com.amotion.amotion_2017.fragment;


import android.content.Intent;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.amotion.amotion_2017.BoardActivity;
import com.amotion.amotion_2017.MainActivity;
import com.amotion.amotion_2017.R;
import com.amotion.amotion_2017.view.SubjectView;
import com.amotion.amotion_2017.asynctask.BoardItemAsyncTask;
import com.amotion.amotion_2017.asynctask.SubjectAsyncTask;
import com.amotion.amotion_2017.asynctask.SubjectSubmenuAsyncTask;
import com.amotion.amotion_2017.asynctask.TableAsyncTask;
import com.amotion.amotion_2017.data.AsyncData;
import com.amotion.amotion_2017.data.Board;
import com.amotion.amotion_2017.data.BoardItemAsyncData;
import com.amotion.amotion_2017.data.Schedule;
import com.amotion.amotion_2017.data.Subject;
import com.amotion.amotion_2017.data.TableAsyncData;
import com.amotion.amotion_2017.data.TableData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import java.util.Collections;

/**
 * Created by YunDongHyeon on 2017-12-05.
 */


public class FragmentSubject extends Fragment {

    View rootView;
    Spinner subjectSpinner;
    ListView subjectList;
    ArrayList<Subject> subjects = null;
    private SubjectAdapter subjectAdapter;
    static ArrayList<Schedule> scheduleArrayList = new ArrayList<>();

    private int subjectIndex=0;
    DatabaseReference subjectFB;
    SharedPreferences subjectSP;

    public FragmentSubject() {
    }


    @Nullable
    //내부화면 관리
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_subject, null);
        subjectSpinner = (Spinner) rootView.findViewById(R.id.subject_spinner);
        subjectList = (ListView) rootView.findViewById(R.id.subject_List);

        String list[] = new String[subjects.size() + 1];
        list[0] = "과목 선택";
        for (int i = 1; i <= subjects.size(); i++) {
            list[i] = subjects.get(i - 1).getSubjectName();
        }




        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.spinner_item, list);
        subjectSpinner.setAdapter(spinnerAdapter);

        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                subjectAdapter = new SubjectAdapter();
                subjectList.setAdapter(subjectAdapter);
                subjectIndex = position-1;

                if (position == 0)
                    return;
                try {
                    if (subjects.get(position - 1).getTableDataArrayList() == null) {
                        new TableAsyncTask().execute(new TableAsyncData(subjects.get(position - 1), MainActivity.loginCookie)).get();
                        Collections.sort(subjects.get(position - 1).getTableDataArrayList());

                    }
                } catch (Exception e) {
                    System.out.print("error");
                    e.printStackTrace();
                }
                ArrayList<TableData> itmes = subjects.get(position - 1).getTableDataArrayList();

                //id로 구분하는것은 분반의 차이가 있기 때문이다.
                //최신글의id를 저장하며 최신글일수록 숫자가 크다.
                String curseId=subjects.get(position-1).getSubjectName().split(" ")[0]+subjects.get(position-1).getClass_no();
                String recentpostId=subjects.get(position-1).getTableDataArrayList().get(0).getId();

                System.out.println(curseId);

                subjectFB.child(curseId).setValue(recentpostId);

                SharedPreferences.Editor editor=subjectSP.edit();
                editor.putString(curseId,recentpostId);
                editor.commit();

                for (int i = 0; i < itmes.size(); i++) {
                    subjectAdapter.addItem(itmes.get(i));
                }
                subjectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                try
                {
                    //TODO
                    Board board = new BoardItemAsyncTask().execute(new BoardItemAsyncData(MainActivity.loginCookie, subjects.get(subjectIndex).getTableDataArrayList().get(position)) ).get();
                    Intent intent = new Intent(getContext(), BoardActivity.class);
                    intent.putExtra("Board", board);

                    startActivityForResult(intent, 101);

                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSubject();
        subjectFB = FirebaseDatabase.getInstance().getReference("Subject");
        subjectSP=getActivity().getSharedPreferences("cnup_subjet",Context.MODE_PRIVATE);
    }

    public void getSubject() {
        try {
            AsyncData asyncData;
            subjects = new SubjectAsyncTask().execute(MainActivity.loginCookie).get();
            asyncData = new AsyncData(MainActivity.loginCookie, subjects);
            subjects = new SubjectSubmenuAsyncTask().execute(asyncData).get();


            //System.out.println(subjects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class SubjectAdapter extends BaseAdapter {
        ArrayList<TableData> items = new ArrayList<TableData>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(TableData item) {
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
            SubjectView view = new SubjectView(getContext());
            TableData item = items.get(position);
            view.setSubject(item.getBoardName().toString());
            view.setTitle(item.getTitle().toString());
            view.setDate(item.getDate());
            return view;
        }
    }

}
