package com.pain.handlertest;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * 不能行的通的原因就是因为初始化handler的是后会把handlemessage方法覆写掉所以不能更新MainActivity里面的视图
 */
public class SecondActivity extends AppCompatActivity {
    Button buttonNext;
    public SHandler handler=new SHandler(this);
    private  static class SHandler extends Handler{
        private WeakReference<Context> reference;
        public SHandler(Context context) {
            reference=new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            SecondActivity activity= (SecondActivity) reference.get();
            if (activity!=null){
                activity.buttonNext.setText(msg.what);
            }
            switch (msg.what){
                case 65536:
                    Toast.makeText(activity, "用这种方式避免内存泄漏", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initViewAndEvent();
    }

    private void initViewAndEvent() {
        buttonNext= (Button) findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message=new Message();
                message.what=65536;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
