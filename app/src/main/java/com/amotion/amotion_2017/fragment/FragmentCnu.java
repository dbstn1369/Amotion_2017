package com.amotion.amotion_2017.fragment;

/**
 * Created by YunDongHyeon on 2017-12-05.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amotion.amotion_2017.BoardActivity;
import com.amotion.amotion_2017.MainActivity;
import com.amotion.amotion_2017.R;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.amotion.amotion_2017.asynctask.BoardItemAsyncTask;
import com.amotion.amotion_2017.asynctask.CseBoardItemAsyncTask;
import com.amotion.amotion_2017.data.Board;
import com.amotion.amotion_2017.data.BoardItemAsyncData;
import com.amotion.amotion_2017.data.CseBoardItemAsyncData;
import com.amotion.amotion_2017.view.SubjectView;
import com.amotion.amotion_2017.asynctask.CseBoardAsyncTask;
import com.amotion.amotion_2017.data.CseAsyncData;
import com.amotion.amotion_2017.data.CseBoardItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;


public class FragmentCnu extends Fragment {
    private static final int BOARDNUMBER = 5;
    ArrayList<CseBoardItem> cseBoardItems[] = new ArrayList[BOARDNUMBER];

    View rootView;
    Spinner cnuSpinner;
    ListView cnuList;

    int cseIndex=1;

    DatabaseReference cnuFB;
    SharedPreferences cnuSP;

    public FragmentCnu() {
    }

    @Nullable
    //내부화면 관리
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cnu, null);
        cnuSpinner = (Spinner) rootView.findViewById(R.id.cse_spinner);
        cnuList = (ListView) rootView.findViewById(R.id.cse_listView);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.cnuMenu, R.layout.spinner_item);
        cnuSpinner.setAdapter(spinnerAdapter);

        cnuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CNUAdapter adapter = new CNUAdapter();
                for (int i = 0; i < cseBoardItems[position].size(); i++) {
                    adapter.addItem(cseBoardItems[position].get(i));
                }
                cseIndex= position;
                cnuList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cnuList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                try
                {
                    CseBoardItemAsyncData cseBoardItemAsyncData = new CseBoardItemAsyncData(MainActivity.cseLoginCookie, cseBoardItems[cseIndex].get(position));
                    System.out.println("test "+cseBoardItemAsyncData);
                    //TODO
                    Board board = new CseBoardItemAsyncTask().execute(cseBoardItemAsyncData ).get();
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
        cnuFB = FirebaseDatabase.getInstance().getReference("CNU");
        cnuSP = getActivity().getSharedPreferences("cnup_cnu", Context.MODE_PRIVATE);
        getCnu();


    }

    public void getCnu() {
        String[] menuName = getResources().getStringArray(R.array.cnuMenu);

        final String[] beforeNum = new String[1];

        try {
            //CSE
            String[] noticeUrl = getResources().getStringArray(R.array.noticeURL);
            for (int i = 0; i < BOARDNUMBER; i++) {
                CseAsyncData cseAsyncData = new CseAsyncData(MainActivity.cseLoginCookie, noticeUrl[i]);
                cseBoardItems[i] = new CseBoardAsyncTask().execute(cseAsyncData).get();
                int j = 0;
                while (cseBoardItems[i].get(j).getNumber().equals("공지")){
                   j++;
                }

                cnuFB.child(menuName[i]).setValue(cseBoardItems[i].get(j).getNumber());

                SharedPreferences.Editor editor=cnuSP.edit();
                editor.putString(menuName[i],cseBoardItems[i].get(j).getNumber());
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class CNUAdapter extends BaseAdapter {
        ArrayList<CseBoardItem> items = new ArrayList<CseBoardItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(CseBoardItem item) {
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
            CseBoardItem item = items.get(position);
            view.setSubject(item.getNumber());
            view.setTitle(item.getTitle());
            view.setDate(item.getDate());
            return view;
        }
    }
}
