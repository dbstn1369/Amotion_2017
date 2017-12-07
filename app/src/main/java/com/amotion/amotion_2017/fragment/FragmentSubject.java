package com.amotion.amotion_2017.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amotion.amotion_2017.R;
import com.amotion.amotion_2017.data.SingerItem;
import com.amotion.amotion_2017.data.SingerItemView;

import java.util.ArrayList;

/**
 * Created by YunDongHyeon on 2017-12-05.
 */


public class FragmentSubject extends Fragment {

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
        Log.d("listView", "listView");


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

}
