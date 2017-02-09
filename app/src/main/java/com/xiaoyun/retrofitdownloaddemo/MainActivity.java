package com.xiaoyun.retrofitdownloaddemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.xiaoyun.downloadlib.DownLoadUtil;
import com.xiaoyun.downloadlib.bean.DownInfo;
import com.xiaoyun.downloadlib.listener.DownLoadProgressListener;

/**
 * Created by zl on 2017/2/9.
 */

public class MainActivity extends Activity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.pb);
        findViewById(R.id.bt_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownInfo info = new DownInfo.Builder()
                        .setBaseUrl("http://10.10.8/165/")
                        .setDownloadUrl("http://10.10.8.165/G2_oem-0159-from-0134.bin")
                        .setCachePath("/sdcard/1111.bin")
                        .setProgressListener(new DownLoadProgressListener() {
                            @Override
                            public void update(final long bytesRead, final long contentLength, boolean done) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setMax((int) contentLength);
                                        progressBar.setProgress((int) bytesRead);
                                    }
                                });
                            }
                        }).build();
                DownLoadUtil.getInstance().start(info);
            }
        });
    }
}
