package com.zhy.http.okhttp.request;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/* loaded from: classes4.dex */
public class CountingRequestBody extends RequestBody {
    protected CountingSink countingSink;
    protected RequestBody delegate;
    protected Listener listener;

    /* loaded from: classes4.dex */
    public interface Listener {
        void onRequestProgress(long j, long j2);
    }

    public CountingRequestBody(RequestBody requestBody, Listener listener) {
        this.delegate = requestBody;
        this.listener = listener;
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        return this.delegate.contentType();
    }

    @Override // okhttp3.RequestBody
    public long contentLength() {
        try {
            return this.delegate.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        this.countingSink = new CountingSink(bufferedSink);
        BufferedSink buffer = Okio.buffer(this.countingSink);
        this.delegate.writeTo(buffer);
        buffer.flush();
    }

    /* loaded from: classes4.dex */
    protected final class CountingSink extends ForwardingSink {
        private long b = 0;

        public CountingSink(Sink sink) {
            super(sink);
        }

        @Override // okio.ForwardingSink, okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            super.write(buffer, j);
            this.b += j;
            CountingRequestBody.this.listener.onRequestProgress(this.b, CountingRequestBody.this.contentLength());
        }
    }
}
