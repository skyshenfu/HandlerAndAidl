package com.pain.handlertest;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by zty
 * 个人github地址：http://www.github.com/skyshenfu
 * 日期：2017/4/12
 * 版本：1.0.0
 * 描述：
 */

public class ThreadToMain extends Thread {
    private Handler mHandler;
    public ThreadToMain(Handler handler) {
        this.mHandler=handler;
    }

    @Override
    public void run() {
        super.run();
        Message message=mHandler.obtainMessage();
        message.what=9527;
        mHandler.sendMessage(message);

    }
}
