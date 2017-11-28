package com.amotion.amotion_2017;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

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

public class SubjectTableAsyncTask extends AsyncTask<ArrayList<Subject>, Void, ArrayList<Subject>> {
    @SuppressLint("StaticFieldLeak")

    private Context context;

    SubjectTableAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Subject> subjects) {
        super.onPostExecute(subjects);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected ArrayList<Subject> doInBackground(ArrayList<Subject>[] arrayLists) {
        try {
            JSONObject idpw = new JSONObject(loadJSONFromAsset());

            //TODO 아이디 패스워드 입력
            Map<String, String> logindata = new HashMap<String, String>();//로그인하기 위한 data 값들.
            logindata.put("user_id", idpw.getString("id"));
            logindata.put("user_password", idpw.getString("pw"));
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

            Log.d("SubjectTableAsyncTask", loginTryCookie1.toString());

            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";

            ArrayList<Subject> subjects = arrayLists[0];

            Subject subject = subjects.get(0);

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
                    .cookies(loginTryCookie1)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute();


            Document subMenuDocument = subjectResponse.parse();

            //Log.d("SubjectTableAsyncTask", subMenuDocument.toString());

            ArrayList<SubMenu> subMenus = new ArrayList<>();

            Elements subMenuElements = subMenuDocument.select("li.subMenu_list");
            for (Element e : subMenuElements) {
                SubMenu temp = new SubMenu();

                temp.setMnid(e.select("input[name=mnid]").first().attr("value"));
                Element boardno = e.select("input[name=board_no]").first();


                if (boardno == null) {
                    temp.setBoard_no("");
                } else {
                    temp.setBoard_no(boardno.attr("value"));
                }
                temp.setMenuName(e.child(0).text());
                subMenus.add(temp);
            }

            for (int i = 0; i < subMenus.size(); i++) {
                System.out.println(subMenus.get(i));
            }

            //Log.d("SubjectAsync", subjects.toString());
/*
            // 저장
            SharedPreferences test = context.getSharedPreferences("subjects", MODE_PRIVATE);
            SharedPreferences.Editor editor = test.edit();
            Gson gson = new Gson();
            String json = gson.toJson("");
            editor.putString("Subjects", json);
            editor.commit();
*/

        } catch (Exception ex)

        {
            Log.e("SubjectAsync", "Error");
            ex.printStackTrace();
        }
        return null;
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = context.getAssets().open("idpw.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
