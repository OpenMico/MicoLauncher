package com.bumptech.glide.integration.okhttp3;

import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.bumptech.glide.util.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* loaded from: classes.dex */
public class OkHttpStreamFetcher implements DataFetcher<InputStream>, Callback {
    private final Call.Factory a;
    private final GlideUrl b;
    private InputStream c;
    private ResponseBody d;
    private DataFetcher.DataCallback<? super InputStream> e;
    private volatile Call f;

    public OkHttpStreamFetcher(Call.Factory factory, GlideUrl glideUrl) {
        this.a = factory;
        this.b = glideUrl;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void loadData(@NonNull Priority priority, @NonNull DataFetcher.DataCallback<? super InputStream> dataCallback) {
        Request.Builder url = new Request.Builder().url(this.b.toStringUrl());
        for (Map.Entry<String, String> entry : this.b.getHeaders().entrySet()) {
            url.addHeader(entry.getKey(), entry.getValue());
        }
        Request build = url.build();
        this.e = dataCallback;
        this.f = this.a.newCall(build);
        this.f.enqueue(this);
    }

    @Override // okhttp3.Callback
    public void onFailure(@NonNull Call call, @NonNull IOException iOException) {
        if (Log.isLoggable("OkHttpFetcher", 3)) {
            Log.d("OkHttpFetcher", "OkHttp failed to obtain result", iOException);
        }
        this.e.onLoadFailed(iOException);
    }

    @Override // okhttp3.Callback
    public void onResponse(@NonNull Call call, @NonNull Response response) {
        this.d = response.body();
        if (response.isSuccessful()) {
            this.c = ContentLengthInputStream.obtain(this.d.byteStream(), ((ResponseBody) Preconditions.checkNotNull(this.d)).contentLength());
            this.e.onDataReady(this.c);
            return;
        }
        this.e.onLoadFailed(new HttpException(response.message(), response.code()));
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cleanup() {
        try {
            if (this.c != null) {
                this.c.close();
            }
        } catch (IOException unused) {
        }
        ResponseBody responseBody = this.d;
        if (responseBody != null) {
            responseBody.close();
        }
        this.e = null;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cancel() {
        Call call = this.f;
        if (call != null) {
            call.cancel();
        }
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    @NonNull
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    @NonNull
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
