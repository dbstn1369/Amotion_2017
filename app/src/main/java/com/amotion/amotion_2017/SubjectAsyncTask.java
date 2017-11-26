package com.amotion.amotion_2017;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JSH on 2017-11-26.
 */
//나의 강의실 긁어오기
public class SubjectAsyncTask extends AsyncTask<Map<String, String>, Subject, ArrayList<Subject>> {

    Context context;

    public SubjectAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Subject> doInBackground(Map<String, String>[] maps) {

        ArrayList<Subject> subjects = new ArrayList<>();

        try {

            Map<String, String> logindata = new HashMap<String, String>();//로그인하기 위한 data 값들.
            logindata.put("user_id", "");
            logindata.put("user_password", "");
            logindata.put("group_cd", "UN");
            logindata.put("sub_group_cd", "");
            logindata.put("sso_url", "http://portal.cnu.ac.kr/enview/portal/");
            logindata.put("schedule_selected_date", new Date().toString());
            logindata.put("fnc_return", "");

            // 로그인
            Connection.Response loginPageResponse = Jsoup.connect("http://e-learn.cnu.ac.kr/login/doLogin.dunet")//세션유지를 위한 사이트 연결
                    .timeout(60000)//header 값들은 구글 크롬개발자로 구하면 됩니다.
                    .header("Origin", "http://e-learn.cnu.ac.kr")
                    .header("Referer", "http://e-learn.cnu.ac.kr/login/doLogin.dunet")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .data(logindata)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute();

            Map<String, String> loginTryCookie1 = loginPageResponse.cookies();//로그인을 하여 얻은 쿠키 아래에서 사용함.

            //Log.d("SubjectAsync", loginTryCookie1.toString());

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
                    .cookies(loginTryCookie1)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute();


            Document loginPageDocument = subjectResponse.parse();

            //Log.d("SubjectAsync", loginPageDocument.toString());


            Elements subjectElements = loginPageDocument.select("a.classin2");
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
            }

            Log.d("SubjectAsync", subjects.toString());

            // 저장
            SharedPreferences test = context.getSharedPreferences("subjects", MODE_PRIVATE);
            SharedPreferences.Editor editor = test.edit();
            Gson gson = new Gson();
            String json = gson.toJson(subjects);
            editor.putString("Subjects", json);
            editor.commit();


        } catch (Exception ex) {
            Log.e("SubjectAsync", "Error");
            ex.printStackTrace();
        }

        return null;
    }
}
