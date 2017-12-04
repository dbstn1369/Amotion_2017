package com.amotion.amotion_2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amotion.amotion_2017.asynctask.LoginAsyncTask;
import com.amotion.amotion_2017.asynctask.SubjectAsyncTask;
import com.amotion.amotion_2017.asynctask.SubjectTableAsyncTask;
import com.amotion.amotion_2017.data.AsyncData;
import com.amotion.amotion_2017.data.SubMenu;
import com.amotion.amotion_2017.data.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> loginCookie=null;
        ArrayList<Subject> subjects =null;
        Map<String, ArrayList<SubMenu>> subMenus=null;
        AsyncData asyncData;

        map.put("id", "pw");
        try {
            loginCookie = new LoginAsyncTask(getApplicationContext()).execute(map).get();
            SubjectAsyncTask subjectAsyncTask = new SubjectAsyncTask(getApplicationContext());
            subjects = subjectAsyncTask.execute(loginCookie).get();

            asyncData = new AsyncData(loginCookie, subjects);
            subjects = new SubjectTableAsyncTask(getApplicationContext()).execute(asyncData).get();



            System.out.println(subjects);


        }catch (Exception e){
            e.printStackTrace();
        }



        // 병렬 처리시
        /*
        //new SubjectAsyncTask(getApplicationContext()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
/*
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        //읽을때
        /*
        SharedPreferences test = getSharedPreferences("subjects", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = test.getString("Subjects", "");
        Type listType = new TypeToken<ArrayList<Subject>>() {}.getType();
        ArrayList<Subject> subjects = gson.fromJson(json, listType);
        Map<String, ArrayList<SubMenu>> subMenus=null;
        */


        System.out.println(subjects);
        //.System.out.println(subMenus.toString());
    }

}
