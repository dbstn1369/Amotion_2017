package com.amotion.amotion_2017.data;

/**
 * Created by JSH on 2017-12-07.
 */

public class TableData
{
    private String title;
    private String boardName;
    private String id;
    private String subjectName;

    public TableData(String title, String boardName, String id, String subjectName)
    {
        this.title = title;
        this.boardName = boardName;
        this.id = id;
        this.subjectName = subjectName;
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
}
