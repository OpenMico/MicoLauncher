package com.google.android.exoplayer2.upstream;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/* loaded from: classes2.dex */
public final class Loader implements LoaderErrorThrower {
    private final ExecutorService a;
    @Nullable
    private a<? extends Loadable> b;
    @Nullable
    private IOException c;
    public static final LoadErrorAction RETRY = createRetryAction(false, C.TIME_UNSET);
    public static final LoadErrorAction RETRY_RESET_ERROR_COUNT = createRetryAction(true, C.TIME_UNSET);
    public static final LoadErrorAction DONT_RETRY = new LoadErrorAction(2, C.TIME_UNSET);
    public static final LoadErrorAction DONT_RETRY_FATAL = new LoadErrorAction(3, C.TIME_UNSET);

    /* loaded from: classes2.dex */
    public interface Callback<T extends Loadable> {
        void onLoadCanceled(T t, long j, long j2, boolean z);

        void onLoadCompleted(T t, long j, long j2);

        LoadErrorAction onLoadError(T t, long j, long j2, IOException iOException, int i);
    }

    /* loaded from: classes2.dex */
    public interface Loadable {
        void cancelLoad();

        void load() throws IOException;
    }

    /* loaded from: classes2.dex */
    public interface ReleaseCallback {
        void onLoaderReleased();
    }

