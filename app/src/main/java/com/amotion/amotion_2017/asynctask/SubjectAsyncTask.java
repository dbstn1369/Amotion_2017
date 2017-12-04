package com.amotion.amotion_2017.asynctask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amotion.amotion_2017.data.Subject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by JSH on 2017-11-26.asd
 */
//나의 강의실 긁어오기
public class SubjectAsyncTask extends AsyncTask<Map<String, String>,Void, ArrayList<Subject>> {

    @Override
    protected ArrayList<Subject> doInBackground(Map<String, String>[] maps) {

        ArrayList<Subject> subjects = new ArrayList<>();
        Map<String, String> loginTryCookie = maps[0];
        try {

            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";

            Connection.Response subjectResponse = Jsoup.connect("http://e-learn.cnu.ac.kr/lms/myLecture/doListView.dunet")
                    .userAgent(userAgent)
                    .timeout(60000)
                    .header("Origin", "http://e-learn.cnu.ac.kr")
                    .header("Referer", "http://e-learn.cnu.ac.kr/main/MainView.dunet")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .cookies(loginTryCookie)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute();

            Document subjectPageDocument = subjectResponse.parse();

            Elements subjectElements = subjectPageDocument.select("a.classin2");
            for (Element e: subjectElements )
            {
                Subject temp = new Subject();
                temp.setCourse_id(e.attr("course_id"));
                temp.setClass_no(e.attr("class_no"));
                temp.setSubject_cd(e.attr("subject_cd"));
                temp.setTerm_cd(e.attr("term_cd"));
                temp.setTerm_year(e.attr("term_ym"));
                temp.setUser_no(e.attr("user_no"));
                temp.setSubjectName(e.text());
                subjects.add(temp);

                //System.out.println( temp.toString());
            }

            //Log.d("SubjectAsync", subjects.toString());

            // 저장
            /*
            SharedPreferences test = context.getSharedPreferences("subjects", MODE_PRIVATE);
            SharedPreferences.Editor editor = test.edit();
            Gson gson = new Gson();
            String json = gson.toJson(subjects);
            editor.putString("Subjects", json);
            editor.commit();
            */

        } catch (Exception ex) {
            Log.e("SubjectAsync", "Error");
            ex.printStackTrace();
        }
        return subjects;
    }
}
