package com.retrofit_rx.listener.upload;

/**
 * 上传进度回调类
 * Created by WZG on 2016/10/20.
 */

public interface UploadProgressListener {
    /**
     * 上传进度
     * 手动回调到主线程中
     * @param currentBytesCount
     * @param totalBytesCount
     */
    void onProgress(long currentBytesCount, long totalBytesCount);
}