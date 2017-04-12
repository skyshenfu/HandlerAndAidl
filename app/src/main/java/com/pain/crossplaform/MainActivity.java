package com.pain.crossplaform;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Messenger方式单向跨进程
 *实质是利用handler机制加上普通的bindService方法生成的binder回调来实现
 */
public class MainActivity extends AppCompatActivity implements ServiceConnection{
    Button button;
    Button button2;
    TextView textView;
    private Messenger messenger;
    private boolean bound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        button= (Button) findViewById(R.id.button);
        button2= (Button) findViewById(R.id.button2);
        textView= (TextView) findViewById(R.id.textView);
    }

    private void initEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayHello(v);
            }
        });
    }

    private void buttonClick() {
        Intent intent=new Intent(this,MyService.class);
        bindService(intent,this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        messenger=new Messenger(service);
        bound=true;

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        messenger=null;
        bound=false;
    }
    public void sayHello(View v){
        if(!bound) return;
        Message msg = Message.obtain(null,MyService.MSGFLAG,0,0);
        try{
            messenger.send(msg);
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound){
            unbindService(this);
        }
    }
}
