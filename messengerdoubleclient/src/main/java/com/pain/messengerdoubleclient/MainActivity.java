package com.pain.messengerdoubleclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 实质上都是通过隐式意图来启动service
 * 客户端一方在绑定service的同时指定msg.replyTo = clientMessenger其中clientMessenger通过客户端new 来初始化
 * 服务器端 通过clientMessenger = msg.replyTo来拿到客户端的messenger 其中serviceMessenger在服务器端通过new来初始化
 *
 */
public class MainActivity extends AppCompatActivity {
    private Button mBtBind;
    private Button mBtUnBind;
    private TextView mTvMsg;

    private static final int SEND_MESSAGE_CODE = 0x0001;
    private static final int RECEIVE_MESSAGE_CODE = 0x0002;
    private boolean isBound = false;

    private String SERVICE_ACTION = "com.pain.messengerdoubleserver.DoubleService";

    private Messenger serviceMessenger = null;

    private Messenger clientMessenger = new Messenger(new ClientHandler());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtBind = (Button) findViewById(R.id.button);
        mBtUnBind = (Button) findViewById(R.id.button2);
        mTvMsg = (TextView) findViewById(R.id.textView);
        mBtUnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBound){
                    unbindService(serviceConnection);
                }
            }
        });
        mBtBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBound){
                    Intent intent = new Intent();
                    intent.setAction(SERVICE_ACTION);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    PackageManager pm = getPackageManager();
                    ResolveInfo info = pm.resolveService(intent,0);
                    if(info != null){
                        String packageName = info.serviceInfo.packageName;
                        String serviceName = info.serviceInfo.name;
                        ComponentName componentName = new ComponentName(packageName,serviceName);
                        intent.setComponent(componentName);
                        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
                    }
                }
            }
        });
    }
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceMessenger = new Messenger(service);
            isBound = true;
            Message msg = Message.obtain();
            msg.what = SEND_MESSAGE_CODE;
            Bundle data = new Bundle();
            data.putString("msg","你好，MyService，我是客户端");
            msg.setData(data);
            msg.replyTo = clientMessenger;
            try {
                serviceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceMessenger = null;
            isBound = false;
        }
    };
    private class ClientHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == RECEIVE_MESSAGE_CODE){
                Bundle data = msg.getData();
                if(data != null){
                    String str = data.getString("msg");
                    mTvMsg.setText(str);
                }
            }
        }
    }
}
