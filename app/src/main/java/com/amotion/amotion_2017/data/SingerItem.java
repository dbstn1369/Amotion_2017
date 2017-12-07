package com.amotion.amotion_2017.data;

/**
 * Created by Choiyoonsoo on 2017-12-05.
 */

public class SingerItem {
    String number;
    String context;
    String day;

    public SingerItem(String number, String context, String day){
        this.number = number;
        this.context = context;
        this.day = day;
    }

    public String getNumber(){
        return number;
    }
    public void setNumber(String number){
        this.number =number;
    }
    public String getContext(){
        return context;
    }
    public void setContext(String context){
        this.context =context;
    }
    public String getDay (){
        return day;
    }
    public void setDay(String day){
        this.day =day;
    }

}
