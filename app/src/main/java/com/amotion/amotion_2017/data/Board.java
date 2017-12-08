package com.amotion.amotion_2017.data;

import android.accessibilityservice.GestureDescription;

/**
 * Created by JSH on 2017-12-08.
 */

public class Board {
    private String title;
    private String writer;
    private String writeDate;
    private String text;

    public Board(String title, String writer, String writeDate, String text) {
        this.title = title;
        this.writer = writer;
        this.writeDate = writeDate;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
