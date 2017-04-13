package com.pain.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pain.aidlclient.TestBeanManager;

import java.util.List;

public class AIDLActivity extends AppCompatActivity {
    Button button;
    Button button2;
    Button button3;

    //由AIDL文件生成的Java类
    private TestBeanManager testBeanManager = null;

    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;

    //包含Book对象的list
    private List<TestBean> mBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewAndEvent();
    }

    private void initViewAndEvent() {
        button= (Button) findViewById(R.id.button);
        button2= (Button) findViewById(R.id.button2);
        button3= (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook(v);
                Log.e("sad", "onClick: ");
            }
        });
    }

    /**
     * 按钮的点击事件，点击之后调用服务端的addBookIn方法
     *
     * @param view
     */
    public void addBook(View view) {
        //如果与服务端的连接处于未连接状态，则尝试连接
        if (!mBound) {
            attemptToBindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (testBeanManager == null) return;

        TestBean testBean = new TestBean();
        testBean.setName("zty123");
        testBean.setAge(300);
        try {
            testBeanManager.addBean(testBean);
            Log.e(getLocalClassName(), testBean.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 尝试与服务端建立连接
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.pain.aidlservice");
        intent.setPackage("com.pain.aidlserver");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            attemptToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(getLocalClassName(), "service connected");
            testBeanManager = TestBeanManager.Stub.asInterface(service);
            mBound = true;

            if (testBeanManager != null) {
                try {
                    mBeans = testBeanManager.getBeans();
                    Log.e(getLocalClassName(), mBeans.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(getLocalClassName(), "service disconnected");
            mBound = false;
        }
    };
}