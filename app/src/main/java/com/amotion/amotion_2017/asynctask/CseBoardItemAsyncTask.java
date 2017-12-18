package com.amotion.amotion_2017.asynctask;

import android.os.AsyncTask;

import com.amotion.amotion_2017.data.Board;
import com.amotion.amotion_2017.data.CseBoardItem;
import com.amotion.amotion_2017.data.CseBoardItemAsyncData;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

/**
 * Created by JSH on 2017-12-09.
 */

public class CseBoardItemAsyncTask extends AsyncTask<CseBoardItemAsyncData, String, Board> {

    @Override
    protected Board doInBackground(CseBoardItemAsyncData... cseBoardItemAsyncData) {
        Map<String, String> loginCookie = cseBoardItemAsyncData[0].getLoginCookie();
        CseBoardItem cseBoardItem = cseBoardItemAsyncData[0].getCseBoardItem();

        Board board = null;
        try {

            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";

            Connection.Response response = Jsoup.connect(cseBoardItem.getUrl())
                    .userAgent(userAgent)
                    .timeout(60000)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", " ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .header("Cache-Control","max-age=0")
                    .header("Connection","keep-alive")
                    .header("Host", "computer.cnu.ac.kr")
                    .header("Referer", cseBoardItem.getUrl())
                    .header("Upgrade-Insecure-Requests","1")
                    .cookies(loginCookie)
                    .method(Connection.Method.GET)
                    .execute();

            Document boardDocument = response.parse();
            Element boardElement = boardDocument.select("div.rd_hd").first();

            //System.out.println(boardDocument.toString());

            String title = boardElement.select("h1.np_18px").first().text();
            String writer = boardElement.select("div.btm_area").first().select(".side").first().text();
            String writeDate =boardElement.select("span.date").first().text();
            String text =boardElement.select("div.xe_content").first().toString();
            boolean isFile = false;

            //System.out.println(boardElement.select("div.xe_content").first().text());

            Elements fileElement = boardElement.select(".btm_area").first().select("ul");

            if (!fileElement.isEmpty()){
                isFile=true;
            }
            board = new Board(title,writer,writeDate,text,isFile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return board;
    }
}
