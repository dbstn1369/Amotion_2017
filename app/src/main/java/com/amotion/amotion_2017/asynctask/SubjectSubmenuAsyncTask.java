package com.amotion.amotion_2017.asynctask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.amotion.amotion_2017.data.AsyncData;
import com.amotion.amotion_2017.data.SubMenu;
import com.amotion.amotion_2017.data.Subject;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JSH on 2017-11-27.asd
 */

public class SubjectSubmenuAsyncTask extends AsyncTask<AsyncData, Void, ArrayList<Subject>> {


    @Override
    protected ArrayList<Subject> doInBackground(AsyncData... asyncData) {
        ArrayList<Subject> subjects = asyncData[0].getSubjects();
        Map<String, String> loginTryCookie = asyncData[0].getLoginTryCookie();

        try {
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";

            for (int index = 0; index < subjects.size(); index++) {
                Subject subject = subjects.get(index);

                Map<String, String> subMenuData = new HashMap<String, String>();
                subMenuData.put("mnid", subject.getMnid());
                subMenuData.put("course_id", subject.getCourse_id());
                subMenuData.put("class_no", subject.getClass_no());
                subMenuData.put("term_year", subject.getTerm_year());
                subMenuData.put("term_cd", subject.getTerm_cd());
                subMenuData.put("subject_cd", subject.getSubject_cd());
                subMenuData.put("user_no", subject.getUser_no());

                Connection.Response subjectResponse = Jsoup.connect("http://e-learn.cnu.ac.kr/lms/class/classroom/doViewClassRoom_new.dunet")
                        .userAgent(userAgent)
                        .timeout(60000)
                        .header("Origin", "http://e-learn.cnu.ac.kr")
                        .header("Referer", "http://e-learn.cnu.ac.kr/main/MainView.dunet")
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .data(subMenuData)
                        .cookies(loginTryCookie)
                        .method(Connection.Method.POST)
                        .ignoreContentType(true)
                        .execute();

                Document subMenuDocument = subjectResponse.parse();

                //Log.d("SubjectSubmenuAsyncTask", subMenuDocument.toString());

                ArrayList<SubMenu> subMenus = new ArrayList<>();

                Elements subMenuElements = subMenuDocument.select("li.subMenu_list");
                for (Element e : subMenuElements) {
                    SubMenu temp = new SubMenu();

                    temp.setMnid(e.select("input[name=mnid]").first().attr("value"));
                    Element boardno = e.select("input[name=board_no]").first();

                    if (boardno == null) {
                        continue;
                    } else {
                        temp.setBoard_no(boardno.attr("value"));
                    }
                    temp.setMenuName(e.child(0).text());
                    subMenus.add(temp);

                }
                subjects.get(index).setSubMenus(subMenus);
            }

        } catch (Exception ex)

        {
            Log.e("SubjectAsync", "Error");
            ex.printStackTrace();
        }
        return subjects;
    }
}
