package com.amotion.amotion_2017.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.amotion.amotion_2017.data.Board;
import com.amotion.amotion_2017.data.BoardItemAsyncData;
import com.amotion.amotion_2017.data.Subject;
import com.amotion.amotion_2017.data.TableData;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by JSH on 2017-12-07.
 */

public class BoardItemAsyncTask extends AsyncTask<BoardItemAsyncData,String,Board>
{
    @Override
    protected Board doInBackground(BoardItemAsyncData... boardItemAsyncData) {
        TableData tableData =  boardItemAsyncData[0].getTableData();
        Map<String, String> loginTryCookie = boardItemAsyncData[0].getLoginCookie();
        Board board=null;
        try {

            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";

            Connection.Response boardResponse = Jsoup.connect("http://e-learn.cnu.ac.kr/lms/class/boardItem/doViewBoardItem.dunet")
                    .userAgent(userAgent)
                    .timeout(60000)
                    .header("Origin", "http://e-learn.cnu.ac.kr")
                    .header("Referer", "http://e-learn.cnu.ac.kr/main/MainView.dunet")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .data(tableData.getData())
                    .cookies(loginTryCookie)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute();

            Document boardDocument = boardResponse.parse();

            Elements boardElements = boardDocument.select("table.view");

            Element boardTable = boardElements.first();

            //System.out.println(boardTable.toString());

            String title = boardTable.select("#td_boarditem_title").first().ownText();
            String writer = boardTable.select("#td_user_name").first().ownText();
            String writeDate = boardTable.select("#td_f_insert_dt").first().ownText();
            String text = boardTable.select("#td_boarditem_content").first().ownText();

            Elements fileElement = boardTable.select("tr").get(2).select("a");

            boolean isFile = false;

            if (!fileElement.isEmpty()){
                isFile=true;
            }

            board = new Board(title,writer,writeDate,text,isFile);

        } catch (Exception ex) {
            Log.e("SubjectAsync", "Error");
            ex.printStackTrace();
        }
        return board;
    }
}
