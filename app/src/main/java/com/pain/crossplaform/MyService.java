package com.pain.crossplaform;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zty
 * 个人github地址：http://www.github.com/skyshenfu
 * 日期：2017/4/10
 * 版本：1.0.0
 * 描述：
 */

public class MyService extends Service {
    public static final  int MSGFLAG=1;
    Messenger messenger=new Messenger(new MyHandle());
    class MyHandle extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSGFLAG:
                    Toast.makeText(getApplicationContext(), "I GOT IT", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("here", "onBind: " );
        return messenger.getBinder();
    }
}
