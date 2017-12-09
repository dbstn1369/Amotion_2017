package com.amotion.amotion_2017.asynctask;

import android.os.AsyncTask;

import com.amotion.amotion_2017.data.CseAsyncData;
import com.amotion.amotion_2017.data.CseBoardItem;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by JSH on 2017-12-09.
 */

public class CseBoardAsyncTask extends AsyncTask<CseAsyncData, String, ArrayList<CseBoardItem>> {
    @Override
    protected ArrayList<CseBoardItem> doInBackground(CseAsyncData... cseAsyncData) {

        Map<String, String> loginTryCookie = cseAsyncData[0].getLoginCookies();
        String url = cseAsyncData[0].getUrl();
        ArrayList<CseBoardItem> cseBoardItems = new ArrayList<>();

        try {

            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";

            Connection.Response response4 = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .timeout(60000)
                    .header("Origin", "http://computer.cnu.ac.kr/")
                    .header("Referer", "http://computer.cnu.ac.kr/index.php?mid=notice")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", " ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .cookies(loginTryCookie)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .execute();
            Document noticeDocument = response4.parse();
            Element tableElement = noticeDocument.select(".bd_lst").first();
            Elements rowList = tableElement.select("tbody>tr");

            for (Element element : rowList) {
                String number = element.select(".no").first().text();
                String title = element.select(".title>a").first().text();
                String writer = element.select(".author").first().text();
                String date = element.select(".time").first().ownText();
                String aUrl = element.select(".title>a").first().attr("href");

                cseBoardItems.add(new CseBoardItem(number,title,writer,date,aUrl));
            }

        } catch (Exception e) {
            e.printStackTrace();
        };


        return cseBoardItems;
    }
}
