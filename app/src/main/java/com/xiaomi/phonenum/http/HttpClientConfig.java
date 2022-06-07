package com.xiaomi.phonenum.http;

/* loaded from: classes4.dex */
public class HttpClientConfig {
    private static volatile long a = 10000;
    private static volatile long b = 5000;
    public final long connectTimeoutMs;
    public final int netWorkSubId;
    public final long readTimeoutMs;
    public final long waitCellularTimeoutMs;
    public final long writeTimeoutMs;

    private HttpClientConfig(Builder builder) {
        this.netWorkSubId = builder.a;
        this.connectTimeoutMs = a;
        this.waitCellularTimeoutMs = b;
        this.readTimeoutMs = 15000L;
        this.writeTimeoutMs = 15000L;
    }

    public static void setDefaultConnectTimeoutMs(long j) {
        a = j;
    }

    public static void setDefaultWaitCellularTimeoutMs(long j) {
        b = j;
    }

    /* loaded from: classes4.dex */
    public static class Builder {
        int a = -1;

        public Builder useDataNetWork(int i) {
            this.a = i;
            return this;
        }

        public HttpClientConfig build() {
            return new HttpClientConfig(this);
        }
    }
}
