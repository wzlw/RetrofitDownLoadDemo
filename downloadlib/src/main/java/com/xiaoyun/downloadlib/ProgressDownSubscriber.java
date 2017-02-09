package com.xiaoyun.downloadlib;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by zl on 2017/2/9.
 */

public class ProgressDownSubscriber<T> extends Subscriber<T>{

    private static final String TAG = "ProgressDownSubscriber";

    @Override
    public void onCompleted() {
        Log.e(TAG, "Completed");
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "Error");
    }

    @Override
    public void onNext(T t) {
        Log.e(TAG, "Next");
    }

}
