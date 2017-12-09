package com.amotion.amotion_2017.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amotion.amotion_2017.R;

import java.util.Date;

/**
 * Created by Choiyoonsoo on 2017-12-05.
 */

public class ScheduleView extends LinearLayout {
    private TextView course;
    private TextView content;
    private TextView startDate;
    private TextView endDate;

    public ScheduleView(Context context){
        super(context);
        init(context);
    }

    public ScheduleView(Context context, AttributeSet attrs){
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.schedule_item,this,true);
        course = (TextView)findViewById(R.id.schedule_course);
        content = (TextView)findViewById(R.id.schedule_content);
        startDate = (TextView)findViewById(R.id.schedule_startDate);
        endDate=(TextView)findViewById(R.id.schedule_endDate);
    }

    public void setCourse(String course) {
        this.course.setText(course);
    }

    public void setContent(String content) {
        this.content.setText(content);
    }

    public void setStartDate(Date startDate) {
        this.startDate.setText(startDate.toString());
    }

    public void setEndDate(Date endDate) {
        this.endDate.setText(endDate.toString());
    }
}
