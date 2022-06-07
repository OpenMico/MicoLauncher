package retrofit2;

import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Timeout;

/* compiled from: OkHttpCall.java */
/* loaded from: classes6.dex */
public final class e<T> implements Call<T> {
    private final j a;
    private final Object[] b;
    private final Call.Factory c;
    private final Converter<ResponseBody, T> d;
    private volatile boolean e;
    @GuardedBy("this")
    @Nullable
    private Call f;
    @GuardedBy("this")
    @Nullable
    private Throwable g;
    @GuardedBy("this")
    private boolean h;

    public e(j jVar, Object[] objArr, Call.Factory factory, Converter<ResponseBody, T> converter) {
        this.a = jVar;
        this.b = objArr;
        this.c = factory;
        this.d = converter;
    }

    /* renamed from: a */
    public e<T> clone() {
        return new e<>(this.a, this.b, this.c, this.d);
    }

    @Override // retrofit2.Call
    public synchronized Request request() {
        try {
        } catch (IOException e) {
            throw new RuntimeException("Unable to create request.", e);
        }
        return b().request();
    }

    @Override // retrofit2.Call
    public synchronized Timeout timeout() {
        try {
        } catch (IOException e) {
            throw new RuntimeException("Unable to create call.", e);
        }
        return b().timeout();
    }

    @GuardedBy("this")
    private Call b() throws IOException {
        Call call = this.f;
        if (call != null) {
            return call;
        }
        Throwable th = this.g;
        if (th == null) {
            try {
                Call c = c();
                this.f = c;
                return c;
            } catch (IOException | Error | RuntimeException e) {
                m.a(e);
                this.g = e;
                throw e;
            }
        } else if (th instanceof IOException) {
            throw ((IOException) th);
        } else if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else {
            throw ((Error) th);
        }
    }

    @Override // retrofit2.Call
    public void enqueue(final Callback<T> callback) {
        Call call;
        Throwable th;
        Objects.requireNonNull(callback, "callback == null");
        synchronized (this) {
            if (!this.h) {
                this.h = true;
                call = this.f;
                th = this.g;
                if (call == null && th == null) {
                    Call c = c();
                    this.f = c;
                    call = c;
                }
            } else {
                throw new IllegalStateException("Already executed.");
            }
        }
        if (th != null) {
            callback.onFailure(this, th);
            return;
        }
        if (this.e) {
            call.cancel();
        }
        call.enqueue(new Callback() { // from class: retrofit2.e.1
            @Override // okhttp3.Callback
            public void onResponse(Call call2, Response response) {
                try {
                    try {
                        callback.onResponse(e.this, e.this.a(response));
                    } catch (Throwable th2) {
                        m.a(th2);
                        th2.printStackTrace();
                    }
                } catch (Throwable th3) {
                    m.a(th3);
                    a(th3);
                }
            }

            @Override // okhttp3.Callback
            public void onFailure(Call call2, IOException iOException) {
                a(iOException);
            }

            private void a(Throwable th2) {
                try {
                    callback.onFailure(e.this, th2);
                } catch (Throwable th3) {
                    m.a(th3);
                    th3.printStackTrace();
                }
            }
        });
    }

    @Override // retrofit2.Call
    public synchronized boolean isExecuted() {
        return this.h;
    }

    @Override // retrofit2.Call
    public Response<T> execute() throws IOException {
        Call b2;
        synchronized (this) {
            if (!this.h) {
                this.h = true;
                b2 = b();
            } else {
                throw new IllegalStateException("Already executed.");
            }
        }
        if (this.e) {
            b2.cancel();
        }
        return a(b2.execute());
    }

    private Call c() throws IOException {
        Call newCall = this.c.newCall(this.a.a(this.b));
        if (newCall != null) {
            return newCall;
        }
        throw new NullPointerException("Call.Factory returned null.");
    }

    /* JADX WARN: Finally extract failed */
    Response<T> a(Response response) throws IOException {
        ResponseBody body = response.body();
        Response build = response.newBuilder().body(new b(body.contentType(), body.contentLength())).build();
        int code = build.code();
        if (code < 200 || code >= 300) {
            try {
                Response<T> error = Response.error(m.a(body), build);
                body.close();
                return error;
            } catch (Throwable th) {
                body.close();
                throw th;
            }
        } else if (code == 204 || code == 205) {
            body.close();
            return Response.success((Object) null, build);
        } else {
            a aVar = new a(body);
            try {
                return Response.success(this.d.convert(aVar), build);
            } catch (RuntimeException e) {
                aVar.a();
                throw e;
            }
        }
    }

    @Override // retrofit2.Call
    public void cancel() {
        Call call;
        this.e = true;
        synchronized (this) {
            call = this.f;
        }
        if (call != null) {
            call.cancel();
        }
    }

    @Override // retrofit2.Call
    public boolean isCanceled() {
        boolean z = true;
        if (this.e) {
            return true;
        }
        synchronized (this) {
            if (this.f == null || !this.f.isCanceled()) {
                z = false;
            }
        }
        return z;
    }

    /* compiled from: OkHttpCall.java */
    /* loaded from: classes6.dex */
    public static final class b extends ResponseBody {
        @Nullable
        private final MediaType a;
        private final long b;

        public b(@Nullable MediaType mediaType, long j) {
            this.a = mediaType;
            this.b = j;
        }

        @Override // okhttp3.ResponseBody
        public MediaType contentType() {
            return this.a;
        }

        @Override // okhttp3.ResponseBody
        public long contentLength() {
            return this.b;
        }

        @Override // okhttp3.ResponseBody
        public BufferedSource source() {
            throw new IllegalStateException("Cannot read raw response body of a converted body.");
        }
    }

    /* compiled from: OkHttpCall.java */
    /* loaded from: classes6.dex */
    public static final class a extends ResponseBody {
        @Nullable
        IOException a;
        private final ResponseBody b;
        private final BufferedSource c;

        a(ResponseBody responseBody) {
            this.b = responseBody;
            this.c = Okio.buffer(new ForwardingSource(responseBody.source()) { // from class: retrofit2.e.a.1
                @Override // okio.ForwardingSource, okio.Source
                public long read(Buffer buffer, long j) throws IOException {
                    try {
                        return super.read(buffer, j);
                    } catch (IOException e) {
                        a.this.a = e;
                        throw e;
                    }
                }
            });
        }

        @Override // okhttp3.ResponseBody
        public MediaType contentType() {
            return this.b.contentType();
        }

        @Override // okhttp3.ResponseBody
        public long contentLength() {
            return this.b.contentLength();
        }

        @Override // okhttp3.ResponseBody
        public BufferedSource source() {
            return this.c;
        }

        @Override // okhttp3.ResponseBody, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.b.close();
        }

        void a() throws IOException {
            IOException iOException = this.a;
            if (iOException != null) {
                throw iOException;
            }
        }
    }
}
