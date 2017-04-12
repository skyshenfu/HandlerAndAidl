package com.pain.handlertest;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 不能行的通的原因就是因为初始化handler的是后会把handlemessage方法覆写掉所以不能更新MainActivity里面的视图
 */
public class SecondActivity extends AppCompatActivity {
    Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initViewAndEvent();
        Looper a=Looper.getMainLooper();
        System.out.println(a);
    }

    private void initViewAndEvent() {
        buttonNext= (Button) findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message=new Message();
                message.what=10086;
                new Handler(Looper.getMainLooper()).sendMessage(message);
            }
        });
    }
}
