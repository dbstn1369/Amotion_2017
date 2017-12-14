package com.amotion.amotion_2017.data;

/**
 * Created by JSH on 2017-12-09.
 */

public class CseBoardItem {
    private String number;
    private String title;
    private String writer;
    private String date;
    private String url;

    public CseBoardItem(String number, String title, String writer, String date, String url) {
        this.number = number;
        this.title = title;
        this.writer = writer;
        this.date = date;
        this.url = url;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
