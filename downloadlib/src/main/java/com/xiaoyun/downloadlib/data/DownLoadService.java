package com.xiaoyun.downloadlib.data;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by zl on 2017/2/8.
 */

public interface DownLoadService {

    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String range, @Url String url);

}
