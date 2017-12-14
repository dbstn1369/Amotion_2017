package com.amotion.amotion_2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.amotion.amotion_2017.data.Board;

public class BoardActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Board board=null;
        try
        {
            board = (Board) getIntent().getExtras().get("Board");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        TextView title = (TextView)findViewById(R.id.subject);
        TextView writer = (TextView)findViewById(R.id.writer);
        TextView writeDate = (TextView)findViewById(R.id.date);
        TextView isFile = (TextView)findViewById(R.id.context);

        WebView text = (WebView) findViewById(R.id.content);

        text.setWebViewClient(new WebViewClient());

        title.setText(board.getTitle());
        writer.setText(board.getWriter());
        writeDate.setText(board.getWriteDate());
        isFile.setText(board.isFile()?"파일있음":"파일없음");

        text.loadData(board.getText(),"text/html; charset=UTF-8", null);


    }
}
