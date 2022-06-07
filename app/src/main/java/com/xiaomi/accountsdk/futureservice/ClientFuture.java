package com.xiaomi.accountsdk.futureservice;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
public abstract class ClientFuture<ServerDataType, ClientDataType> extends FutureTask<ClientDataType> {
    private static final String TAG = "ClientFuture";
    private final ClientCallback<ClientDataType> mClientCallback;

    /* loaded from: classes2.dex */
    public interface ClientCallback<ClientSideDataType> {
        void call(ClientFuture<?, ClientSideDataType> clientFuture);
    }

    protected abstract ClientDataType convertServerDataToClientData(ServerDataType serverdatatype) throws Throwable;

    public abstract void interpretExecutionException(ExecutionException executionException) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public ClientFuture(ClientCallback<ClientDataType> clientCallback) {
        super(new Callable<ClientDataType>() { // from class: com.xiaomi.accountsdk.futureservice.ClientFuture.1
            @Override // java.util.concurrent.Callable
            public ClientDataType call() throws Exception {
                throw new IllegalStateException("this should never be called");
            }
        });
        this.mClientCallback = clientCallback;
    }

    @Override // java.util.concurrent.FutureTask
    protected void done() {
        super.done();
        onDone();
    }

    public final void setServerData(ServerDataType serverdatatype) {
        try {
            set(convertServerDataToClientData(serverdatatype));
        } catch (Throwable th) {
            setException(th);
        }
    }

    public final void setServerSideThrowable(Throwable th) {
        setException(th);
    }

    private void onDone() {
        if (!isCancelled() && this.mClientCallback != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.xiaomi.accountsdk.futureservice.ClientFuture.2
                @Override // java.lang.Runnable
                public void run() {
                    ClientFuture.this.mClientCallback.call(ClientFuture.this);
                }
            });
        }
    }

    @Override // java.util.concurrent.FutureTask, java.util.concurrent.Future
    public ClientDataType get() throws InterruptedException, ExecutionException {
        if (!isDone()) {
            ensureNotOnMainThread();
        }
        return (ClientDataType) super.get();
    }

    @Override // java.util.concurrent.FutureTask, java.util.concurrent.Future
    public ClientDataType get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        if (!isDone()) {
            ensureNotOnMainThread();
        }
        return (ClientDataType) super.get(j, timeUnit);
    }

    private void ensureNotOnMainThread() {
        Looper myLooper = Looper.myLooper();
        if (myLooper != null && myLooper == Looper.getMainLooper()) {
            Log.e(TAG, "calling this from your main thread can lead to deadlock and/or ANRs", new IllegalStateException("ClientFuture#calling this from your main thread can lead to deadlock"));
        }
    }
}
