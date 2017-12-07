package com.amotion.amotion_2017.data;

import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by JSH on 2017-12-07.
 */

public class TableData implements Comparable<TableData>
{
    private String title;
    private String boardName;
    private String id;
    private String subjectName;
    private Map<String, String> data;

    public TableData(String title, String boardName, String id, String subjectName, Map<String, String> data)
    {
        this.title = title;
        this.boardName = boardName;
        this.id = id;
        this.subjectName = subjectName;
        this.data = data;
    }


    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getBoardName()
    {
        return boardName;
    }

    public void setBoardName(String boardName)
    {
        this.boardName = boardName;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }


    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public Map<String, String> getData()
    {
        return data;
    }

    public void setData(Map<String, String> data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "TableData{" +
                "title='" + title + '\'' +
                ", boardName='" + boardName + '\'' +
                ", id='" + id + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }


    @Override
    public int compareTo(@NonNull TableData o)
    {
        return o.getId().compareTo(this.id);
    }
}
