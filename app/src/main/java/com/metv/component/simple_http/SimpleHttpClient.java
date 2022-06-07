package com.metv.component.simple_http;

/* loaded from: classes2.dex */
public class SimpleHttpClient {
    public static final String TAG = "simple_http";
    private String a;
    private SimpleHttpExecutor b;

    public SimpleHttpClient(String str) {
        this(str, 1);
    }

    public SimpleHttpClient(String str, int i) {
        if (SimpleHttpUtils.checkHost(str)) {
            this.a = str;
            this.b = new SimpleHttpExecutor(i);
            return;
        }
        throw new IllegalArgumentException("Host error: " + str);
    }

    public void request(SimpleHttpRequest simpleHttpRequest, SimpleHttpCallback simpleHttpCallback) {
        SimpleHttpExecutor simpleHttpExecutor = this.b;
        if (simpleHttpExecutor == null || simpleHttpExecutor.isReleased()) {
            throw new RuntimeException("Error: executor is null or released");
        }
        this.b.execute(new SimpleHttpTask(this.a, simpleHttpRequest, simpleHttpCallback));
    }

    public void close() {
        SimpleHttpExecutor simpleHttpExecutor = this.b;
        if (simpleHttpExecutor != null) {
            simpleHttpExecutor.release();
        }
        this.b = null;
    }
}
