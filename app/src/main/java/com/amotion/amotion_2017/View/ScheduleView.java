package com.amotion.amotion_2017.data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amotion.amotion_2017.R;

/**
 * Created by Choiyoonsoo on 2017-12-05.
 */

public class SingerItemView extends LinearLayout {
    private TextView subject;
    private TextView content;
    private TextView startDate;
    private TextView endDate;

    public SingerItemView(Context context){
        super(context);

        init(context);
    }

    public SingerItemView(Context context, AttributeSet attrs){
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.schedule_layout,this,true);
        subject = (TextView)findViewById(R.id.subject);
        content = (TextView)findViewById(R.id.content);
        startDate = (TextView)findViewById(R.id.startDate);
        endDate=(TextView)findViewById(R.id.endDate);
    }

    public void setSubject(TextView subject) {
        this.subject = subject;
    }

    public void setContent(TextView content) {
        this.content = content;
    }

    public void setStartDate(TextView startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(TextView endDate) {
        this.endDate = endDate;
    }
}
