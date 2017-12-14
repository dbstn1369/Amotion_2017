package com.amotion.amotion_2017.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amotion.amotion_2017.R;

/**
 * Created by YunDongHyeon on 2017-12-09.
 */

public class SubjectView extends LinearLayout {
    private TextView subject;
    private TextView title;
    private TextView date;

    public SubjectView(Context context) {
        super(context);
        init(context);
    }

    public SubjectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.subject_item, this, true);

        subject=(TextView)findViewById(R.id.subject_course);
        title=(TextView)findViewById(R.id.subject_title);
        date=(TextView)findViewById(R.id.subject_date);
    }

    public void setSubject(String subject) {
        this.subject.setText(subject);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDate(String date) {
        this.date.setText(date);
    }
}
