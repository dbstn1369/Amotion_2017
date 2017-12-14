package com.amotion.amotion_2017.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by YunDongHyeon on 2017-12-14.
 */

public class StartService extends BroadcastReceiver {
    public static final String ACTION_RESTART=  "ACTION.Restart.PushService";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_RESTART)){
            Intent intent1=new Intent(context,PushService.class);
            context.startService(intent1);
        }
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent intent1=new Intent(context,PushService.class);
            context.startService(intent1);
        }
    }
}
