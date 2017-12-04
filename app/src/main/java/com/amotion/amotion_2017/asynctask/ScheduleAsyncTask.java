package com.amotion.amotion_2017.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.amotion.amotion_2017.data.AsyncData;
import com.amotion.amotion_2017.data.Subject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSH on 2017-12-04.
 */

public class ScheduleAsyncTask extends AsyncTask<AsyncData, Void, ArrayList<Subject>>
{
    @Override
    protected ArrayList<Subject> doInBackground(AsyncData... asyncData)
    {
        ArrayList<Subject> subjects = asyncData[0].getSubjects();
        Map<String, String> loginTryCookie = asyncData[0].getLoginTryCookie();
        try {
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";
            Map<String, String> data = new HashMap<String, String>();
            data.put("mnid", "201212600596");
            Connection.Response subjectResponse = Jsoup.connect("http://e-learn.cnu.ac.kr/lms/mypage/schedule/doListView.dunet")
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

            Document scheduleDocument = subjectResponse.parse();
            //Log.d("ScheduleAsyncTask", scheduleDocument.toString());

            Element element = scheduleDocument.getElementById("tbody_schedule_list");

            

        } catch (Exception ex) {
            Log.e("SubjectAsync", "Error");
            ex.printStackTrace();
        }
        return subjects;
    }
}
