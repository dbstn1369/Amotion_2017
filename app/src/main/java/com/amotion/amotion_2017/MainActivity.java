package com.amotion.amotion_2017;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> loginCookie;
        map.put("id", "pw");
        try {
            loginCookie = new LoginAsyncTask(getApplicationContext()).execute(map).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        SubjectAsyncTask subjectAsyncTask = new SubjectAsyncTask(getApplicationContext());
        subjectAsyncTask.execute();


        // 병렬 처리시
        /*
        //new SubjectAsyncTask(getApplicationContext()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //읽을때
        SharedPreferences test = getSharedPreferences("subjects", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = test.getString("Subjects", "");
        Type listType = new TypeToken<ArrayList<Subject>>() {}.getType();
        ArrayList<Subject> subjects = gson.fromJson(json, listType);
        Map<String, ArrayList<SubMenu>> subMenus=null;

        try {
          subMenus = new SubjectTableAsyncTask(getApplicationContext()).execute(subjects).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        System.out.println(subjects);
        System.out.println(subMenus.toString());
    }

}
