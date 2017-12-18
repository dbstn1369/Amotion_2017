package com.amotion.amotion_2017.asynctask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSH on 2017-12-09.
 */

public class CseLoginAsyncTask extends AsyncTask<Map<String, String>, String, Map<String, String>> {

    @Override
    protected Map<String, String> doInBackground(Map<String, String>[] maps) {
        Map<String, String> loginTryCookie=null;
        try {

            Map<String, String> logindata = new HashMap<>();//로그인하기 위한 data 값들.
            logindata.put("user_id", maps[0].get("id"));
            logindata.put("password",maps[0].get("pw"));

            // 로그인
            Connection.Response loginPageResponse = Jsoup.connect("http://computer.cnu.ac.kr/index.php?act=procMemberLogin")//세션유지를 위한 사이트 연결
                    .timeout(60000)//header 값들은 구글 크롬개발자로 구하면 됩니다.
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", " ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .header("Cache-Control","max-age=0")
                    .header("Connection","keep-alive")
                    .header("Host", "computer.cnu.ac.kr")
                    .header("Origin", "http://computer.cnu.ac.kr")
                    .header("Referer", "https://computer.cnu.ac.kr/index.php?act=dispMemberLoginForm")
                    .header("Upgrade-Insecure-Requests","1")
                    .data(logindata)
                    .method(Connection.Method.POST)
                    .execute();

            loginTryCookie = loginPageResponse.cookies();//로그인을 하여 얻은 쿠키 아래에서 사용함.

        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginTryCookie;
    }

}
