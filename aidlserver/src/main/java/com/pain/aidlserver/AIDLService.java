package com.pain.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pain.aidlclient.TestBean;
import com.pain.aidlclient.TestBeanManager;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {

    public final String TAG = this.getClass().getSimpleName();

    //包含Book对象的list
    private List<TestBean> mBeans = new ArrayList<>();

    //由AIDL文件生成的BookManager
        private final TestBeanManager.Stub mTestBeanManager = new TestBeanManager.Stub() {
        @Override
        public List<TestBean> getBeans() throws RemoteException {
            synchronized (this){
                if (mBeans!=null){
                    return mBeans;
                }
                return new ArrayList<>();

            }
        }

        @Override
        public void addBean(TestBean testBean) throws RemoteException {
            synchronized (this){

                if (mBeans==null){
                    mBeans=new ArrayList<>();
                }
                if (testBean==null){
                    testBean=new TestBean();
                }
                Log.e(TAG, testBean.toString());
                testBean.setName("123 one punch");
                if (!mBeans.contains(testBean)){
                    mBeans.add(testBean);
                }
                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBeans.toString());
            }
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
     /*   @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {
                Log.e(TAG, "invoking getBooks() method , now the list is : " + mBooks.toString());
                if (mBooks != null) {
                    return mBooks;
                }
                return new ArrayList<>();
            }*/
        };


      /*  @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.e(TAG, "Book is null in In");
                    book = new Book();
                }
                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                book.setPrice(2333);
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
            }
        }
    };*/

    @Override
    public void onCreate() {
        super.onCreate();
        TestBean testBean = new TestBean();
        testBean.setName("pain");
        testBean.setAge(23);
        mBeans.add(testBean);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getClass().getSimpleName(), String.format("on bind,intent = %s", intent.toString()));
        return mTestBeanManager;
    }
}