    /* loaded from: classes2.dex */
    public static final class UnexpectedLoaderException extends IOException {
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public UnexpectedLoaderException(java.lang.Throwable r5) {
            /*
                r4 = this;
                java.lang.Class r0 = r5.getClass()
                java.lang.String r0 = r0.getSimpleName()
                java.lang.String r1 = r5.getMessage()
                java.lang.String r2 = java.lang.String.valueOf(r0)
                int r2 = r2.length()
                int r2 = r2 + 13
                java.lang.String r3 = java.lang.String.valueOf(r1)
                int r3 = r3.length()
                int r2 = r2 + r3
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>(r2)
                java.lang.String r2 = "Unexpected "
                r3.append(r2)
                r3.append(r0)
                java.lang.String r0 = ": "
                r3.append(r0)
                r3.append(r1)
                java.lang.String r0 = r3.toString()
                r4.<init>(r0, r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.Loader.UnexpectedLoaderException.<init>(java.lang.Throwable):void");
        }
    }

    /* loaded from: classes2.dex */
    public static final class LoadErrorAction {
        private final int a;
        private final long b;

        private LoadErrorAction(int i, long j) {
            this.a = i;
            this.b = j;
        }

        public boolean isRetry() {
            int i = this.a;
            return i == 0 || i == 1;
        }
    }

    public Loader(String str) {
        String valueOf = String.valueOf("ExoPlayer:Loader:");
        String valueOf2 = String.valueOf(str);
        this.a = Util.newSingleThreadExecutor(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    public static LoadErrorAction createRetryAction(boolean z, long j) {
        return new LoadErrorAction(z ? 1 : 0, j);
    }

    public boolean hasFatalError() {
        return this.c != null;
    }

    public void clearFatalError() {
        this.c = null;
    }

    public <T extends Loadable> long startLoading(T t, Callback<T> callback, int i) {
        this.c = null;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        new a((Looper) Assertions.checkStateNotNull(Looper.myLooper()), t, callback, i, elapsedRealtime).a(0L);
        return elapsedRealtime;
    }

    public boolean isLoading() {
        return this.b != null;
    }

    public void cancelLoading() {
        ((a) Assertions.checkStateNotNull(this.b)).a(false);
    }

    public void release() {
        release(null);
    }

    public void release(@Nullable ReleaseCallback releaseCallback) {
        a<? extends Loadable> aVar = this.b;
        if (aVar != null) {
            aVar.a(true);
        }
        if (releaseCallback != null) {
            this.a.execute(new b(releaseCallback));
        }
        this.a.shutdown();
    }

    @Override // com.google.android.exoplayer2.upstream.LoaderErrorThrower
    public void maybeThrowError() throws IOException {
        maybeThrowError(Integer.MIN_VALUE);
    }

    @Override // com.google.android.exoplayer2.upstream.LoaderErrorThrower
    public void maybeThrowError(int i) throws IOException {
        IOException iOException = this.c;
        if (iOException == null) {
            a<? extends Loadable> aVar = this.b;
            if (aVar != null) {
                if (i == Integer.MIN_VALUE) {
                    i = aVar.a;
                }
                aVar.a(i);
                return;
            }
            return;
        }
        throw iOException;
    }

    @SuppressLint({"HandlerLeak"})
    /* loaded from: classes2.dex */
    public final class a<T extends Loadable> extends Handler implements Runnable {
        public final int a;
        private final T c;
        private final long d;
        @Nullable
        private Callback<T> e;
        @Nullable
        private IOException f;
        private int g;
        @Nullable
        private Thread h;
        private boolean i;
        private volatile boolean j;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(Looper looper, T t, Callback<T> callback, int i, long j) {
            super(looper);
            Loader.this = r1;
            this.c = t;
            this.e = callback;
            this.a = i;
            this.d = j;
        }

        public void a(int i) throws IOException {
            IOException iOException = this.f;
            if (iOException != null && this.g > i) {
                throw iOException;
            }
        }

        public void a(long j) {
            Assertions.checkState(Loader.this.b == null);
            Loader.this.b = this;
            if (j > 0) {
                sendEmptyMessageDelayed(0, j);
            } else {
                a();
            }
        }

        public void a(boolean z) {
            this.j = z;
            this.f = null;
            if (hasMessages(0)) {
                this.i = true;
                removeMessages(0);
                if (!z) {
                    sendEmptyMessage(1);
                }
            } else {
                synchronized (this) {
                    this.i = true;
                    this.c.cancelLoad();
                    Thread thread = this.h;
                    if (thread != null) {
                        thread.interrupt();
                    }
                }
            }
            if (z) {
                b();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                ((Callback) Assertions.checkNotNull(this.e)).onLoadCanceled(this.c, elapsedRealtime, elapsedRealtime - this.d, true);
                this.e = null;
            }
        }

        /* JADX WARN: Finally extract failed */
        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            try {
                synchronized (this) {
                    z = !this.i;
                    this.h = Thread.currentThread();
                }
                if (z) {
                    String valueOf = String.valueOf(this.c.getClass().getSimpleName());
                    TraceUtil.beginSection(valueOf.length() != 0 ? "load:".concat(valueOf) : new String("load:"));
                    try {
                        this.c.load();
                        TraceUtil.endSection();
                    } catch (Throwable th) {
                        TraceUtil.endSection();
                        throw th;
                    }
                }
                synchronized (this) {
                    this.h = null;
                    Thread.interrupted();
                }
                if (!this.j) {
                    sendEmptyMessage(1);
                }
            } catch (IOException e) {
                if (!this.j) {
                    obtainMessage(2, e).sendToTarget();
                }
            } catch (OutOfMemoryError e2) {
                if (!this.j) {
                    Log.e("LoadTask", "OutOfMemory error loading stream", e2);
                    obtainMessage(2, new UnexpectedLoaderException(e2)).sendToTarget();
                }
            } catch (Error e3) {
                if (!this.j) {
                    Log.e("LoadTask", "Unexpected error loading stream", e3);
                    obtainMessage(3, e3).sendToTarget();
                }
                throw e3;
            } catch (Exception e4) {
                if (!this.j) {
                    Log.e("LoadTask", "Unexpected exception loading stream", e4);
                    obtainMessage(2, new UnexpectedLoaderException(e4)).sendToTarget();
                }
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            long j;
            if (!this.j) {
                if (message.what == 0) {
                    a();
                } else if (message.what != 3) {
                    b();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    long j2 = elapsedRealtime - this.d;
                    Callback callback = (Callback) Assertions.checkNotNull(this.e);
                    if (this.i) {
                        callback.onLoadCanceled(this.c, elapsedRealtime, j2, false);
                        return;
                    }
                    switch (message.what) {
                        case 1:
                            try {
                                callback.onLoadCompleted(this.c, elapsedRealtime, j2);
                                return;
                            } catch (RuntimeException e) {
                                Log.e("LoadTask", "Unexpected exception handling load completed", e);
                                Loader.this.c = new UnexpectedLoaderException(e);
                                return;
                            }
                        case 2:
                            this.f = (IOException) message.obj;
                            this.g++;
                            LoadErrorAction onLoadError = callback.onLoadError(this.c, elapsedRealtime, j2, this.f, this.g);
                            if (onLoadError.a == 3) {
                                Loader.this.c = this.f;
                                return;
                            } else if (onLoadError.a != 2) {
                                if (onLoadError.a == 1) {
                                    this.g = 1;
                                }
                                if (onLoadError.b != C.TIME_UNSET) {
                                    j = onLoadError.b;
                                } else {
                                    j = c();
                                }
                                a(j);
                                return;
                            } else {
                                return;
                            }
                        default:
                            return;
                    }
                } else {
                    throw ((Error) message.obj);
                }
            }
        }

        private void a() {
            this.f = null;
            Loader.this.a.execute((Runnable) Assertions.checkNotNull(Loader.this.b));
        }

        private void b() {
            Loader.this.b = null;
        }

        private long c() {
            return Math.min((this.g - 1) * 1000, 5000);
        }
    }

    /* loaded from: classes2.dex */
    public static final class b implements Runnable {
        private final ReleaseCallback a;

        public b(ReleaseCallback releaseCallback) {
            this.a = releaseCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.onLoaderReleased();
        }
    }
}
