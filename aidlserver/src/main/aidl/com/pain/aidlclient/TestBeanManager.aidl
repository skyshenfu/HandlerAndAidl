// TestBeanManager.com.pain.aidl
package com.pain.aidlclient;
import com.pain.aidlclient.TestBean;

// Declare any non-default types here with import statements

interface TestBeanManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<TestBean> getBeans();
    void addBean(in TestBean testBean);
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
