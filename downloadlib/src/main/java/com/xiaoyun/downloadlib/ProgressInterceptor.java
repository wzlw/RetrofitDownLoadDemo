package com.xiaoyun.downloadlib;

import com.xiaoyun.downloadlib.listener.DownLoadProgressListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by zl on 2017/2/8.
 */

public class ProgressInterceptor implements Interceptor {

    private final DownLoadProgressListener mListener;

    public ProgressInterceptor(DownLoadProgressListener listener) {
        mListener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), mListener))
                .build();
    }
}
