package com.pain.messengerdoubleserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LaunchReceiver extends BroadcastReceiver
{  
    @Override  
    public void onReceive(Context context, Intent intent)
    {  
        Intent intent1 = new Intent(context , DoubleService.class);
        // 启动指定Server  
        context.startService(intent1);  
    }  
} 