package com.xiaomi.infra.galaxy.fds.android;

import com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential;
import com.xiaomi.infra.galaxy.fds.android.util.Args;

/* loaded from: classes3.dex */
public class FDSClientConfiguration {
    public static final int DEFAULT_CONNECTION_TIMEOUT_MS = 50000;
    public static final int DEFAULT_MAX_RETRY_TIMES = 3;
    public static final int DEFAULT_SOCKET_TIMEOUT_MS = 50000;
    public static final int DEFAULT_THREAD_POOL_CORE_SIZE = 4;
    public static final int DEFAULT_THREAD_POOL_KEEP_ALIVE_SECS = 30;
    public static final int DEFAULT_THREAD_POOL_MAX_SIZE = 10;
    public static final int DEFAULT_UPLOAD_PART_SIZE = 4096;
    public static final int DEFAULT_WORK_QUEUE_CAPACITY = 10240;
    private GalaxyFDSCredential k;
    private String r;
    private int a = 50000;
    private int b = 50000;
    private int c = 0;
    private int d = 0;
    private int e = 3;
    private int f = 4096;
    private int g = 4;
    private int h = 10;
    private int i = 30;
    private int j = 10240;
    private String l = "cnbj0";
    private boolean m = true;
    private boolean n = false;
    private boolean o = true;
    private boolean p = false;
    private String q = "";

    @Deprecated
    public void setCdnServiceBaseUri(String str) {
    }

    @Deprecated
    public void setFdsServiceBaseUri(String str) {
    }

    @Deprecated
    public FDSClientConfiguration withCdnServiceBaseUri(String str) {
        return this;
    }

    @Deprecated
    public FDSClientConfiguration withFdsServiceBaseUri(String str) {
        return this;
    }

    public int getMaxRetryTimes() {
        return this.e;
    }

    public void setMaxRetryTimes(int i) {
        Args.notNegative(i, "max retry times");
        this.e = i;
    }

    public FDSClientConfiguration withMaxRetryTimes(int i) {
        setMaxRetryTimes(i);
        return this;
    }

    public int getSocketTimeoutMs() {
        return this.a;
    }

    public void setSocketTimeoutMs(int i) {
        this.a = i;
    }

    public FDSClientConfiguration withSocketTimeoutMs(int i) {
        setSocketTimeoutMs(i);
        return this;
    }

    public int getConnectionTimeoutMs() {
        return this.b;
    }

    public void setConnectionTimeoutMs(int i) {
        this.b = i;
    }

    public FDSClientConfiguration withConnectionTimeoutMs(int i) {
        setConnectionTimeoutMs(i);
        return this;
    }

    public int[] getSocketBufferSizeHints() {
        return new int[]{this.c, this.d};
    }

    public void setSocketBufferSizeHints(int i, int i2) {
        this.c = i;
        this.d = i2;
    }

    public FDSClientConfiguration withSocketBufferSizeHints(int i, int i2) {
        setSocketBufferSizeHints(i, i2);
        return this;
    }

    public int getUploadPartSize() {
        return this.f;
    }

    public void setUploadPartSize(int i) {
        Args.positive(i, "upload part size");
        this.f = i;
    }

    public FDSClientConfiguration withUploadPartSize(int i) {
        setUploadPartSize(i);
        return this;
    }

    public int getThreadPoolCoreSize() {
        return this.g;
    }

    public void setThreadPoolCoreSize(int i) {
        this.g = i;
    }

    public FDSClientConfiguration withThreadPoolCoreSize(int i) {
        setThreadPoolCoreSize(i);
        return this;
    }

    public int getThreadPoolMaxSize() {
        return this.h;
    }

    public void setThreadPoolMaxSize(int i) {
        this.h = i;
    }

    public FDSClientConfiguration withThreadPoolMaxSize(int i) {
        setThreadPoolMaxSize(i);
        return this;
    }

    public int getThreadPoolKeepAliveSecs() {
        return this.i;
    }

    public void setThreadPoolKeepAliveSecs(int i) {
        this.i = i;
    }

    public FDSClientConfiguration withThreadPoolKeepAliveSecs(int i) {
        setThreadPoolKeepAliveSecs(i);
        return this;
    }

    public int getWorkQueueCapacity() {
        return this.j;
    }

    public void setWorkQueueCapacity(int i) {
        this.j = i;
    }

    public FDSClientConfiguration withWorkQueueCapacity(int i) {
        setWorkQueueCapacity(i);
        return this;
    }

    public GalaxyFDSCredential getCredential() {
        return this.k;
    }

    public void setCredential(GalaxyFDSCredential galaxyFDSCredential) {
        Args.notNull(galaxyFDSCredential, "credential");
        this.k = galaxyFDSCredential;
    }

    public FDSClientConfiguration withCredential(GalaxyFDSCredential galaxyFDSCredential) {
        setCredential(galaxyFDSCredential);
        return this;
    }

    public String getEndpoint() {
        return this.r;
    }

    public void setEndpoint(String str) {
        this.r = str;
    }

    @Deprecated
    public String getFdsServiceBaseUri() {
        return a();
    }

    @Deprecated
    public String getCdnServiceBaseUri() {
        return b();
    }

    public String getRegionName() {
        return this.l;
    }

    public void setRegionName(String str) {
        this.l = str;
    }

    public FDSClientConfiguration withRegionName(String str) {
        setRegionName(str);
        return this;
    }

    public boolean isHttpsEnabled() {
        return this.m;
    }

    public void enableHttps(boolean z) {
        this.m = z;
    }

    public FDSClientConfiguration withHttps(boolean z) {
        enableHttps(z);
        return this;
    }

    public boolean isCdnEnabledForUpload() {
        return this.n;
    }

    public void enableCdnForUpload(boolean z) {
        this.n = z;
    }

    public FDSClientConfiguration withCdnForUpload(boolean z) {
        enableCdnForUpload(z);
        return this;
    }

    public boolean isCdnEnabledForDownload() {
        return this.o;
    }

    public void enableCdnForDownload(boolean z) {
        this.o = z;
    }

    public FDSClientConfiguration withCdnForDownload(boolean z) {
        enableCdnForDownload(z);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String a() {
        return a(false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String b() {
        return a(true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String c() {
        return a(this.o);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String d() {
        return a(this.n);
    }

    String a(boolean z) {
        if (this.p) {
            return this.q;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.m ? "https://" : "http://");
        String str = this.r;
        if (str != null && !str.isEmpty()) {
            sb.append(this.r);
        } else if (z) {
            sb.append("cdn." + this.l + ".fds.api.mi-img.com");
        } else {
            sb.append(this.l + ".fds.api.xiaomi.com");
        }
        return sb.toString();
    }
}
