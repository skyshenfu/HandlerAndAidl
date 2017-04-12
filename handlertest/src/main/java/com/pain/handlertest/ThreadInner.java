package com.pain.handlertest;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by zty
 * 个人github地址：http://www.github.com/skyshenfu
 * 日期：2017/4/12
 * 版本：1.0.0
 * 描述：如果子线程没有对Looper进行初始化操作的话会报错----->inside thread that has not called Looper.prepare()
 * 先prepare再Looper.myLopper，调用Looper.loop()之后后面的代码不会立即执行;

 */

public class ThreadInner extends Thread {
    private Handler mHandler;

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        mHandler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.e("handleMessage: ","call" );
                if (msg.what==4399){
                    Log.e("TAG", "子线程自产自销");
                }
            }
        };
        Message message=mHandler.obtainMessage();
        message.what=4399;
        mHandler.sendMessage(message);
        Log.e("TAG", "23333");
        Looper.loop();

    }
}
