package com.amotion.amotion_2017.data;

import android.accessibilityservice.GestureDescription;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JSH on 2017-12-08.
 */

public class Board implements Parcelable
{
    private String title;
    private String writer;
    private String writeDate;
    private String text;
    private boolean isFile = false;

    public Board(String title, String writer, String writeDate, String text, boolean isFile)
    {
        this.title = title;
        this.writer = writer;
        this.writeDate = writeDate;
        this.text = text;
        this.isFile = isFile;
    }


    protected Board(Parcel in)
    {
        title = in.readString();
        writer = in.readString();
        writeDate = in.readString();
        text = in.readString();
        isFile = (in.readInt()==1);
    }

    public static final Creator<Board> CREATOR = new Creator<Board>()
    {
        @Override
        public Board createFromParcel(Parcel in)
        {
            return new Board(in);
        }

        @Override
        public Board[] newArray(int size)
        {
            return new Board[size];
        }
    };

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getWriter()
    {
        return writer;
    }

    public void setWriter(String writer)
    {
        this.writer = writer;
    }

    public String getWriteDate()
    {
        return writeDate;
    }

    public void setWriteDate(String writeDate)
    {
        this.writeDate = writeDate;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public boolean isFile()
    {
        return isFile;
    }

    public void setFile(boolean file)
    {
        isFile = file;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(title);
        dest.writeString(writer);
        dest.writeString(writeDate);
        dest.writeString(text);
        dest.writeInt(isFile?1:0);
    }

    @Override
    public String toString()
    {
        return "Board{" +
                "title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", writeDate='" + writeDate + '\'' +
                ", text='" + text + '\'' +
                ", isFile=" + isFile +
                '}';
    }
}
