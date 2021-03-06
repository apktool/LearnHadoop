/**
 * Description
 * Created with IntelliJ IDEA.
 *
 * @author apktool
 * @version 0.1
 * @since 10/11/17
 */
package com.thread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class MyThread extends Thread{
    static private Object object;
    static private Method method;
    static private HashMap map;

    @Override
    public void run() {
        try {
            if (map == null){
                method.invoke(object);
            }else{
                method.invoke(object, map);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    public void start(Object object, Method method, HashMap map) {
        this.object = object;
        this.method = method;
        this.map = map;
        this.start();
    }

    public void start(Object object, Method method) {
        this.object = object;
        this.method = method;
        this.map = null;
        this.start();
    }

    @Override
    protected void finalize() throws Throwable {
        Thread.currentThread().interrupt();
        return;
    }
}
