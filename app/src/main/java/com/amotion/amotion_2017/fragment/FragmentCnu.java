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

import com.amotion.amotion_2017.MainActivity;
import com.amotion.amotion_2017.R;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.amotion.amotion_2017.View.SubjectView;
import com.amotion.amotion_2017.asynctask.CseBoardAsyncTask;
import com.amotion.amotion_2017.data.CseAsyncData;
import com.amotion.amotion_2017.data.CseBoardItem;
import com.amotion.amotion_2017.data.TableData;

import java.util.ArrayList;


public  class FragmentCnu extends Fragment {
    private static final int BOARDNUMBER=5;
    ArrayList<CseBoardItem> cseBoardItems[]=new ArrayList[BOARDNUMBER];

    View rootView;
    Spinner cnuSpinner;
    ListView cnuList;

    public FragmentCnu() {
    }

    @Nullable
    //내부화면 관리
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cnu, null);
        cnuSpinner=(Spinner)rootView.findViewById(R.id.cse_spinner);
        cnuList=(ListView)rootView.findViewById(R.id.cse_listView);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.cnuMenu,R.layout.spinner_item);
        cnuSpinner.setAdapter(spinnerAdapter);

        cnuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CNUAdapter adapter =new CNUAdapter();
                for(int i=0;i<cseBoardItems[position].size();i++){
                    adapter.addItem(cseBoardItems[position].get(i));
                }
                cnuList.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCnu();
    }

    public void getCnu(){
        try {
            //CSE
            String[] noticeUrl= getResources().getStringArray(R.array.noticeURL);

            for(int i=0;i<BOARDNUMBER;i++){
                CseAsyncData cseAsyncData = new CseAsyncData(MainActivity.cseLoginCookie, noticeUrl[i]);
                cseBoardItems[i] = new CseBoardAsyncTask().execute(cseAsyncData).get();
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
