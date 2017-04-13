package com.pain.aidlclient;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zty
 * 个人github地址：http://www.github.com/skyshenfu
 * 日期：2017/4/10
 * 版本：1.0.0
 * 描述：
 */

public class TestBean implements Parcelable {
    private int age;
    private String name;

    protected TestBean(Parcel in) {
        age = in.readInt();
        name = in.readString();
    }

    public static final Creator<TestBean> CREATOR = new Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel in) {
            return new TestBean(in);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        name = dest.readString();
        age = dest.readInt();
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
