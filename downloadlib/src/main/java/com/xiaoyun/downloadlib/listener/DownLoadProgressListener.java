package com.xiaoyun.downloadlib.listener;

/**
 * Created by zl on 2017/2/8.
 */

public interface DownLoadProgressListener {

    void update(long bytesRead, long contentLength, boolean done);
}
