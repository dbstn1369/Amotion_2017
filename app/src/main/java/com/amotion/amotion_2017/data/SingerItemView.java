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
    TextView textView1;
    TextView textView2;
    TextView textView3;

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
        inflater.inflate(R.layout.singer_item,this,true);
        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
    }

    public void setNumber(int number){textView1.setText(number);}
    public void setContext(String context){textView2.setText(context);}
    public void setDay(String day){textView3.setText(day);}

}
