package com.amotion.amotion_2017.asynctask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSH on 2017-12-04.
 */

public class LoginAsyncTask extends AsyncTask<Map<String, String>, Void, Map<String, String>> {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    LoginAsyncTask(Context context) {
        LoginAsyncTask.context = context;
    }


    @Override
    protected Map<String, String> doInBackground(Map<String, String>[] maps) {
        Map<String, String> loginTryCookie=null;
        try {
            JSONObject idpw = new JSONObject(loadJSONFromAsset());

            //TODO 아이디 패스워드 입력
            Map<String, String> logindata = new HashMap<>();//로그인하기 위한 data 값들.
            logindata.put("user_id", idpw.getString("id"));
            logindata.put("user_password", idpw.getString("pw"));
            logindata.put("group_cd", "UN");
            logindata.put("sub_group_cd", "");
            //logindata.put("sso_url", "http://portal.cnu.ac.kr/enview/portal/");
            //logindata.put("schedule_selected_date", new Date().toString());
            //logindata.put("fnc_return", "");

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

            loginTryCookie = loginPageResponse.cookies();//로그인을 하여 얻은 쿠키 아래에서 사용함.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loginTryCookie;
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
