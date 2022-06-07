package com.xiaomi.micolauncher.module.appstore.bean;

import com.xiaomi.mico.appstore.bean.AppInfo;

/* loaded from: classes3.dex */
public class DownloadRequest {
    private AppInfo a;
    private long b;
    private int c;
    private int d;
    private long e;
    private long f;
    private String g;
    private int h;
    private long i;
    private long j;
    private IDownloadResult m = null;
    private int k = -2;
    private float l = 0.0f;

    /* loaded from: classes3.dex */
    public interface IDownloadResult {
        void OnDownloadResult(DownloadRequest downloadRequest);
    }

    public DownloadRequest(AppInfo appInfo) {
        this.a = appInfo;
    }

    public AppInfo getAppInfo() {
        return this.a;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.a = appInfo;
    }

    public long getDownloadId() {
        return this.b;
    }

    public void setDownloadId(long j) {
        this.b = j;
    }

    public int getDownloadType() {
        return this.c;
    }

    public void setDownloadType(int i) {
        this.c = i;
    }

    public int getDownloadStatus() {
        return this.d;
    }

    public void setDownloadStatus(int i) {
        this.d = i;
    }

    public long getTotalSize() {
        return this.e;
    }

    public void setTotalSize(long j) {
        this.e = j;
    }

    public long getDownloadedSize() {
        return this.f;
    }

    public void setDownloadedSize(long j) {
        this.f = j;
    }

    public String getLocalUri() {
        return this.g;
    }

    public void setLocalUri(String str) {
        this.g = str;
    }

    public int getDownloadReason() {
        return this.h;
    }

    public void setDownloadReason(int i) {
        this.h = i;
    }

    public long getCreateTime() {
        return this.i;
    }

    public void setCreateTime(long j) {
        this.i = j;
    }

    public long getCompletedTime() {
        return this.j;
    }

    public void setCompletedTime(long j) {
        this.j = j;
    }

    public void setDownloadCallback(IDownloadResult iDownloadResult) {
        this.m = iDownloadResult;
    }

    public IDownloadResult getDownloadCallback() {
        return this.m;
    }

    public void setInstallStatus(int i) {
        this.k = i;
    }

    public int getInstallStatus() {
        return this.k;
    }

    public float getInstallProgress() {
        return this.l;
    }

    public void setInstallProgress(float f) {
        this.l = f;
    }
}
