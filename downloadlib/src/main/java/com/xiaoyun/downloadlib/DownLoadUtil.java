package com.xiaoyun.downloadlib;

import com.xiaoyun.downloadlib.bean.DownInfo;
import com.xiaoyun.downloadlib.data.DownLoadService;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zl on 2017/2/8.
 */

public class DownLoadUtil {

    private static DownLoadUtil INSTANCE;

    private DownLoadUtil() {

    }

    public static DownLoadUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (DownLoadUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DownLoadUtil();
                }
            }
        }
        return INSTANCE;
    }

    public void start(final DownInfo info) {

        ProgressDownSubscriber<Object> subscriber = new ProgressDownSubscriber<>();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS)
                .addNetworkInterceptor(new ProgressInterceptor(info.getProgressListener()))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(info.getBaseUrl())
                .client(client)
                .build();

        DownLoadService service = retrofit.create(DownLoadService.class);
        service.download("bytes="+info.getRange()+"-", info.getDownloadUrl())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, Object>() {
                    @Override
                    public Object call(ResponseBody responseBody) {
                        try {
                            writeCache(responseBody, new File(info.getCachePath()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 写入文件
     *
     * @param file
     * @throws IOException
     */
    public void writeCache(ResponseBody responseBody, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        long allLength = responseBody.contentLength();
        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        randomAccessFile = new RandomAccessFile(file, "rwd");
        channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                0, allLength);
        byte[] buffer = new byte[1024 * 4];
        int len;
        int record = 0;
        while ((len = responseBody.byteStream().read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            record += len;
        }
        responseBody.byteStream().close();
        if (channelOut != null) {
            channelOut.close();
        }
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }


}
