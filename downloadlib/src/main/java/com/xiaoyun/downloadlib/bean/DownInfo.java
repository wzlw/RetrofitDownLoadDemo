package com.xiaoyun.downloadlib.bean;

import com.xiaoyun.downloadlib.listener.DownLoadProgressListener;

/**
 * Created by zl on 2017/2/8.
 */

public class DownInfo {

    private String baseUrl;

    private String downloadUrl;

    private DownLoadProgressListener progressListener;

    private long range;

    private String cachePath;

    public DownInfo(String baseUrl, String downloadUrl, DownLoadProgressListener progressListener, long range, String cachePath) {
        this.baseUrl = baseUrl;
        this.downloadUrl = downloadUrl;
        this.progressListener = progressListener;
        this.range = range;
        this.cachePath = cachePath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public long getRange() {
        return range;
    }

    public String getCachePath() {
        return cachePath;
    }

    public DownLoadProgressListener getProgressListener() {
        return progressListener;
    }

    public static class Builder {

        private String baseUrl;

        private String downloadUrl;

        private DownLoadProgressListener progressListener;

        private long range;

        private String cachePath;

        public Builder setBaseUrl(String url) {
            baseUrl = url;
            return this;
        }

        public Builder setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
            return this;
        }

        public Builder setProgressListener(DownLoadProgressListener progressListener) {
            this.progressListener = progressListener;
            return this;
        }

        public Builder setRange(long range) {
            this.range = range;
            return this;
        }

        public Builder setCachePath(String cachePath) {
            this.cachePath = cachePath;
            return this;
        }

        public DownInfo build() {
            return new DownInfo(baseUrl, downloadUrl, progressListener, range, cachePath);
        }
    }

}
