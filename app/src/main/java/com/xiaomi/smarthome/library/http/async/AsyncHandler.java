package com.xiaomi.smarthome.library.http.async;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.xiaomi.smarthome.library.http.Error;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Response;

/* loaded from: classes4.dex */
public abstract class AsyncHandler<R, E extends Error> {
    private static final int MSG_FAILURE = 2;
    private static final int MSG_PROGRESS = 1;
    private static final int MSG_SUCCESS = 0;
    private Handler mDispatchHandler;

    public abstract void onFailure(Error error, Exception exc, Response response);

    public void onProgress(long j, long j2) {
    }

    public abstract void onSuccess(R r, Response response);

    public abstract void processFailure(Call call, IOException iOException);

    public abstract void processResponse(Response response);

    public AsyncHandler() {
        Looper myLooper = Looper.myLooper();
        if (myLooper == null) {
            Log.e("AsyncHandler", "Async Callback must have Looper");
            myLooper = Looper.getMainLooper();
        }
        this.mDispatchHandler = new a(this, myLooper);
    }

    public final void sendSuccessMessage(R r, Response response) {
        d dVar = new d();
        dVar.a = r;
        dVar.b = response;
        Handler handler = this.mDispatchHandler;
        handler.sendMessage(handler.obtainMessage(0, dVar));
    }

    public final void sendProgressMessage(long j, long j2) {
        c cVar = new c();
        cVar.a = j;
        cVar.b = j2;
        Handler handler = this.mDispatchHandler;
        handler.sendMessage(handler.obtainMessage(1, cVar));
    }

    public final void sendFailureMessage(E e, Exception exc, Response response) {
        b bVar = new b();
        bVar.a = e;
        bVar.b = exc;
        bVar.c = response;
        Handler handler = this.mDispatchHandler;
        handler.sendMessage(handler.obtainMessage(2, bVar));
    }

    /* loaded from: classes4.dex */
    private static class d<R> {
        R a;
        Response b;

        private d() {
        }
    }

    /* loaded from: classes4.dex */
    private static class c {
        long a;
        long b;

        private c() {
        }
    }

    /* loaded from: classes4.dex */
    private static class b<E extends Error> {
        E a;
        Exception b;
        Response c;

        private b() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class a<R, E extends Error> extends Handler {
        private AsyncHandler<R, E> a;

        a(AsyncHandler asyncHandler, Looper looper) {
            super(looper);
            this.a = asyncHandler;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    d dVar = (d) message.obj;
                    this.a.onSuccess(dVar.a, dVar.b);
                    return;
                case 1:
                    c cVar = (c) message.obj;
                    this.a.onProgress(cVar.a, cVar.b);
                    return;
                case 2:
                    b bVar = (b) message.obj;
                    this.a.onFailure(bVar.a, bVar.b, bVar.c);
                    return;
                default:
                    return;
            }
        }
    }
}
