package com.zhy.http.okhttp.request;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes4.dex */
public class RequestCall {
    private OkHttpRequest a;
    private Request b;
    private Call c;
    private long d;
    private long e;
    private long f;
    private OkHttpClient g;

    public RequestCall(OkHttpRequest okHttpRequest) {
        this.a = okHttpRequest;
    }

    public RequestCall readTimeOut(long j) {
        this.d = j;
        return this;
    }

    public RequestCall writeTimeOut(long j) {
        this.e = j;
        return this;
    }

    public RequestCall connTimeOut(long j) {
        this.f = j;
        return this;
    }

    public Call buildCall(Callback callback) {
        this.b = a(callback);
        if (this.d > 0 || this.e > 0 || this.f > 0) {
            long j = this.d;
            if (j <= 0) {
                j = 10000;
            }
            this.d = j;
            long j2 = this.e;
            if (j2 <= 0) {
                j2 = 10000;
            }
            this.e = j2;
            long j3 = this.f;
            if (j3 <= 0) {
                j3 = 10000;
            }
            this.f = j3;
            this.g = OkHttpUtils.getInstance().getOkHttpClient().newBuilder().readTimeout(this.d, TimeUnit.MILLISECONDS).writeTimeout(this.e, TimeUnit.MILLISECONDS).connectTimeout(this.f, TimeUnit.MILLISECONDS).build();
            this.c = this.g.newCall(this.b);
        } else {
            this.c = OkHttpUtils.getInstance().getOkHttpClient().newCall(this.b);
        }
        return this.c;
    }

    private Request a(Callback callback) {
        return this.a.generateRequest(callback);
    }

    public void execute(Callback callback) {
        buildCall(callback);
        if (callback != null) {
            callback.onBefore(this.b, getOkHttpRequest().getId());
        }
        OkHttpUtils.getInstance().execute(this, callback);
    }

    public Call getCall() {
        return this.c;
    }

    public Request getRequest() {
        return this.b;
    }

    public OkHttpRequest getOkHttpRequest() {
        return this.a;
    }

    public Response execute() throws IOException {
        buildCall(null);
        return this.c.execute();
    }

    public void cancel() {
        Call call = this.c;
        if (call != null) {
            call.cancel();
        }
    }
}
