package com.amotion.amotion_2017.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.amotion.amotion_2017.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by YunDongHyeon on 2017-12-14.
 */

public class PushService extends Service implements Runnable {
    private static final int REBOOT_DELAY_TIMER = 10 * 1000;
    private static final int MAX_SUBJET = 10;
    ArrayList<DatabaseReference> subjectFBlist;
    ArrayList<DatabaseReference> cnuFBlist;
    NotificationManager notificationManager;

    String[] menuName;
    String[] subjectNameList;
    private Handler mHandler;
    private boolean mIsRunning;
    static int position;
    private int mStartId = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        subjectFBlist = new ArrayList<DatabaseReference>();
        cnuFBlist = new ArrayList<DatabaseReference>();

        menuName = getResources().getStringArray(R.array.cnuMenu);
        subjectNameList = new String[MAX_SUBJET];

        for (int i = 0; i < 5; i++) {
            DatabaseReference cnuRef = FirebaseDatabase.getInstance().getReference("CNU").child(menuName[i]);
            cnuFBlist.add(cnuRef);
        }

        for (int i = 1; ; i++) {
            String subjectName = getSharedPreferences("cnup_subjet", MODE_PRIVATE).getString("MYSUBJECT" + i, "null");
            subjectNameList[i - 1] = subjectName;
            if (subjectName.equals("null"))
                break;
            DatabaseReference subjectRef = FirebaseDatabase.getInstance().getReference("Subject").child(subjectName);
            subjectFBlist.add(subjectRef);

            Log.e("MYSUBJECT",subjectName);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        mStartId = startId;

        mHandler = new Handler();
        mHandler.postDelayed(this, REBOOT_DELAY_TIMER);
        mIsRunning = true;
    }

    @Override
    public void run() {
        if (mIsRunning) {
            check();
            mHandler.postDelayed(this, REBOOT_DELAY_TIMER);
            mIsRunning = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setCNUListener(int i, DataSnapshot dataSnapshot) {
        String firebseValue = dataSnapshot.getValue(String.class);
        String storeValue = getSharedPreferences("cnup_cnu", MODE_PRIVATE).getString(menuName[i], "없음");

        SharedPreferences.Editor editor =getSharedPreferences("cnup_cnu", MODE_PRIVATE).edit();
        editor.putString(menuName[i],firebseValue);
        editor.commit();

        if (!firebseValue.equals(storeValue)) {
            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("CNUP 알림 학과게시판 업로드")
                    .setContentText(menuName[i] + " : " + firebseValue + " / " + storeValue)
                    .setSmallIcon(R.drawable.fj_icon)
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(1, notification);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setSubjetListener(int i, DataSnapshot dataSnapshot) {
        String firebseValue = dataSnapshot.getValue(String.class);
        String storeValue = getSharedPreferences("cnup_subjet", MODE_PRIVATE).getString(subjectNameList[i], "없음");

        SharedPreferences.Editor editor =getSharedPreferences("cnup_subjet", MODE_PRIVATE).edit();
        editor.putString(subjectNameList[i],firebseValue);
        editor.commit();

        if (!firebseValue.equals(storeValue)) {
            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("CNUP 알림 이러닝 업로드")
                    .setContentText(subjectNameList[i] + " : " + firebseValue + " / " + storeValue)
                    .setSmallIcon(R.drawable.fj_icon)
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(1, notification);
        }
    }

    private void check() {

        for (int i = 0; i < cnuFBlist.size(); i++) {
            final int finalI = i;
            cnuFBlist.get(i).addValueEventListener(new ValueEventListener() {
                int position = finalI;

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    setCNUListener(position, dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        for (int i = 0; i < subjectFBlist.size(); i++) {
            final int finalI = i;
            subjectFBlist.get(i).addValueEventListener(new ValueEventListener() {
                int position = finalI;

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    setSubjetListener(finalI,dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


}
