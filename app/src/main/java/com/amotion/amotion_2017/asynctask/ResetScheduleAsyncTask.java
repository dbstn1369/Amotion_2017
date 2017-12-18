package com.amotion.amotion_2017.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.amotion.amotion_2017.data.AsyncData;
import com.amotion.amotion_2017.data.Schedule;
import com.amotion.amotion_2017.data.Subject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Choiyoonsoo on 2017-12-08.
 */

public class ResetScheduleAsyncTask extends AsyncTask<AsyncData, Void, ArrayList<Schedule>>
    {
        String returnday;
        @Override
        protected ArrayList<Schedule> doInBackground(AsyncData... asyncData)
        {
            ArrayList<Subject> subjects = asyncData[0].getSubjects();
            Map<String, String> loginTryCookie = asyncData[0].getLoginTryCookie();

            ArrayList<Schedule> scheduleArrayList= new ArrayList<>();
            try {
                String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";
                Map<String, String> data = new HashMap<String, String>();

                data.put("schedule_dt",returnday);



                Connection.Response scheduleResponse = Jsoup.connect("http://e-learn.cnu.ac.kr/lms/mypage/schedule/doListMySchedule.dunet")
                        .userAgent(userAgent)
                        .timeout(60000)
                        .header("Origin", "http://e-learn.cnu.ac.kr")
                        .header("Referer", "http://e-learn.cnu.ac.kr/main/MainView.dunet")
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .data(data)
                        .cookies(loginTryCookie)
                        .method(Connection.Method.POST)
                        .ignoreContentType(true)
                        .execute();

                //System.out.println(scheduleResponse.body());

                JsonParser parser = new JsonParser();
                JsonElement jsonElement =parser.parse( scheduleResponse.body() );
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("scheduleList");

                //System.out.println(jsonArray.toString());

                for (int i = 0 ;i<jsonArray.size();i++)
                {
                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
                    JsonObject schedule = jsonArray.get(i).getAsJsonObject();

                    String course = schedule.get("course_nm").getAsString();
                    String title = schedule.get("title").getAsString();
                    Date str_dt = simpleDateFormat1.parse(schedule.get("str_dt").getAsString());
                    Date end_dt = simpleDateFormat1.parse(schedule.get("end_dt").getAsString());

                    scheduleArrayList.add(new Schedule(course, title, str_dt, end_dt));
                }
            } catch (Exception ex) {
                Log.e("RescheduleAsyncTask", "Error");
                ex.printStackTrace();
            }
            return scheduleArrayList;
        }

        public void returnday(String day){
            returnday = day;

        }

    }


