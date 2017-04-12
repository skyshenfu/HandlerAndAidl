package com.pain.handlertest;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textView;
    Button button1;
    Button button2;
    Button button3;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==9527){
                textView.setText("子线程更新了"+msg.what);

            }else if (msg.what==10086){
                textView.setText("下级页面通知更新"+msg.what);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewAndEvent();
        Looper a=Looper.getMainLooper();
        System.out.println(a);
    }

    private void initViewAndEvent() {
       textView= (TextView) findViewById(R.id.textview);
        button1= (Button) findViewById(R.id.button1);
        button2= (Button) findViewById(R.id.button2);
        button3= (Button) findViewById(R.id.button3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                new ThreadToMain(handler).start();
                break;
            case R.id.button2:
                new ThreadInner().start();
                Log.e("ssss", "onClick: 2" );
                break;
            case R.id.button3:
                startActivity(new Intent(this,SecondActivity.class));
                break;
        }
    }
}
