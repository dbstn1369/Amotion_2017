package com.amotion.amotion_2017.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JSH on 2017-12-04.
 */

public class Schedule
{
    private String course;
    private String title;
    private Date start;
    private Date end;

    public Schedule(String course, String title, Date start, Date end)
    {
        this.course = course;
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public String getCourse()
    {
        return course;
    }

    public void setCourse(String course)
    {
        this.course = course;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getStart()
    {
        return start;
    }

    public void setStart(Date start)
    {
        this.start = start;
    }

    public Date getEnd()
    {
        return end;
    }

    public void setEnd(Date end)
    {
        this.end = end;
    }

    @Override
    public String toString()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return "Schedule{" +
                "course='" + course + '\'' +
                ", title='" + title + '\'' +
                ", start=" + simpleDateFormat.format(start) +
                ", end=" + simpleDateFormat.format(end) +
                '}';
    }
}
